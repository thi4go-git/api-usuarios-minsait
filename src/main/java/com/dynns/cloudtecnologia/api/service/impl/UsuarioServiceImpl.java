package com.dynns.cloudtecnologia.api.service.impl;

import com.dynns.cloudtecnologia.api.exception.GeralException;
import com.dynns.cloudtecnologia.api.model.entity.Usuario;
import com.dynns.cloudtecnologia.api.model.repository.UsuarioRepository;
import com.dynns.cloudtecnologia.api.rest.dto.UsuarioNewDTO;
import com.dynns.cloudtecnologia.api.rest.dto.UsuarioUpdateDTO;
import com.dynns.cloudtecnologia.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    private static final String USER_NOTFOUND = "Nenhum usuário encontrado para o ID: ";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Usuario save(UsuarioNewDTO dto) {
        Usuario newUser = Usuario.builder()
                .nome(dto.getNome().trim())
                .email(dto.getEmail().trim())
                .build();
        return usuarioRepository.save(newUser);
    }

    @Override
    public Page<Usuario> findAll(int page, int size, Integer id, String nome, String email) {
        Usuario userFiltro = Usuario.builder()
                .id(id)
                .nome(nome)
                .email(email)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Usuario> example = Example.of(userFiltro, matcher);

        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.Direction.ASC, "id");

        return usuarioRepository.findAll(example, pageRequest);
    }

    @Override
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOTFOUND + id));
    }

    @Override
    @Transactional
    public Usuario update(Integer id, UsuarioUpdateDTO dto) {
        Optional<Usuario> user = usuarioRepository.findByEmailAndDifferentId(dto.getEmail(), id);
        if (user.isPresent()) {
            throw new GeralException("Não foi possível atualizar: O email '" +
                    dto.getEmail() + "' já está sendo usado em outro cadastro!");
        }

        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOTFOUND + id));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Usuario userDelete = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOTFOUND + id));
        usuarioRepository.delete(userDelete);
    }
}

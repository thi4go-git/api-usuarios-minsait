package com.dynns.cloudtecnologia.api.service;

import com.dynns.cloudtecnologia.api.model.entity.Usuario;
import com.dynns.cloudtecnologia.api.rest.dto.UsuarioNewDTO;
import com.dynns.cloudtecnologia.api.rest.dto.UsuarioUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByEmail(String email);

    Usuario save(UsuarioNewDTO dto);

    Page<Usuario> findAll(int page, int size, Integer id, String nome, String email);

    Usuario findById(Integer id);

    Usuario update(Integer id, UsuarioUpdateDTO dto);

    void delete(Integer id);
}

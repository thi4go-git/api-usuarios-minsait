package com.dynns.cloudtecnologia.api.rest.mapper;

import com.dynns.cloudtecnologia.api.model.entity.Usuario;
import com.dynns.cloudtecnologia.api.rest.dto.UsuarioResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioResponseDTO usuarioToUsuarioResponseDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public List<UsuarioResponseDTO> listUsuarioToListUsuarioResponseDTO(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::usuarioToUsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<UsuarioResponseDTO> pageUsuarioToPageUsuarioResponseDTO(Page<Usuario> pageUsuario) {
        List<UsuarioResponseDTO> usuarios = pageUsuario.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(usuarios, pageUsuario.getPageable(), pageUsuario.getTotalElements());
    }
}

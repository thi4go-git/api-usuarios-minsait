package com.dynns.cloudtecnologia.api.rest.controller;

import com.dynns.cloudtecnologia.api.rest.dto.UsuarioNewDTO;
import com.dynns.cloudtecnologia.api.rest.dto.UsuarioUpdateDTO;

public class UsuariosSource {
    private UsuariosSource(){}

    private static final String EMAIL_USER_2 = "email2@usuario2.com.br";

    public static UsuarioNewDTO getUsuarioDadosVazios(){
        return UsuarioNewDTO
                .builder()
                .nome("")
                .email("")
                .build();
    }
    public static UsuarioNewDTO getUsuarioDadosInvalidos(){
        return UsuarioNewDTO
                .builder()
                .nome("as")
                .email("asd")
                .build();
    }
    public static UsuarioNewDTO getUsuarioEmailInvalido(){
        return UsuarioNewDTO
                .builder()
                .nome("Usuário TESTE")
                .email("email@")
                .build();
    }
    public static UsuarioNewDTO getUsuarioValido(){
        return UsuarioNewDTO
                .builder()
                .nome("Usuário Dados OK")
                .email("email@usuario.com.br")
                .build();
    }
    public static UsuarioNewDTO getUsuarioValido2(){
        return UsuarioNewDTO
                .builder()
                .nome("Usuário Dados OK 2")
                .email(EMAIL_USER_2)
                .build();
    }
    public static UsuarioUpdateDTO getUsuarioUpdateDTO(){
        return  UsuarioUpdateDTO.builder()
                .nome("Usuário 1 UPDATE")
                .email("usuario1@mail.com.br")
                .build();
    }
    public static UsuarioUpdateDTO getUsuarioUpdateEmailExistenteDTO(){
        return  UsuarioUpdateDTO.builder()
                .nome("Usuário 1 UPDATE")
                .email(EMAIL_USER_2)
                .build();
    }
}

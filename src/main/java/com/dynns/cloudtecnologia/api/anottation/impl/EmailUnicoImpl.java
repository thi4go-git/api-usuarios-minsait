package com.dynns.cloudtecnologia.api.anottation.impl;

import com.dynns.cloudtecnologia.api.anottation.EmailUnico;
import com.dynns.cloudtecnologia.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailUnicoImpl implements ConstraintValidator<EmailUnico, String> {
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return usuarioService.findByEmail(email).isEmpty();
    }
}

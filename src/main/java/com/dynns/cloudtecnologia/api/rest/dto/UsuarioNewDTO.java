package com.dynns.cloudtecnologia.api.rest.dto;

import com.dynns.cloudtecnologia.api.anottation.EmailUnico;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioNewDTO {

    @ApiModelProperty(value = "Nome Usuário", example = "Fulano da Silva Sauro")
    @NotBlank(message = "{campo.nome.obrigatorio}")
    private String nome;

    @ApiModelProperty(value = "E-mail Usuário", example = "mail@mail.com.br")
    @Email(message = "{campo.email.invalido}")
    @NotBlank(message = "{campo.email.obrigatorio}")
    @EmailUnico
    private String email;
}

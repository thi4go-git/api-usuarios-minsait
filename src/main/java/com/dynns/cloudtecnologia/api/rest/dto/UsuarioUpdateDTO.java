package com.dynns.cloudtecnologia.api.rest.dto;

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
public class UsuarioUpdateDTO {

    @ApiModelProperty(value = "Nome Atualizado Usuário", example = "Fulano da Silva Sauro update")
    @NotBlank(message = "{campo.nome.obrigatorio}")
    private String nome;

    @ApiModelProperty(value = "E-mail Atualizado Usuário", example = "updatemail@mail.com.br")
    @Email(message = "{campo.email.invalido}")
    @NotBlank(message = "{campo.email.obrigatorio}")
    private String email;
}

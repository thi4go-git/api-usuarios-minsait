package com.dynns.cloudtecnologia.api.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioUpdateDTO {

    @ApiModelProperty(value = "Nome Atualizado Usuário", example = "Fulano da Silva Sauro update")
    @NotBlank(message = "{campo.nome.obrigatorio}")
    @Size(min = 5, message = "O nome atualizado deve ter no mínimo 5 caracteres")
    private String nome;

    @ApiModelProperty(value = "E-mail Atualizado Usuário", example = "updatemail@mail.com.br")
    @Email(message = "{campo.email.invalido}")
    @NotBlank(message = "{campo.email.obrigatorio}")
    @Size(min = 5, message = "O email atualizado deve ter no mínimo 5 caracteres")
    private String email;
}

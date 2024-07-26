package com.dynns.cloudtecnologia.api.rest.dto;

import com.dynns.cloudtecnologia.api.anottation.EmailUnico;
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
public class UsuarioNewDTO {

    @ApiModelProperty(value = "Nome Usuário", example = "Fulano da Silva Sauro")
    @NotBlank(message = "{campo.nome.obrigatorio}")
    @Size(min = 5, message = "O nome deve ter no mínimo 5 caracteres")
    private String nome;

    @ApiModelProperty(value = "E-mail Usuário", example = "mail@mail.com.br")
    @Email(message = "{campo.email.invalido}")
    @NotBlank(message = "{campo.email.obrigatorio}")
    @Size(min = 5, message = "O email deve ter no mínimo 5 caracteres")
    @EmailUnico
    private String email;
}

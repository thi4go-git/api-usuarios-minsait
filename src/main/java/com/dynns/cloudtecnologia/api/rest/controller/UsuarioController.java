package com.dynns.cloudtecnologia.api.rest.controller;

import com.dynns.cloudtecnologia.api.model.entity.Usuario;
import com.dynns.cloudtecnologia.api.rest.dto.UsuarioNewDTO;
import com.dynns.cloudtecnologia.api.rest.dto.UsuarioResponseDTO;
import com.dynns.cloudtecnologia.api.rest.dto.UsuarioUpdateDTO;
import com.dynns.cloudtecnologia.api.rest.mapper.UsuarioMapper;
import com.dynns.cloudtecnologia.api.service.UsuarioService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/users")
@Api("API - Usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioMapper usuarioMapper;

    private static final String ERRO_INTERNO = "Ocorreu um erro interno no Servidor!";
    private static final String ID_OBRIGATORIO = "O Campo id é Obrigatório!";
    private static final String MSG_NOTFOUND = "Usuário não localizado!";

    @PostMapping
    @ApiOperation("Criar Usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuário criado com sucesso!"),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<UsuarioResponseDTO> save(
            @ApiParam(value = "Dados do novo Usuário", required = true)
            @RequestBody @Valid UsuarioNewDTO dto
    ) {
        Usuario newUser = usuarioService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.usuarioToUsuarioResponseDTO(newUser));
    }

    @GetMapping
    @ApiOperation("Listar Usuários com paginação + opção de aplicar filtros ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dados carregados com Sucesso!"),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<Page<UsuarioResponseDTO>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
            @RequestParam(value = "id", required = false) final Integer id,
            @RequestParam(value = "nome", required = false) final String nome,
            @RequestParam(value = "email", required = false) final String email
    ) {
        Page<Usuario> pageUsuarios = usuarioService.findAll(page, size, id, nome, email);
        return ResponseEntity.ok().body(usuarioMapper.pageUsuarioToPageUsuarioResponseDTO(pageUsuarios));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter Usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário obtido com sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<UsuarioResponseDTO> findById(
            @ApiParam(value = "ID do Usuário", required = true)
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final Integer id
    ) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok().body(usuarioMapper.usuarioToUsuarioResponseDTO(usuario));
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar Usuário através do ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário Atualizado com Sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<UsuarioResponseDTO> update(
            @ApiParam(value = "ID do Usuário", required = true)
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final Integer id,
            @ApiParam(value = "Dados atualizados do Usuário", required = true)
            @RequestBody @Valid UsuarioUpdateDTO dtoUpdate
    ) {
        Usuario userUpdate = usuarioService.update(id, dtoUpdate);
        return ResponseEntity.ok().body(usuarioMapper.usuarioToUsuarioResponseDTO(userUpdate));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deleta o Usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Usuário deletado com sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<Void> delete(
            @ApiParam(value = "ID do Usuário", required = true)
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final Integer id
    ) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

package com.crud.usuarios.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.usuarios.advice.BusinessException;
import com.crud.usuarios.advice.UsuarioNotFoundException;
import com.crud.usuarios.model.dto.LoginDto;
import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.dto.UsuarioDto;
import com.crud.usuarios.model.entities.Usuario;
import com.crud.usuarios.service.Usuario.UsuarioService;
import com.crud.usuarios.utilities.UsuarioMapper;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;
        @Autowired
    private UsuarioMapper usuarioMapper;

    //---------MÉTODOS GET---------//
    
    //Obtener lista completa de usuarios
    // @GetMapping
    // public List<UsuarioDto> getAllUsuarios(){
    //     log.info("GET /usuarios");
    //     log.info("Retornando lista de usuarios");
    //     return usuarioService.getAllUsuarios();
    // }

    @GetMapping
    public CollectionModel<EntityModel<UsuarioDto>> getAllUsuarios() {
        List<UsuarioDto> usuarios = usuarioService.getAllUsuarios();
        log.info("GET /usuarios");
        log.info("Retornando todos los usuarios");
        List<EntityModel<UsuarioDto>> usuariosResources = usuarios.stream()
            .map( usuario -> EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(usuario.getIdUsuario())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuarios());
        CollectionModel<EntityModel<UsuarioDto>> resources = CollectionModel.of(usuariosResources, linkTo.withRel("usuarios"));

        return resources;
    }
    //Obtener usuario por su id
    // @GetMapping("/{id}")
    // public ResponseEntity<Object> getUsuarioById(@PathVariable Integer id){
    //     log.info("GET /usuarios/" + id);
    //     log.info("Obteniendo usuario por id " + id);
    //     var response = usuarioService.getUsuarioById(id);
    //     if (response.isEmpty()) {
    //         log.error("No se encontro usuario con id " + id);
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"El usuario ingresado no existe."));
    //     }
    //     log.info("Usuario encontrado con éxito. Id: " + id);
    //     return ResponseEntity.ok(response);
    // }
    @GetMapping("/{id}")
    public EntityModel<UsuarioDto> getUsuarioById(@PathVariable Integer id){
        log.info("GET /usuarios/" + id);
        log.info("Obteniendo usuario por id " + id);
        var usuario = usuarioService.getUsuarioById(id);

        if (usuario.isPresent()) {
            log.info("Usuario encontrado. Id " + id);
            var usuarioDto = usuarioMapper.convertirADTO(usuario.get());
            return EntityModel.of(usuarioDto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuarios()).withRel("all-usuarios"));
        } else {
            log.error("Usuario no encontrado. Id " + id);
            throw new UsuarioNotFoundException(String.valueOf(id));
        }
    }

    //---------MÉTODOS POST---------//
    //Crear usuario
    // @PostMapping
    // public ResponseEntity<Object> createUsuario(@RequestBody @Valid UsuarioDto usuario){
    //     log.info("POST /usuarios/createUsuario");
    //     log.info("Creando usuario...");

    //     var response = usuarioService.createUsuario(usuario);

    //     log.info(response.getMessage());
    //     return ResponseEntity.status(HttpStatus.CREATED).body(response);
    // }

    @PostMapping
    public EntityModel<UsuarioDto> createUsuario(@RequestBody @Valid UsuarioDto usuarioDto){
        log.info("POST /usuarios/createUsuario");
        log.info("Creando usuario...");
        log.info("Validando email " + usuarioDto.getEmail());
        var validacionEmail = usuarioService.validarUsuarioPorEmail(usuarioDto.getEmail());
        if (!validacionEmail.getStatus()) {
            log.error(null);
            throw new BusinessException("EMAIL_EXISTE", validacionEmail.getMessage());
        }
        
        var response = usuarioService.createUsuario(usuarioDto);
        log.info("Usuario creado con exito. Id: " + response.getIdUsuario());
        return EntityModel.of(response,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).createUsuario(usuarioDto)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(response.getIdUsuario())).withRel("get-usuario-by-id"));

    }

    //Login usuario
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {
        log.info("POST /usuarios/login");
        log.info("Inicio login...");
        String email = loginDto.getEmail();
        String password = loginDto.getContrasena();

        //Validar el usuario y contraseña
        ResponseModel response = usuarioService.validarLogin(email, password);
        if (response.getStatus()) {
            log.info("Login realizado con exito. Email " + email);
            log.info("Fin login.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            log.error(response.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    //---------MÉTODOS PUT---------//
    //Actualizar usuario
    // @PutMapping("/{id}")
    // public ResponseEntity<Object> updateUsuario(@PathVariable Integer id, @RequestBody @Valid UsuarioDto usuarioDto){
    //     log.info("PUT /usuarios/"+id);
    //     log.info("Actualizando usuario con id " + id);
    //     var response = usuarioService.updateUsuario(id, usuarioDto);
    //     if (response == null) {
    //         log.error("No existe un usuario con id " + id);
    //         return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(false,"El usuario ingresado no existe."));
    //     }
    //     log.info("Usuario actualizado con éxito. Id " + id);
    //     return ResponseEntity.status(HttpStatus.OK).body(response);
    // }
    @PutMapping("/{id}")
    public EntityModel<UsuarioDto> updateUsuario(@PathVariable Integer id, @RequestBody @Valid UsuarioDto usuarioDto){
        log.info("PUT /usuarios/"+id);
        log.info("Actualizando usuario con id " + id);

        var response = usuarioService.updateUsuario(id, usuarioDto);
        if (response == null) {
            log.error("Usuario no encontrado. Id " + id);
            throw new UsuarioNotFoundException(String.valueOf(id));
        }
        log.info("Usuario creado con exito. Id: " + response.getIdUsuario());
        return EntityModel.of(response,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).createUsuario(usuarioDto)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(response.getIdUsuario())).withRel("get-usuario-by-id"));
    }

    //---------MÉTODOS DELETE---------//
    //Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable Integer id){
        log.info("DELETE /usuarios/"+id);
        log.info("Eliminando usuario con id " + id);
  
        var response = usuarioService.deleteUsuario(id);
        if (!response.getStatus()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        log.info("Usuario eliminado con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

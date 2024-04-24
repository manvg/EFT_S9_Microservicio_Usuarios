package com.crud.usuarios.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.usuarios.advice.BusinessException;
import com.crud.usuarios.advice.GeneralNotFoundException;
import com.crud.usuarios.model.dto.LoginDto;
import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.entities.Usuario;
import com.crud.usuarios.service.Usuario.UsuarioService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;

    //---------MÉTODOS GET---------//
    @GetMapping
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        log.info("GET /usuarios");
        log.info("Retornando todos los usuarios");
        List<EntityModel<Usuario>> usuariosResources = usuarios.stream()
            .map( usuario -> EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(usuario.getIdUsuario())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuarios());
        CollectionModel<EntityModel<Usuario>> resources = CollectionModel.of(usuariosResources, linkTo.withRel("usuarios"));

        return resources;
    }

    @GetMapping("/{id}")
    public EntityModel<Usuario> getUsuarioById(@PathVariable Integer id){
        log.info("GET /usuarios/" + id);
        log.info("Obteniendo usuario por id " + id);
        var usuario = usuarioService.getUsuarioById(id);

        if (!usuario.isEmpty()) {
            log.info("Usuario encontrado. Id " + id);
            return EntityModel.of(usuario.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuarios()).withRel("all-usuarios"));
        } else {
            log.error("Usuario no encontrado. Id " + id);
            throw new GeneralNotFoundException(String.valueOf(id));
        }
    }

    //---------MÉTODOS POST---------//
    @PostMapping
    public EntityModel<Usuario> createUsuario(@RequestBody @Valid Usuario usuario){
        log.info("POST /usuarios/createUsuario");
        log.info("Creando usuario...");
        log.info("Validando email " + usuario.getEmail());
        var validacionEmail = usuarioService.validarUsuarioPorEmail(usuario.getEmail());
        if (!validacionEmail.getStatus()) {
            log.error(null);
            throw new BusinessException("EMAIL_EXISTE", validacionEmail.getMessage());
        }
        
        var response = usuarioService.createUsuario(usuario);
        log.info("Usuario creado con exito. Id: " + response.getIdUsuario());
        return EntityModel.of(response,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).createUsuario(usuario)).withSelfRel(),
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
            return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(response,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).login(loginDto)).withSelfRel())); 
        }else{
            log.error(response.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(EntityModel.of(response,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).login(loginDto)).withSelfRel())); 
        }
    }

    //---------MÉTODOS PUT---------//
    //Actualizar usuario
    @PutMapping("/{id}")
    public EntityModel<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody @Valid Usuario usuario){
        log.info("PUT /usuarios/"+id);
        log.info("Actualizando usuario con id " + id);

        var response = usuarioService.updateUsuario(id, usuario);
        if (response == null) {
            log.error("Usuario no encontrado. Id " + id);
            throw new GeneralNotFoundException(String.valueOf(id));
        }
        log.info("Usuario creado con exito. Id: " + response.getIdUsuario());
        return EntityModel.of(response,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).createUsuario(usuario)).withSelfRel(),
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
        return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(response,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteUsuario(id)).withSelfRel())); 
    }
}

package com.crud.usuarios.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.dto.UsuarioDto;
import com.crud.usuarios.service.UsuarioService;

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
    
    //Obtener lista completa de usuarios
    @GetMapping
    public List<UsuarioDto> getAllUsuarios(){
        log.info("GET /usuarios");
        log.info("Retornando lista de usuarios");
        return usuarioService.getAllUsuarios();
    }

    //Obtener usuario por su id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuarioById(@PathVariable Integer id){
        log.info("GET /usuarios/" + id);
        log.info("Obteniendo usuario por id " + id);
        var response = usuarioService.getUsuarioById(id);
        if (response.isEmpty()) {
            log.error("No se encontro usuario con id " + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"El usuario ingresado no existe."));
        }
        log.info("Usuario encontrado con éxito. Id: " + id);
        return ResponseEntity.ok(response);
    }

    //---------MÉTODOS POST---------//
    //Crear usuario
    @PostMapping
    public ResponseEntity<Object> createUsuario(@RequestBody @Valid UsuarioDto usuario){
        log.info("POST /usuarios/createUsuario");
        log.info("Creando usuario...");

        var response = usuarioService.createUsuario(usuario);

        log.info(response.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Login usuario
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UsuarioDto usuarioDto) {
        log.info("POST /usuarios/login");
        log.info("Inicio login...");
        String email = usuarioDto.getEmail();
        String password = usuarioDto.getContrasena();

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
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable Integer id, @RequestBody @Valid UsuarioDto usuarioDto){
        log.info("PUT /usuarios/"+id);
        log.info("Actualizando usuario con id " + id);
        var response = usuarioService.updateUsuario(id, usuarioDto);
        if (response == null) {
            log.error("No existe un usuario con id " + id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(false,"El usuario ingresado no existe."));
        }
        log.info("Usuario actualizado con éxito. Id " + id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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

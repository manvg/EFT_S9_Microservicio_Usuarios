package com.crud.usuarios.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
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

import com.crud.usuarios.model.ResponseModel;
import com.crud.usuarios.model.Usuario;
import com.crud.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios(){
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuarioById(@PathVariable Integer id){
        var response = usuarioService.getUsuarioById(id);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(false,"El usuario ingresado no existe."));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Object> createUsuario(@RequestBody Usuario usuario){
        var response = usuarioService.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario){
        var response = usuarioService.updateUsuario(id, usuario);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(false,"El usuario ingresado no existe."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable Integer id){
        if (usuarioService.getUsuarioById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(false,"El usuario ingresado no existe."));
        }
        //Eliminar usuario
        usuarioService.deleteUsuario(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true,"El usuario ha sido eliminado con éxito."));
    }
}

package com.crud.usuarios.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.crud.usuarios.model.dto.PerfilDto;
import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.service.Perfil.PerfilService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {
    private static final Logger log = LoggerFactory.getLogger(PerfilController.class);
    @Autowired
    private PerfilService perfilService;

    //---------MÉTODOS GET---------//
    
    //Obtener lista completa de perfiles
    @GetMapping
    public List<PerfilDto> getAllPerfils(){
        log.info("GET /perfiles");
        log.info("Retornando lista de perfiles");
        return perfilService.getAllPerfiles();
    }

    //Obtener perfil por su id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPerfilById(@PathVariable Integer id){
        log.info("GET /perfiles/" + id);
        log.info("Obteniendo perfil por id " + id);
        var response = perfilService.getPerfilById(id);
        if (response.isEmpty()) {
            log.error("No se encontro perfil con id " + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"El perfil ingresado no existe."));
        }
        log.info("Perfil encontrado con éxito. Id: " + id);
        return ResponseEntity.ok(response);
    }

    //---------MÉTODOS POST---------//
    //Crear perfil
    @PostMapping
    public ResponseEntity<Object> createPerfil(@RequestBody @Valid PerfilDto perfil){
        log.info("POST /perfiles/createPerfil");
        log.info("Creando perfil...");

        var response = perfilService.createPerfil(perfil);
        if (!response.getStatus()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        log.info(response.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //---------MÉTODOS PUT---------//
    //Actualizar perfil
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePerfil(@PathVariable Integer id, @RequestBody @Valid PerfilDto perfilDto){
        log.info("PUT /perfiles/"+id);
        log.info("Actualizando perfil con id " + id);
        var response = perfilService.updatePerfil(id, perfilDto);
        if (!response.getStatus()) {
            log.error(response.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        log.info("Perfil actualizado con éxito. Id " + id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //---------MÉTODOS DELETE---------//
    //Eliminar perfil
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerfil(@PathVariable Integer id){
        log.info("DELETE /perfiles/"+id);
        log.info("Eliminando perfil con id " + id);
  
        var response = perfilService.deletePerfil(id);
        if (!response.getStatus()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        log.info("Perfil eliminado con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

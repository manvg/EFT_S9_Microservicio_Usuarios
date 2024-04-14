package com.crud.usuarios.service;

import java.util.List;
import java.util.Optional;

import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.entities.Usuario;

public interface UsuarioService {
    List<Usuario> getAllUsuarios();
    Optional<Usuario> getUsuarioById(Integer id);
    Usuario createUsuario(Usuario usuario);
    Usuario updateUsuario(Integer id, Usuario usuario);
    void deleteUsuario(Integer id);
    ResponseModel validarLogin(String email, String contrasena);
}

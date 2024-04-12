package com.crud.usuarios.service;

import java.util.List;
import java.util.Optional;

import com.crud.usuarios.model.Usuario;

public interface UsuarioService {
    List<Usuario> getAllUsuarios();
    Optional<Usuario> getUsuarioById(Integer id);
    Usuario createUsuario(Usuario usuario);
}

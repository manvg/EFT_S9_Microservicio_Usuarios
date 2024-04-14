package com.crud.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.usuarios.model.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByemail(String email);
}

package com.crud.usuarios.repository.Perfil;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.usuarios.model.entities.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer>{
    Optional<Perfil> findByNombre(String nombre);
}

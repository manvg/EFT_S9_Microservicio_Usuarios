package com.crud.usuarios.service.Perfil;

import java.util.List;
import java.util.Optional;

import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.entities.Perfil;

public interface PerfilService {
    List<Perfil> getAllPerfiles();
    Optional<Perfil> getPerfilById(Integer id);
    ResponseModel createPerfil(Perfil perfil);
    ResponseModel updatePerfil(Integer id,Perfil perfil);
    ResponseModel deletePerfil(Integer id);
}

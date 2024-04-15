package com.crud.usuarios.service.Perfil;

import java.util.List;
import java.util.Optional;

import com.crud.usuarios.model.dto.PerfilDto;
import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.entities.Perfil;

public interface PerfilService {
    List<PerfilDto> getAllPerfiles();
    Optional<Perfil> getPerfilById(Integer id);
    ResponseModel createPerfil(PerfilDto perfilDto);
    ResponseModel updatePerfil(Integer id,PerfilDto perfilDto);
    ResponseModel deletePerfil(Integer id);
}

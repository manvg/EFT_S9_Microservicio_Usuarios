package com.crud.usuarios.service.Perfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.entities.Perfil;
import com.crud.usuarios.repository.Perfil.PerfilRepository;

@Service
public class PerfilServiceImpl implements PerfilService{
    @Autowired
    PerfilRepository perfilRepository;

    //---------GET---------//
    public List<Perfil> getAllPerfiles(){
        return perfilRepository.findAll();
    }

    @Override
    public Optional<Perfil> getPerfilById(Integer id){
        return perfilRepository.findById(id);
    }

    //---------POST---------//
    @Override
    public ResponseModel createPerfil(Perfil perfil){
        var existeNombrePerfil = perfilRepository.findByNombre(perfil.getNombre());
        if (!existeNombrePerfil.isEmpty()) {
            return new ResponseModel(false, "El nombre del perfil ya existe");
        }
        var resultado = perfilRepository.save(perfil);
        return new ResponseModel(true, "Perfil creado con éxito. Id: " + resultado.getIdPerfil());
    }

    //---------PUT---------//
    @Override
    public ResponseModel updatePerfil(Integer id, Perfil objPerfil){
        //Validar que el nombre del perfil no exista
        var existeNombrePerfil = perfilRepository.findByNombre(objPerfil.getNombre());
        if (!existeNombrePerfil.isEmpty()) {
            return new ResponseModel(false, "El nombre del perfil ya existe");
        }
        //Valida existencia id perfil
        var perfilExiste = perfilRepository.findById(id);
        if (!perfilExiste.isEmpty()) {
            Perfil perfil = perfilExiste.get();
            perfil.setNombre(objPerfil.getNombre());
            perfil.setIdPerfil(id);
            //Actualizar perfil
            var resultado = perfilRepository.save(perfil);
            return new ResponseModel(true, "Perfil actualizado con éxito. Id: " + resultado.getIdPerfil());
        }else{
            return new ResponseModel(false, "El perfil ingresado no existe. Id: " + id);
        }
    }

    //---------DELETE---------//
    @Override
    public ResponseModel deletePerfil(Integer id){
        if (perfilRepository.existsById(id)) {
            perfilRepository.deleteById(id);
            return new ResponseModel(true, "Perfil eliminado con éxito");
        }else{
            return new ResponseModel(false, "El perfil ingresado no existe");
        }
    }
}

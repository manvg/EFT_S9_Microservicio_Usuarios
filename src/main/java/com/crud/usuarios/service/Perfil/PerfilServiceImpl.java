package com.crud.usuarios.service.Perfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crud.usuarios.model.dto.PerfilDto;
import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.entities.Perfil;
import com.crud.usuarios.repository.Perfil.PerfilRepository;
import com.crud.usuarios.utilities.PerfilMapper;

@Service
public class PerfilServiceImpl implements PerfilService{
    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    private PerfilMapper perfilMapper;

    //---------GET---------//
    public List<PerfilDto> getAllPerfiles(){
        List<Perfil> perfiles = perfilRepository.findAll();
        return perfiles.stream().map(perfilMapper::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public Optional<Perfil> getPerfilById(Integer id){
        return perfilRepository.findById(id);
    }

    //---------POST---------//
    @Override
    public ResponseModel createPerfil(PerfilDto perfilDto){
        var existeNombrePerfil = perfilRepository.findByNombre(perfilDto.getNombre());
        if (!existeNombrePerfil.isEmpty()) {
            return new ResponseModel(false, "El nombre del perfil ya existe");
        }
        Perfil perfil = perfilMapper.convertirAEntity(perfilDto);//Mapeo
        var resultado = perfilRepository.save(perfil);
        return new ResponseModel(true, "Perfil creado con éxito. Id: " + resultado.getIdPerfil());
    }

    //---------PUT---------//
    @Override
    public ResponseModel updatePerfil(Integer id, PerfilDto perfilDto){
        //Validar que el nombre del perfil no exista
        var existeNombrePerfil = perfilRepository.findByNombre(perfilDto.getNombre());
        if (!existeNombrePerfil.isEmpty()) {
            return new ResponseModel(false, "El nombre del perfil ya existe");
        }
        //Valida existencia id perfil
        var perfilExiste = perfilRepository.findById(id);
        if (!perfilExiste.isEmpty()) {
            Perfil perfil = perfilExiste.get();
            perfil.setNombre(perfilDto.getNombre());
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

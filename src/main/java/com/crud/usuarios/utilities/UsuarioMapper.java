package com.crud.usuarios.utilities;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.crud.usuarios.model.dto.UsuarioDto;
import com.crud.usuarios.model.entities.Usuario;

@Component
public class UsuarioMapper {
    private final ModelMapper modelMapper;

    public UsuarioMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public UsuarioDto convertirADTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    public Usuario convertirAEntity(UsuarioDto usuarioDto){
        return modelMapper.map(usuarioDto, Usuario.class);
    }
}

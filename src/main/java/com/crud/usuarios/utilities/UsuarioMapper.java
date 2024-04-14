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
    
    public UsuarioDto convertirADTO(Usuario atencionMedica) {
        return modelMapper.map(atencionMedica, UsuarioDto.class);
    }

    public Usuario convertirAEntity(UsuarioDto atencionMedicaDto){
        return modelMapper.map(atencionMedicaDto, Usuario.class);
    }
}

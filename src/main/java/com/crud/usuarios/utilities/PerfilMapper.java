package com.crud.usuarios.utilities;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.crud.usuarios.model.dto.PerfilDto;
import com.crud.usuarios.model.entities.Perfil;

@Component
public class PerfilMapper {
    private final ModelMapper modelMapper;

    public PerfilMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public PerfilDto convertirADTO(Perfil atencionMedica) {
        return modelMapper.map(atencionMedica, PerfilDto.class);
    }

    public Perfil convertirAEntity(PerfilDto atencionMedicaDto){
        return modelMapper.map(atencionMedicaDto, Perfil.class);
    }
}

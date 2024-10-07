package com.example.parcial_mutantes.mapper;

import com.example.parcial_mutantes.dto.PersonaDTO;
import com.example.parcial_mutantes.entity.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PersonaMapper {

    PersonaMapper instancia = Mappers.getMapper(PersonaMapper.class);

    @Mapping(target = "fullAdn", expression = "java(mapFullAdn(personaDTO.getAdn()))")
    Persona personaDTOToPersona(PersonaDTO personaDTO);

    // MapStruct accederá a los parámetros sin la necesidad de usar el objeto completo 'personaDTO'
    default String mapFullAdn(List<String> adn) {
        return adn != null ? adn.stream().collect(Collectors.joining(" , ")) : null;
    }
}

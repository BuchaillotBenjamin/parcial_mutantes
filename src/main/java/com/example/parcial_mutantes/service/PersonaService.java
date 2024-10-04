package com.example.parcial_mutantes.service;


import com.example.parcial_mutantes.dto.PersonaDTO;
import com.example.parcial_mutantes.entity.Persona;
import com.example.parcial_mutantes.repository.PersonaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public void ResgistrarHumano(PersonaDTO persona) throws Exception{

        persona.setEsMutante(PersonaDTO.isMutant(persona.getAdn()));

        Persona personaPersistir = Persona.builder()
                .adn(persona.getAdn())
                .esMutante(persona.isEsMutante())
                .build();

        //envia a la bd
        personaRepository.save(personaPersistir);

    }

    @Transactional
    public List<Persona> findAll() throws Exception{
        try {
            List<Persona> personas = personaRepository.findAll();
            return personas;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Transactional
    public Persona findById(Long id) throws Exception{
        try {
            Optional<Persona> persona = personaRepository.findById(id);
            return persona.get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



}

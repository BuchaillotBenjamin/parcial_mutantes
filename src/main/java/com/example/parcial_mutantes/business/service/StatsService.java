package com.example.parcial_mutantes.business.service;

import com.example.parcial_mutantes.domain.dto.StatsDTO;
import com.example.parcial_mutantes.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    PersonaRepository personaRepository;


    public StatsDTO getStats() throws Exception{

        // Contar mutantes
        long countMutantes = personaRepository.countByEsMutante(true);

        // Contar el total de humanos (mutantes y no mutantes)
        long totalHumanos = personaRepository.count();

        // Calcular el porcentaje de mutantes
        double porcentajeMutantes = totalHumanos > 0 ? ((double) countMutantes / totalHumanos) * 100 : 0;

        StatsDTO statsDTO = StatsDTO.builder()
                .count_mutant_dna(countMutantes)
                .count_human_dna(totalHumanos)
                .ratio(porcentajeMutantes)
                .build();

        return (statsDTO);

    }

}

package com.example.parcial_mutantes;

import com.example.parcial_mutantes.business.service.StatsService;
import com.example.parcial_mutantes.domain.dto.StatsDTO;
import com.example.parcial_mutantes.domain.entity.Persona; // Asegúrate de importar tu entidad Persona
import com.example.parcial_mutantes.repositories.PersonaRepository; // Asegúrate de importar tu repositorio
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Asegura que todas las pruebas se ejecuten en una transacción
public class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired // Inyecta el repositorio para poder insertar datos
    private PersonaRepository personaRepository;

    @Mock
    private StatsService statsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Agregar datos de prueba antes de cada prueba
        personaRepository.deleteAll(); // Limpia la base de datos antes de agregar nuevos datos

        // Aquí puedes agregar datos de prueba adicionales si es necesario
        Persona persona1 = new Persona(); // Crea la primera persona
        persona1.setFullAdn("ATGC"); // Asigna el ADN correspondiente
        persona1.setEsMutante(true); // Indica si es mutante

        Persona persona2 = new Persona(); // Crea la segunda persona
        persona2.setFullAdn("AAGC");
        persona2.setEsMutante(false);

        // Guarda las personas en la base de datos
        personaRepository.save(persona1);
        personaRepository.save(persona2);
    }

    @Test
    public void testGetStats() throws Exception {
        // Datos de prueba
        StatsDTO statsDTO = new StatsDTO();
        statsDTO.setCount_mutant_dna(1L);
        statsDTO.setCount_human_dna(2L);
        statsDTO.setRatio(50.0);

        // Configurar el comportamiento simulado del servicio
        when(statsService.getStats()).thenReturn(statsDTO);

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/stats").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(1))
                .andExpect(jsonPath("$.count_human_dna").value(2))
                .andExpect(jsonPath("$.ratio").value(50.0));
    }

}

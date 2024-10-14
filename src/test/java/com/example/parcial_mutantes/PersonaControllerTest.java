package com.example.parcial_mutantes;

import com.example.parcial_mutantes.domain.dto.PersonaDTO;
import com.example.parcial_mutantes.business.service.MutantesService;
import com.example.parcial_mutantes.business.service.PersonaService;
import com.example.parcial_mutantes.presentation.controller.PersonaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class) // Extender para usar Mockito
@WebMvcTest(PersonaController.class)
public class PersonaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonaService personaService;

    private PersonaDTO personaDTO;

    @BeforeEach
    public void setup() {
        personaDTO = new PersonaDTO();
        personaDTO.setAdn(List.of("ATCG", "ATCG", "ATCG", "ATCG"));
    }

    @Test
    public void testRegistroMutanteFound() throws Exception {
        try (MockedStatic<MutantesService> mockedStatic = Mockito.mockStatic(MutantesService.class)) {
            mockedStatic.when(() -> MutantesService.adnValidacion(any())).thenReturn(true);
            mockedStatic.when(() -> MutantesService.isMutant(any())).thenReturn(true);

            mockMvc.perform(post("/personas/mutant")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"adn\":[\"ATCG\",\"ATCG\",\"ATCG\",\"ATCG\"]}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Mutante encontrado"));
        }
    }

    @Test
    public void testRegistroMutanteNotFound() throws Exception {
        try (MockedStatic<MutantesService> mockedStatic = Mockito.mockStatic(MutantesService.class)) {
            mockedStatic.when(() -> MutantesService.adnValidacion(any())).thenReturn(true);
            mockedStatic.when(() -> MutantesService.isMutant(any())).thenReturn(false);

            mockMvc.perform(post("/personas/mutant")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"adn\":[\"ATCG\",\"ATCG\",\"ATCG\",\"ATCG\"]}"))
                    .andExpect(status().isForbidden())
                    .andExpect(content().string("Mutante no encontrado"));
        }
    }

    @Test
    public void testRegistroInvalidAdn() throws Exception {
        try (MockedStatic<MutantesService> mockedStatic = Mockito.mockStatic(MutantesService.class)) {
            mockedStatic.when(() -> MutantesService.adnValidacion(any())).thenReturn(false);

            mockMvc.perform(post("/personas/mutant")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"adn\":[\"INVALID_ADN\"]}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("ADN no valido"));
        }
    }

    @Test
    public void testGetAllPersonas() throws Exception {
        when(personaService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/personas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetOnePersonaNotFound() throws Exception {
        when(personaService.findById(1L)).thenThrow(new RuntimeException("No encontrado"));

        mockMvc.perform(get("/personas/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No encontrado"));
    }
}

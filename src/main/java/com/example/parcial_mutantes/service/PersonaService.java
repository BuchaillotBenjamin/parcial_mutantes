package com.example.parcial_mutantes.service;


import com.example.parcial_mutantes.dto.PersonaDTO;
import com.example.parcial_mutantes.entity.Persona;
import com.example.parcial_mutantes.mapper.PersonaMapper;
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
    public void ResgistrarHumano(PersonaDTO persona) throws Exception {

        persona.setEsMutante(isMutant(persona.getAdn()));

        Persona personaPersistir = PersonaMapper.instancia.personaDTOToPersona(persona);

        //envia a la bd
        personaRepository.save(personaPersistir);

    }

    @Transactional
    public List<Persona> findAll() throws Exception {
        try {
            List<Persona> personas = personaRepository.findAll();
            return personas;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @Transactional
    public Persona findById(Long id) throws Exception {
        try {
            Optional<Persona> persona = personaRepository.findById(id);
            return persona.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static boolean adnValidacion(List<String> adn) throws Exception {

        if (adn == null || adn.isEmpty()) {
            return false;
        }
        // Verifica alto
        if (adn.size() < 4 || adn.size() > 10 ) {
            return false;
        }

        for (String cadena : adn) {
            //verifica ancho
            if (cadena.length() != adn.size()) {
                return false;
            }

            // verfica las letras
            for (char c : cadena.toCharArray()) {
                char cMayuscula = Character.toUpperCase(c);
                if (cMayuscula != 'G' && cMayuscula != 'C' && cMayuscula != 'A' && cMayuscula != 'T') {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isMutant(List<String> adn) throws Exception {
        final int n = adn.size(); // Tamaño de la lista
        final int secuenciasMin = 2; // Mínimo de secuencias
        final int secuenciaMinima = 4; // Mínimo de letras

        int secuencias = 0;

        // Verifica secuencias horizontales
        for (int i = 0; i < n; i++) {
            int contador = 1; // Reiniciar contador para cada fila
            for (int j = 0; j < n - 1; j++) {
                if (Character.toUpperCase(adn.get(i).charAt(j)) == Character.toUpperCase(adn.get(i).charAt(j + 1))) {
                    contador++;
                    if (contador == secuenciaMinima) { // Solo incrementa si se alcanza la secuencia mínima
                        secuencias++;
                    }
                } else {
                    contador = 1; // Reinicia el contador
                }
            }
        }

        // Verifica secuencias verticales
        for (int j = 0; j < n; j++) {
            int contador = 1; // Reiniciar contador para cada columna
            for (int i = 0; i < n - 1; i++) {
                if (Character.toUpperCase(adn.get(i).charAt(j)) == Character.toUpperCase(adn.get(i + 1).charAt(j))) {
                    contador++;
                    if (contador == secuenciaMinima) { // Solo incrementa si se alcanza la secuencia mínima
                        secuencias++;
                    }
                } else {
                    contador = 1; // Reinicia el contador
                }
            }
        }

        // Verifica secuencias diagonales descendentes
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                int contador = 1; // Reiniciar contador para cada diagonal
                while (i + contador < n && j + contador < n &&
                        Character.toUpperCase(adn.get(i).charAt(j)) == Character.toUpperCase(adn.get(i + contador).charAt(j + contador))) {
                    contador++;
                    if (contador == secuenciaMinima) { // Solo incrementa si se alcanza la secuencia mínima
                        secuencias++;
                    }
                }
            }
        }

        // Verifica secuencias diagonales ascendentes
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                int contador = 1; // Reiniciar contador para cada diagonal
                while (i - contador >= 0 && j + contador < n &&
                        Character.toUpperCase(adn.get(i).charAt(j)) == Character.toUpperCase(adn.get(i - contador).charAt(j + contador))) {
                    contador++;
                    if (contador == secuenciaMinima) { // Solo incrementa si se alcanza la secuencia mínima
                        secuencias++;
                    }
                }
            }
        }

        return secuencias >= secuenciasMin;
    }




}

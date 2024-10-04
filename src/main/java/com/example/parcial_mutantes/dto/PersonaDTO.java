package com.example.parcial_mutantes.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PersonaDTO {

    private List<String> adn;
    private boolean esMutante;


    public static boolean isMutant(List<String> adn) throws Exception {
        final int n = adn.size(); // Tamaño de la lista
        final int secuenciasMin = 2; // Mínimo de secuencias
        final int secuenciaMinima = 4; // Mínimo de letras

        int secuencias = 0;

        // Verifica secuencias horizontales
        for (int i = 0; i < n; i++) {
            int contador = 1; // Reiniciar contador para cada fila
            for (int j = 0; j < n - 1; j++) {
                if (adn.get(i).charAt(j) == adn.get(i).charAt(j + 1)) {
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
                if (adn.get(i).charAt(j) == adn.get(i + 1).charAt(j)) {
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
                        adn.get(i).charAt(j) == adn.get(i + contador).charAt(j + contador)) {
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
                        adn.get(i).charAt(j) == adn.get(i - contador).charAt(j + contador)) {
                    contador++;
                    if (contador == secuenciaMinima) { // Solo incrementa si se alcanza la secuencia mínima
                        secuencias++;
                    }
                }
            }
        }

        return secuencias >= secuenciasMin;
    }



    public static boolean adnValidacion(List<String> adn) throws Exception {
        // Verifica alto
        if (adn.size() != 6){
            return false;
        }

        for (String cadena : adn) {
            //verifica ancho
            if (cadena.length() != 6) {
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





}
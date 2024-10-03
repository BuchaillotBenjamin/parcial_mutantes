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

    private String[] adn;
    private boolean esMutante;

    public boolean isMutant(String[] adn) {
        final int n = 6; // Tamaño del arreglo
        final int secuenciasMin = 2; //minimo de secuencias para ser mutante

        int secuencias = 0;

        // Verificar secuencias horizontales, verticales y diagonales
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                if (j <= n - 4) { // Verifica secuencia horizontal
                    if (adn[i].charAt(j) == adn[i].charAt(j + 1) &&
                            adn[i].charAt(j) == adn[i].charAt(j + 2) &&
                            adn[i].charAt(j) == adn[i].charAt(j + 3)) {
                        secuencias++;
                        if (secuencias >= secuenciasMin) return true;
                    }
                }

                if (i <= n - 4) { // Verifica secuencia vertical
                    if (adn[i].charAt(j) == adn[i + 1].charAt(j) &&
                            adn[i].charAt(j) == adn[i + 2].charAt(j) &&
                            adn[i].charAt(j) == adn[i + 3].charAt(j)) {
                        secuencias++;
                        if (secuencias >= secuenciasMin) return true;
                    }
                }

                if (i <= n - 4 && j <= n - 4) { // Verifica secuencia diagonal descendente
                    if (adn[i].charAt(j) == adn[i + 1].charAt(j + 1) &&
                            adn[i].charAt(j) == adn[i + 2].charAt(j + 2) &&
                            adn[i].charAt(j) == adn[i + 3].charAt(j + 3)) {
                        secuencias++;
                        if (secuencias >= secuenciasMin) return true;
                    }
                }

                if (i <= n - 4 && j >= 3) { // Verifica secuencia diagonal ascendente
                    if (adn[i].charAt(j) == adn[i + 1].charAt(j - 1) &&
                            adn[i].charAt(j) == adn[i + 2].charAt(j - 2) &&
                            adn[i].charAt(j) == adn[i + 3].charAt(j - 3)) {
                        secuencias++;
                        if (secuencias >= secuenciasMin) return true;
                    }
                }
            }
        }

        //Falso si no encuentra las secuencias
        return false;
    }


    public boolean adnValidacion(String[] adn) {
        // Verificar tamaño del arreglo
        if (adn.length != 6 || adn[0].length() != 6) {
            return false;
        }

        // Verificar las letras
        for (int i = 0; i < adn.length; i++) {
            for (int j = 0; j < adn[i].length(); j++) {
                char c = adn[i].charAt(j);
                if (c != 'G' && c != 'C' && c != 'A' && c != 'T') {
                    return false;
                }
            }
        }

        return true;
    }

}
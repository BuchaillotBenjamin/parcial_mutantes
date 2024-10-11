package com.example.parcial_mutantes.business.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MutantesService {

    public static boolean adnValidacion(List<String> adn) throws Exception {

        if (adn == null || adn.isEmpty()) {
            return false;
        }
        // Verifica alto
        if (adn.size() < 4) {
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
        final int n = adn.size();
        final int secuenciasMin = 2;
        AtomicInteger secuencias = new AtomicInteger(0);

        for (int i = 0; i < n; i++) {
            adn.set(i, adn.get(i).toUpperCase());
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<Integer> horizontal = executor.submit(() -> verHorizontal(adn, n));
        if ((secuencias.get()+horizontal.get()) >= secuenciasMin){
            executor.shutdown();
            return true;
        }

        Future<Integer> vertical = executor.submit(() -> verVertical(adn, n));
        if ((secuencias.get()+vertical.get()) >= secuenciasMin){
            executor.shutdown();
            return true;
        }

        Future<Integer> diagonalDescendente = executor.submit(() -> verDiagonalDescendente(adn, n));
        if ((secuencias.get()+diagonalDescendente.get()) >= secuenciasMin){
            executor.shutdown();
            return true;
        }

        Future<Integer> diagonalAscendente = executor.submit(() -> verDiagonalAscendente(adn, n));
        if ((secuencias.get()+diagonalAscendente.get()) >= secuenciasMin){
            executor.shutdown();
            return true;
        }

        executor.shutdown();

        return false;
    }

    private static Integer verHorizontal(List<String> adn, Integer n) throws Exception {
        int secuencias = 0;

        // Verifica secuencias horizontales
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 3; j++) {
                if (adn.get(i).charAt(j + 1) == adn.get(i).charAt(j + 2)) {
                    if (adn.get(i).charAt(j + 1) == adn.get(i).charAt(j + 3) && adn.get(i).charAt(j + 1) == adn.get(i).charAt(j)) {
                        if (j != 0) {
                            if (adn.get(i).charAt(j) != adn.get(i).charAt(j - 1)) {
                                secuencias++;
                            }
                        } else {
                            secuencias++;
                        }
                        j += 2;
                    }
                } else {
                    j++;
                }
            }
        }
        return secuencias;
    }

    private static Integer verVertical(List<String> adn, Integer n) throws Exception {
        int secuencias = 0;

        // Verifica secuencias verticales
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n - 3; i++) {
                if (adn.get(i + 1).charAt(j) == adn.get(i + 2).charAt(j)) {
                    if (adn.get(i + 1).charAt(j) == adn.get(i + 3).charAt(j) && adn.get(i + 1).charAt(j) == adn.get(i).charAt(j)) {
                        if (i != 0) {
                            if (adn.get(i).charAt(j) != adn.get(i - 1).charAt(j)) {
                                secuencias++;
                            }
                        } else {
                            secuencias++;
                        }
                        i += 2; // Salta dos posiciones si se cuenta una secuencia
                    }
                } else {
                    i++; // Si no hay coincidencia, avanza a la siguiente posición
                }
            }
        }

        return secuencias;
    }

    private static Integer verDiagonalDescendente(List<String> adn, Integer n) throws Exception {
        int secuencias = 0;
        int contador;
        // Verifica secuencias diagonales descendentes
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                contador = 1; // Reiniciar contador para cada diagonal
                while (i + contador < n && j + contador < n &&
                        adn.get(i).charAt(j) == adn.get(i + contador).charAt(j + contador)) {
                    contador++;
                    if (contador == 4) { // Solo incrementa si se alcanza la secuencia mínima
                        secuencias++;
                    }
                }
            }
        }

        return secuencias;
    }

    private static Integer verDiagonalAscendente(List<String> adn, Integer n) throws Exception {
        int secuencias = 0;
        int contador;
        // Verifica secuencias diagonales ascendentes
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                contador = 1; // Reiniciar contador para cada diagonal
                while (i - contador >= 0 && j + contador < n &&
                        adn.get(i).charAt(j) == adn.get(i - contador).charAt(j + contador)) {
                    contador++;
                    if (contador == 4) { // Solo incrementa si se alcanza la secuencia mínima
                        secuencias++;
                    }
                }
            }
        }

        return secuencias;
    }


}

package com.example.parcial_mutantes.business.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MutantesService {

    public static boolean adnValidacion(List<String> adn) {
        Set<Character> letrasValidas = Set.of('G', 'C', 'A', 'T');

        if (adn == null || adn.isEmpty()) {
            return false;
        }

        int longitudEsperada = adn.size();

        // Verifica alto
        if (longitudEsperada < 4) {
            return false;
        }

        for (String cadena : adn) {
            //verifica ancho
            if (cadena.length() != longitudEsperada) {
                return false;
            }

            // verfica las letras
            for (char c : cadena.toCharArray()) {
                char cMayuscula = Character.toUpperCase(c);
                if (!letrasValidas.contains(cMayuscula)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isMutant(List<String> adn) throws Exception {
        final int n = adn.size();

        // Numero minimo para que sea mutante
        final int secuenciasMin = 2;

        // Usar AtomicInteger para llevar un conteo seguro de las secuencias encontradas entre hilos
        AtomicInteger secuencias = new AtomicInteger(0);

        // Cadenas a mayusculas
        for (int i = 0; i < n; i++) {
            adn.set(i, adn.get(i).toUpperCase());
        }

        // Crear un ExecutorService con un pool de 4 hilos para ejecutar tareas concurrentemente
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Enviar una tarea para verificar secuencias horizontales y obtener un Future
        Future<Integer> horizontal = executor.submit(() -> verHorizontal(adn, n));
        // Comprobar si las secuencias encontradas más las ya contabilizadas son suficientes
        if ((secuencias.get() + horizontal.get()) >= secuenciasMin) {
            executor.shutdown(); // Apagar el executor si se cumple la condición
            return true; // Retornar true indicando que se encontraron suficientes secuencias
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

        // Apagar el executor después de realizar todas las tareas
        executor.shutdown();

        // Retornar false si no es mutante
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

package com.example.parcial_mutantes.util;

import java.util.List;
import java.util.stream.Collectors;

public class AdnUtills {
    public static String mapFullAdn(List<String> adn) {
        return adn != null ? adn.stream().collect(Collectors.joining(", ")) : null;
    }
}

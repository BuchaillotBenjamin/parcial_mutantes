package com.example.parcial_mutantes.repositories;


import com.example.parcial_mutantes.domain.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    // Verifica si el adn ya ha sido ingresado
    boolean existsByFullAdn(String fullAdn);

    // Cuenta cuantos mutantes o no mutantes hay
    long countByEsMutante(boolean esMutante);

}



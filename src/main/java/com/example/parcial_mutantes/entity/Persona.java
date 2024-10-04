package com.example.parcial_mutantes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="persona")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @Column(name="ADN", columnDefinition = "VARCHAR(10)")
    private List<String> adn;

    @Column(name="Mutante")
    private boolean esMutante;

}

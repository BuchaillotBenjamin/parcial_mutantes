package com.example.parcial_mutantes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;

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
    @Column(name = "ID")
    private Long id;

    @Column(name="MUTANTE")
    private boolean esMutante;

    @Column(name="ADN-COMPLETO", columnDefinition = "TEXT")
    private String fullAdn;

    //@ElementCollection
    //@Column(name="ADN", columnDefinition = "VARCHAR(10)")
    @Transient
    private List<String> adn;


}
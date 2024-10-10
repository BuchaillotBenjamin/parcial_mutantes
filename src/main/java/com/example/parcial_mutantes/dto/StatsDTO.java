package com.example.parcial_mutantes.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsDTO {

    private Long count_mutant_dna;
    private Long count_human_dna;
    private Double ratio;




}

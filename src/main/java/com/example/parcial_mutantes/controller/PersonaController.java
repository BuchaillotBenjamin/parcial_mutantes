package com.example.parcial_mutantes.controller;

import com.example.parcial_mutantes.dto.PersonaDTO;
import com.example.parcial_mutantes.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(path = "/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @PostMapping("/mutant")
    public ResponseEntity<?> registro(@RequestBody PersonaDTO persona) {

        try {
            if (!PersonaService.adnValidacion(persona.getAdn())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ADN no valido");
            }

            personaService.ResgistrarHumano(persona);

            if(PersonaService.isMutant(persona.getAdn())){
                return ResponseEntity.status(HttpStatus.OK).body("Mutante encontrado");
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Mutante no encontrado");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error interno");
        }

    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(personaService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrados");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(personaService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

    }





}

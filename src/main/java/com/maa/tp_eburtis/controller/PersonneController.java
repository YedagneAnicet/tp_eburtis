package com.maa.tp_eburtis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.maa.tp_eburtis.model.Personne;
import com.maa.tp_eburtis.service.PersonneInterface;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonneController {

    @Autowired
    private PersonneInterface personneService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void configureObjectMapper() {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @GetMapping("/getAllPersonne")
    public Iterable<Personne> getPersonne(){
        return personneService.findAll();
    }

    @PostMapping("/addPersonne")
    public Personne savePersonne(@RequestBody Personne personne){
        return personneService.save(personne);
    }

    @DeleteMapping("/deletePersonne/{id}")
    public ResponseEntity<HttpStatus> deletePersonne(@PathVariable("id") Long id){
        try{
            personneService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updatePersonne/{id}")
    public ResponseEntity<Personne> updatePesonne(@PathVariable("id") Long id, @RequestBody Personne personne){
        try{
            Personne updatePersonne = personneService.update(id, personne);
            return new ResponseEntity<>(updatePersonne, HttpStatus.OK);
        }catch (EntityNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

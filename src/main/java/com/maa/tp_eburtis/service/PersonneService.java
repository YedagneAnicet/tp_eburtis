package com.maa.tp_eburtis.service;

import com.maa.tp_eburtis.model.Personne;
import com.maa.tp_eburtis.repository.PersonneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonneService implements PersonneInterface {
    @Autowired
    private PersonneRepository personneRepository;

    @Override
    public List<Personne> findAll(){
        return (List<Personne>) personneRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Personne personneSelect = findById(id);
        personneRepository.delete(personneSelect);
    }

    @Override
    public Personne save(Personne personne){
        if(personne == null || personne.getNom() == null || personne.getPrenoms() == null){
            throw new IllegalArgumentException("Les champs nom et prenoms sont obligatoires.");
        }
        return personneRepository.save(personne);
    }

    @Override
    public Personne update(Long id, Personne personne) {
        if(personne == null || personne.getNom() == null || personne.getPrenoms() == null){
            throw new IllegalArgumentException("Les champs nom et prenoms sont obligatoires.");
        }
        Personne personneSelect = findById(id);
        personneSelect.setNom(personne.getNom());
        personneSelect.setPrenoms(personne.getPrenoms());
        personneSelect.setAge(personne.getAge());

        return personneRepository.save(personneSelect);
    }

    @Override
    public Personne findById(Long id) {
        Optional<Personne> optionalPersonne= personneRepository.findById(id);
        if(optionalPersonne.isEmpty()) {
            throw new EntityNotFoundException("la personne avec l'id " + id + " est introuvable");
        }
        return optionalPersonne.get();
    }
}

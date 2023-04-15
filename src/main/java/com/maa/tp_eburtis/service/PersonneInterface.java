package com.maa.tp_eburtis.service;

import com.maa.tp_eburtis.model.Personne;

import java.util.List;

public interface PersonneInterface {
    Personne save(Personne personne);
    Personne update(Long id, Personne personne);
    Personne findById(Long id);
    List<Personne> findAll();
    void delete(Long id);

}

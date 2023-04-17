package com.maa.tp_eburtis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="departement")
public class Departement {

    @Id
    @Column(name = "code")
    private String code;
    @Column(name="designation")
    private String designation;
    @OneToMany(targetEntity = Personne.class, mappedBy = "departement", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Personne> personnes = new HashSet<>();

    public Departement(){
    }
    public Departement(String code, String designation, Set<Personne> personnes) {
        this.code = code;
        this.designation = designation;
        this.personnes = personnes;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Set<Personne> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(Set<Personne> personnes) {
        this.personnes = personnes;
    }
}

package com.maa.tp_eburtis.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="personne")
public class Personne implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="nom")
    private String nom;
    @Column(name="prenoms")
    private String prenoms;
    @Column(name="age")
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_code", referencedColumnName = "code")
    private Departement departement;

    public Personne() {}
    public Personne(Long id, String nom, String prenoms, int age, Departement departement) {
        this.id = id;
        this.nom = nom;
        this.prenoms = prenoms;
        this.age = age;
        this.departement = departement;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Departement getDepartement() {
        return departement;
    }
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}

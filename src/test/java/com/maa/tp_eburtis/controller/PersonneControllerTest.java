package com.maa.tp_eburtis.controller;

import com.maa.tp_eburtis.model.Departement;
import com.maa.tp_eburtis.model.Personne;
import com.maa.tp_eburtis.repository.PersonneRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Tag("PersonneControllerTest")
@DisplayName("Test unitaire du controlleur de la classe personne")
public class PersonneControllerTest {
    @Autowired
    private PersonneRepository personneRepository;
    @Autowired
    private MockMvc mvc;

    private JSONObject json;

    @BeforeAll
    @AfterAll
    public void clearDatabase(){
        this.personneRepository.deleteAll();
        this.json=null;
    }
    @Test
    @Order(value=1)
    @DisplayName("Test unitaire pour la création d'une personne")
    public void testCreatePersonne() throws Exception {
        // Création d'un objet JSON pour les données de la personne
        JSONObject json = new JSONObject();
        json.put("nom", "Yedagne");
        json.put("prenoms", "Mel Ange Anicet");
        json.put("age", 22);
        JSONObject departementJson = new JSONObject();
        departementJson.put("code", "DAB");
        json.put("departement", departementJson);

        // Envoi de la requête POST pour créer la personne
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .post("/addPersonne")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk())
                .andReturn();

        // Vérification que la personne a été créée avec succès
        JSONObject createdPersonne = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("Yedagne", createdPersonne.getString("nom"));
        assertEquals("Mel Ange Anicet", createdPersonne.getString("prenoms"));
        assertEquals(22, createdPersonne.getInt("age"));
        JSONObject createdDepartement = createdPersonne.getJSONObject("departement");
        assertEquals("DAB", createdDepartement.getString("code"));
    }

    @Test
    @Order(value=2)
    @DisplayName("test unitaire pour la récupération de toutes les personnes")
    public void testGetAllPersonne() throws Exception{
        // Ajouter une personne pour les besoins du test
        Departement departement = new Departement("DAB", "DABOU", new HashSet<>());
        Personne personne = new Personne(null, "Yedagne", "Mel Ange Anicet", 22, departement);
        departement.getPersonnes().add(personne);
        personneRepository.save(personne);

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/getAllPersonne"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(content);

        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertEquals("Yedagne", jsonObject.getString("nom"));
        assertEquals("Mel Ange Anicet", jsonObject.getString("prenoms"));
        assertEquals(22, jsonObject.getInt("age"));
        assertEquals("DAB", jsonObject.getJSONObject("departement").getString("code"));
    }


    @Test
    @Order(value=3)
    @DisplayName("test unitaire pour la suppression d'une personne existante")
    public void testDeleteExistingPersonne() throws Exception{
        // Ajouter une personne pour les besoins du test
        Departement departement = new Departement("YAM", "Yamoussoukro", new HashSet<>());
        Personne personne = new Personne(null, "Yedagne", "Anne Marie", 21, departement);
        personne = personneRepository.save(personne);

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.delete("/deletePersonne/" + personne.getId()))
                .andExpect(status().isNoContent())
                .andReturn();


        // Vérifier que la personne a bien été supprimée
        Assertions.assertFalse(personneRepository.findById(personne.getId()).isPresent());
    }


}

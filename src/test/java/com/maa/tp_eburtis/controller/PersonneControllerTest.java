package com.maa.tp_eburtis.controller;

import com.maa.tp_eburtis.model.Departement;
import com.maa.tp_eburtis.model.Personne;
import com.maa.tp_eburtis.repository.PersonneRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
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
        json = null;
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
        Personne personne = new Personne("Akpa", "Bedi Paul Donatien", 24, new Departement("DAB","DABOU"));
        this.personneRepository.save(personne);

        this.mvc.perform(MockMvcRequestBuilders.get("/getAllPersonne"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.[0].nom", is("Akpa")))
                .andExpect((ResultMatcher) jsonPath("$.[0].prenoms", is("Bedi Paul Donatien")))
                .andExpect((ResultMatcher) jsonPath("$.[0].age", is(24)))
                .andExpect((ResultMatcher) jsonPath("$.[0].departement_code", is("DAB")))
                .andReturn();
    }

    @Test
    @Order(value=3)
    @DisplayName("test unitaire pour la suppression d'une personne existante")
    public void testDeleteExistingPersonne() throws Exception{
        // Ajouter une personne pour les besoins du test
        Personne personne = new Personne("Yedagne", "Anne Marie", 21, new Departement("YAM", "Yamoussoukro"));
        personne = this.personneRepository.save(personne);

        this.mvc.perform(MockMvcRequestBuilders.delete("/deletePersonne/" + personne.getId()))
                .andExpect(status().isNoContent())
                .andReturn();

        // Vérifier que la personne a bien été supprimée
        Assertions.assertFalse(this.personneRepository.findById(personne.getId()).isPresent());
    }

    @Test
    @Order(value=4)
    @DisplayName("test unitaire pour la suppression d'une personne inexistante")
    public void testDeleteNonExistingPersonne() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders.delete("/deletePersonne/9999"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}

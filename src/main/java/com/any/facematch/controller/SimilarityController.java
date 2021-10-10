package com.any.facematch.controller;

import com.any.facematch.Service.PersonsService;
import com.any.facematch.Service.SimilarityService;
import com.any.facematch.model.api.Person;
import com.any.facematch.model.api.PersonSimilarityResults;
import com.any.facematch.model.db.PersonEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class SimilarityController {

    private PersonsService personsService;
    private SimilarityService similarityService;

    @Value("${detection.vector.length}")
    private int FACE_DETECTION_VECTOR_LENGTH;
    @Value("${crowd.max.people}")
    private int MAX_PEOPLE_IN_CROWD;

    @Autowired
    public SimilarityController(PersonsService personsService, SimilarityService similarityService) {
        this.personsService = personsService;
        this.similarityService = similarityService;
    }

    // returns:
    // true : added
    // false: max people exceeded, person already exists, face detection vector not 256 length
    @ResponseBody
    @PostMapping(path = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addPerson(@RequestBody Person person){
        log.info("POST /person person = " + person);
        boolean valid = validateAddPerson(person);
        if(!valid){
            return false;
        }
        PersonEntry personEntry = new PersonEntry(person);
        similarityService.addPerson(personEntry);
        personsService.savePerson(personEntry);
        return true;
    }

    // return: PersonSimilarityResults
    @GetMapping(path = "/person")
    @ResponseBody
    public PersonSimilarityResults getPersonSimilarity(@RequestParam String personName){
        log.info("GET /person person = " + personName);
        PersonSimilarityResults personSimilarityResults = similarityService.getPersonSimilarity(personName);
        return personSimilarityResults;
    }

    private boolean validateAddPerson(Person person) {
        if(person.getName()==null || person.getName().isEmpty()){
            log.error("Add person: name not specified");
            return false;
        }
        if(person.getFaceFeatures() == null || person.getFaceFeatures().length != FACE_DETECTION_VECTOR_LENGTH){
            log.error("Add person: illegal face detection vector");
            return false;
        }
        if(personsService.getNumOfPeople() >= MAX_PEOPLE_IN_CROWD){
            log.error("Add person: max people exceeded");
            return false;
        }
        if(personsService.isPersonExists(person.getName())){
            log.error("Add person: person already exists");
            return false;
        }
        return true;
    }
}

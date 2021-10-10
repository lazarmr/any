package com.any.facematch.Service;

import com.any.facematch.model.db.PersonEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonsService {

//    PersonsRepository personsRepository;
    SimilarityService similarityService;

    @Autowired
    public PersonsService(SimilarityService similarityService) {
//        this.personsRepository = personsRepository;
        this.similarityService = similarityService;
    }

    public void savePerson(PersonEntry personEntry){
//        personsRepository.save(personEntry);
    }

    public int getNumOfPeople() {
        // will move to personsRepository on adding db
        return similarityService.getNumOfPeopleIn();
    }

    public boolean isPersonExists(String name) {
        // will move to personsRepository on adding db
        return similarityService.isPersonExists(name);
    }
}

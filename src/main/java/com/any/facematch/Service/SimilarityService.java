package com.any.facematch.Service;

import com.any.facematch.Similarity.SimilarityCalculator;
import com.any.facematch.Similarity.SimilarityCalculatorInterface;
import com.any.facematch.Similarity.SimilarityFunction;
import com.any.facematch.Similarity.SimilarityResultsCache;
import com.any.facematch.model.api.PersonSimilarityResults;
import com.any.facematch.model.db.PersonEntry;
import com.any.facematch.model.db.PersonSimilarityResultsEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.vecmath.GVector;
import java.sql.Timestamp;
import java.util.Comparator;

@Service
public class SimilarityService {

    SimilarityCalculatorInterface similarityCalculator;
    SimilarityResultsCache similarityResultsCache;

    @Autowired
    public SimilarityService(SimilarityCalculator similarityCalculator, SimilarityResultsCache similarityResultsCache) {
        this.similarityCalculator = similarityCalculator;
        this.similarityResultsCache = similarityResultsCache;
    }

    // number of similar people to search for
    @Value("${similarity.num.of.results}")
    private int NUM_OF_SIMILAR_PEOPLE;

    // Similarity Function definition - can be set here
    public SimilarityFunction dotProductSimilarity = (double[] first, double[] second)->{
        GVector firstV = new GVector(first);
        GVector secondV = new GVector(second);
        return firstV.dot(secondV);
    };

    // similarity result comparator - set if you want max or min result
    public Comparator<Double> dotProductResultsComparator = (Double first, Double second)->{
        if(first>second){
            return 1;
        }
        if(first < second){
            return -1;
        }
        return 0;
    };

    public void addPerson(PersonEntry personEntry) {
        similarityCalculator.addPersonEntry(personEntry);
    }

    // if person exists in results cash - search only in later added entries
    // otherwise search all entries
    public PersonSimilarityResults getPersonSimilarity(String personName) {

        // get cached result and set search start time if found
        PersonSimilarityResultsEntry cashedPersonSimilarityResultsEntry = similarityResultsCache.get(personName);
        Timestamp startTime = null;
        if(cashedPersonSimilarityResultsEntry != null){
            startTime = cashedPersonSimilarityResultsEntry.getCalculationTime();
        }

        // search similarity
        PersonSimilarityResultsEntry similarityResultsEntry =
                similarityCalculator.calcSimilarity(personName,NUM_OF_SIMILAR_PEOPLE,dotProductSimilarity,dotProductResultsComparator,startTime);

        // merge cache and search results
        PersonSimilarityResultsEntry mergedEntry =  mergeResults(similarityResultsEntry,cashedPersonSimilarityResultsEntry,dotProductResultsComparator);

        // add new result to cache and return
        similarityResultsCache.add(mergedEntry);
        return new PersonSimilarityResults(mergedEntry);
    }

    public PersonSimilarityResultsEntry mergeResults(PersonSimilarityResultsEntry cashedPersonSimilarityResultsEntry, PersonSimilarityResultsEntry similarityResultsEntry,Comparator<Double> similarityResultComparator){
        if(cashedPersonSimilarityResultsEntry==null){
            return similarityResultsEntry;
        }
        if(similarityResultsEntry == null || similarityResultsEntry.getSimilarityResults().isEmpty()){
            return cashedPersonSimilarityResultsEntry;
        }

        // similarity result has the right (latest) timestamp so merge results into it
        similarityResultsEntry.setSimilarityResults(
                similarityCalculator.mergeSimilarityResults(NUM_OF_SIMILAR_PEOPLE,dotProductResultsComparator,cashedPersonSimilarityResultsEntry.getSimilarityResults(),similarityResultsEntry.getSimilarityResults()));
        return similarityResultsEntry;
    }

    // will move to person service on adding db
    public int getNumOfPeopleIn(){
        return similarityCalculator.getNumOfPeople();
    }
    public boolean isPersonExists(String name){
        return similarityCalculator.isPersonExists(name);
    }

}

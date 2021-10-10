package com.any.facematch.Similarity;

import com.any.facematch.exceptions.PersonNotFoundException;
import com.any.facematch.model.api.SimilarityResult;
import com.any.facematch.model.db.PersonEntry;
import com.any.facematch.model.db.PersonSimilarityResultsEntry;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class SimilarityCalculator implements SimilarityCalculatorInterface{

    // data structure
    Map<String,PersonEntry> crowdMap = new ConcurrentHashMap<>();
    private Deque<PersonEntry> crowdEntries = new ConcurrentLinkedDeque<>();

    @Override
    public void addPersonEntry(PersonEntry personEntry){
        crowdMap.put(personEntry.getName(),personEntry);
        crowdEntries.addLast(personEntry);
    }

    @Override
    public PersonSimilarityResultsEntry calcSimilarity(String personName, int numOfSimilarPeoples, SimilarityFunction similarityFunction, Comparator<Double> similarityResultComparator, Timestamp startTime){
        PersonEntry personEntry = crowdMap.get(personName);
        if(personEntry == null){
            throw new PersonNotFoundException("Person to match not found in DB: " + personName);
        }// default
        if(numOfSimilarPeoples==0){
            numOfSimilarPeoples=3;
        }
        List<SimilarityResult> similarityResults = new ArrayList<>();
        Iterator<PersonEntry> crowdIterator = crowdEntries.descendingIterator();
        while(crowdIterator.hasNext()){
            PersonEntry currentEntry = crowdIterator.next();
            if(startTime!= null && currentEntry.getUpdateTime().before(startTime)){
                break;
            }
            if(currentEntry.getName().equals(personEntry.getName())){
                continue;
            }
            double similarityResult = similarityFunction.calcSimilarity(personEntry.getFaceFeatures(), currentEntry.getFaceFeatures());
            addResultToListIfNeeded(currentEntry,similarityResult,similarityResults,numOfSimilarPeoples,similarityResultComparator);

        }
        return new PersonSimilarityResultsEntry(personEntry.getName(),similarityResults,new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public List<SimilarityResult> mergeSimilarityResults(int numOfSimilarPeoples,Comparator<Double> similarityResultComparator,List<SimilarityResult>... results) {
        List<SimilarityResult> similarityResults = new ArrayList<>();
        for(List<SimilarityResult> resultsToMerge : results){
            for ( SimilarityResult similarityToMerge : resultsToMerge){
                addResultToListIfNeeded(similarityToMerge, similarityResults,numOfSimilarPeoples, similarityResultComparator);
            }
        }
        return similarityResults;
    }

    private void addResultToListIfNeeded(PersonEntry currentEntry, double similarityResult, List<SimilarityResult> similarityResults, int numOfSimilarPeoples, Comparator<Double> similarityResultComparator) {
        addResultToListIfNeeded(new SimilarityResult(currentEntry.getName(),similarityResult),similarityResults,numOfSimilarPeoples,similarityResultComparator);
    }

    private void addResultToListIfNeeded(SimilarityResult similarityResultToAdd, List<SimilarityResult> similarityResults, int numOfSimilarPeoples, Comparator<Double> similarityResultComparator) {
        if(similarityResults.size()<numOfSimilarPeoples){
            similarityResults.add(similarityResultToAdd);
            return;
        }
        SimilarityResult toReplaceSimilarityResult = null;
        double minimumSimilarityValue = similarityResultToAdd.getValue();
        for(SimilarityResult currentSimilarityResult : similarityResults){
            if(similarityResultComparator.compare(minimumSimilarityValue,currentSimilarityResult.getValue())>0){
                toReplaceSimilarityResult = currentSimilarityResult;
                minimumSimilarityValue = currentSimilarityResult.getValue();
            }
        }
        if(toReplaceSimilarityResult!=null){
            similarityResults.remove(toReplaceSimilarityResult);
            similarityResults.add(similarityResultToAdd);
        }
    }

    // will move to person service on adding db
    public int getNumOfPeople(){
        return crowdMap.size();
    }
    public boolean isPersonExists(String name){
        if(crowdMap.get(name)!=null){
            return true;
        }
        return false;
    }
}

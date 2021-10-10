package com.any.facematch.Similarity;

import com.any.facematch.model.db.PersonSimilarityResultsEntry;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SimilarityResultsCache {

    public PersonSimilarityResultsEntry get(String personName){
        return similarityResultsCache.get(personName);
    }

    public void add(PersonSimilarityResultsEntry similarityResultsEntry){
        similarityResultsCache.put(similarityResultsEntry.getPersonName(),similarityResultsEntry);
    }

    Map<String, PersonSimilarityResultsEntry> similarityResultsCache = new ConcurrentHashMap<>();
}

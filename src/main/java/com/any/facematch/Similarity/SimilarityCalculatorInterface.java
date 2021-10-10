package com.any.facematch.Similarity;

import com.any.facematch.model.api.SimilarityResult;
import com.any.facematch.model.db.PersonEntry;
import com.any.facematch.model.db.PersonSimilarityResultsEntry;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

public interface SimilarityCalculatorInterface {
    // similarity
    void addPersonEntry(PersonEntry personEntry);
    PersonSimilarityResultsEntry calcSimilarity(String personName, int numOfSimilarPeoples, SimilarityFunction similarityFunction, Comparator<Double> similarityResultComparator, Timestamp startTime);
    List<SimilarityResult> mergeSimilarityResults(int numOfSimilarPeoples, Comparator<Double> similarityResultComparator, List<SimilarityResult>... results);

    // will move to person service on adding db
    int getNumOfPeople();
    boolean isPersonExists(String name);
}

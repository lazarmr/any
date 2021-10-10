package com.any.facematch;

import com.any.facematch.Service.SimilarityService;
import com.any.facematch.Similarity.SimilarityCalculator;
import com.any.facematch.Similarity.SimilarityResultsCache;
import com.any.facematch.model.api.PersonSimilarityResults;
import com.any.facematch.model.db.PersonEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class SimilarityServiceTest {
    @Test
    public void testSimilarityService(){
        SimilarityService similarityService = new SimilarityService(new SimilarityCalculator(),new SimilarityResultsCache());
        double[] v1 = new double[256];
        double[] v2 = new double[256];
        for (int i=0;i<256;i++){
            v1[i] = 1.0;
            v2[i] = i;
        }
        PersonEntry bob = new PersonEntry("Bob",new Timestamp(System.currentTimeMillis()),v1);
        PersonEntry moshe = new PersonEntry("Moshe",new Timestamp(System.currentTimeMillis()),v2);
        similarityService.addPerson(bob);
        similarityService.addPerson(moshe);

        PersonSimilarityResults similarityResults = similarityService.getPersonSimilarity(bob.getName());
        Assertions.assertEquals("Moshe",similarityResults.getSimilarityResults().get(0).getName());
    }
}

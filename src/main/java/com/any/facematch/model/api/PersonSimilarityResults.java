package com.any.facematch.model.api;

import com.any.facematch.model.db.PersonSimilarityResultsEntry;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonSimilarityResults {
    @JsonProperty
    private String personName;
    @JsonProperty
    private List<SimilarityResult> similarityResults;

    public PersonSimilarityResults(PersonSimilarityResultsEntry similarityResultsEntry) {
        this.personName = similarityResultsEntry.getPersonName();
        this.similarityResults = similarityResultsEntry.getSimilarityResults();
    }
}

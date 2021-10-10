package com.any.facematch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
public class SimilarityResult {
    @JsonProperty
    private String name;
    @JsonProperty
    private double value;
}

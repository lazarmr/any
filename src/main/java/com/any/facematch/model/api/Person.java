package com.any.facematch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    @JsonProperty
    String name;
    @JsonProperty
    double[] faceFeatures;
}

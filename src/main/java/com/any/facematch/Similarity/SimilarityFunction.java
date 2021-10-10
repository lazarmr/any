package com.any.facematch.Similarity;

@FunctionalInterface
public interface SimilarityFunction {
    double calcSimilarity(double[] first, double[] second);
}

package com.any.facematch.model.db;

import com.any.facematch.model.api.SimilarityResult;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "similarity_results")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@AllArgsConstructor
@Data
public class PersonSimilarityResultsEntry {
    @Id
    @Column(name = "name")
    private String personName;

    @Type(type = "jsonb")
    @Column(name = "calc_time",columnDefinition = "jsonb")
    private List<SimilarityResult> similarityResults;

    @Column(name = "calc_time")
    private Timestamp calculationTime;

}

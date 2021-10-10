package com.any.facematch.model.db;

import com.any.facematch.model.api.Person;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "persons")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@AllArgsConstructor
@Data
@Entity
public class PersonEntry {

    @Id
    @Column(name = "person_name")
    String name;

    @Column(name = "last_update")
    Timestamp updateTime;

    @Type(type = "jsonb")
    @Column(name = "face_features",columnDefinition = "jsonb")
    double[] faceFeatures;

    public PersonEntry(Person person){
        name = person.getName();
        updateTime = new Timestamp(System.currentTimeMillis());
        this.faceFeatures = person.getFaceFeatures();
    }

}

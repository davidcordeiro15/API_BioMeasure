package com.API_BioMeasure.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class usuarios {
    @Id
    private int id;

    private String email;
    private String senha;


}

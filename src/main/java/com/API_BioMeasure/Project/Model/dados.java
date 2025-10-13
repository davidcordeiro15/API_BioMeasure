package com.API_BioMeasure.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class dados {
    @Id
    private  int id;

    private String nome__peca;
    private String nome__responsavel;
    private int dados;
}


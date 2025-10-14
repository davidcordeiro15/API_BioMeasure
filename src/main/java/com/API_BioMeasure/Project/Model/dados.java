package com.API_BioMeasure.Project.Model;

import jakarta.persistence.Column;
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
    @Column(name = "nome__responsavel")
    private String nomeResponsavel;
    private int dados;
}


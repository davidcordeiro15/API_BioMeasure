package com.API_BioMeasure.Project.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;

public class DadosDTO {
    private  int id;

    private String nome__peca;
    private String nome__responsavel;

    private double dados;

    public String getNome__peca() {
        return nome__peca;
    }

    public void setNome__peca(String nome__peca) {
        this.nome__peca = nome__peca;
    }

    public String getNome__responsavel() {
        return nome__responsavel;
    }

    public void setNome__responsavel(String nome__responsavel) {
        this.nome__responsavel = nome__responsavel;
    }

    public double getDados() {
        return dados;
    }

    public void setDados(double dados) {
        this.dados = dados;
    }
}

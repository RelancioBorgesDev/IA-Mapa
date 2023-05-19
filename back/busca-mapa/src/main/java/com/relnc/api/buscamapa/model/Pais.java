package com.relnc.api.buscamapa.model;

public class Pais {
    private String nome;

    public Pais(String nome) {
        this.nome = nome;
    }

    public Pais() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

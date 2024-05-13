package com.relnc.api.buscamapa.model;

public class Countries {
    private String nome;

    public Countries(String nome) {
        this.nome = nome;
    }

    public Countries() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

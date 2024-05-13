package com.relnc.api.buscamapa.structures;

import java.util.ArrayList;
import java.util.Collections;

public class List {
    private Node inicio;
    private Node fim;

    public List() {
        this.inicio = null;
        this.fim = null;
    }

    public void insereInicio(Node novo) {
        if (this.inicio == null) {
            this.inicio = novo;
            this.fim = novo;
        } else {
            novo.setProximo(this.inicio);
            this.inicio.setAnterior(novo);
            this.inicio = novo;
        }
    }

    public void insereFim(Node novo) {
        if (this.inicio == null) {
            this.inicio = novo;
            this.fim = novo;
        } else {
            this.fim.setProximo(novo);
            novo.setAnterior(this.fim);
            this.fim = novo;
        }

    }

    public Node removeInicio() {
        if (this.inicio == null) {
            System.out.println("A lista está vazia.");
            return null;
        } else if (this.inicio == this.fim) {
            Node noRemovido = this.inicio;
            this.inicio = null;
            this.fim = null;
            return noRemovido;
        } else {
            Node noRemovido = this.inicio;
            this.inicio = this.inicio.getProximo();
            this.inicio.setAnterior(null);
            return noRemovido;
        }
    }

    public Node removeFim() {
        if (this.inicio == null) {
            System.out.println("A lista está vazia.");
            return null;
        } else if (this.inicio == this.fim) {
            Node noRemovido = this.fim;
            this.inicio = null;
            this. fim = null;
            return noRemovido;
        } else {
            Node noRemovido = this.fim;
            this.fim = this.fim.getAnterior();
            this.fim.setProximo(null);
            return noRemovido;
        }
    }

    public boolean contem(Object valor) {
        Node atual = inicio;
        while (atual != null) {
            if (atual.getValor1().equals(valor)) {
                return true;
            }
            atual = atual.getProximo();
        }
        return false;
    }

    public boolean vazio(){
        return this.inicio == null;
    }

    public Node getInicio() {
        return inicio;
    }

    public Node getFim() {
        return fim;
    }

    public void exibeCaminho(Node fim) {
        java.util.List<Object> caminho = new ArrayList<>();
        Node atual = fim;
        while (atual != null) {
            caminho.add(atual.getValor1());
            atual = atual.getPai();
        }
        Collections.reverse(caminho);
        System.out.println("Caminho encontrado: " + caminho);
    }
    @Override
    public String toString() {
        return "Lista{" +
                "inicio=" + inicio +
                ", fim=" + fim +
                '}';
    }
}

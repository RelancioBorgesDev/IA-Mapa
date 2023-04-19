package com.relnc.api.buscamapa.structures;

public class Node {
    private Node pai;
    //Valor do nó
    private Object valor1;
    //Nivel do nó
    private int valor2;
    private Node anterior;
    private Node proximo;

    public Node(Object valor1, int valor2,Node pai) {
        this.valor1 = valor1;
        this.valor2 = valor2;
        this.pai = pai;
        this.anterior = null;
        this.proximo = null;
    }

    public Node getPai() {
        return pai;
    }

    public void setPai(Node pai) {
        this.pai = pai;
    }

    public Object getValor1() {
        return valor1;
    }

    public void setValor1(Object valor1) {
        this.valor1 = valor1;
    }

    public int getValor2() {
        return valor2;
    }

    public void setValor2(int valor2) {
        this.valor2 = valor2;
    }

    public Node getAnterior() {
        return anterior;
    }

    public void setAnterior(Node anterior) {
        this.anterior = anterior;
    }

    public Node getProximo() {
        return proximo;
    }

    public void setProximo(Node proximo) {
        this.proximo = proximo;
    }

    @Override
    public String toString() {
        return "Node{" +
                ", valor1=" + valor1 +
                ", valor2=" + valor2 +
                '}';
    }
}

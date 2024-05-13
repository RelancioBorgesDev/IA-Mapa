package com.relnc.api.buscamapa.structures;

public class NodeStar {
    private NodeStar pai;
    private NodeStar proximo;
    private NodeStar anterior;
    private String nome;
    private int custo;
    private int custoAproximado;
    private int fn;
    private double lat;
    private double lng;

    public NodeStar() {
    }
    public NodeStar(String nome, int custo, int custoAproximado, int fn, NodeStar pai) {
        this.pai = pai;
        this.custo = custo;
        this.custoAproximado = custoAproximado;
        this.fn = fn;
        this.nome = nome;
        this.proximo = null;
        this.anterior = null;
    }
    public NodeStar(String nome, int custo, int custoAproximado, NodeStar pai) {
        this.pai = pai;
        this.custo = custo;
        this.custoAproximado = custoAproximado;
        this.nome = nome;
        this.proximo = null;
        this.anterior = null;
    }

    public NodeStar(String nome, double lat, double lng, NodeStar pai) {
        this.pai = pai;
        this.nome = nome;
        this.lat = lat;
        this.lng = lng;
    }

    public NodeStar getPai() {
        return pai;
    }

    public void setPai(NodeStar pai) {
        this.pai = pai;
    }

    public NodeStar getProximo() {
        return proximo;
    }

    public void setProximo(NodeStar proximo) {
        this.proximo = proximo;
    }

    public NodeStar getAnterior() {
        return anterior;
    }

    public void setAnterior(NodeStar anterior) {
        this.anterior = anterior;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public int getCustoAproximado() {
        return custoAproximado;
    }

    public void setCustoAproximado(int custoAproximado) {
        this.custoAproximado = custoAproximado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }
    @Override
    public String toString() {
        return "nome: " + nome +
                " custo:" + custo +
                " custoAproximado: " + custoAproximado;
    }
}

package com.relnc.api.buscamapa.structures;

public class NodeEstrela {
    private NodeEstrela pai;
    private NodeEstrela proximo;
    private NodeEstrela anterior;
    private String nome;
    private int custo;
    private int custoAproximado;
    private int fn;
    private double lat;
    private double lng;

    public NodeEstrela() {
    }
    public NodeEstrela(String nome, int custo, int custoAproximado,int fn, NodeEstrela pai) {
        this.pai = pai;
        this.custo = custo;
        this.custoAproximado = custoAproximado;
        this.fn = fn;
        this.nome = nome;
        this.proximo = null;
        this.anterior = null;
    }
    public NodeEstrela(String nome, int custo, int custoAproximado, NodeEstrela pai) {
        this.pai = pai;
        this.custo = custo;
        this.custoAproximado = custoAproximado;
        this.nome = nome;
        this.proximo = null;
        this.anterior = null;
    }

    public NodeEstrela(String nome, double lat, double lng, NodeEstrela pai) {
        this.pai = pai;
        this.nome = nome;
        this.lat = lat;
        this.lng = lng;
    }

    public NodeEstrela getPai() {
        return pai;
    }

    public void setPai(NodeEstrela pai) {
        this.pai = pai;
    }

    public NodeEstrela getProximo() {
        return proximo;
    }

    public void setProximo(NodeEstrela proximo) {
        this.proximo = proximo;
    }

    public NodeEstrela getAnterior() {
        return anterior;
    }

    public void setAnterior(NodeEstrela anterior) {
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

package com.relnc.api.buscamapa.structures;

public class DoublyList {
    private NodeStar inicio;
    private NodeStar fim;

    public DoublyList() {
    }

    public void insereInicio(NodeStar novo) {
        if (novo == null) {
            System.err.println("Erro: não é possível inserir um nó nulo na lista.");
            return;
        }

        if (this.inicio == null) {
            this.inicio = novo;
            this.fim = novo;
        } else {
            novo.setProximo(this.inicio);
            if (this.inicio != null) {
                this.inicio.setAnterior(novo);
            }
            this.inicio = novo;
        }
    }

    public void insereFim(NodeStar novo) {
        if (this.inicio == null) {
            this.inicio = novo;
            this.fim = novo;
        } else {
            this.fim.setProximo(novo);
            novo.setAnterior(this.fim);
            this.fim = novo;
        }
    }

    public NodeStar removeInicio(){
        NodeStar noRemovido;
        if (this.inicio == null){
            return null;
        }

        noRemovido = this.inicio;
        this.inicio = this.inicio.getProximo();
        return noRemovido;
    }

    public NodeStar removeFinal(){
        NodeStar noRemovido;
        if (this.fim == null){
            return null;
        }

        noRemovido = this.fim;
        this.fim = this.fim.getAnterior();
        this.fim.setProximo(null);
        return noRemovido;
    }

    public boolean isVazio(){
        return this.inicio == null;
    }


    public NodeStar getInicio() {
        return inicio;
    }

    public void setInicio(NodeStar inicio) {
        this.inicio = inicio;
    }

    public NodeStar getFim() {
        return fim;
    }

    public void setFim(NodeStar fim) {
        this.fim = fim;
    }

    @Override
    public String toString() {
        NodeStar noAtual = inicio;
        StringBuilder sb = new StringBuilder();
        while (noAtual != null) {
            sb.append(noAtual.toString());
            sb.append(" ");
            noAtual = noAtual.getProximo();
        }
        return sb.toString();
    }
}

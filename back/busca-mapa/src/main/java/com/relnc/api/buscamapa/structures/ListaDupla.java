package com.relnc.api.buscamapa.structures;

public class ListaDupla {
    private NodeEstrela inicio;
    private NodeEstrela fim;


    public ListaDupla() {
    }

    public void insereInicio(NodeEstrela novo) {
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

    public void insereFim(NodeEstrela novo) {
        if (this.inicio == null) {
            this.inicio = novo;
            this.fim = novo;
        } else {
            this.fim.setProximo(novo);
            novo.setAnterior(this.fim);
            this.fim = novo;
        }
    }

    public NodeEstrela removeInicio(){
        NodeEstrela noRemovido;
        if (this.inicio == null){
            return null;
        }

        noRemovido = this.inicio;
        this.inicio = this.inicio.getProximo();
        return noRemovido;
    }

    public NodeEstrela removeFinal(){
        NodeEstrela noRemovido;
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


    public NodeEstrela getInicio() {
        return inicio;
    }

    public void setInicio(NodeEstrela inicio) {
        this.inicio = inicio;
    }

    public NodeEstrela getFim() {
        return fim;
    }

    public void setFim(NodeEstrela fim) {
        this.fim = fim;
    }

    @Override
    public String toString() {
        NodeEstrela noAtual = inicio;
        StringBuilder sb = new StringBuilder();
        while (noAtual != null) {
            sb.append(noAtual.toString());
            sb.append(" ");
            noAtual = noAtual.getProximo();
        }
        return sb.toString();
    }
}

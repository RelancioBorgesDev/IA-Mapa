package com.relnc.api.buscamapa.normalizer;

import java.text.Normalizer;

public class CountriesNormalizer {
    public String normalizarNomeDoPais(String nomeDoPais) {
        // Remove acentos e outros diacríticos do nome do país
        String nomeDoPaisNormalizado = Normalizer.normalize(nomeDoPais, Normalizer.Form.NFD);
        nomeDoPaisNormalizado = nomeDoPaisNormalizado.replaceAll("[^\\p{ASCII}]", "");

        return nomeDoPaisNormalizado;
    }

}

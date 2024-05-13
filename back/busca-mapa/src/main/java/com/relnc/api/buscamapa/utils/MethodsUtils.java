package com.relnc.api.buscamapa.utils;

import com.relnc.api.buscamapa.file.File;
import com.relnc.api.buscamapa.structures.NodeStar;

import java.util.Map;

public class MethodsUtils {
    public static Integer calculoDoFn(Integer custo, Integer custoAproximado){
        return custo + custoAproximado;
    }

    public static Integer calculoDoCusto(String pais, String coordenada){
        File grafo = new File();
        Map<String, NodeStar> mapaCoordenadasCapitais = grafo.retornaCoordenadasCapitais(coordenada);
        NodeStar pais1Location = mapaCoordenadasCapitais.get(pais);

        return (int) (pais1Location.getLat() + pais1Location.getLng());
    }

    public static Integer menorDistanciaEntreDoisPaises(String pais1, String pais2, String coordenada) {
        File grafo = new File(); //Criar a classe que manipula os arquivos

        Map<String, NodeStar> mapaCoordenadasCapitais = grafo.retornaCoordenadasCapitais(coordenada);

        //Verifica se os países existem no mapa
        if (!mapaCoordenadasCapitais.containsKey(pais1) || !mapaCoordenadasCapitais.containsKey(pais2)) {
            throw new IllegalArgumentException("Países não encontrados no mapa de coordenadas.");
        }

        //Objeto Location que contém as coordenadas do país1
        NodeStar pais1Location = mapaCoordenadasCapitais.get(pais1);
        //Objeto Location que contém as coordenadas do país2
        NodeStar pais2Location = mapaCoordenadasCapitais.get(pais2);

        // Retorna a distancia em km;
        return (int) Math.round(calculaDistanciaHaversine(pais1Location, pais2Location));
    }

    public static double calculaDistanciaHaversine(NodeStar pais1Location, NodeStar pais2Location) {
        //Raio de curvatura aproximado em
        final double R = 6371;
        //Transformando os valores de lat e lng em radianos
        double lat1 = Math.toRadians(pais1Location.getLat());
        double lng1 = Math.toRadians(pais1Location.getLng());
        double lat2 = Math.toRadians(pais2Location.getLat());
        double lng2 = Math.toRadians(pais2Location.getLng());

        //Calculado a diferença entre (x2-x1) (y2-y1)
        double diferencaLat = lat2 - lat1;
        double diferencaLng = lng2 - lng1;

        double a = Math.sin(diferencaLat / 2) * Math.sin(diferencaLat / 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.sin(diferencaLng / 2) * Math.sin(diferencaLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public static String veirificaPaisRetornaCoordenadas(String path) {
        return switch (path) {
            case "americano" -> "coords_caps_americano";
            case "europeu" -> "coords_caps_europeu";
            case "africano" -> "coords_caps_africano";
            default -> "";
        };
    }
}

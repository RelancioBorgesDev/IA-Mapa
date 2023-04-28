package com.relnc.api.buscamapa.file;

import com.relnc.api.buscamapa.model.Location;
import com.relnc.api.buscamapa.normalizer.PaisNormalizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Arquivo {

    public Map<String, Set<String>> retornaPaises(String fileName) {
        Map<String, Set<String>> mapa = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linha;
            PaisNormalizer pn = new PaisNormalizer();
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" - ");
                String key = partes[0].trim();
                String keyNormalized = pn.normalizarNomeDoPais(key);
                String[] valores = partes[1].split(", ");
                Set<String> valoresSet = new HashSet<>();
                for (String valor : valores) {
                    valor = pn.normalizarNomeDoPais(valor);
                    if (!valoresSet.contains(valor.trim()) && !valor.equals(key)) {
                        valoresSet.add(valor);
                    }
                }
                mapa.put(keyNormalized, valoresSet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapa;
    }

    public Map<String, Location> retornaCoordenadasCapitais(String path) {
        Map<String, Location> mapa = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linha;
            PaisNormalizer pn = new PaisNormalizer();
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(":");
                String key = partes[0].trim();
                String keyNormalized = pn.normalizarNomeDoPais(key);
                String[] valores = partes[1].split(",");
                double lat = Double.parseDouble(valores[0]);
                double lng = Double.parseDouble(valores[1]);
                mapa.put(keyNormalized, new Location(lat, lng));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapa;
    }
}

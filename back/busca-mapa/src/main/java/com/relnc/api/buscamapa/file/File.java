package com.relnc.api.buscamapa.file;

import com.relnc.api.buscamapa.model.MethodLabel;
import com.relnc.api.buscamapa.normalizer.CountriesNormalizer;
import com.relnc.api.buscamapa.structures.NodeStar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class File {

    public Map<String, Set<String>> retornaPaises(String fileName) {
        Map<String, Set<String>> mapa = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linha;
            CountriesNormalizer pn = new CountriesNormalizer();
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

    public Map<String, NodeStar> retornaCoordenadasCapitais(String path) {
        Map<String, NodeStar> mapa = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linha;
            CountriesNormalizer pn = new CountriesNormalizer();
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(":");
                String key = partes[0].trim();
                String keyNormalized = pn.normalizarNomeDoPais(key);
                String[] valores = partes[1].split(",");
                double lat = Double.parseDouble(valores[0]);
                double lng = Double.parseDouble(valores[1]);
                mapa.put(keyNormalized, new NodeStar(keyNormalized, lat, lng, null));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapa;
    }

    public List<MethodLabel> retornaNomeDosMetodos(){
        List<MethodLabel> metodosNomes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("nome_dos_metodos"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                Arrays.stream(partes).forEach((metodoNome) -> metodosNomes.add(new MethodLabel(metodoNome)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return metodosNomes;
    }
}

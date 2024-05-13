package com.relnc.api.buscamapa.controller;

import com.relnc.api.buscamapa.file.File;
import com.relnc.api.buscamapa.model.Countries;
import com.relnc.api.buscamapa.model.CountriesCoordinates;
import com.relnc.api.buscamapa.structures.NodeStar;
import org.springframework.web.bind.annotation.*;

import static com.relnc.api.buscamapa.utils.MethodsUtils.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/paises")
@CrossOrigin(origins = "*")
public class CountriesController {
    @GetMapping("/pais/{continente}")
    public List<Countries> retornaPaises(@PathVariable String continente) {
        File grafo = new File();
        String coordenada = veirificaPaisRetornaCoordenadas(continente);
        Map<String, NodeStar> paises = grafo.retornaCoordenadasCapitais(coordenada);

        return paises.entrySet().stream()
                .map(entry -> {
                    String nomePais = entry.getKey();
                    NodeStar nodeStar = entry.getValue();
                    return new Countries(nomePais);
                })
                .sorted(Comparator.comparing(Countries::getNome))
                .collect(Collectors.toList());
    }

    @GetMapping("/coordenadas/{continente}")
    public Map<String, CountriesCoordinates> retornaCoordenadas(@PathVariable String continente) {
        File grafo = new File();
        String coordenada = veirificaPaisRetornaCoordenadas(continente);
        Map<String, NodeStar> paises = grafo.retornaCoordenadasCapitais(coordenada);
        Map<String, CountriesCoordinates> retornoCoordenadasPaises = new HashMap<>();

        paises.entrySet().stream()
                .forEach(entry -> {
                    String nomePais = entry.getKey();
                    NodeStar nodeStar = entry.getValue();
                    double latitude = nodeStar.getLat();
                    double longitude = nodeStar.getLng();
                    retornoCoordenadasPaises.put(nomePais, new CountriesCoordinates(latitude, longitude));
                });

        return retornoCoordenadasPaises;
        }


}

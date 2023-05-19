package com.relnc.api.buscamapa.controller;

import com.relnc.api.buscamapa.file.Arquivo;
import com.relnc.api.buscamapa.model.Pais;
import com.relnc.api.buscamapa.model.PaisCoordenada;
import com.relnc.api.buscamapa.structures.NodeEstrela;
import org.springframework.web.bind.annotation.*;

import static com.relnc.api.buscamapa.utils.MetodosUtils.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/paises")
@CrossOrigin(origins = "*")
public class PaisesController {
    @GetMapping("/pais/{continente}")
    public List<Pais> retornaPaises(@PathVariable String continente) {
        Arquivo grafo = new Arquivo();
        String coordenada = veirificaPaisRetornaCoordenadas(continente);
        Map<String, NodeEstrela> paises = grafo.retornaCoordenadasCapitais(coordenada);

        return paises.entrySet().stream()
                .map(entry -> {
                    String nomePais = entry.getKey();
                    NodeEstrela nodeEstrela = entry.getValue();
                    return new Pais(nomePais);
                })
                .sorted(Comparator.comparing(Pais::getNome))
                .collect(Collectors.toList());
    }

    @GetMapping("/coordenadas/{continente}")
    public Map<String, PaisCoordenada> retornaCoordenadas(@PathVariable String continente) {
        Arquivo grafo = new Arquivo();
        String coordenada = veirificaPaisRetornaCoordenadas(continente);
        Map<String, NodeEstrela> paises = grafo.retornaCoordenadasCapitais(coordenada);
        Map<String, PaisCoordenada> retornoCoordenadasPaises = new HashMap<>();

        paises.entrySet().stream()
                .forEach(entry -> {
                    String nomePais = entry.getKey();
                    NodeEstrela nodeEstrela = entry.getValue();
                    double latitude = nodeEstrela.getLat();
                    double longitude = nodeEstrela.getLng();
                    retornoCoordenadasPaises.put(nomePais, new PaisCoordenada(latitude, longitude));
                });

        return retornoCoordenadasPaises;
        }


}

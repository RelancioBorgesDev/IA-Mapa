package com.relnc.api.buscamapa.controller;

import com.relnc.api.buscamapa.file.Arquivo;
import com.relnc.api.buscamapa.structures.Lista;
import com.relnc.api.buscamapa.structures.Node;
import com.relnc.api.buscamapa.structures.NodeEstrela;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.relnc.api.buscamapa.utils.MetodosUtils.*;

@RestController
@RequestMapping("/api/algoritimos")
@CrossOrigin(origins = "*")
public class ArvoreController {
    Arquivo arq = new Arquivo();

    @GetMapping(path = "/amplitude/{continente}/{inicio}/{fim}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> amplitude(
            @PathVariable("continente") String continente,
            @PathVariable("inicio") String inicio,
            @PathVariable("fim") String fim
                                  ) {

        Lista l1 = new Lista(); // fila de nós para visitar
        Lista l2 = new Lista(); // árvore de busca (nós visitados)
        Map<String, Set<String>> grafo = arq.retornaPaises(continente); // grafo de conexões

        Set<Object> visitados = new HashSet<>(); // conjunto de nós visitados

        // insere o nó inicial como raiz da árvore e na fila de visitas
        Node inicial = new Node(inicio, 0, null);
        l1.insereFim(inicial);
        l2.insereFim(inicial);

        // marca o nó inicial como visitado
        visitados.add(inicio);

        List<String> caminho = new LinkedList<>();

        while (!l1.vazio()) {
            Node atual = l1.removeInicio();// remove o primeiro nó da fila
            caminho.add((String) atual.getValor1());

            if (atual.getValor1().equals(fim)) {
                return caminho; // retorna a árvore de busca (caminho encontrado)
            }

            Set<String> conexoes = grafo.get((String) atual.getValor1()); // obtém as conexões do nó atual
            if (conexoes != null) { // se há conexões
                for (Object conexao : conexoes) { // para cada conexão
                    if (!visitados.contains(conexao)) { // se a conexão não foi visitada
                        Node novo = new Node(conexao, atual.getValor2() + 1, atual); // cria um nó com a conexão
                        l1.insereFim(novo); // insere o novo nó na fila de visitas
                        l2.insereFim(novo); // insere o novo nó na árvore de busca
                        visitados.add(conexao); // marca a conexão como visitada
                    }
                }
            }
        }


        return Collections.singletonList("Caminho não encontrado");
    }

    @GetMapping(path = "/profundidade/{continente}/{inicio}/{fim}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> profundidade(   @PathVariable("continente") String continente,
                                       @PathVariable("inicio") String inicio,
                                       @PathVariable("fim") String fim) {
        Lista l1 = new Lista(); // fila de nós para visitar
        Lista l2 = new Lista(); // árvore de busca (nós visitados)
        Map<String, Set<String>> grafo = arq.retornaPaises(continente); // grafo de conexões
        Set<Object> visitados = new HashSet<>(); // conjunto de nós visitados

        // insere o nó inicial como raiz da árvore e na fila de visitas
        Node inicial = new Node(inicio, 0, null);
        l1.insereFim(inicial);
        l2.insereFim(inicial);

        // marca o nó inicial como visitado
        visitados.add(inicio);

        List<String> caminho = new LinkedList<>();

        while (!l1.vazio()) {
            Node atual = l1.removeFim(); // remove o primeiro nó da fila

            // verifica se encontrou o nó objetivo
            caminho.add((String) atual.getValor1());
            if (atual.getValor1().equals(fim)) {
                return caminho; // retorna a árvore de busca (caminho encontrado)
            }

            Set<String> conexoes = grafo.get((String) atual.getValor1()); // obtém as conexões do nó atual
            if (conexoes != null) { // se há conexões
                for (Object conexao : conexoes) { // para cada conexão
                    if (!visitados.contains(conexao)) { // se a conexão não foi visitada
                        Node novo = new Node(conexao, atual.getValor2() + 1, atual); // cria um nó com a conexão
                        l1.insereFim(novo); // insere o novo nó na fila de visitas
                        l2.insereFim(novo); // insere o novo nó na árvore de busca
                        visitados.add(conexao); // marca a conexão como visitada
                    }
                }
            }
        }

        return Collections.singletonList("Caminho não encontrado");
    }

    @GetMapping(path = "/profundidadelimitada/{continente}/{inicio}/{fim}/{limite}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> profundidadeLimitada(   @PathVariable("continente") String continente,
                                               @PathVariable("inicio") String inicio,
                                               @PathVariable("fim") String fim,
                                               @PathVariable("limite") int limite) {

        Lista l1 = new Lista(); // pilha de nós para visitar
        Lista l2 = new Lista(); // árvore de busca (nós visitados)
        Map<String, Set<String>> grafo = arq.retornaPaises(continente); // grafo de conexões
        Set<Object> visitados = new HashSet<>(); // conjunto de nós visitados

        // insere o nó inicial como raiz da árvore e na pilha de visitas
        Node inicial = new Node(inicio, 0, null);
        l1.insereFim(inicial);
        l2.insereFim(inicial);

        // marca o nó inicial como visitado
        visitados.add(inicio);
        List<String> caminho = new LinkedList<>();

        while (!l1.vazio()) {
            Node atual = l1.removeFim(); // remove o primeiro nó da fila

            // verifica se encontrou o nó objetivo
            caminho.add((String) atual.getValor1());
            // verifica se encontrou o nó objetivo
            if (atual.getValor1().equals(fim)) {
                return caminho; // retorna a árvore de busca (caminho encontrado)
            }

            if (atual.getValor2() < limite) { // verifica se ainda é possível descer mais na árvore
                Set<String> conexoes = grafo.get((String) atual.getValor1()); // obtém as conexões do nó atual
                if (conexoes != null) { // se há conexões
                    for (Object conexao : conexoes) { // para cada conexão
                        if (!visitados.contains(conexao)) { // se a conexão não foi visitada
                            Node novo = new Node(conexao, atual.getValor2() + 1, atual); // cria um nó com a conexão
                            l1.insereFim(novo); // insere o novo nó na pilha de visitas
                            l2.insereFim(novo); // insere o novo nó na árvore de busca
                            visitados.add(conexao); // marca a conexão como visitada
                        }
                    }
                }
            }
        }

        return Collections.singletonList("Caminho não encontrado"); // caminho não encontrado
    }

    @GetMapping(path = "/aprofundamentoiterativo/{continente}/{inicio}/{fim}/{limiteMaximo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> aprofundamentoIterativo(@PathVariable("continente") String continente,
                                               @PathVariable("inicio") String inicio,
                                               @PathVariable("fim") String fim,
                                               @PathVariable("limiteMaximo") int limiteMaximo) {

        Lista l1 = new Lista(); // pilha de nós para visitar
        Lista l2 = new Lista(); // árvore de busca (nós visitados)
        Set<Object> visitados = new HashSet<>(); // conjunto de nós visitados
        Map<String, Set<String>> grafo = arq.retornaPaises(continente);
        // insere o nó inicial como raiz da árvore e na pilha de visitas
        Node inicial = new Node(inicio, 0, null);
        l1.insereFim(inicial);
        l2.insereFim(inicial);

        // marca o nó inicial como visitado
        visitados.add(inicio);
        List<String> caminho = new LinkedList<>();
        for (int i = 0; i < limiteMaximo; i++) {
            Lista l1Temp = new Lista(); // pilha temporária para os nós visitados nesta iteração

            while (!l1.vazio()) {
                Node atual = l1.removeFim(); // remove o último nó da pilha
                caminho.add((String) atual.getValor1());
                // verifica se encontrou o nó objetivo
                if (atual.getValor1().equals(fim)) {
                    return caminho; // retorna a árvore de busca (caminho encontrado)
                }

                if (atual.getValor2() < limiteMaximo) { // verifica se ainda é possível descer mais na árvore
                    Set<String> conexoes = grafo.get((String) atual.getValor1()); // obtém as conexões do nó atual
                    if (conexoes != null) { // se há conexões
                        for (Object conexao : conexoes) { // para cada conexão
                            if (!visitados.contains(conexao)) { // se a conexão não foi visitada
                                Node novo = new Node(conexao, atual.getValor2() + 1, atual); // cria um nó com a conexão
                                l1Temp.insereFim(novo); // insere o novo nó na pilha temporária de visitas
                                l2.insereFim(novo); // insere o novo nó na árvore de busca
                                visitados.add(conexao); // marca a conexão como visitada
                            }
                        }
                    }
                }
            }

            l1 = l1Temp; // atualiza a pilha de visitas para a próxima iteração
        }

        return Collections.singletonList("Caminho não encontrado"); // caminho não encontrado
    }
    @GetMapping(path = "/bidirecional/{continente}/{inicio}/{fim}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> bidirecional(@PathVariable("continente") String continente, @PathVariable("inicio") Object inicio, @PathVariable("fim") Object fim) {

        Lista l1 = new Lista(); // fila de nós para visitar
        Lista l3 = new Lista(); // fila de nós para visitar da segunda árvore

        Lista l2 = new Lista(); // árvore de busca (nós visitados)
        Lista l4 = new Lista(); // árvore de busca (nós visitados) da segunda árvore

        Map<String, Set<String>> grafo = arq.retornaPaises(continente); // grafo de conexões

        Set<Object> visitados = new HashSet<>(); // conjunto de nós visitados
        Set<Object> visitados2 = new HashSet<>(); // conjunto de nós visitados da segunda árvore

        // insere o nó inicial como raiz da árvore e na fila de visitas
        Node inicial = new Node(inicio, 0, null);
        l1.insereFim(inicial);
        l2.insereFim(inicial);

        //insere o nó final como raiz da segunda árvore e na fila de visitas da segunda árvore
        Node ultimo = new Node(fim, 0, null);
        l3.insereFim(ultimo);
        l4.insereFim(ultimo);

        // marca o nó inicial como visitado
        visitados.add(inicio);
        // marca o nó inicial como visitado da segunda árvore
        visitados2.add(ultimo);

        List<String> caminho1 = new LinkedList<>();
        List<String> caminho2 = new LinkedList<>();
        int ni = 0;
        while(!l1.vazio() || !l3.vazio()){
            //Procura na árvore 1 que começa pelo início
            while (!l1.vazio()) {
                if(ni != l1.getInicio().getValor2()){
                    break;
                }
                Node atual = l1.removeInicio(); // remove o primeiro nó da fila
                caminho1.add((String) atual.getValor1());
                // verifica se encontrou o nó objetivo
                if (atual.getValor1().equals(fim)) {
                    return caminho1; // retorna a árvore de busca (caminho encontrado)
                }

                Set<String> conexoes = grafo.get((String) atual.getValor1()); // obtém as conexões do nó atual
                if (conexoes != null) { // se há conexões
                    for (Object conexao : conexoes) { // para cada conexão
                        if (!visitados.contains(conexao)) { // se a conexão não foi visitada
                            Node novo = new Node(conexao, atual.getValor2() + 1, atual); // cria um nó com a conexão
                            l1.insereFim(novo); // insere o novo nó na fila de visitas
                            l2.insereFim(novo); // insere o novo nó na árvore de busca
                            visitados.add(conexao); // marca a conexão como visitada
                        }
                    }
                }
            }

            //Procura na árvore 2 que começa pelo final ou objetivo
            while (!l3.vazio()) {
                if(ni != l3.getInicio().getValor2()){
                    break;
                }

                Node atual = l3.removeInicio(); // remove o primeiro nó da fila
                caminho2.add((String) atual.getValor1());
                // verifica se encontrou o nó objetivo
                if (atual.getValor1().equals(inicio)) {
                    return caminho2; // retorna a árvore de busca (caminho encontrado)
                }

                Set<String> conexoes = grafo.get((String) atual.getValor1()); // obtém as conexões do nó atual
                if (conexoes != null) { // se há conexões
                    for (Object conexao : conexoes) { // para cada conexão
                        if (!visitados2.contains(conexao)) { // se a conexão não foi visitada
                            Node novo = new Node(conexao, atual.getValor2() + 1, atual); // cria um nó com a conexão
                            l3.insereFim(novo); // insere o novo nó na fila de visitas
                            l4.insereFim(novo); // insere o novo nó na árvore de busca
                            visitados2.add(conexao); // marca a conexão como visitada
                        }
                    }
                }
            }
            ni += 1;
        }

        return Collections.singletonList("Caminho não encontrado"); // caminho não encontrado
    }
    @GetMapping("/custouniforme/{continente}/{inicio}/{fim}")
    public List<String> custoUniforme(@PathVariable("continente") String continente,
                                 @PathVariable("inicio") String inicio,
                                 @PathVariable("fim") String fim) {

        Map<String, Set<String>> mapaLocais = arq.retornaPaises(continente);
        String coordenada = veirificaPaisRetornaCoordenadas(continente);
        Map<String, NodeEstrela> coordenadas = arq.retornaCoordenadasCapitais(coordenada);
        PriorityQueue<NodeEstrela> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeEstrela::getCusto));

        Map<String, NodeEstrela> visitados = new HashMap<>();
        NodeEstrela noPartida = coordenadas.get(inicio);
        noPartida.setCusto(0);
        noPartida.setCustoAproximado(menorDistanciaEntreDoisPaises(inicio, fim, coordenada));
        noPartida.setFn(calculoDoFn(noPartida.getCusto(),noPartida.getCustoAproximado()));
        pq.add(noPartida);

        List<String> caminho = new ArrayList<>();

        while (!pq.isEmpty()) {
            NodeEstrela atual = pq.poll();

            if (visitados.containsKey(atual.getNome())) {
                continue;
            }

            visitados.put(atual.getNome(), atual);

            if (Objects.equals(atual.getNome(), fim)) {

                NodeEstrela no = atual;
                while (no != null) {
                    caminho.add(no.getNome());
                    no = no.getPai();
                }
                Collections.reverse(caminho);
                return caminho;
            }

            Set<String> conexoes = mapaLocais.get(atual.getNome());
            for (String conexao : conexoes) {
                if (visitados.containsKey(conexao)) {
                    continue;
                }
                if (!coordenadas.containsKey(conexao)) {
                    continue;
                }
                NodeEstrela novoNo = coordenadas.get(conexao);
                double custo = atual.getCusto() + Math.abs(calculoDoCusto(conexao, coordenada));
                if (!visitados.containsKey(conexao) || custo < novoNo.getCusto()) {
                    novoNo.setCusto((int) custo);
                    novoNo.setCustoAproximado(menorDistanciaEntreDoisPaises(conexao, fim, coordenada));
                    novoNo.setFn(calculoDoFn(novoNo.getCusto(), novoNo.getCustoAproximado()));
                    novoNo.setPai(atual);
                    pq.add(novoNo);
                }
            }
        }

        return Collections.singletonList("Caminho não encontrado");
    }
    @GetMapping("/aestrela/{continente}/{inicio}/{fim}")
    public List<String> aEstrela(@PathVariable("continente") String continente,
                               @PathVariable("inicio") String inicio,
                               @PathVariable("fim") String fim) {

        Map<String, Set<String>> mapaLocais = arq.retornaPaises(continente);
        String coordenada = veirificaPaisRetornaCoordenadas(continente);
        Map<String, NodeEstrela> coordenadas = arq.retornaCoordenadasCapitais(coordenada);
        PriorityQueue<NodeEstrela> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeEstrela::getFn));

        Map<String, NodeEstrela> visitados = new HashMap<>();
        NodeEstrela noPartida = coordenadas.get(inicio);
        noPartida.setCusto(0);
        noPartida.setCustoAproximado(menorDistanciaEntreDoisPaises(inicio, fim, coordenada));
        noPartida.setFn(calculoDoFn(noPartida.getCusto(),noPartida.getCustoAproximado()));
        pq.add(noPartida);

        List<String> caminho = new ArrayList<>();

        while (!pq.isEmpty()) {
            NodeEstrela atual = pq.poll();

            if (visitados.containsKey(atual.getNome())) {
                continue;
            }

            visitados.put(atual.getNome(), atual);

            if (Objects.equals(atual.getNome(), fim)) {

                NodeEstrela no = atual;
                while (no != null) {
                    caminho.add(no.getNome());
                    no = no.getPai();
                }
                Collections.reverse(caminho);
                return caminho;
            }

            Set<String> conexoes = mapaLocais.get(atual.getNome());
            for (String conexao : conexoes) {
                if (visitados.containsKey(conexao)) {
                    continue;
                }
                if (!coordenadas.containsKey(conexao)) {
                    continue;
                }
                NodeEstrela novoNo = coordenadas.get(conexao);
                double custo = atual.getCusto() + Math.abs(calculoDoCusto(conexao, coordenada));
                if (!visitados.containsKey(conexao) || custo < novoNo.getCusto()) {
                    novoNo.setCusto((int) custo);
                    novoNo.setCustoAproximado(menorDistanciaEntreDoisPaises(conexao, fim, coordenada));
                    novoNo.setFn(calculoDoFn(novoNo.getCusto(), novoNo.getCustoAproximado()));
                    novoNo.setPai(atual);
                    pq.add(novoNo);
                }
            }
        }

        return Collections.singletonList("Caminho não encontrado");
    }

    @GetMapping("/greedy/{continente}/{inicio}/{fim}")
    public List<String> greedy(@PathVariable("continente") String continente,
                              @PathVariable("inicio") String inicio,
                              @PathVariable("fim") String fim ) {

        Map<String, Set<String>> mapaLocais = arq.retornaPaises(continente);
        String coordenada = veirificaPaisRetornaCoordenadas(continente);
        Map<String, NodeEstrela> coordenadas = arq.retornaCoordenadasCapitais(coordenada);

        PriorityQueue<NodeEstrela> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeEstrela::getCustoAproximado));

        Map<String, NodeEstrela> visitados = new HashMap<>();
        NodeEstrela noPartida = coordenadas.get(inicio);
        noPartida.setCusto(0);
        noPartida.setCustoAproximado(menorDistanciaEntreDoisPaises(inicio, fim, coordenada));
        pq.add(noPartida);

        List<String> caminho = new ArrayList<>();

        while (!pq.isEmpty()) {
            NodeEstrela atual = pq.poll();

            if (visitados.containsKey(atual.getNome())) {
                continue;
            }

            visitados.put(atual.getNome(), atual);

            if (Objects.equals(atual.getNome(), fim)) {

                NodeEstrela no = atual;
                while (no != null) {
                    caminho.add(no.getNome());
                    no = no.getPai();
                }

                return caminho;
            }

            Set<String> conexoes = mapaLocais.get(atual.getNome());
            for (String conexao : conexoes) {
                if (visitados.containsKey(conexao)) {
                    continue;
                }
                if (!coordenadas.containsKey(conexao)) {
                    continue;
                }
                NodeEstrela novoNo = coordenadas.get(conexao);
                double custo = atual.getCusto() + Math.abs(calculoDoCusto(conexao, coordenada));
                if (!visitados.containsKey(conexao) || custo < novoNo.getCusto()) {
                    novoNo.setCusto((int) custo);
                    novoNo.setCustoAproximado(menorDistanciaEntreDoisPaises(conexao, fim, coordenada));
                    novoNo.setPai(atual);
                    pq.add(novoNo);
                }
            }
        }

        return Collections.singletonList("Caminho não encontrado");
    }
    @GetMapping("/aia/{continente}/{inicio}/{fim}/{limite}")
    public List<String> aia(@PathVariable("continente") String continente,
                            @PathVariable("inicio") String inicio,
                            @PathVariable("fim") String fim,
                            @PathVariable("limite") int limite) {

        Map<String, Set<String>> mapaLocais = arq.retornaPaises(continente);
        String coordenada = veirificaPaisRetornaCoordenadas(continente);
        Map<String, NodeEstrela> coordenadas = arq.retornaCoordenadasCapitais(coordenada);
        PriorityQueue<NodeEstrela> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeEstrela::getFn));

        Map<String, NodeEstrela> visitados = new HashMap<>();
        NodeEstrela noPartida = coordenadas.get(inicio);
        noPartida.setCusto(0);
        noPartida.setCustoAproximado(menorDistanciaEntreDoisPaises(inicio, fim, coordenada));
        noPartida.setFn(calculoDoFn(noPartida.getCusto(), noPartida.getCustoAproximado()));
        pq.add(noPartida);

        List<String> caminho = new ArrayList<>();

        while (!pq.isEmpty()) {
            NodeEstrela atual = pq.poll();

            if (visitados.containsKey(atual.getNome())) {
                continue;
            }

            visitados.put(atual.getNome(), atual);

            if (Objects.equals(atual.getNome(), fim)) {
                NodeEstrela no = atual;
                while (no != null) {
                    caminho.add(no.getNome());
                    no = no.getPai();
                }
                Collections.reverse(caminho);
                return caminho;
            }

            Set<String> conexoes = mapaLocais.get(atual.getNome());
            for (String conexao : conexoes) {
                if (visitados.containsKey(conexao)) {
                    continue;
                }
                if (!coordenadas.containsKey(conexao)) {
                    continue;
                }

                NodeEstrela novoNo = coordenadas.get(conexao);

                int v2 = atual.getCusto() + Math.abs(calculoDoCusto(conexao, coordenada));
                int v1 = v2 + novoNo.getCustoAproximado();

                if (v1 <= limite && (!visitados.containsKey(conexao) || v2 < novoNo.getCusto())) {
                    novoNo.setCusto(v2);
                    novoNo.setCustoAproximado(menorDistanciaEntreDoisPaises(conexao, fim, coordenada));
                    novoNo.setFn(calculoDoFn(novoNo.getCusto(), novoNo.getCustoAproximado()));
                    novoNo.setPai(atual);
                    pq.add(novoNo);
                }
            }
        }

        return Collections.singletonList("Caminho não encontrado");
    }
}

package com.relnc.api.buscamapa.controller;

import com.relnc.api.buscamapa.file.Arquivo;
import com.relnc.api.buscamapa.model.MetodoLabel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metodos")
@CrossOrigin(origins = "*")
public class MetodoLabelController {
    Arquivo arq = new Arquivo();
    @GetMapping("/listaMetodosNomes")
    public List<MetodoLabel> listaMetodos(){
        return arq.retornaNomeDosMetodos();
    }
}

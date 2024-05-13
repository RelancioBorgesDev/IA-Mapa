package com.relnc.api.buscamapa.controller;

import com.relnc.api.buscamapa.file.File;
import com.relnc.api.buscamapa.model.MethodLabel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metodos")
@CrossOrigin(origins = "*")
public class MethodLabelController {
    File arq = new File();
    @GetMapping("/listaMetodosNomes")
    public List<MethodLabel> listaMetodos(){
        return arq.retornaNomeDosMetodos();
    }
}

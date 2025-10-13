package com.API_BioMeasure.Project.Controller;



import com.API_BioMeasure.Project.Model.dados;

import com.API_BioMeasure.Project.Service.DadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DadosController {

    @Autowired
    private DadosService dadosService;

    // Somente acessível com token válido
    @GetMapping("/dados")
    public ResponseEntity<List<dados>> listarDados() {
        List<dados> dados = dadosService.listarDados();
        return ResponseEntity.ok(dados);
    }
}
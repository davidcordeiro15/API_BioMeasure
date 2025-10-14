package com.API_BioMeasure.Project.Service;



import com.API_BioMeasure.Project.Model.dados;
import com.API_BioMeasure.Project.Repository.DadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DadosService {

    @Autowired
    private DadosRepository dadosRepository;

    public List<dados> listarDados() {
        return dadosRepository.findAll();
    }

    public List<dados> buscarPorResponsavel(String nomeResponsavel) {
        return dadosRepository.findByNomeResponsavelContainingIgnoreCase(nomeResponsavel);
    }
}


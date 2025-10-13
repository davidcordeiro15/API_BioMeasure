package com.API_BioMeasure.Project.Repository;

import com.API_BioMeasure.Project.Model.dados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosRepository extends JpaRepository<dados, Integer> {

}

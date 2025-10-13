package com.API_BioMeasure.Project.Repository;



import com.API_BioMeasure.Project.Model.usuarios;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<usuarios, Integer> {

    usuarios findByEmailAndSenha(String email, String senha);
    default usuarios verificarCredenciais(String email, String senha) {
        return findByEmailAndSenha(email, senha);
    }

}


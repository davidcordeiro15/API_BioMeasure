package com.API_BioMeasure.Project.Service;


import com.API_BioMeasure.Project.DTO.UserDTO;
import com.API_BioMeasure.Project.Model.usuarios;
import com.API_BioMeasure.Project.Repository.UserRepository;
import com.API_BioMeasure.Project.Security.JwtUtil;
import jakarta.transaction.Transactional;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public List<UserDTO> listarUsuarios() {
        List<usuarios> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).toList();
    }

    @Transactional
    public String loginComToken(String email, String senha) {
        boolean autenticado = !userRepository.findByEmailAndSenha(email, senha).toString().isEmpty();

        if (autenticado) {
            return JwtUtil.generateToken(email);
        } else {
            return null;
        }
    }

}
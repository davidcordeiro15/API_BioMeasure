package com.API_BioMeasure.Project.DTO;


import com.API_BioMeasure.Project.Model.usuarios;
import com.API_BioMeasure.Project.Repository.UserRepository;
import org.springframework.beans.BeanUtils;


public class UserDTO {
    private int id;

    private String email;
    private String senha;

    public UserDTO(UserRepository user) {
        BeanUtils.copyProperties(user, this);
    }
    public UserDTO() {}

    public UserDTO(usuarios userModel) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}


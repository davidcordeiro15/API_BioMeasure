package com.API_BioMeasure.Project.Service;

import com.API_BioMeasure.Project.DTO.UserDTO;
import com.API_BioMeasure.Project.Model.usuarios;
import com.API_BioMeasure.Project.Repository.UserRepository;
import com.API_BioMeasure.Project.Security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service para gerenciamento de usuários
 * Responsável pela lógica de negócio relacionada a autenticação e usuários
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Realiza login e retorna token JWT se as credenciais forem válidas
     *
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @return Token JWT se autenticado, ou lança exceção
     * @throws RuntimeException Se credenciais inválidas
     */
    @Transactional
    public String loginComToken(String email, String senha) {
        try {
            // Validar entrada
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email não pode estar vazio");
            }

            if (senha == null || senha.trim().isEmpty()) {
                throw new IllegalArgumentException("Senha não pode estar vazia");
            }

            System.out.println("🔐 Tentando autenticar usuário: " + email);

            // ✅ CORREÇÃO: Buscar usuário corretamente
            usuarios usuario = userRepository.findByEmailAndSenha(email, senha);

            // Verificar se usuário foi encontrado
            if (usuario == null) {
                System.err.println("❌ Credenciais inválidas para: " + email);
                throw new RuntimeException("Credenciais inválidas! Email ou senha incorretos.");
            }

            // Gerar token JWT
            String token = jwtUtil.generateToken(email);

            System.out.println("✅ Login bem-sucedido para: " + email);
            System.out.println("🎫 Token gerado com sucesso");

            return token;

        } catch (IllegalArgumentException e) {
            System.err.println("❌ Erro de validação: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("❌ Erro ao fazer login: " + e.getMessage());
            throw new RuntimeException("Erro ao processar login: " + e.getMessage(), e);
        }
    }






}
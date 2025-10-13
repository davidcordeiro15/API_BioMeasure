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
     * Lista todos os usuários do sistema
     *
     * @return Lista de UserDTO com todos os usuários
     */
    @Transactional
    public List<UserDTO> listarUsuarios() {
        try {
            List<usuarios> users = userRepository.findAll();

            if (users.isEmpty()) {
                System.out.println("⚠️ Nenhum usuário encontrado no banco de dados");
            } else {
                System.out.println("✅ " + users.size() + " usuários encontrados");
            }

            return users.stream()
                    .map(UserDTO::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("❌ Erro ao listar usuários: " + e.getMessage());
            throw new RuntimeException("Erro ao listar usuários", e);
        }
    }

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

    /**
     * Valida um token JWT
     *
     * @param token Token JWT a ser validado
     * @param email Email do usuário
     * @return true se válido, false caso contrário
     */
    public boolean validarToken(String token, String email) {
        try {
            if (token == null || token.trim().isEmpty()) {
                System.err.println("❌ Token vazio ou nulo");
                return false;
            }

            boolean valido = jwtUtil.validateToken(token, email);

            if (valido) {
                System.out.println("✅ Token válido para: " + email);
            } else {
                System.err.println("❌ Token inválido para: " + email);
            }

            return valido;

        } catch (Exception e) {
            System.err.println("❌ Erro ao validar token: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca um usuário por ID
     *
     * @param id ID do usuário
     * @return UserDTO do usuário encontrado
     * @throws RuntimeException Se usuário não encontrado
     */
    @Transactional
    public UserDTO buscarUsuarioPorId(Integer id) {
        try {
            Optional<usuarios> usuario = userRepository.findById(id);

            if (usuario.isEmpty()) {
                throw new RuntimeException("Usuário com ID " + id + " não encontrado");
            }

            System.out.println("✅ Usuário encontrado: ID " + id);
            return new UserDTO(usuario.get());

        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar usuário por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
    }

    /**
     * Busca um usuário por email
     *
     * @param email Email do usuário
     * @return usuarios encontrado ou null
     */
    @Transactional
    public usuarios buscarUsuarioPorEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email não pode estar vazio");
            }

            // Você precisará adicionar este método no UserRepository
            // usuarios usuario = userRepository.findByEmail(email);

            List<usuarios> todosUsuarios = userRepository.findAll();
            return todosUsuarios.stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email))
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar usuário por email: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
    }

    /**
     * Registra um novo usuário
     *
     * @param usuario Dados do novo usuário
     * @return Usuario cadastrado
     * @throws RuntimeException Se email já existe
     */
    @Transactional
    public usuarios registrarUsuario(usuarios usuario) {
        try {
            // Validações
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email é obrigatório");
            }

            if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
                throw new IllegalArgumentException("Senha é obrigatória");
            }

            // Verificar se email já existe
            usuarios usuarioExistente = buscarUsuarioPorEmail(usuario.getEmail());
            if (usuarioExistente != null) {
                throw new RuntimeException("Email já cadastrado no sistema");
            }

            // Salvar usuário
            usuarios novoUsuario = userRepository.save(usuario);

            System.out.println("✅ Novo usuário cadastrado: " + novoUsuario.getEmail());

            return novoUsuario;

        } catch (IllegalArgumentException e) {
            System.err.println("❌ Erro de validação: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("❌ Erro ao registrar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao registrar usuário: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza dados de um usuário
     *
     * @param id ID do usuário
     * @param usuarioAtualizado Dados atualizados
     * @return Usuario atualizado
     * @throws RuntimeException Se usuário não encontrado
     */
    @Transactional
    public usuarios atualizarUsuario(Integer id, usuarios usuarioAtualizado) {
        try {
            Optional<usuarios> usuarioExistente = userRepository.findById(id);

            if (usuarioExistente.isEmpty()) {
                throw new RuntimeException("Usuário com ID " + id + " não encontrado");
            }

            usuarios usuario = usuarioExistente.get();

            // Atualizar campos (não atualizar email se já existir)
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                usuario.setSenha(usuarioAtualizado.getSenha());
            }

            usuarios atualizado = userRepository.save(usuario);

            System.out.println("✅ Usuário atualizado: ID " + id);

            return atualizado;

        } catch (Exception e) {
            System.err.println("❌ Erro ao atualizar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    /**
     * Deleta um usuário
     *
     * @param id ID do usuário a ser deletado
     * @throws RuntimeException Se usuário não encontrado
     */
    @Transactional
    public void deletarUsuario(Integer id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new RuntimeException("Usuário com ID " + id + " não encontrado");
            }

            userRepository.deleteById(id);

            System.out.println("✅ Usuário deletado: ID " + id);

        } catch (Exception e) {
            System.err.println("❌ Erro ao deletar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }

    /**
     * Verifica se um usuário existe por email
     *
     * @param email Email a verificar
     * @return true se existe, false caso contrário
     */
    public boolean usuarioExiste(String email) {
        try {
            usuarios usuario = buscarUsuarioPorEmail(email);
            return usuario != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Conta total de usuários cadastrados
     *
     * @return Número total de usuários
     */
    public long contarUsuarios() {
        try {
            long total = userRepository.count();
            System.out.println("📊 Total de usuários: " + total);
            return total;
        } catch (Exception e) {
            System.err.println("❌ Erro ao contar usuários: " + e.getMessage());
            throw new RuntimeException("Erro ao contar usuários", e);
        }
    }

    /**
     * Renova o token JWT de um usuário
     *
     * @param tokenAntigo Token JWT antigo
     * @return Novo token JWT
     */
    public String renovarToken(String tokenAntigo) {
        try {
            String email = jwtUtil.extractUsername(tokenAntigo);

            // Verificar se usuário ainda existe
            usuarios usuario = buscarUsuarioPorEmail(email);
            if (usuario == null) {
                throw new RuntimeException("Usuário não encontrado");
            }

            // Gerar novo token
            String novoToken = jwtUtil.generateToken(email);

            System.out.println("🔄 Token renovado para: " + email);

            return novoToken;

        } catch (Exception e) {
            System.err.println("❌ Erro ao renovar token: " + e.getMessage());
            throw new RuntimeException("Erro ao renovar token", e);
        }
    }
}
# 🧬 API BioMeasure – Sistema de Gestão de Usuários e Dados Biométricos

## 📘 Descrição do Projeto
A **API BioMeasure** é uma aplicação **Spring Boot 3.5.6** desenvolvida em **Java 21**, projetada para gerenciar **usuários** e **dados biométricos** de medições laboratoriais.  
Ela oferece endpoints RESTful para autenticação via **JWT**, cadastro e consulta de dados, integrando-se com **Oracle Database** por meio do **Spring Data JPA**.


---

## 👥 Integrantes
- **RM 557538 – David Cordeiro**
- **RM 555619 – Tiago Morais**
- **RM 557065 – Vinicius Augusto**

---

## 🚀 Funcionalidades Principais

### 🔐 Autenticação (JWT)
- Endpoint: `/login`
- Recebe credenciais de usuário e retorna um **token JWT**.
- Implementação baseada em `JwtAuthenticationFilter`, `JwtUtil` e `SecurityConfig`.

### 📊 Dados Biométricos
- Endpoint: `/dados`
- Permite listar e consultar dados biométricos associados as análise realizadas.

### 🔎 Filtro por Responsável
- Endpoint: `/buscar-por-responsavel`
- Filtra registros de dados conforme o nome do responsável.
- Recebe um objeto `UserFilterRequestDTO` no corpo da requisição.

---

## 🗂 Estrutura do Projeto

```bash
src/
└── main/
    ├── java/
    │   └── com.API_BioMeasure.Project/
    │       ├── Controller/
    │       │   ├── DadosController.java           # Endpoints de dados biométricos
    │       │   └── UserController.java            # Endpoints de autenticação
    │       │
    │       ├── DTO/
    │       │   ├── DadosDTO.java                  # Transferência de dados de medições
    │       │   ├── UserDTO.java                   # Transferência de dados de usuários
    │       │   └── UserFilterRequestDTO.java      # DTO de filtro por responsável
    │       │
    │       ├── Model/
    │       │   ├── dados/                         # Entidades relacionadas às medições
    │       │   └── usuarios/                      # Entidades de usuários
    │       │
    │       ├── Repository/
    │       │   ├── DadosRepository.java           # Interface JPA de dados
    │       │   └── UserRepository.java            # Interface JPA de usuários
    │       │
    │       ├── Security/
    │       │   ├── JwtAuthenticationFilter.java   # Filtro de autenticação JWT
    │       │   ├── JwtUtil.java                   # Utilitário de geração e validação de tokens
    │       │   └── SecurityConfig.java            # Configuração de segurança Spring
    │       │
    │       ├── Service/
    │       │   ├── DadosService.java              # Regras de negócio de medições
    │       │   └── UserService.java               # Regras de negócio de usuários
    │       │
    │       └── ProjectApplication.java            # Classe principal da aplicação Spring Boot
    │
    └── resources/
        ├── application.properties                 # Configuração do banco de dados Oracle
        └── static/ e templates/                   # (opcional) conteúdo estático ou de teste
```

---

## ⚙️ Tecnologias e Dependências

| Categoria | Tecnologia / Biblioteca |
|------------|------------------------|
| Framework Principal | Spring Boot 3.5.6 |
| Linguagem | Java 21 |
| ORM / Persistência | Spring Data JPA, Jakarta Persistence API |
| Banco de Dados | Oracle (Driver: `ojdbc11`) |
| Autenticação | Spring Security + JWT (`jjwt 0.11.5`) |
| Utilitários | Lombok, DevTools |
| Testes | Spring Boot Starter Test (JUnit 5) |

---

## 🔌 Endpoints da API

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `POST` | `/login` | Realiza autenticação e retorna token JWT |
| `GET`  | `/dados` | Lista todos os dados biométricos registrados |
| `POST` | `/buscar-por-responsavel` | Filtra dados pelo nome do responsável |

### 🔑 Exemplo de Login
```json
POST /login
{
  "email": "usuario@exemplo.com",
  "senha": "123456"
}
```

**Resposta**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

---

## ⚙️ Configuração do Banco de Dados

No arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ Como Executar

### 1️⃣ Clonar o Repositório
```bash
git clone https://github.com/davidcordeiro15/API_BioMeasure.git
```

### 2️⃣ Build e Execução
```
Rode o projeto no seu editor de código
```

### 3️⃣ Acesso à API
```
http://localhost:8080/
```

---

## 🔒 Segurança JWT

A API utiliza **JSON Web Tokens (JWT)** para proteger endpoints autenticados.  
O processo é:

1. O usuário faz login com email/senha.
2. Recebe um token JWT válido.
3. Envia o token no cabeçalho `Authorization: Bearer <token>` em chamadas subsequentes.
4. O filtro `JwtAuthenticationFilter` valida o token antes de permitir o acesso.

---

## 🧠 Boas Práticas Adotadas
- Padrão de projeto **MVC + Service + Repository**
- Uso de **DTOs** para isolamento da camada de domínio
- **Injeção de dependência** via Spring
- **Configuração modular** de segurança (Jwt, filtros e permissões)
- **Padrões RESTful** nos endpoints

---


## 📄 Licença
Este projeto foi desenvolvido como parte do **Challenge FIAP – DASA**,  
destinado ao estudo de **API REST segura** com **Spring Boot, JPA e JWT**.

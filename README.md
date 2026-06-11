# Library-auth 🔐

Este é o microsserviço responsável pela segurança, controle de acesso e gerenciamento de sessões do ecossistema da **Livraria**. Ele foi desenvolvido como parte de um projeto pessoal focado em arquitetura de microsserviços.

## 🚀 Escopo e Foco do Serviço

O **Library-auth** centraliza toda a lógica de identidade do sistema. Suas principais responsabilidades incluem:

* **Autenticação:** Validação de credenciais de usuários.
* **Geração de Tokens:** Emissão de tokens seguros utilizando JWT (JSON Web Tokens) para comunicação *stateless* entre os demais microsserviços.
* **Invalidação de Tokens (Logout/Blacklist):** Mecanismo de invalidação de tokens ativos utilizando o **Redis** para garantir o encerramento rápido e seguro de sessões.

---

## 🛣️ Endpoints da API

Abaixo estão descritas as rotas disponíveis no serviço expostas sob o prefixo `/auth`.

### 1. Criar Conta (Register)

* **Rota:** `POST /auth/register`
* **Descrição:** Cria uma nova credencial no sistema de livraria.
* **Corpo da Requisição (JSON):**

```json
{
  "email": "usuario@email.com",
  "nickname": "dev_thiago",
  "password": "sua_senha_segura"
}

```

* **Respostas:**
* `201 Created`: Usuário registrado com sucesso.
* `400 Bad Request`: Dados inválidos ou e-mail já cadastrado.



### 2. Autenticar (Login)

* **Rota:** `POST /auth/login`
* **Descrição:** Valida as credenciais enviadas e gera um token de acesso JWT.
* **Corpo da Requisição (JSON):**

```json
{
  "email": "usuario@email.com",
  "password": "sua_senha_segura"
}

```

* **Respostas:**
* `202 Accepted`: Autenticação bem-sucedida. Retorna o token gerado.
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}

```


* `401 Unauthorized`: Credenciais incorretas ou inválidas.



### 3. Encerrar Sessão (Logout)

* **Rota:** `POST /auth/logout`
* **Descrição:** Invalida o token JWT atual enviando-o para uma lista de bloqueio (*blacklist*) temporária no Redis.
* **Cabeçalhos Necessários:**
* `Authorization`: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`


* **Respostas:**
* `244 No Content`: Logout processado e sessão encerrada localmente.
* `401 Unauthorized`: Token ausente ou já expirado.



---

## 🛠️ Tecnologias Principais

* **Java 17** & **Spring Boot 4.0.6**
* **Spring Security** (Controle de acesso e filtros)
* **PostgreSQL 17** (Persistência de dados de usuários e permissões)
* **Redis 7** (Cache de alta performance para gerenciamento e invalidação de tokens)
* **Docker & Docker Compose** (Orquestração simplificada do ambiente de desenvolvimento)

---

## 🏁 Como Inicializar o Projeto

Graças à integração do módulo `spring-boot-docker-compose` presente no projeto, a inicialização do banco de dados e do cache ocorre de forma automatizada ao rodar a aplicação.

### 1. Configurando as Variáveis de Ambiente

Antes de subir a aplicação, duplique o arquivo `.env.example`, renomeie-o para `.env` na raiz do projeto e preencha com os valores desejados:

```bash
# PostgreSQL Config
POSTGRES_USER=seu_usuario
POSTGRES_PW=sua_senha
POSTGRES_DB=library_auth_db
POSTGRES_URL=jdbc:postgresql://localhost:5433/library_auth_db

# Redis Config
REDIS_PW=sua_senha_do_redis

# Security Config
JWT_SECRET=uma_chave_secreta_e_longa_aqui

```

### 2. Executando a Aplicação

Certifique-se de ter o **Docker** instalado e rodando em sua máquina. Você pode compilar e rodar a aplicação através do Maven:

```bash
# Limpar e compilar o projeto
./mvnw clean package

# Iniciar a aplicação (O Spring Boot subirá o compose.dev.yaml automaticamente)
./mvnw spring-boot:run

```

Se preferir subir os serviços do Docker manualmente em segundo plano antes de rodar a aplicação por uma IDE, utilize:

```bash
docker compose -f compose.dev.yaml up -d

```

---

## 📚 Detalhes e Documentação Aprofundada

Para entender melhor as decisões de design, modelagem e estratégias de validação deste microsserviço, consulte a nossa pasta interna de documentação localizada em `/docs`. Lá você encontrará os seguintes arquivos explicativos:

* 📄 **`ARCHITECTURE.md`**: Visão geral da arquitetura de software e fluxo de dados do microsserviço.
* 🗄️ **`DATABASE.md`**: Modelagem do banco de dados relacional e estratégias de indexação/persistência.
* 🧩 **`PATTERNS.md`**: Padrões de projeto (*Design Patterns*) e boas práticas aplicadas no código-fonte.
* 🛡️ **`SECURITY.md`**: Detalhes técnicos sobre o ciclo de vida do JWT, criptografia de senhas e filtros de segurança.
* 🧪 **`TEST.md`**: Estratégia de testes unitários e de integração utilizando JUnit 5, Mockito e H2.
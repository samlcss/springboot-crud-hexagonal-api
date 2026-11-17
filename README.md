# Auto Escola 3ES — API REST

API REST para gestão de uma auto escola: cadastro de usuários, alunos e instrutores, agendamento e cancelamento de aulas (instruções), autenticação com JWT e notificação por e-mail via RabbitMQ.

Projeto desenvolvido durante curso do SENAI.

## Tecnologias

- Java 21 + Spring Boot 3.5
- Spring Web, Spring Data JPA, Bean Validation
- Spring Security + JWT (`java-jwt`)
- MySQL + Flyway (migrations)
- RabbitMQ (fila de eventos de e-mail) + Spring Mail (SMTP Gmail)
- SpringDoc OpenAPI (Swagger UI)
- Integração com a API pública ViaCEP
- Lombok
- Maven, Docker e Docker Compose

## Arquitetura

O código é organizado em camadas inspiradas na Arquitetura Hexagonal (Ports & Adapters):

```
src/main/java/br/com/fiap3es/springbootproject
├── adapters
│   ├── in          # Entrada: controllers REST, consumidor RabbitMQ, ViaCEP
│   └── out         # Saída: repositórios JPA, envio SMTP, publicador RabbitMQ, cliente ViaCEP
├── application
│   ├── core
│   │   ├── domain      # Modelos, enums e value objects
│   │   ├── usecases    # Casos de uso (regras de negócio)
│   │   ├── services    # Serviços de apoio (autenticação, e-mail, CEP)
│   │   └── validation  # Validadores de regras de negócio
│   └── ports
│       ├── in          # Portas de entrada
│       └── out         # Portas de saída (repositórios, publicador de eventos)
├── config          # Segurança, RabbitMQ, documentação
└── exception       # Exceções e tratamento global de erros
```

## Regras de negócio

**Agendamento de instruções**
- Funcionamento de segunda a sábado, das 06:00 às 21:00 (última aula começa às 20:00)
- Horários apenas em horas cheias (08:00, 09:00, ...)
- Antecedência mínima de 30 minutos
- No máximo uma instrução por aluno por dia
- Aluno e instrutor precisam estar ativos
- O instrutor não pode ter outra instrução no mesmo horário
- Um aluno só pode agendar instruções para si mesmo

**Cancelamento**
- Antecedência mínima de 24 horas
- Motivo obrigatório: `ALUNO_DESISTIU`, `INSTRUTOR_CANCELOU` ou `OUTROS`

**Perfis de acesso:** `ADMIN`, `USER`, `ALUNO`, `INSTRUTOR`

## Endpoints

Todas as rotas exigem o header `Authorization: Bearer <token>`, exceto as marcadas como públicas.

| Método | Rota | Descrição |
|---|---|---|
| POST | `/login` | Autenticação, retorna o token JWT (**pública**) |
| GET | `/health-check` | Status da aplicação (**pública**) |
| POST / GET / PUT / PATCH | `/usuarios` | Cadastrar, listar, atualizar e alterar perfil (ADMIN) |
| PATCH | `/usuarios/senha` | Alterar a própria senha |
| GET / DELETE | `/usuarios/{id}` | Detalhar / excluir usuário |
| POST / GET / PUT | `/alunos` | Cadastrar, listar e atualizar alunos |
| GET / DELETE | `/alunos/{id}` | Detalhar / inativar aluno |
| GET | `/alunos/self` | Dados do aluno logado (ALUNO) |
| POST / GET / PUT | `/instrutores` | Cadastrar, listar e atualizar instrutores |
| GET / DELETE | `/instrutores/{id}` | Detalhar / inativar instrutor |
| GET | `/instrutores/self` | Dados do instrutor logado (INSTRUTOR) |
| POST / GET | `/instrucoes` | Agendar e listar instruções |
| GET | `/instrucoes/aluno/{id}` | Instruções de um aluno |
| GET | `/instrucoes/instrutor/{id}` | Instruções de um instrutor |
| POST | `/instrucoes/cancelamentos` | Cancelar instrução |
| GET | `/cep/{cep}` | Consulta de endereço pelo CEP (ViaCEP) |

A documentação completa e interativa fica no Swagger: **http://localhost:8080/swagger-ui.html** (pública).

## Variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto (ele já está no `.gitignore`):

```env
# MySQL (Docker / perfil prod)
MYSQL_ROOT_PASSWORD=
MYSQL_DATABASE=auto_escola_3es
MYSQL_USER=
MYSQL_PASSWORD=

# MySQL local (perfil dev)
MYSQL_DEV_USER=
MYSQL_DEV_PASS=

# JWT
JWT_SECRET=

# RabbitMQ
RABBITMQ_USER=
RABBITMQ_PASS=

# E-mail (Gmail com senha de app)
E_MAIL=
SENHA_APP=
```

## Como executar

### Opção 1 — Docker Compose (tudo junto)

Sobe MySQL, RabbitMQ e a aplicação (perfil `prod`):

```bash
docker compose up -d --build
```

- API: http://localhost:8080
- Painel do RabbitMQ: http://localhost:15672

### Opção 2 — Local (perfil `dev`)

Pré-requisitos: Java 21, MySQL rodando em `localhost:3306` com o banco `auto_escola_3es` e RabbitMQ em `localhost:5672`. Dica: dá para subir só a infraestrutura com `docker compose up -d db rabbitmq`.

Exporte as variáveis de ambiente do `.env` e rode:

```bash
./mvnw spring-boot:run      # Linux/Mac
mvnw.cmd spring-boot:run    # Windows
```

As migrations do Flyway (`src/main/resources/db/migration`) são aplicadas automaticamente na inicialização.

## Testes

```bash
./mvnw test
```

Os endpoints também podem ser testados pelo Swagger UI ou pelo Postman.

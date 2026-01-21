🏦 MiniAutorizador

API REST desenvolvida em Java + Spring Boot para simular um autorizador de cartões, responsável por criar cartões, consultar saldo e realizar transações financeiras de forma segura e concorrente.


### 📌 Funcionalidades

✅ Criar cartão

✅ Consultar saldo do cartão

✅ Realizar transações (débito)

✅ Validações de senha, saldo, numeros negativos e dados invalidos

✅ Controle de concorrência (evita débito duplicado)

✅ Padronização de erros via ErrorCode

✅ Testes unitários, end-to-end e de concorrência

✅ Documentação com swagger

### 📌 Requisitos Funcionais
Cartões

- O sistema deve permitir a criação de cartões de débito.

- O número do cartão deve conter apenas dígitos numéricos.

- O número do cartão deve possuir no mínimo 13 e no máximo 19 dígitos.

- Não deve ser permitido criar um cartão com número vazio ou nulo.

- Não deve ser permitido criar um cartão com senha vazia ou nula.

- Não deve ser permitido criar mais de um cartão com o mesmo número.

- Todo cartão criado deve iniciar com saldo padrão de R$ 500,00.

- O sistema deve permitir consultar o saldo de um cartão existente.

- O sistema deve permitir excluir um cartão existente.

Transações

- O sistema deve permitir realizar transações de débito em um cartão existente.

Uma transação deve conter:

      - número do cartão
      - senha
      - valor a ser debitado

- Não deve ser permitido realizar transações com cartão inexistente.

- Não deve ser permitido realizar transações com senha inválida.

- Não deve ser permitido realizar transações com saldo insuficiente.

- Não deve ser permitido realizar transações com valor zero ou negativo.

- Quando uma transação for autorizada, o valor deve ser debitado do saldo do cartão.

  - O sistema deve persistir as transações realizadas, armazenando:

        número do cartão
      
        valor debitado
      
        saldo após a transação
      
        data/hora da transação

- O sistema deve permitir consultar todas as transações de um cartão.

### 🧱 Arquitetura

O projeto segue uma arquitetura em camadas:

Controller

- Responsável por receber requisições HTTP

- Valida entradas (@Valid)

- Retorna respostas REST

Service

- Contém as regras de negócio

- Orquestra validações e transações

- Controla concorrência

Repository

- Acesso ao banco via Spring Data JPA

Entity

- Representação da tabela Card

Exception

- Tratamento centralizado de erros

- Uso de ErrorCode para padronização

### ⚙️ Tecnologias Utilizadas

- Java 21

- Spring Boot

- Spring Data JPA

- Spring Validation

- Spring Security 

- MySQL 5.7 (Docker)

- Hibernate

- JUnit 5

- Mockito

- Docker & Docker Compose

- Maven

### 🗄️ Banco de Dados

- Banco: MySQL 5.7

- Gerenciado via Docker

Tabela cards

| campo	        | tipo           |
|---------------|----------------|
| numero_cartao | VARCHAR(16) PK |
| senha         | 	VARCHAR       |
| saldo         | 	DECIMAL(10,2) |

Tabela transaction   

| campo	           | tipo            |
|------------------|-----------------|
| id	              | BIGINT PK       |
| numero_cartao    | VARCHAR(16) FK  |
| valor	           | DECIMAL  |
| saldo_resultante | 	DECIMAL |
| created_at	      | TIMESTAMP       |

Saldo inicial padrão: R$ 500,00

A relação da tabela de transação de é 1:N com o cartão.

### 🐳 Subindo o banco com Docker

Dentro da pasta docker:

    docker compose up -d


Verifique se o container está rodando:

    docker ps

### ▶️ Rodando a aplicação
mvn spring-boot:run


    A API estará disponível em:

    http://localhost:8080

### 📮 Endpoints
Criar cartão

    POST /cards
    {
    "numeroCartao": "123456789000000",
    "senha": "1234"
    }

Respostas possíveis:

    201 Created

    422 Unprocessable Entity (cartão já existe)

💰 Consultar saldo

    GET /cards/{numeroCartao}

Resposta:

    {
    "saldo": 500.00
    }


Erros:

    404 Not Found (cartão inexistente)

💳 Realizar transação (débito)

    POST /transacoes

    {
    "numeroCartao": "123456789",
    "senha": "1234",
    "valor": 10.00
    }


Erros possíveis:

    404 – Cartão inexistente

    422 – Senha inválida

    422 – Saldo insuficiente

    422 - Valor Inválido

    422 - Cartão Invalido

### 🔐 Teste de Concorrência e Consistência

O sistema foi projetado para evitar double spending.

Cenário testado:

    Saldo: R$10,00

Duas transações simultâneas de R$10,00

- Apenas uma transação é aprovada

- A outra falha corretamente

- Saldo final = R$0,00

Isso é garantido por:

    @Transactional

    Lock pessimista no banco (SELECT ... FOR UPDATE)


### 🧪 Testes Unitários

O projeto possui testes unitários para:

1 - Validação de senha

2 - Validação de saldo

3 - Criação de cartão

4 - Consulta de saldo

5 - Débito

6 - Valida se o campo numero do cartão esta vazio

7 - Valida se o campo senha esta vazio

8 - Valida se o numero que foi debitado não é negativo

9 - Valida se o valor debitado não é zero

10 - Valida se cria cartão com 13 digitos

11 - Valida se cria cartão com menos de 13 digitos

12 - Valida se cria cartão com 19 digitos

13 - Valida se cria cartão com mais de 19 digitos

14 - Valida se o sistema aceita valor 0

15 - Valida se cria carão com alfabeticos

### Teste end-to-end
Este projeto possui testes end-to-end (E2E) que validam o fluxo completo da aplicação, simulando o comportamento real de um consumidor da API.

Os testes E2E garantem que:

- A aplicação esteja corretamente integrada (Controller, Service, Repository, Banco de Dados e Segurança)

- As regras de negócio funcionem de ponta a ponta

- Os contratos HTTP (status codes e responses) sejam respeitados


### 📘 Documentação da API (Swagger)

Este projeto disponibiliza uma documentação interativa da API utilizando **Swagger (OpenAPI)**.

Após subir a aplicação, a documentação pode ser acessada em:

🔗 **Swagger UI:**  
http://localhost:8080/swagger-ui/index.html#

A API utiliza Basic Authentication.

Para acessar os endpoints protegidos via Swagger:

1. Clique no botão Authorize no Swagger UI
2. Informe:
    - Username: `username`
    - Password: `password`
3. Clique em Authorize

Após isso, os endpoints estarão liberados para teste.



🧠 Decisões de Design

- Uso de ErrorCode para evitar explosão de exceptions

- BusinessException como base para erros de negócio

- GlobalExceptionHandler para centralizar respostas

- Regras de negócio concentradas no domínio (Card)

- Concorrência tratada no nível de banco

Autora

Maria Elizabeth Bárcena Silva

Desenvolvedora Backend Java
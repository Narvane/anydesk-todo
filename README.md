# Task Management API

## 🔧 Como rodar

```bash
git clone https://github.com/seu-usuario/task-management-api.git
cd task-management-api
mvn quarkus:dev
```

## 📑 Documentação

- **Swagger (OpenAPI):** [http://localhost:8080/q/openapi](http://localhost:8080/q/openapi)
- **Dev UI Quarkus:** [http://localhost:8080/q/dev](http://localhost:8080/q/dev)

> Os endpoints estão protegidos com **HTTP Basic Authentication**.  
> Use o botão de **cadeado no Swagger** para autenticar.  
> **Usuário:** `admin`  
> **Senha:** `123`

---

## 🧠 Estrutura do Projeto

O projeto foi dividido em três camadas principais:

### `domain`
Contém os modelos, exceções e **use cases**, representando a lógica de negócio da aplicação.  
Essa camada utiliza apenas dependências essenciais como **injeção de componentes** e **validações de bean**, mantendo-se livre de detalhes de infraestrutura.

### `app`
Camada responsável por expor os **endpoints REST**, controlar requisições e tratar exceções. Serve como **interface externa da aplicação**, sendo a porta de entrada para o domínio.

### `persistence`
Contém os **DAOs**, responsáveis exclusivamente por mapear e transitar dados entre o banco e o domínio, mantendo o domínio isolado da persistência.

---

## 🧱 Arquitetura

A estrutura segue princípios da **Clean Architecture** e do **Domain-Driven Design (DDD)**.

Essa separação por camadas foi adotada por escolha pessoal para demonstrar clareza, testabilidade e domínio conceitual.  
Em um projeto real e menor, possivelmente seria adotada uma abordagem mais direta, como:

- Uso direto do JPA nos modelos
- Substituição dos use cases por services

---

## ✅ Testes

Foram implementados **testes unitários** para:

- **UseCases** (regras de negócio)
- **Resources** (endpoints)
- **Repositories** (persistência)

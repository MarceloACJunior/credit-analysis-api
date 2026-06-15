# credit-analysis-api

Microsserviço responsável pela análise e aprovação de crédito para clientes do ecossistema JazzTech.

## Visão Geral

Dado um cliente (validado via `client-api`) e uma renda mensal, aplica regras de negócio para determinar se o crédito é aprovado e qual o limite disponível. Os resultados são consumidos pelo `card-holder-api` na criação de portadores de cartão.

## Stack

- **Java 17** + **Spring Boot 3.0.6**
- **Spring Cloud OpenFeign** — comunicação com `client-api`
- **Spring Data JPA** + **PostgreSQL**
- **MapStruct** | **Lombok** | **JUnit 5** + **Mockito**

## Rodando

### Opção 1 — Docker Compose (recomendado)

Sobe todos os serviços do ecossistema de uma vez. Ver [README raiz](../README.md).

### Opção 2 — Local

**Pré-requisitos:** Java 17+, Docker, Maven 3.8+, `client-api` rodando na porta 8080.

> **Atenção:** este projeto não possui Maven Wrapper (`.mvn/`). Use o `mvn` instalado no sistema.

```bash
# 1. Subir o banco PostgreSQL (porta 5433)
docker-compose up -d

# 2. Criar as tabelas
docker cp database/ddl.sql postgresql:/tmp/ddl.sql
docker exec postgresql psql -U admin -d postgres -f /tmp/ddl.sql

# 3. Iniciar a aplicação
mvn spring-boot:run

# Parar o banco
docker-compose down
```

A API fica disponível em `http://localhost:9001`.

## Endpoints

| Método | Endpoint | Status | Descrição |
|--------|----------|--------|-----------|
| `POST` | `/credit/analysis` | 202 | Solicitar análise de crédito |
| `GET` | `/credit/analysis` | 200 | Listar todas as análises |
| `GET` | `/credit/analysis/{id}` | 200 | Buscar análise por ID |
| `GET` | `/credit/analysis/findBy-clientId/{clientId}` | 200 | Filtrar por cliente |
| `GET` | `/credit/analysis/findBy-clientCPF?cpf=xxx` | 200 | Filtrar por CPF |

### Exemplo — Solicitar análise

```http
POST /credit/analysis
Content-Type: application/json

{
  "clientId": "uuid-do-cliente",
  "monthlyIncome": 5000.00,
  "requestedAmount": 2000.00
}
```

## Regras de aprovação

| Condição | Resultado | Limite aprovado |
|----------|-----------|-----------------|
| Valor solicitado > renda mensal | ❌ Reprovado | R$ 0 |
| Valor solicitado > 50% da renda | ✅ Aprovado | 15% da renda |
| Valor solicitado ≤ 50% da renda | ✅ Aprovado | 30% da renda |

- Renda máxima considerada: **R$ 50.000**
- Juros anuais: **15%**
- Limite de saque: **10% do limite aprovado**

## Banco de dados

Schema em `database/ddl.sql`. PostgreSQL na porta `5433`, usuário `admin`, senha `senha123`, banco `postgres`.

## Testes

```bash
mvn test    # Todos os testes
mvn verify  # Testes + relatório JaCoCo (target/site/jacoco/)
```

## Microsserviços relacionados

| Serviço | Porta | Função |
|---------|-------|--------|
| `client-api` | 8080 | Cadastro de clientes |
| `credit-analysis-api` | 9001 | **Este serviço** |
| `card-holder-api` | 9002 | Portadores e cartões |

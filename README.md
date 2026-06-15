# credit-analysis-api

Microsserviço responsável pela análise e aprovação de crédito para clientes do ecossistema JazzTech.

## Visão Geral

Dado um cliente (validado via `client-api`) e uma renda mensal, aplica regras de negócio para determinar se o crédito é aprovado e qual o limite disponível. Os resultados são consumidos pelo `card-holder-api` na criação de portadores de cartão.

## Stack

- **Java 17** + **Spring Boot 3.0.6**
- **Spring Cloud OpenFeign** — comunicação com `client-api`
- **Resilience4j (Circuit Breaker)** — proteção da chamada ao `client-api`
- **Spring Data MongoDB**
- **MapStruct** | **Lombok** | **JUnit 5** + **Mockito**

## Rodando

### Opção 1 — Docker Compose (recomendado)

Sobe todos os serviços do ecossistema de uma vez. Ver [README raiz](../README.md).

### Opção 2 — Local

**Pré-requisitos:** Java 17+, Docker, Maven 3.8+, `client-api` rodando na porta 8080.

> **Atenção:** este projeto não possui Maven Wrapper (`.mvn/`). Use o `mvn` instalado no sistema.

```bash
# 1. Subir o MongoDB (porta 27017)
docker run -d --name mongo -p 27017:27017 mongo:7

# 2. Iniciar a aplicação (a collection é criada automaticamente)
mvn spring-boot:run

# Parar o banco
docker stop mongo && docker rm mongo
```

A API fica disponível em `http://localhost:9001`. A URI do Mongo pode ser sobrescrita
via variável de ambiente `SPRING_DATA_MONGODB_URI` (padrão `mongodb://localhost:27017/creditdb`).

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

**MongoDB** na porta `27017`, database `creditdb`, collection `credit_analysis`. Sem schema/DDL —
a collection é criada na primeira inserção. O `id` de cada documento é um UUID gerado pela aplicação.

## Resiliência

A chamada ao `client-api` é protegida por um **circuit breaker (Resilience4j)** integrado ao OpenFeign.
Se o `client-api` estiver fora do ar, após o limiar de falhas o circuito abre e a API responde
**`503 Service Unavailable`** rapidamente, em vez de pendurar a requisição. Erros de negócio do
`client-api` (cliente não encontrado, 4xx) continuam sendo tratados como `400`.

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

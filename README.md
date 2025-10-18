# Bank Accounts Microservice

---

## Overview

This repository contains a Spring Boot microservice for managing bank accounts. 

The project includes:

* A JPA model for bank accounts and customers
* Spring Data repositories (including a custom `@RestResource` on one method)
* REST controllers for CRUD operations
* A GraphQL controller and schema
* DTOs and a simple mapper
* Unit / integration test skeletons
* H2 in-memory database configuration
* OpenAPI (springdoc) dependency for Swagger UI

---

## Project structure (packages and important files)

All packages use the base `com.example.microservice`.

Key packages and files found in the ZIP (exact names preserved):

* `com.example.microservice.JPA`

  * `BankAccount` (entity) — `src/main/java/com/example/microservice/JPA/BankAccount.java`
  * `Customer` (entity) — `src/main/java/com/example/microservice/JPA/Customer.java`
  * `AccountProjection` (projection interface) — `src/main/java/com/example/microservice/JPA/AccountProjection.java`

* `com.example.microservice.enums`

  * `AccountType` (enum) — `src/main/java/com/example/microservice/enums/AccountType.java`

* `com.example.microservice.Repos`

  * `BankAccountRepository` — `src/main/java/com/example/microservice/Repos/BankAccountRepository.java`

    * extends `JpaRepository<BankAccount, String>`
    * defines `@RestResource(path = "/byType") List<BankAccount> findByType(@Param("t") AccountType type);`
  * `CustomerRepository` — `src/main/java/com/example/microservice/Repos/CustomerRepository.java`

* `com.example.microservice.DTO`

  * `BankAccountRequestDTO` — request DTO
  * `BankAccountResponseDTO` — response DTO

* `com.example.microservice.mappers`

  * `AccountMapper` — Spring `@Component` that maps `BankAccount` → `BankAccountResponseDTO`

* `com.example.microservice.Service`

  * `AccountService` (interface)
  * `AccountServiceImpl` (implementation, `@Service`)

* `com.example.microservice.Web`

  * `AccountRestController` — REST controller annotated with `@RestController` and class-level `@RequestMapping("/api")`.
  * `BankAccountGraphQLController` — GraphQL controller using `@QueryMapping` / `@MutationMapping`.

* Application and tests

  * `MicroserviceApplication` — main Spring Boot application class
  * `MicroserviceApplicationTests` — test class (uses `@SpringBootTest`)

* Resources

  * `src/main/resources/application.properties` — application configuration (H2, JPA, etc.)
  * `src/main/resources/graphql/schema.graphqls` — GraphQL schema used by the GraphQL controller

---

## Entities (fields summary)

* **BankAccount** (entity)

  * `id: String` (primary key)
  * `createdAt: Date`
  * `balance: Double`
  * `currency: String`
  * `type: AccountType` (enum; `CURRENT_ACCOUNT`, `SAVING_ACCOUNT`)
  * `customer: Customer` (`@ManyToOne`)

* **Customer** (entity)

  * `id: Long` (generated)
  * `name: String`
  * `bankAccounts: List<BankAccount>` (`@OneToMany` mappedBy "customer")

* **AccountType** (enum)

  * `CURRENT_ACCOUNT`, `SAVING_ACCOUNT`

* **AccountProjection** — projection interface present (used for Spring Data projections if needed)

---

## Repositories

* `BankAccountRepository` (extends `JpaRepository<BankAccount, String>`)

  * Custom Spring Data REST exposure: `@RestResource(path = "/byType")` on `findByType` method.

* `CustomerRepository` (extends `CrudRepository<Customer, Long>`)

Because the project includes `@RestResource` on the repository method, Spring Data REST can expose repository endpoints automatically if Spring Data REST is enabled. In this project `BankAccountRepository` exposes a custom path `/byType`.

---

## DTOs & Mapper

* `BankAccountRequestDTO` — fields: `balance`, `currency`, `type` (and optional date fields as present in the source)
* `BankAccountResponseDTO` — fields: `id`, `createdAt`, `balance`, `currency`, `type`
* `AccountMapper` — a simple Spring `@Component` that copies properties from `BankAccount` to `BankAccountResponseDTO` using `BeanUtils.copyProperties`.

---

## REST API (endpoints — exact paths as in source)

The REST controller class-level mapping is: `@RequestMapping("/api")` (class `AccountRestController`). Below are the method-level mappings **exactly as they appear in the code**:

* `GET  /api/bankAccounts` — list all bank accounts (`getBankAccounts`)
* `GET  /api/bankAccount/{id}` — get a bank account by id (`getBankAccount`)
* `POST /api/bankAccounts` — create a bank account (`save`)
* `PUT  /api/bankAccouts/{id}` — update a bank account (note: path spelled `bankAccouts` in the source) (`updateBankAccount`)
* `DELETE /api/bankAccount/{id}` — delete a bank account (`deleteAccount`)

**Important:** The `PUT` mapping uses the path `/api/bankAccouts/{id}` (a spelling found in the source). The README preserves that spelling so the paths match the code exactly.

### Example `curl` (create)

```bash
curl -X POST http://localhost:8080/api/bankAccounts \
  -H "Content-Type: application/json" \
  -d '{"balance":100.0, "currency":"USD", "type":"CURRENT_ACCOUNT"}'
```

### Example `curl` (get all)

```bash
curl http://localhost:8080/api/bankAccounts
```

---

## GraphQL API

* GraphQL schema file located at: `src/main/resources/graphql/schema.graphqls` (exact file found in the ZIP).
* Default GraphQL endpoint provided by Spring Boot GraphQL: **`/graphql`**.

The schema defines these operations (exact names preserved):

**Queries**

* `accountsList : [BankAccount]`
* `getBankAccountById(id: String) : BankAccount`
* `getCustomers : [Customer]`

**Mutations**

* `addBankAccount(bankAccount: BankAccountDTO) : BankAccount`
* `updateAccountBank(id: String, bankAccount: BankAccountDTO) : BankAccount`
* `deleteAccountBank(id: String) : Boolean`

Input type `BankAccountDTO` is defined in the schema with `balance`, `currency`, `type` fields.

Example GraphQL query (list accounts):

```graphql
query {
  accountsList {
    id
    balance
    currency
    type
  }
}
```

Example GraphQL mutation (add account):

```graphql
mutation {
  addBankAccount(bankAccount: { balance: 12.5, currency: "EUR", type: "SAVING_ACCOUNT" }) {
    id
    balance
    currency
  }
}
```

---

## Configuration & tools

* `application.properties` is present under `src/main/resources/application.properties` (the project uses an H2 in-memory database).
* H2 Console: you can enable and open the H2 console (`/h2-console`) depending on `application.properties` settings.
* OpenAPI / Swagger UI: the `pom.xml` includes `org.springdoc:springdoc-openapi-starter-webmvc-ui`, so on startup you should be able to reach the Swagger UI (commonly at `/swagger-ui.html` or `/swagger-ui/index.html`).
* GraphQL schema is in `src/main/resources/graphql/schema.graphqls` and Spring Boot GraphQL is included in `pom.xml`.

---

## How to build & run

From the project root (the module is `microservice/microservice` in the ZIP):

```bash
cd microservice/microservice
./mvnw clean package
./mvnw spring-boot:run
# or
mvn spring-boot:run
```

The application will start on port `8080` by default.

---

## Tests

There is a `MicroserviceApplicationTests` class under `src/test/java/com/example/microservice/MicroserviceApplicationTests.java` using `@SpringBootTest`.

---



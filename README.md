# Coupon API

API REST para gerenciamento de cupons de desconto, desenvolvida como desafio técnico.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Lombok
- Banco de dados H2
- Bean Validation
- OpenAPI / Swagger
- Maven
- Docker
- JUnit
- Mockito

## Arquitetura

O projeto segue princípios de separação de responsabilidades:

- `controller` → Camada HTTP
- `service` → Regras de aplicação
- `domain` → Regras de negócio encapsuladas
- `repository` → Persistência
- `handler` → Tratamento global de exceções
- `config` → Configurações (OpenAPI)

Regras de negócio estão encapsuladas na entidade de domínio.

---

## Documentação da API (Swagger)

Após subir a aplicação, acesse: http://localhost:8080/swagger-ui/index.html

---

## Banco de Dados (H2)

Console do banco disponível em:

http://localhost:8080/h2-console

---

## Como executar o projeto

### Rodando com Docker Compose

É necessário ter **Docker** instalado na máquina.

```bash
docker-compose up --build
```
 
A aplicação ficará disponível em:
> http://localhost:8080

### Rodando com o Maven

É necessário ter **Java 17** e o **Maven** instalados na máquina.

```bash
mvn clean install
```
```bash
java -jar target/coupon-api-0.0.1-SNAPSHOT.jar
```

---

## Executando os testes
```bash
mvn test 
```

---

## Autor

**Alan Sales**
- [LinkedIn](https://www.linkedin.com/in/alanfsales/)
- [GitHub](https://github.com/alanfsales)

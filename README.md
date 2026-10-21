# Gerenciador de Biblioteca Pessoal

API REST em Java + Spring Boot para cadastrar e organizar livros de uma biblioteca pessoal.

## Stack
- Java 11, Spring Boot 2.7.18
- Spring Web, Spring Data JPA, Bean Validation
- H2 (banco em memória)

## Como executar
```bash
mvn spring-boot:run
```
A API fica disponível em `http://localhost:8080`.

## Como testar
```bash
mvn test
```
Os testes em `BookControllerTests` exercitam o ciclo completo: cadastrar, listar, consultar, atualizar e excluir.

## Endpoints

| Método | Rota          | Descrição                     |
|--------|---------------|--------------------------------|
| POST   | `/livros`     | Cadastra um novo livro         |
| GET    | `/livros`     | Lista todos os livros          |
| GET    | `/livros/{id}`| Consulta um livro pelo id      |
| PUT    | `/livros/{id}`| Atualiza um livro existente    |
| DELETE | `/livros/{id}`| Remove um livro                |

### Exemplo de corpo (POST/PUT)
```json
{
  "titulo": "O Hobbit",
  "autor": "J.R.R. Tolkien",
  "genero": "Fantasia",
  "anoPublicacao": 1937,
  "statusLeitura": "NAO_LIDO"
}
```
`statusLeitura` aceita: `LIDO`, `LENDO`, `NAO_LIDO`.

### Testando manualmente com curl
```bash
curl -X POST http://localhost:8080/livros -H "Content-Type: application/json" \
  -d '{"titulo":"O Hobbit","autor":"J.R.R. Tolkien","genero":"Fantasia","anoPublicacao":1937,"statusLeitura":"NAO_LIDO"}'

curl http://localhost:8080/livros
curl http://localhost:8080/livros/1
curl -X DELETE http://localhost:8080/livros/1
```
# livro

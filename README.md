# Pos Tech Lanchonete

Projeto desenvolvido para o Tech Challenge da Pos Tech FIAP+Alura.
Aplicação para integração de meios de pagamento e controle de pagamentos realizados.

## Video

Gravamos um video sobre a nossa quarta parte do projeto:

- https://youtu.be/MrVDanTUrKI

## Link da projeto no SonarCloud

- [SonarCloud - Pagamento](https://sonarcloud.io/project/overview?id=Diegobbrito_tech-challenge-pagamento)

## Como testar a aplicação com docker compose

Faça download do projeto e na pasta principal rode o comando no terminal:

```bash
   docker-compose --env-file exemplo.env up -d
```

No navegador, abra a pagina do Swagger da aplicação:
http://localhost:8080/lanchonete/swagger-ui/index.html#/

O Swagger está documentado com exemplos de request.

A aplicação é iniciada com dados de categorias, produtos e status no banco de dados.

## Stack utilizada

**Banco de dados:** MongoDB

**Back-end:** Java, Springboot

## Autores

- [Diego B Brito](https://github.com/Diegobbrito)
- [Gustavo Joia](https://github.com/GustavoJoiaP)
# Spring PlayMySongs

Mini sistema web para enviar (upload) e buscar músicas `.mp3`/`.ogg`.

- `PlayMySongsBackEnd/` — API REST em Spring Boot 4.1.1 + MongoDB
- `PlayMySongsFrontEnd/` — páginas HTML/CSS/JS que consomem a API

## Pré-requisitos

- Java 25 (ou superior)
- MongoDB rodando em `localhost:27017` **ou** um cluster no MongoDB Atlas
  (passo a passo em [GUIA_MONGODB_ATLAS.md](GUIA_MONGODB_ATLAS.md))

Não precisa instalar o Maven: o projeto usa o Maven Wrapper (`mvnw`).

## Como rodar o back-end

Abra um terminal na pasta `PlayMySongsBackEnd/`:

```bash
# Linux/Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Ou abra a pasta `PlayMySongsBackEnd/` no IntelliJ e rode `PlayMySongsBackEndApplication`.

A API sobe em `http://localhost:8080`. O banco usado é o `play_my_songs` (criado automaticamente no primeiro upload).

**Conexão com o banco:** sem configurar nada, o back-end usa o MongoDB local (`mongodb://localhost:27017`).
Para usar o MongoDB Atlas, defina a variável de ambiente `MONGODB_URI` com a string de conexão
(veja o [guia](GUIA_MONGODB_ATLAS.md)). A senha **nunca** deve ser colocada nos arquivos do projeto.

## Endpoints

Todos sob `http://localhost:8080/apis/`. Em caso de falha, respondem **HTTP 400** com `{"mensagem": "..."}`.

| endpoint | tipo | parâmetros | retorno |
|---|---|---|---|
| `music-upload` | POST (multipart/form-data) | `arquivo` (.mp3 ou .ogg), `nome`, `estilo`, `artista` | música gravada (JSON) com `url` |
| `find-musics` | GET | `chave` (query param) | lista de músicas cujo nome, estilo ou artista contém a chave |
| `get-music-styles` | GET | — | lista de estilos musicais (`nome`, `descricao`) |

O arquivo enviado é salvo em `PlayMySongsBackEnd/src/main/resources/static/uploads/` com o nome
`titulo_estilo_artista.extensao` (minúsculas, sem acentos e sem espaços) e fica acessível pela `url` retornada,
por exemplo `http://localhost:8080/uploads/lovemylife_popinternacional_robbiewilliams.mp3`.

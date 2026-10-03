# Guia do grupo — MongoDB Atlas no PlayMySongs

Este guia explica como o banco do projeto funciona no MongoDB Atlas, como cada integrante conecta o
back-end nele, como cadastrar músicas e o que conferir antes da apresentação.

> **Nunca coloque a senha do banco em arquivos do projeto** (código, `application.properties`, README...).
> O repositório vai para o GitHub. A senha é passada só em particular, pelo grupo.

---

## 1. Como funciona

- O **banco é um só**, no Atlas (na nuvem), e é compartilhado pelo grupo todo.
  - Banco: `play_my_songs`
  - Collection: `musicas`
- O back-end lê a string de conexão da **variável de ambiente `MONGODB_URI`**.
  Se ela não estiver definida, ele tenta usar um MongoDB local (`mongodb://localhost:27017`) — que ninguém do
  grupo tem instalado. Aí a página de estilos funciona (não usa o banco), mas **envio e busca ficam carregando
  ~30 s e dão erro**. Ou seja: **sem configurar a variável (item 4), o projeto não funciona.**
- O banco guarda só os **dados** da música (nome, estilo, artista e nome do arquivo).
  **O arquivo `.mp3`/`.ogg` fica no computador de quem fez o upload**, na pasta
  `PlayMySongsBackEnd/src/main/resources/static/uploads/` — essa pasta **não** vai para o GitHub.
  Por isso, uma música enviada por outra pessoa aparece na busca, mas a `url` dela dá **404** no seu computador.

---

## 2. Criar o banco no Atlas (só UMA pessoa faz — já está feito)

Esta parte já foi feita por quem administra o banco do grupo. Fica aqui para referência, caso precise refazer.

1. **Cluster:** em [cloud.mongodb.com](https://cloud.mongodb.com), *Database → Clusters → Create*,
   plano **Free (M0)**, provedor AWS, região **São Paulo (sa-east-1)**. Nome: `Cluster0`.
2. **Usuário do banco:** *Security → Database & Network Access → aba Database Users → Add New Database User*
   - Método: **Password**
   - Usuário: `playmysongs`
   - Senha: **só letras e números** (caracteres como `@ : / # ?` quebram a string de conexão).
     Dica: botão *Autogenerate Secure Password*. Anote a senha.
   - Em **Built-in Role**, marque **Read and write to any database**.
   - Não adicione nada em *Specific Privileges* (se adicionar com o banco em branco, dá o erro *Invalid role database*).
3. **Liberar acesso de rede:** *Security → Database & Network Access → aba IP Access List → Add IP Address →
   Allow Access from Anywhere* (`0.0.0.0/0`) → *Confirm*.
   Isso é necessário porque cada integrante (e a rede da faculdade no dia da apresentação) tem um IP diferente.
4. **Não precisa criar o banco nem a collection.** O MongoDB cria `play_my_songs` e `musicas` sozinho no
   primeiro upload.
5. **(Opcional) Convidar o grupo para ver o painel:** *Security → Project Identity & Access → Invite to Project*,
   informe o e-mail de cada integrante e escolha o papel **Project Data Access Read/Write**.
   Cada um recebe um convite por e-mail. Isso só serve para enxergar os dados pelo site —
   para o back-end funcionar basta a string de conexão.

---

## 3. Pegar a string de conexão

No Atlas: *Database → Clusters → botão **Connect** do Cluster0 → **Drivers*** e copie a string.
O formato final (já com usuário e senha, **sem** os sinais `< >`) é:

```
mongodb+srv://playmysongs:SENHA_AQUI@cluster0.3nrhwoe.mongodb.net/?appName=Cluster0
```

Troque `SENHA_AQUI` pela senha que foi passada no grupo.
Erro comum: deixar `<db_username>` ou `<senha>` com os sinais `< >` — eles **não** fazem parte da string.

---

## 4. Configurar no seu computador (cada integrante)

### Pré-requisitos
- Java 25 ou superior instalado (`java -version` no terminal).
- Projeto clonado do GitHub ou extraído do `.zip`.

### Opção A — IntelliJ (recomendado, igual no Windows e no Mac)

1. Abra no IntelliJ a pasta **`PlayMySongsBackEnd`** (não a pasta `PlayMySongs` de cima — o back-end salva as
   músicas relativo à pasta em que roda; se abrir a pasta errada, os arquivos vão parar no lugar errado).
2. Se o back-end já estiver rodando, pare com **■** (a variável só vale depois de rodar de novo).
3. No topo, ao lado do botão ▶, clique no nome da configuração (`PlayMySongsBackEndApplication`) → **Edit Configurations...**
4. O campo **Environment variables** vem **escondido** na configuração Spring Boot. Para mostrar:
   clique no link azul **Modify options** (à direita, perto de *Build and run*) → na seção **Operating System**,
   marque **Environment variables**.
5. No campo que apareceu, cole:
   ```
   MONGODB_URI=mongodb+srv://playmysongs:SENHA_AQUI@cluster0.3nrhwoe.mongodb.net/?appName=Cluster0
   ```
6. Confira se o **Working directory** é a pasta `PlayMySongsBackEnd`.
7. **OK** e rode com ▶.

Essa configuração fica na pasta `.idea`, que está no `.gitignore` — a senha não vai para o GitHub.
Se não achar o campo de jeito nenhum, use a **Opção B** (terminal), que funciona sempre.

### Opção B — Terminal

Antes, pare o back-end no IntelliJ (■), senão dá `Port 8080 was already in use`.
Na pasta `PlayMySongsBackEnd/`:

**Mac / Linux**
```bash
export MONGODB_URI='mongodb+srv://playmysongs:SENHA_AQUI@cluster0.3nrhwoe.mongodb.net/?appName=Cluster0'
./mvnw spring-boot:run
```

**Windows (Prompt de Comando — cmd)**
```bat
set "MONGODB_URI=mongodb+srv://playmysongs:SENHA_AQUI@cluster0.3nrhwoe.mongodb.net/?appName=Cluster0"
mvnw.cmd spring-boot:run
```

**Windows (PowerShell)**
```powershell
$env:MONGODB_URI="mongodb+srv://playmysongs:SENHA_AQUI@cluster0.3nrhwoe.mongodb.net/?appName=Cluster0"
.\mvnw.cmd spring-boot:run
```

A variável vale só para aquela janela do terminal; ao abrir outra, defina de novo.
Deixe a janela aberta enquanto usa o site; para parar, **Ctrl+C**.

Quando aparecer `Started PlayMySongsBackEndApplication` no log, a API está em `http://localhost:8080`.

### Como saber se pegou

No log do back-end, procure o endereço do banco:
- aparece `3nrhwoe.mongodb.net` → **certo**, está usando o Atlas;
- aparece `localhost:27017` → a variável **não** pegou (confira o item 4 e rode de novo).

---

## 5. Testar a conexão e cadastrar músicas

A conexão com o Atlas só é usada de verdade quando a API grava ou busca músicas.
As músicas **devem ser cadastradas pelo endpoint de upload** (pelo front-end, Postman/Thunder Client ou curl) —
**não** insira documentos direto pelo site do Atlas, porque o arquivo de áudio precisa ser salvo na pasta `uploads`.

1. Abra no navegador `http://localhost:8080/apis/get-music-styles` → deve listar 16 estilos (não usa o banco,
   só confirma que a API subiu).
2. Envie uma música (troque o caminho pelo de um `.mp3` real do seu computador):

   **Mac / Linux / Windows cmd**
   ```bash
   curl -F "arquivo=@musica.mp3" -F "nome=Love My Life" -F "estilo=Pop" -F "artista=Robbie Williams" http://localhost:8080/apis/music-upload
   ```
   No **PowerShell**, use `curl.exe` em vez de `curl`.

   No **Postman / Thunder Client**: método `POST`, URL `http://localhost:8080/apis/music-upload`,
   Body → **form-data**, com as chaves `arquivo` (tipo *File*), `nome`, `estilo` e `artista` (tipo *Text*).

3. A resposta traz a música com a `url`. Abra a `url` no navegador — a música deve tocar.
4. Busque: `http://localhost:8080/apis/find-musics?chave=love`

Se o upload devolveu a música com `id`, **a conexão com o Atlas está funcionando**.

---

## 6. Ver e apagar dados no Atlas

*Database → Clusters → **Browse Collections*** (ou *Data Explorer* no menu) → banco `play_my_songs` →
collection `musicas`.

- Cada documento tem `nome`, `estilo`, `artista` e `arquivo`.
- Para apagar uma música: passe o mouse sobre o documento → ícone de lixeira → *Delete*.
  Apague também o arquivo correspondente na pasta `uploads` de quem enviou (se ainda existir).
- Antes de criar dados o banco não aparece aqui — é normal.

---

## 7. Checklist da apresentação

- [ ] IP Access List com `0.0.0.0/0` (senão não conecta na rede da faculdade).
- [ ] Apresentar em **um único computador**, que tenha a variável `MONGODB_URI` configurada.
- [ ] No Atlas, apagar as músicas de teste que foram enviadas de **outros** computadores
      (elas aparecem na busca, mas a `url` dá 404 na máquina da apresentação).
- [ ] Ter alguns `.mp3`/`.ogg` reais à mão para fazer os uploads ao vivo.
- [ ] Rodar o back-end antes e testar `get-music-styles`, um upload e uma busca.

---

## 8. Problemas comuns

| Sintoma | Causa provável | Solução |
|---|---|---|
| Upload demora ~30 s e dá erro `Timed out after 30000 ms` | IP não liberado no Atlas | Item 2.3 (IP Access List `0.0.0.0/0`) |
| Erro `bad auth` / `Exception authenticating` | Usuário ou senha errados, ou `< >` na string | Revise a string (item 3) |
| Envio/busca ficam carregando e a página mostra `Timed out while waiting for a server ... localhost:27017 ... Connection refused` (ou `localhost:27017` no log) | Variável `MONGODB_URI` não definida, ou o back-end não foi reiniciado depois de definir | Item 4 (configure a variável, pare com ■ e rode de novo) |
| `url` da música dá 404 | O arquivo foi enviado de outro computador | Envie a música de novo no computador atual |
| `Port 8080 was already in use` | Já tem um back-end rodando | Pare a outra execução (botão ■ no IntelliJ) |
| Erro de tamanho no upload | Arquivo maior que 20 MB | Use um arquivo menor |
| Músicas salvas em pasta estranha | IntelliJ aberto na pasta errada | Abra a pasta `PlayMySongsBackEnd` (item 4, opção A) |

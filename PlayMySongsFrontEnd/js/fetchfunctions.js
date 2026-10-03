/* endereço base das APIs do PlayMySongsBackEnd */
const URL_API = "http://localhost:8080/apis/";

let temporizadorMensagem;

/* mostra um aviso na #mensagem (tipo: "sucesso" ou "erro") */
function mostrarMensagem(texto, tipo) {
    const mensagem = document.getElementById("mensagem");
    mensagem.textContent = texto;
    mensagem.className = tipo;
    mensagem.style.display = "block";
    clearTimeout(temporizadorMensagem);
    temporizadorMensagem = setTimeout(function () {
        mensagem.style.display = "none";
    }, 3000);
}

/* troca caracteres especiais para inserir texto com innerHTML */
function escaparHtml(texto) {
    return String(texto)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#39;");
}

/* devolve o HTML do player de áudio da música */
function montarPlayer(musica) {
    let tipo = "audio/mpeg";
    if (musica.arquivo.toLowerCase().endsWith(".ogg"))
        tipo = "audio/ogg";
    return `<audio controls>
                <source src="${escaparHtml(musica.url)}" type="${tipo}">
            </audio>`;
}

/* devolve o HTML do cartão de uma música (usado no envio e na busca) */
function montarCartaoMusica(musica) {
    return `<div class="cartao">
                <h3>${escaparHtml(musica.nome)}</h3>
                <p><span class="rotulo">Artista:</span> ${escaparHtml(musica.artista)}</p>
                <p><span class="rotulo">Estilo:</span> ${escaparHtml(musica.estilo)}</p>
                ${montarPlayer(musica)}
            </div>`;
}

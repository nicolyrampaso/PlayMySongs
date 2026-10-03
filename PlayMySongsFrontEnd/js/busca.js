/* módulo de busca de músicas (busca.html) */

/* busca as músicas pela chave e monta a lista de cartões */
function buscarMusicas(event) {
    event.preventDefault();
    const chave = document.getElementById("chave").value.trim();
    const resultado = document.getElementById("resultado");
    const total = document.getElementById("total");
    fetch(URL_API + "find-musics?chave=" + encodeURIComponent(chave))
        .then(response => response.json())
        .then(musicas => {
            if (musicas.mensagem) {
                resultado.innerHTML = "";
                total.textContent = "";
                mostrarMensagem(musicas.mensagem, "erro");
                return;
            }
            total.textContent = musicas.length === 1
                ? "1 música encontrada"
                : musicas.length + " músicas encontradas";
            let html = "";
            for (const musica of musicas)
                html += montarCartaoMusica(musica);
            resultado.innerHTML = html;
        })
        .catch(() => mostrarMensagem("Erro ao buscar as músicas", "erro"));
}

/* módulo de envio de músicas (envio.html) */

/* preenche o combobox de estilos ao carregar a página */
function carregarEstilos() {
    fetch(URL_API + "get-music-styles")
        .then(response => response.json())
        .then(estilos => {
            if (estilos.mensagem) {
                mostrarMensagem(estilos.mensagem, "erro");
                return;
            }
            const select = document.getElementById("estilo");
            for (const estilo of estilos) {
                const opcao = document.createElement("option");
                opcao.value = estilo.nome;
                opcao.textContent = estilo.nome;
                opcao.title = estilo.descricao;
                select.appendChild(opcao);
            }
        })
        .catch(() => mostrarMensagem("Erro ao carregar os estilos", "erro"));
}

/* confere se todos os campos estão preenchidos e se o arquivo é .mp3 ou .ogg */
function validarFormulario(form) {
    if (form.estilo.value === "" || form.nome.value.trim() === ""
        || form.artista.value.trim() === "" || form.arquivo.files.length === 0) {
        mostrarMensagem("Preencha todos os campos e selecione o arquivo", "erro");
        return false;
    }
    const nomeArquivo = form.arquivo.files[0].name.toLowerCase();
    if (!nomeArquivo.endsWith(".mp3") && !nomeArquivo.endsWith(".ogg")) {
        mostrarMensagem("Somente arquivos .mp3 ou .ogg são aceitos", "erro");
        return false;
    }
    return true;
}

/* envia o formulário para o back-end */
function enviarMusica(event) {
    event.preventDefault();
    const form = document.getElementById("fdados");
    if (!validarFormulario(form))
        return;
    fetch(URL_API + "music-upload", { method: "POST", body: new FormData(form) })
        .then(response => response.json())
        .then(musica => {
            if (musica.mensagem) {
                mostrarMensagem(musica.mensagem, "erro");
                return;
            }
            mostrarMensagem("Música enviada com sucesso", "sucesso");
            form.reset();
            document.getElementById("enviada").innerHTML = montarCartaoMusica(musica);
        })
        .catch(() => mostrarMensagem("Erro ao enviar a música", "erro"));
}

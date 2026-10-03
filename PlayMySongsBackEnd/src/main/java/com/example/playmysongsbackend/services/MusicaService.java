package com.example.playmysongsbackend.services;

import com.example.playmysongsbackend.entities.Musica;
import com.example.playmysongsbackend.repositories.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class MusicaService {
    // pasta fisica onde as musicas sao gravadas (caminho absoluto, funciona em Windows e Linux)
    public static final Path PASTA_UPLOADS = Paths.get("src", "main", "resources", "static", "uploads").toAbsolutePath();

    @Autowired
    private MusicaRepository musicaRepository;

    // gera o nome no padrao titulo_estilo_artista.extensao
    public String gerarNomeArquivo(String nome, String estilo, String artista, String extensao) {
        return limparTexto(nome) + "_" + limparTexto(estilo) + "_" + limparTexto(artista) + "." + extensao;
    }

    // minusculas, sem acentos, sem espacos e sem caracteres especiais
    private String limparTexto(String texto) {
        String semAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return semAcentos.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    // valida, salva o arquivo em static/uploads e grava a musica no MongoDB
    public Musica salvarMusica(MultipartFile arquivo, String nome, String estilo, String artista) throws Exception {
        if (arquivo == null || arquivo.isEmpty() || nome == null || nome.isBlank()
                || estilo == null || estilo.isBlank() || artista == null || artista.isBlank())
            throw new Exception("Preencha todos os campos e selecione o arquivo");

        String nomeOriginal = arquivo.getOriginalFilename() != null ? arquivo.getOriginalFilename().toLowerCase() : "";
        String extensao;
        if (nomeOriginal.endsWith(".mp3"))
            extensao = "mp3";
        else if (nomeOriginal.endsWith(".ogg"))
            extensao = "ogg";
        else
            throw new Exception("Somente arquivos .mp3 ou .ogg são aceitos");

        String nomeArquivo = gerarNomeArquivo(nome, estilo, artista, extensao);
        if (musicaRepository.existsByArquivo(nomeArquivo))
            throw new Exception("Música já cadastrada");

        Files.createDirectories(PASTA_UPLOADS);
        Path destino = PASTA_UPLOADS.resolve(nomeArquivo);
        Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        return musicaRepository.save(new Musica(nome, estilo, artista, nomeArquivo));
    }

    // Pattern.quote faz a chave ser buscada como texto literal (evita erro com "(", "?", etc.)
    public List<Musica> buscarMusicas(String chave) {
        return musicaRepository.buscarMusicasPorChave(Pattern.quote(chave));
    }
}

package com.example.playmysongsbackend.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "musicas")
public class Musica {
    private ObjectId _id;
    private String nome;
    private String estilo;
    private String artista;
    private String arquivo;
    @Transient
    private String url; // nao e gravada no banco, so preenchida na resposta

    public Musica() {
    }

    public Musica(String nome, String estilo, String artista, String arquivo) {
        this.nome = nome;
        this.estilo = estilo;
        this.artista = artista;
        this.arquivo = arquivo;
    }

    // devolve o id como texto para o JSON nao sair como objeto
    public String getId() {
        return _id != null ? _id.toHexString() : null;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

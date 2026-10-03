package com.example.playmysongsbackend.repositories;

import com.example.playmysongsbackend.entities.Musica;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicaRepository extends MongoRepository<Musica, String> {
    public boolean existsByArquivo(String arquivo);

    // procura a chave em parte do nome, do estilo ou do artista, sem diferenciar maiusculas/minusculas
    @Query("{ $or: [ { 'nome': { $regex: ?0, $options: 'i' } }, { 'estilo': { $regex: ?0, $options: 'i' } }, { 'artista': { $regex: ?0, $options: 'i' } } ] }")
    public List<Musica> buscarMusicasPorChave(String chave);
}

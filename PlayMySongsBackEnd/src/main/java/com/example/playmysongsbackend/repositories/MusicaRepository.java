package com.example.playmysongsbackend.repositories;

import com.example.playmysongsbackend.entities.Musica;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicaRepository extends MongoRepository<Musica, String> {
    public boolean existsByArquivo(String arquivo);
}

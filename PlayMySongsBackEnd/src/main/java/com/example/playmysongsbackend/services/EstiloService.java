package com.example.playmysongsbackend.services;

import com.example.playmysongsbackend.entities.Estilo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstiloService {
    // lista fixa em memoria (nao fica no MongoDB)
    private final List<Estilo> estilos = List.of(
            new Estilo("Rock", "Gênero musical que se originou na década de 1950. Caracterizado pelo uso de guitarra elétrica, baixo e bateria."),
            new Estilo("Pop", "Gênero musical que se caracteriza por suas melodias cativantes e produções acessíveis. Popular desde os anos 1950."),
            new Estilo("Jazz", "Estilo musical que surgiu no início do século XX, caracterizado pela improvisação e pelo uso de instrumentos de sopro."),
            new Estilo("Hip Hop", "Gênero que começou na década de 1970, conhecido por seu ritmo sincopado e letras rap. Envolve elementos de DJing e grafite."),
            new Estilo("Eletrônica", "Gênero que envolve a criação de música usando sintetizadores e tecnologia digital. Inclui subgêneros como techno e house."),
            new Estilo("Clássica", "Gênero que abrange uma longa tradição musical europeia, com obras compostas por grandes compositores como Bach, Mozart e Beethoven."),
            new Estilo("Reggae", "Gênero musical originário da Jamaica, conhecido por seu ritmo distinto e letras que frequentemente abordam questões sociais e políticas."),
            new Estilo("Country", "Estilo musical que se desenvolveu nos Estados Unidos, baseado na música folk e blues, com letras que frequentemente falam sobre a vida rural."),
            new Estilo("Blues", "Gênero musical que se originou no sul dos Estados Unidos no final do século 19, baseado na expressão emocional e frequentemente utilizando a forma de 12 compassos."),
            new Estilo("Metal", "Estilo de rock caracterizado por guitarras elétricas distorcidas, bateria pesada e vocais intensos. Emergiu no final dos anos 1960 e início dos anos 1970."),
            new Estilo("Funk", "Gênero que combina elementos de soul, jazz e rhythm and blues, caracterizado por suas linhas de baixo groovy e batidas rítmicas."),
            new Estilo("Samba", "Estilo de música e dança brasileira que é sinônimo de cultura carioca, com raízes africanas."),
            new Estilo("Tango", "Gênero musical e dança que se originou na Argentina, caracterizado por suas melodias românticas e ritmos marcantes."),
            new Estilo("Punk", "Estilo de música que surgiu na década de 1970, famoso pelo seu som agressivo, letras politizadas e estética DIY."),
            new Estilo("Alternativa", "Gênero que surgiu na década de 1980 como uma alternativa à música popular convencional. Abrange uma variedade de estilos e influências."),
            new Estilo("Sertanejo", "Estilo musical originário do Brasil, caracterizado por canções que abordam temas como amor, vida rural e relacionamentos, muito popular nas regiões Centro-Oeste e Sudeste.")
    );

    public List<Estilo> buscarTodosEstilos() {
        return estilos;
    }
}

package com.example.playmysongsbackend.restcontrollers;

import com.example.playmysongsbackend.entities.Erro;
import com.example.playmysongsbackend.entities.Estilo;
import com.example.playmysongsbackend.entities.Musica;
import com.example.playmysongsbackend.services.EstiloService;
import com.example.playmysongsbackend.services.MusicaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "apis")
public class MusicaRestController {
    @Autowired
    private EstiloService estiloService;
    @Autowired
    private MusicaService musicaService;
    @Autowired
    private HttpServletRequest request;

    /* retorna a url da pasta static/uploads */
    public String getHostStatic() {
        return "http://" + request.getServerName().toString() + ":" + request.getServerPort() + "/uploads/";
    }

    @GetMapping(value = "get-music-styles")
    public ResponseEntity<Object> buscarEstilos() {
        List<Estilo> estiloList = estiloService.buscarTodosEstilos();
        if (estiloList.isEmpty())
            return ResponseEntity.badRequest().body(new Erro("Nenhum estilo encontrado"));
        return ResponseEntity.ok(estiloList);
    }

    @PostMapping(value = "music-upload")
    public ResponseEntity<Object> enviarMusica(@RequestParam(value = "arquivo", required = false) MultipartFile arquivo,
                                               @RequestParam(value = "nome", required = false) String nome,
                                               @RequestParam(value = "estilo", required = false) String estilo,
                                               @RequestParam(value = "artista", required = false) String artista) {
        try {
            Musica musica = musicaService.salvarMusica(arquivo, nome, estilo, artista);
            musica.setUrl(getHostStatic() + musica.getArquivo());
            return ResponseEntity.ok(musica);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Erro(e.getMessage()));
        }
    }

    @GetMapping(value = "find-musics")
    public ResponseEntity<Object> buscarMusicas(@RequestParam(value = "chave", defaultValue = "") String chave) {
        List<Musica> musicaList = musicaService.buscarMusicas(chave);
        if (musicaList.isEmpty())
            return ResponseEntity.badRequest().body(new Erro("Nenhuma música encontrada"));
        for (Musica musica : musicaList)
            musica.setUrl(getHostStatic() + musica.getArquivo());
        return ResponseEntity.ok(musicaList);
    }
}

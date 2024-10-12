package com.example.api_livraria.controller;

import com.example.api_livraria.service.RelatorioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {


    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/livros-por-autor")
    public ResponseEntity<byte[]> gerarRelatorioLivrosPorAutor() {
        try {
            byte[] relatorio = relatorioService.gerarRelatorioLivrosPorAutor();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "relatorio_livros_por_autor.pdf");
            return ResponseEntity.ok().headers(headers).body(relatorio);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    }

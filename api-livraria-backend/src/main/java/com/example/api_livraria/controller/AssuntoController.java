package com.example.api_livraria.controller;

import com.example.api_livraria.model.Assunto;
import com.example.api_livraria.service.AssuntoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/assuntos")
public class AssuntoController {

    private final AssuntoService assuntoService;

    public AssuntoController(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }

    @GetMapping
    public ResponseEntity<List<Assunto>> listarAssuntos() {
        List<Assunto> assuntos = assuntoService.listarTodos();
        return ResponseEntity.ok(assuntos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assunto> buscarAssuntoPorId(@PathVariable Long id) {
        Assunto assunto = assuntoService.buscarPorId(id);
        if (assunto != null) {
            return ResponseEntity.ok(assunto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Assunto> criarAssunto(@RequestBody Assunto assunto) {
        Assunto novoAssunto = assuntoService.salvar(assunto);
        return ResponseEntity.ok(novoAssunto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assunto> atualizarAssunto(@PathVariable Long id, @RequestBody Assunto assuntoAtualizado) {
        Assunto assunto = assuntoService.atualizar(id, assuntoAtualizado);
        if (assunto != null) {
            return ResponseEntity.ok(assunto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssunto(@PathVariable Long id) {
        assuntoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

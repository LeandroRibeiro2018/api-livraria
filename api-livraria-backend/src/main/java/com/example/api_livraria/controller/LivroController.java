package com.example.api_livraria.controller;

import com.example.api_livraria.exception.DatabaseException;
import com.example.api_livraria.exception.ResourceNotFoundException;
import com.example.api_livraria.model.Livro;
import com.example.api_livraria.service.LivroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        try {
            List<Livro> livros = livroService.listarTodos();
            return ResponseEntity.ok(livros); // Retorna 200 OK com a lista de livros
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 em caso de erro
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        try {
            Livro livro = livroService.buscarPorId(id);
            return ResponseEntity.ok(livro); // Retorna 200 OK com o livro encontrado
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404 Not Found se o livro não for encontrado
        }
    }

    @PostMapping
    public ResponseEntity<Livro> salvar(@RequestBody Livro livro) {
        try {
            Livro livroSalvo = livroService.salvar(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo); // Retorna 201 Created com o livro salvo
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Retorna 400 Bad Request se os dados forem inválidos
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Retorna 500 em caso de erro
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        try {
            Livro livro = livroService.atualizar(id, livroAtualizado);
            return ResponseEntity.ok(livro); // Retorna 200 OK com o livro atualizado
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404 Not Found se o livro não for encontrado
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Retorna 500 em caso de erro
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            livroService.deletar(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se a exclusão for bem-sucedida
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 Not Found se o livro não for encontrado
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 em caso de erro
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }
}


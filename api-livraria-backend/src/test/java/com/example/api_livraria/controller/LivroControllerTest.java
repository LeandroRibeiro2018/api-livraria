package com.example.api_livraria.controller;

import com.example.api_livraria.exception.DatabaseException;
import com.example.api_livraria.exception.ResourceNotFoundException;
import com.example.api_livraria.model.Livro;
import com.example.api_livraria.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LivroControllerTest {

    @InjectMocks
    private LivroController livroController;

    @Mock
    private LivroService livroService;

    private Livro livro;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Livro Teste");
    }

    @Test
    public void testListarTodos() {
        when(livroService.listarTodos()).thenReturn(Arrays.asList(livro));
        ResponseEntity<List<Livro>> response = livroController.listarTodos();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testBuscarPorId_Sucesso() {
        when(livroService.buscarPorId(1L)).thenReturn(livro);
        ResponseEntity<Livro> response = livroController.buscarPorId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Livro Teste", response.getBody().getTitulo());
    }

    @Test
    public void testBuscarPorId_Falha() {
        when(livroService.buscarPorId(1L)).thenThrow(new ResourceNotFoundException("Livro não encontrado"));
        ResponseEntity<Livro> response = livroController.buscarPorId(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSalvar_Sucesso() throws DatabaseException {
        when(livroService.salvar(any(Livro.class))).thenReturn(livro);
        ResponseEntity<Livro> response = livroController.salvar(livro);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Livro Teste", response.getBody().getTitulo());
    }

    @Test
    public void testAtualizar_Sucesso() {
        when(livroService.atualizar(eq(1L), any(Livro.class))).thenReturn(livro);
        ResponseEntity<Livro> response = livroController.atualizar(1L, livro);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Livro Teste", response.getBody().getTitulo());
    }

    @Test
    public void testDeletar_Sucesso() throws DatabaseException {
        doNothing().when(livroService).deletar(1L);
        ResponseEntity<Void> response = livroController.deletar(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeletar_Falha() throws DatabaseException {
        doThrow(new ResourceNotFoundException("Livro não encontrado")).when(livroService).deletar(1L);
        ResponseEntity<Void> response = livroController.deletar(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
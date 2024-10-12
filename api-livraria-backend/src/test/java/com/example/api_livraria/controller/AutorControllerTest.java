package com.example.api_livraria.controller;

import com.example.api_livraria.exception.DatabaseException;
import com.example.api_livraria.exception.ResourceNotFoundException;
import com.example.api_livraria.model.Autor;
import com.example.api_livraria.service.AutorService;
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

class AutorControllerTest {
    @InjectMocks
    private AutorController autorController;

    @Mock
    private AutorService autorService;

    private Autor autor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        autor = new Autor();
        autor.setId(1L);
        autor.setNome("Autor Teste");
    }

    @Test
    public void testListarAutores() {
        when(autorService.listar()).thenReturn(Arrays.asList(autor));
        ResponseEntity<List<Autor>> response = autorController.listarAutores();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testBuscarAutorPorId_Sucesso() {
        when(autorService.buscarPorId(1L)).thenReturn(autor);
        ResponseEntity<Autor> response = autorController.buscarAutorPorId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Autor Teste", response.getBody().getNome());
    }

    @Test
    public void testBuscarAutorPorId_Falha() {
        when(autorService.buscarPorId(1L)).thenThrow(new ResourceNotFoundException("Autor não encontrado"));
        ResponseEntity<Autor> response = autorController.buscarAutorPorId(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCriarAutor_Sucesso() throws DatabaseException {
        when(autorService.salvar(any(Autor.class))).thenReturn(autor);
        ResponseEntity<Autor> response = autorController.criarAutor(autor);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Autor Teste", response.getBody().getNome());
    }

    @Test
    public void testAtualizarAutor_Sucesso() {
        when(autorService.atualizar(eq(1L), any(Autor.class))).thenReturn(autor);
        ResponseEntity<Autor> response = autorController.atualizarAutor(1L, autor);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Autor Teste", response.getBody().getNome());
    }

    @Test
    public void testDeletarAutor_Sucesso() throws DatabaseException {
        doNothing().when(autorService).deletar(1L);
        ResponseEntity<Void> response = autorController.deletarAutor(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeletarAutor_Falha() throws DatabaseException {
        doThrow(new ResourceNotFoundException("Autor não encontrado")).when(autorService).deletar(1L);
        ResponseEntity<Void> response = autorController.deletarAutor(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
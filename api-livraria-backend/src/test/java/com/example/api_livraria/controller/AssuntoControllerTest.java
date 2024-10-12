package com.example.api_livraria.controller;

import com.example.api_livraria.model.Assunto;
import com.example.api_livraria.service.AssuntoService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class AssuntoControllerTest {

    @InjectMocks
    private AssuntoController assuntoController;

    @Mock
    private AssuntoService assuntoService;

    private Assunto assunto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        assunto = new Assunto();
        assunto.setId(1L);
        assunto.setDescricao("Assunto Teste");
    }

    @Test
    public void testListarAssuntos() {
        when(assuntoService.listarTodos()).thenReturn(Arrays.asList(assunto));
        ResponseEntity<List<Assunto>> response = assuntoController.listarAssuntos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testBuscarAssuntoPorId_Sucesso() {
        when(assuntoService.buscarPorId(1L)).thenReturn(assunto);
        ResponseEntity<Assunto> response = assuntoController.buscarAssuntoPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Assunto Teste", response.getBody().getDescricao());
    }

    @Test
    public void testBuscarAssuntoPorId_Falha() {
        when(assuntoService.buscarPorId(1L)).thenReturn(null);
        ResponseEntity<Assunto> response = assuntoController.buscarAssuntoPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCriarAssunto() {
        when(assuntoService.salvar(any(Assunto.class))).thenReturn(assunto);
        ResponseEntity<Assunto> response = assuntoController.criarAssunto(assunto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Assunto Teste", response.getBody().getDescricao());
    }

    @Test
    public void testAtualizarAssunto_Sucesso() {
        when(assuntoService.atualizar(eq(1L), any(Assunto.class))).thenReturn(assunto);
        ResponseEntity<Assunto> response = assuntoController.atualizarAssunto(1L, assunto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Assunto Teste", response.getBody().getDescricao());
    }

    @Test
    public void testAtualizarAssunto_Falha() {
        when(assuntoService.atualizar(eq(1L), any(Assunto.class))).thenReturn(null);
        ResponseEntity<Assunto> response = assuntoController.atualizarAssunto(1L, assunto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeletarAssunto_Sucesso() {
        doNothing().when(assuntoService).deletar(1L);
        ResponseEntity<Void> response = assuntoController.deletarAssunto(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
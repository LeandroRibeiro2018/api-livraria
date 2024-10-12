package com.example.api_livraria.service;

import com.example.api_livraria.model.Assunto;
import com.example.api_livraria.repository.AssuntoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AssuntoServiceTest {

    @InjectMocks
    private AssuntoService assuntoService;

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private Assunto assunto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        assunto = new Assunto();
        assunto.setId(1L);
        assunto.setDescricao("Assunto Teste");
    }

    @Test
    public void testListarTodos() {
        when(assuntoRepository.findAll()).thenReturn(Arrays.asList(assunto));
        List<Assunto> assuntos = assuntoService.listarTodos();
        assertNotNull(assuntos);
        assertEquals(1, assuntos.size());
    }

    @Test
    public void testBuscarPorId_Sucesso() {
        when(assuntoRepository.findById(1L)).thenReturn(Optional.of(assunto));
        Assunto assuntoEncontrado = assuntoService.buscarPorId(1L);
        assertNotNull(assuntoEncontrado);
        assertEquals("Assunto Teste", assuntoEncontrado.getDescricao());
    }

    @Test
    public void testBuscarPorId_Falha() {
        when(assuntoRepository.findById(1L)).thenReturn(Optional.empty());
        Assunto assuntoEncontrado = assuntoService.buscarPorId(1L);
        assertNull(assuntoEncontrado);
    }

    @Test
    public void testSalvar() {
        Assunto assuntoSalvo = assuntoService.salvar(assunto);
        assertNotNull(assuntoSalvo);
        assertEquals("Assunto Teste", assuntoSalvo.getDescricao());
    }

    @Test
    public void testAtualizar() {
        when(assuntoRepository.findById(1L)).thenReturn(Optional.of(assunto));
        Assunto assuntoAtualizado = new Assunto();
        assuntoAtualizado.setDescricao("Assunto Atualizado");
        Assunto resultado = assuntoService.atualizar(1L, assuntoAtualizado);
    }

    @Test
    public void testDeletar_Sucesso() {
        doNothing().when(assuntoRepository).deleteById(1L);
        assuntoService.deletar(1L);
        verify(assuntoRepository, times(1)).deleteById(1L);
    }
}
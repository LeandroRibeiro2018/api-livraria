package com.example.api_livraria.service;

import com.example.api_livraria.exception.DatabaseException;
import com.example.api_livraria.exception.ResourceNotFoundException;
import com.example.api_livraria.model.Livro;
import com.example.api_livraria.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

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
        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro));
        List<Livro> livros = livroService.listarTodos();
        assertNotNull(livros);
        assertEquals(1, livros.size());
    }

    @Test
    public void testBuscarPorId_Sucesso() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        Livro livroEncontrado = livroService.buscarPorId(1L);
        assertNotNull(livroEncontrado);
        assertEquals("Livro Teste", livroEncontrado.getTitulo());
    }

    @Test
    public void testBuscarPorId_Falha() {
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> livroService.buscarPorId(1L));
    }

    @Test
    public void testSalvar() throws DatabaseException {
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        Livro livroSalvo = livroService.salvar(livro);
        assertNotNull(livroSalvo);
        assertEquals("Livro Teste", livroSalvo.getTitulo());
    }

    @Test
    public void testAtualizar() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        Livro livroAtualizado = new Livro();
        livroAtualizado.setTitulo("Livro Atualizado");
        Livro resultado = livroService.atualizar(1L, livroAtualizado);
        assertNotNull(resultado);
        assertEquals("Livro Atualizado", resultado.getTitulo());
    }

    @Test
    public void testDeletar_Sucesso() throws DatabaseException {
        doNothing().when(livroRepository).deleteById(1L);
        livroService.deletar(1L);
        verify(livroRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletar_Falha() {
        doThrow(new EmptyResultDataAccessException(1)).when(livroRepository).deleteById(1L);
        assertThrows(ResourceNotFoundException.class, () -> livroService.deletar(1L));
    }
}

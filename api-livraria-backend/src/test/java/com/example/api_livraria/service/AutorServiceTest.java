package com.example.api_livraria.service;

import com.example.api_livraria.exception.DatabaseException;
import com.example.api_livraria.exception.ResourceNotFoundException;
import com.example.api_livraria.model.Autor;
import com.example.api_livraria.repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorServiceTest {

    @InjectMocks
    private AutorService autorService;

    @Mock
    private AutorRepository autorRepository;


    private Autor autor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        autor = new Autor();
        autor.setId(1L);
        autor.setNome("Autor Teste");
    }

    @Test
    public void testListar() {
        when(autorRepository.findAll()).thenReturn(Arrays.asList(autor));
        List<Autor> autores = autorService.listar();
        assertNotNull(autores);
        assertEquals(1, autores.size());
    }

    @Test
    public void testBuscarPorId_Sucesso() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        Autor autorEncontrado = autorService.buscarPorId(1L);
        assertNotNull(autorEncontrado);
        assertEquals("Autor Teste", autorEncontrado.getNome());
    }

    @Test
    public void testBuscarPorId_Falha() {
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> autorService.buscarPorId(1L));
    }

    @Test
    public void testSalvar() throws DatabaseException {
        Autor autorSalvo = autorService.salvar(autor);
        assertNotNull(autorSalvo);
        assertEquals("Autor Teste", autorSalvo.getNome());
    }

    @Test
    public void testAtualizar() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        Autor autorAtualizado = new Autor();
        autorAtualizado.setNome("Autor Atualizado");


        Autor resultado = autorService.atualizar(1L, autorAtualizado);
        assertNotNull(resultado);
        assertEquals("Autor Atualizado", resultado.getNome());
    }

    @Test
    public void testDeletar_Sucesso() throws DatabaseException {
        doNothing().when(autorRepository).deleteById(1L);
        autorService.deletar(1L);
    }

    @Test
    public void testDeletar_Falha() {
        doThrow(new EmptyResultDataAccessException(1)).when(autorRepository).deleteById(1L);
        assertThrows(ResourceNotFoundException.class, () -> autorService.deletar(1L));
    }
}
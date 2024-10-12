package com.example.api_livraria.service;

import com.example.api_livraria.exception.DatabaseException;
import com.example.api_livraria.exception.ResourceNotFoundException;
import com.example.api_livraria.model.Livro;
import com.example.api_livraria.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            return livro.get();
        } else {
            throw new ResourceNotFoundException("Livro não encontrado com o ID: " + id);
        }
    }

    @Transactional
    public Livro salvar(Livro livro) throws DatabaseException {
        try {
            return livroRepository.save(livro);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar o livro: " + e.getMessage());
        }
    }

    @Transactional
    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro livro = buscarPorId(id); // Verifica se o livro existe
        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setAutor(livroAtualizado.getAutor());
        return livroRepository.save(livro);
    }

    @Transactional
    public void deletar(Long id) throws DatabaseException {
        try {
            livroRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Livro não encontrado com o ID: " + id);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao deletar o livro: " + e.getMessage());
        }
    }
}

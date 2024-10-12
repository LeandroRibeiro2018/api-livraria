package com.example.api_livraria.service;

import com.example.api_livraria.exception.DatabaseException;
import com.example.api_livraria.exception.ResourceNotFoundException;
import com.example.api_livraria.model.Autor;
import com.example.api_livraria.repository.AutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listar() {
        return autorRepository.findAll();
    }

    public Autor buscarPorId(Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        if (autor.isPresent()) {
            return autor.get();
        } else {
            throw new ResourceNotFoundException("Autor não encontrado com o ID: " + id);
        }
    }

    @Transactional
    public Autor salvar(Autor autor) throws DatabaseException {
        try {
            return autorRepository.save(autor);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar o autor: " + e.getMessage());
        }
    }

    @Transactional
    public Autor atualizar(Long id, Autor autorAtualizado) {
        Autor autor = buscarPorId(id); // Verifica se o autor existe
        autor.setNome(autorAtualizado.getNome());
        return autorRepository.save(autor);
    }

    @Transactional
    public void deletar(Long id) throws DatabaseException {
        try {
            autorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Autor não encontrado com o ID: " + id);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao deletar o autor: " + e.getMessage());
        }
    }
}

package com.example.api_livraria.service;

import com.example.api_livraria.model.Assunto;
import com.example.api_livraria.repository.AssuntoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AssuntoService {


    private final AssuntoRepository assuntoRepository;

    public AssuntoService(AssuntoRepository assuntoRepository) {
        this.assuntoRepository = assuntoRepository;
    }

    public List<Assunto> listarTodos() {
        return assuntoRepository.findAll();
    }

    public Assunto buscarPorId(Long id) {
        return assuntoRepository.findById(id).orElse(null);
    }

    public Assunto salvar(Assunto assunto) {
        return assuntoRepository.save(assunto);
    }

    public void deletar(Long id) {
        assuntoRepository.deleteById(id);
    }

    public Assunto atualizar(Long id, Assunto assuntoAtualizado) {
        Optional<Assunto> assuntoExistente = assuntoRepository.findById(id);
        if (assuntoExistente.isPresent()) {
            Assunto assunto = assuntoExistente.get();
            assunto.setDescricao(assuntoAtualizado.getDescricao());
            return assuntoRepository.save(assunto);
        }
        return null;
    }
}

package com.example.api_livraria.repository;

import com.example.api_livraria.model.Autor;
import com.example.api_livraria.model.LivroAutorView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
}

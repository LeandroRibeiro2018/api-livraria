package com.example.api_livraria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LivroAutorView {

    @Id
    private String isbn; // ou outro campo único
    private String autorNome;
    private String livroTitulo;
    private int anoPublicacao;

}

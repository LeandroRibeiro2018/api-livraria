package com.example.api_livraria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "autor")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}

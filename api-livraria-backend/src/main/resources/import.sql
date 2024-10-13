-- Criando o banco de dados
CREATE DATABASE LivrariaDB;
USE LivrariaDB;

-- Tabela Autor
CREATE TABLE autor (
                       id INT AUTO_INCREMENT PRIMARY KEY,  -- Chave primária com auto incremento
                       nome VARCHAR(255) NOT NULL
);

-- Índice para busca rápida por nome de autor
CREATE INDEX idx_autor_nome ON autor(nome);

-- Tabela Assunto
CREATE TABLE assunto (
                         id INT AUTO_INCREMENT PRIMARY KEY,  -- Chave primária com auto incremento
                         descricao VARCHAR(255) NOT NULL
);

-- Índice para busca rápida por descrição de assunto
CREATE INDEX idx_assunto_descricao ON assunto(descricao);

-- Tabela Livro
CREATE TABLE livro (
                       id INT AUTO_INCREMENT PRIMARY KEY,  -- Chave primária com auto incremento
                       titulo VARCHAR(255) NOT NULL,
                       editora VARCHAR(255) NOT NULL,
                       edicao INT NOT NULL,
                       ano_publicacao YEAR NOT NULL,
                       assunto_id INT NOT NULL,  -- Chave estrangeira
                       CONSTRAINT fk_livro_assunto FOREIGN KEY (assunto_id) REFERENCES assunto(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Índices para otimizar busca por título e ano de publicação
CREATE INDEX idx_livro_titulo ON livro(titulo);
CREATE INDEX idx_livro_ano_publicacao ON livro(ano_publicacao);

-- Tabela Livro_Autor (tabela de relacionamento muitos-para-muitos)
CREATE TABLE livro_autor (
                             livro_id INT NOT NULL,  -- Chave estrangeira
                             autor_id INT NOT NULL,  -- Chave estrangeira
                             PRIMARY KEY (livro_id, autor_id),  -- Chave primária composta
                             CONSTRAINT fk_livro_autor_livro FOREIGN KEY (livro_id) REFERENCES livro(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             CONSTRAINT fk_livro_autor_autor FOREIGN KEY (autor_id) REFERENCES autor(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Índice para otimizar busca por autor ou livro no relacionamento
CREATE INDEX idx_livro_autor_livro ON livro_autor(livro_id);
CREATE INDEX idx_livro_autor_autor ON livro_autor(autor_id);

-- Tabela Livro_Assunto (caso seja necessário relacionar mais de um assunto a um livro)
CREATE TABLE livro_assunto (
                               livro_id INT NOT NULL,  -- Chave estrangeira
                               assunto_id INT NOT NULL,  -- Chave estrangeira
                               PRIMARY KEY (livro_id, assunto_id),  -- Chave primária composta
                               CONSTRAINT fk_livro_assunto_livro FOREIGN KEY (livro_id) REFERENCES livro(id) ON DELETE CASCADE ON UPDATE CASCADE,
                               CONSTRAINT fk_livro_assunto_assunto FOREIGN KEY (assunto_id) REFERENCES assunto(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Índices para otimizar busca por livro ou assunto no relacionamento
CREATE INDEX idx_livro_assunto_livro ON livro_assunto(livro_id);
CREATE INDEX idx_livro_assunto_assunto ON livro_assunto(assunto_id);

-- Melhorias de Performance:
-- 1. Definir as colunas que serão frequentemente usadas em consultas com índices para acelerar o desempenho.
-- 2. Uso de `ON DELETE CASCADE` e `ON UPDATE CASCADE` para manter a integridade referencial ao excluir ou atualizar dados.
-- 3. Normalização para evitar duplicações de dados (tabelas normalizadas 3FN).

CREATE VIEW vw_livros_por_autor AS
SELECT a.nome AS autor_nome, l.titulo AS livro_titulo, l.ano_publicacao,l.isbn
FROM autor a
         JOIN livro_autor la ON a.id = la.autor_id
         JOIN livro l ON la.livro_id = l.id
ORDER BY a.nome;


INSERT INTO livro (id, Titulo, Editora, Edicao, ano_publicacao)
VALUES
    (1, 'O Senhor dos Anéis: As Duas Torres', 'Martins Fontes', 1, '2001'),
    (2, 'Harry Potter e a Pedra Filosofal', 'Rocco', 1, '2002'),
    (3, '1984', 'Companhia das Letras', 1, '1949'),
    (4, 'Admirável Mundo Novo', 'Biblioteca Azul', 1, '1932'),
    (5, 'Neuromancer', 'Aleph', 1, '1984'),
    (6, 'Duna', 'Aleph', 1, '1965'),
    (7, 'Orgulho e Preconceito', 'Martin Claret', 1, '1813'),
    (8, 'Dom Quixote', 'Penguin', 1, '1605'),
    (9, 'A Revolução dos Bichos', 'Companhia das Letras', 1, '1945'),
    (10, 'A História da Ciência', 'Zahar', 1, '2007');

-- Inserindo autores
INSERT INTO autor (id, Nome)
VALUES
    (1, 'J.R.R. Tolkien'),
    (2, 'J.K. Rowling'),
    (3, 'George Orwell'),
    (4, 'Aldous Huxley'),
    (5, 'William Gibson'),
    (6, 'Frank Herbert'),
    (7, 'Jane Austen'),
    (8, 'Miguel de Cervantes'),
    (9, 'John Gribbin');

-- Relacionando livros com autores
INSERT INTO livro_autor (livro_id, autor_id)
VALUES
    (1, 1),  -- O Senhor dos Anéis: As Duas Torres foi escrito por J.R.R. Tolkien
    (2, 2),  -- Harry Potter e a Pedra Filosofal foi escrito por J.K. Rowling
    (3, 3),  -- 1984 foi escrito por George Orwell
    (4, 4),  -- Admirável Mundo Novo foi escrito por Aldous Huxley
    (5, 5),  -- Neuromancer foi escrito por William Gibson
    (6, 6),  -- Duna foi escrito por Frank Herbert
    (7, 7),  -- Orgulho e Preconceito foi escrito por Jane Austen
    (8, 8),  -- Dom Quixote foi escrito por Miguel de Cervantes
    (9, 3),  -- A Revolução dos Bichos foi escrito por George Orwell
    (10, 9); -- A História da Ciência foi escrito por John Gribbin

-- Inserindo assuntos
INSERT INTO assunto (id, descricao)
VALUES
    (1, 'Ficção Científica'),
    (2, 'Fantasia'),
    (3, 'Romance'),
    (4, 'História'),
    (5, 'Tecnologia');

-- Relacionando livros com assuntos
INSERT INTO livro_assunto (livro_id, assunto_id)
VALUES
    (1, 2),  -- O Senhor dos Anéis é Fantasia
    (2, 2),  -- Harry Potter é Fantasia
    (3, 1),  -- 1984 é Ficção Científica
    (4, 1),  -- Admirável Mundo Novo é Ficção Científica
    (5, 1),  -- Neuromancer é Ficção Científica
    (6, 1),  -- Duna é Ficção Científica
    (7, 3),  -- Orgulho e Preconceito é Romance
    (8, 3),  -- Dom Quixote é Romance
    (9, 1),  -- A Revolução dos Bichos é Ficção Científica
    (10, 4); -- A História da Ciência é História

-- Atualizando o ISBN de cada livro
UPDATE livro
SET isbn = '9788578273153'
WHERE id = 1;

UPDATE livro
SET isbn = '9788532530783'
WHERE id = 2;

UPDATE livro
SET isbn = '9788535914849'
WHERE id = 3;

UPDATE livro
SET isbn = '9788525056032'
WHERE id = 4;

UPDATE livro
SET isbn = '9788576570100'
WHERE id = 5;

UPDATE livro
SET isbn = '9788576573132'
WHERE id = 6;

UPDATE livro
SET isbn = '9788572325759'
WHERE id = 7;

UPDATE livro
SET isbn = '9780142437230'
WHERE id = 8;

UPDATE livro
SET isbn = '9788535906998'
WHERE id = 9;

UPDATE livro
SET isbn = '9788537801437'
WHERE id = 10;

-- Índice na tabela 'Livro' para a coluna 'id' (Codl)
CREATE INDEX idx_livro_id ON livro(id);

-- Índice na tabela 'Autor' para a coluna 'id' (CodAu)
CREATE INDEX idx_autor_id ON autor(id);

-- Índice na tabela 'Livro_Autor' para as colunas 'Livro_Codl' e 'Autor_CodAu'
CREATE INDEX idx_livro_autor_livro_codl ON livro_autor(livro_id);
CREATE INDEX idx_livro_autor_autor_codau ON livro_autor(autor_id);

-- Índice na tabela 'livro_assunto' para as colunas 'Livro_id' e 'Assunto_id'
CREATE INDEX idx_livro_assunto_livro_id ON livro_assunto(Livro_id);
CREATE INDEX idx_livro_assunto_assunto_id ON livro_assunto(Assunto_id);

-- Criando uma visão (View) chamada 'vw_LivrosAutores' que exibe o título do livro e o nome do autor
CREATE VIEW vw_livros_por_autor AS
SELECT a.nome AS autor_nome, l.titulo AS livro_titulo, l.ano_publicacao,l.isbn
FROM autor a
         JOIN livro_autor la ON a.id = la.autor_id
         JOIN livro l ON la.livro_id = l.id
ORDER BY a.nome;

-- Inserindo dados na tabela 'Livro'
INSERT INTO livro (id, Titulo, Editora, Edicao, ano_publicacao)
VALUES
    (1, 'O Senhor dos Anéis', 'Martins Fontes', 1, '2000'),
    (3, 'Harry Potter', 'Rocco', 1, '2002'),
    (4, 'O Hobbit', 'Martins Fontes', 1, '2008');



-- Inserindo dados na tabela 'autor'
INSERT INTO autor (id, Nome)
VALUES
    (1, 'J.R.R. Tolkien'),
    (2, 'George Orwell');

-- Inserindo dados na tabela 'Livro_Autor'
INSERT INTO livro_autor (livro_id, autor_id)
VALUES
    (1, 1),  -- O Senhor dos Anéis As Duas Torres foi escrito por J.R.R. Tolkien
    (3, 2);  -- Harry Potter foi escrito por George Orwell (exemplo fictício)

-- Inserindo dados na tabela 'assunto'
INSERT INTO assunto (id, descricao)
VALUES
    (1, 'Ficção Científica'),
    (2, 'Fantasia'),
    (3, 'Romance'),
    (4, 'História'),
    (5, 'Tecnologia');

-- Inserindo dados na tabela 'livro_assunto'
INSERT INTO livro_assunto (Livro_id, Assunto_id)
VALUES
    (2, 2),  -- O Senhor dos Anéis As Duas Torres é de Fantasia
    (3, 3);  -- Harry Potter é de Romance (exemplo fictício)
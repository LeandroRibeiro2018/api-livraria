 Projeto de Cadastro de Livros

## Descrição do Desafio
O objetivo deste projeto é criar um sistema de cadastro de livros utilizando as boas práticas de desenvolvimento de software. O projeto inclui funcionalidades de CRUD (Create, Read, Update, Delete) para as entidades **
Livro
**, **
Autor
** e **
Assunto
**, além de um relatório que agrupa os dados por autor. O sistema é desenvolvido utilizando **
Java
** com **
Spring Boot
**, com persistência de dados em um banco de dados **
MySQL
** e geração de relatórios com **
JasperReports
**. O front-end é desenvolvido em um repositório separado, utilizando **
Angular 18
** para a interface.

## Tecnologias Utilizadas
- **
Java 17
**: Linguagem de programação principal.
- **
Spring Boot
**: Framework para desenvolvimento da aplicação.
- **
Maven
**: Gerenciador de dependências e build.
- **
MySQL
**: Banco de dados relacional.
- **
JPA/Hibernate
**: Camada de persistência de dados.
- **
JUnit 5
**: Framework para testes unitários.
- **
Mockito
**: Framework para criação de mocks nos testes.
- **
JasperReports
**: Biblioteca para geração de relatórios.
- **
Angular 18
**: Framework front-end para construção da interface de usuário (no repositório front-end).

## Estrutura do Projeto
O projeto segue a arquitetura **
MVC (Model-View-Controller)
** e está organizado da seguinte forma:


## Funcionalidades
- **CRUD de Livro, Autor e Assunto**: Permite criar, visualizar, atualizar e excluir registros de livros, autores e assuntos.
- **Relatório de Livros por Autor**: Relatório agrupado por autor, gerado em formato PDF utilizando **JasperReports**.
- **Formas de Compra**: Sistema que permite informar o valor do livro dependendo da forma de compra (balcão, self-service, internet, etc.).
- **Testes Unitários**: Implementados com **JUnit 5** e **Mockito** para garantir a qualidade do código.

## Requisitos
- **Java 17** ou superior
- **Maven 3.6+**
- **MySQL 8.0+**

## Configuração do Ambiente

### 1. Clonar o Repositório

git clone https://github.com/seu-usuario/cadastro-livros.git
cd cadastro-livros

 CREATE DATABASE livraria;

### 3. Executar o Projeto
Para rodar o projeto, execute o comando:

bater

Copiar código
mvn spring-boot:run
A aplicação estará disponível em http ://localhost :8080 .

### 4. Executar Testes
Para rodar os testes unitários:
```bash
mvn test
```
Copiar código
### 4.1 Executar Projeto
Para rodar os testes unitários:
```bash
1- mvn clean
2- mvn install
```
Copiar código
### 5. Geração de Relatórios
O relatório de livros por autor pode ser acessado através do seguinte URL:

http

Copiar código
GET http://localhost:8080/relatorios/livros-por-autor
O relatório será gerado em formato PDF e baixado automaticamente.

Scripts SQL
Os scripts SQL para criação das tabelas e da view estão localizados no diretório src/main/resources/db. Para criar as tabelas e a view, execute os scripts no MySQL.

Exemplo de criação de view:

SQLite
```bash
Copiar código
CREATE VIEW vw_livros_por_autor AS
SELECT a.nome AS autor_nome, l.titulo AS livro_titulo, l.ano_publicacao, l.isbn
FROM autores a
JOIN livros_autores la ON a.id = la.autor_id
JOIN livros l ON la.livro_id = l.id
ORDER BY a.nome;
```

## Testes Unitários
Os testes cobrem as principais funcionalidades do sistema, incluindo transferências de CRUD e geração de relatórios.

Exemplo de teste unitário com Mockito:

Java
```bash
Copiar código
@Test
public void testAtualizarLivro() {
    Livro livro = new Livro();
    livro.setId(1L);
    livro.setTitulo("Livro Original");

    Livro livroAtualizado = new Livro();
    livroAtualizado.setTitulo("Livro Atualizado");

    when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
    when(livroRepository.save(any(Livro.class))).thenReturn(livroAtualizado);

    Livro resultado = livroService.atualizar(1L, livroAtualizado);

    assertNotNull(resultado);
    assertEquals("Livro Atualizado", resultado.getTitulo());
}
```
## Relatórios com JasperReports
O relatório de livros por autor foi implementado utilizando JasperReports . O modelo do relatório está localizado em src/main/resources/relatorios/livros_por_autor.jrxml.

## Exemplo de geração de relatório:

Java
Copiar código
```bash
public byte[] gerarRelatorioLivrosPorAutor() throws JRException {
    List<LivroAutorView> dados = livroAutorRepository.findAllLivrosPorAutor();
    InputStream relatorioStream = this.getClass().getResourceAsStream("/relatorios/livros_por_autor.jrxml");
    JasperReport jasperReport = JasperCompileManager.compileReport(relatorioStream);
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);
    Map<String, Object> parametros = new HashMap<>();
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
    return JasperExportManager.exportReportToPdf(jasperPrint);
}
```

# Front-End com Angular 18
O front-end deste projeto foi desenvolvido com Angular 18 e está disponível dentro da pasta api-livraria-front-end.

## 1. Abrir a pasta dentro do projeto
abrir a pasta no CMD 
```bash
cd cadastro-livros-frontend
2. Instalar Dependências
bater

Copiar código
npm install
3. Executar o Front-End
bater

Copiar código
ng serve
A aplicação estará disponível em http ://localhost :4200 .
```
## Conclusão
Este projeto implementa um sistema completo de cadastro de livros com funcionalidades de CRUD, geração de relatórios e testes unitários. Ele segue as melhores práticas e utiliza tecnologias amplamente adotadas no mercado, como Spring Boot , MySQL , JUnit , Mockito , JasperReports e Angular 18 .

Sinta-se à vontade para explorar o código e fazer melhorias conforme necessário. Para dúvidas ou sugestões, entre em contato!

### Autor : Leandro Ribeiro
### E-mail : devleandroribeiro @gmail .com
### GitHub : LeandroRibeiro2018

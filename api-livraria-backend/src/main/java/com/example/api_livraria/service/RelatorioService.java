package com.example.api_livraria.service;

import com.example.api_livraria.model.LivroAutorView;
import com.example.api_livraria.repository.LivroAutorRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RelatorioService {

    private static final Logger logger = LoggerFactory.getLogger(RelatorioService.class);

    @Autowired
    private LivroAutorRepository livroAutorRepository;

    public byte[] gerarRelatorioLivrosPorAutor() {
        try {
            // Obter os dados da view
            List<LivroAutorView> dados = livroAutorRepository.findAllLivrosPorAutor();

            // Carregar o arquivo .jrxml (template do relatório)
            InputStream relatorioStream = this.getClass().getResourceAsStream("/relatorios/livros_por_autor.jrxml");
            if (relatorioStream == null) {
                throw new RuntimeException("Relatório não encontrado: /relatorios/livros_por_autor.jrxml");
            }

            // Compilar o relatório
            JasperReport jasperReport = JasperCompileManager.compileReport(relatorioStream);

            // Preencher o relatório com os dados
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);
            Map<String, Object> parametros = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

            // Exportar o relatório para PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            logger.error("Erro ao gerar relatório de livros por autor: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao gerar relatório de livros por autor", e);
        }
    }
}
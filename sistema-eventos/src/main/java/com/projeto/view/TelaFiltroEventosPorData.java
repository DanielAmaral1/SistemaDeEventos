package com.projeto.view;

import com.projeto.entities.EventoEntity;
import com.projeto.service.EventoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaFiltroEventosPorData extends JFrame {
    private JTextField txtDataInicio;
    private JTextField txtDataFim;
    private JTable tabela;
    private DefaultTableModel modelo;
    private EventoService service;

    public TelaFiltroEventosPorData() {
        service = new EventoService();
        
        // Configurações da janela
        setTitle("Filtrar Eventos por Data");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Painel de filtro
        JPanel filterPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        filterPanel.add(new JLabel("Data Inicial (dd/mm/aaaa):"));
        txtDataInicio = new JTextField();
        filterPanel.add(txtDataInicio);
        
        filterPanel.add(new JLabel("Data Final (dd/mm/aaaa):"));
        txtDataFim = new JTextField();
        filterPanel.add(txtDataFim);
        
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(13, 110, 253));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.addActionListener(e -> filtrarEventos());
        filterPanel.add(btnFiltrar);
        
        // Modelo da tabela
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Nome do Evento");
        modelo.addColumn("Duração (horas)");
        modelo.addColumn("Data");
        modelo.addColumn("Palestrante");
        
        // Tabela
        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(108, 117, 125));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.addActionListener(e -> dispose());
        buttonPanel.add(btnFechar);
        
        // Adicionar componentes ao painel principal
        mainPanel.add(new JLabel("Filtrar Eventos por Intervalo de Datas", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adicionar painel principal à janela
        add(mainPanel);
    }
    
    private void filtrarEventos() {
        try {
            String dataInicioStr = txtDataInicio.getText().trim();
            String dataFimStr = txtDataFim.getText().trim();
            
            if (dataInicioStr.isEmpty() || dataFimStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha ambas as datas", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Converter datas
            SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");
            
            java.util.Date dataInicioUtil = sdfInput.parse(dataInicioStr);
            java.util.Date dataFimUtil = sdfInput.parse(dataFimStr);
            
            java.sql.Date dataInicio = java.sql.Date.valueOf(sdfOutput.format(dataInicioUtil));
            java.sql.Date dataFim = java.sql.Date.valueOf(sdfOutput.format(dataFimUtil));
            
            // Limpar tabela
            modelo.setRowCount(0);
            
            // Buscar eventos no intervalo de datas
            List<EventoEntity> eventos = service.buscarPorIntervaloDeDatas(dataInicio, dataFim);
            
            if (eventos.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum evento encontrado no período especificado", 
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Adicionar à tabela
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                for (EventoEntity e : eventos) {
                    String data = e.getData_evento() != null ? sdf.format(e.getData_evento()) : "";
                    String palestrante = e.getPalestrante() != null ? e.getPalestrante().getNome() : "";
                    
                    modelo.addRow(new Object[]{
                        e.getId_evento(),
                        e.getNome_evento(),
                        e.getDuracao_evento(),
                        data,
                        palestrante
                    });
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Formato de data inválido. Use dd/mm/aaaa", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao filtrar eventos: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package com.projeto.view;

import com.projeto.service.EventoService;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;
import javax.swing.border.EmptyBorder;

public class TelaEstatisticas extends JFrame {
    private JPanel statsPanel;
    private EventoService service;

    public TelaEstatisticas() {
        service = new EventoService();
        
        // Configurações da janela
        setTitle("Estatísticas do Sistema");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Painel de estatísticas
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(statsPanel);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBackground(new Color(13, 110, 253));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.addActionListener(e -> carregarEstatisticas());
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(108, 117, 125));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnFechar);
        
        // Adicionar componentes ao painel principal
        mainPanel.add(new JLabel("Estatísticas do Sistema (Operações Agregadas)", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adicionar painel principal à janela
        add(mainPanel);
        
        // Carregar estatísticas
        carregarEstatisticas();
    }
    
    private void carregarEstatisticas() {
        try {
            // Limpar painel
            statsPanel.removeAll();
            
            // Obter estatísticas
            Map<String, Object> estatisticas = service.obterEstatisticas();
            
            // Formatar números
            DecimalFormat df = new DecimalFormat("#.##");
            
            // Adicionar estatísticas ao painel
            addStatistic("Total de Eventos (COUNT)", estatisticas.get("totalEventos").toString());
            
            Double duracaoMedia = (Double) estatisticas.get("duracaoMedia");
            addStatistic("Duração Média dos Eventos (AVG)", df.format(duracaoMedia) + " horas");
            
            Object[] eventoMaisLongo = (Object[]) estatisticas.get("eventoMaisLongo");
            if (eventoMaisLongo != null) {
                addStatistic("Evento Mais Longo (MAX)", 
                    eventoMaisLongo[0] + " (" + eventoMaisLongo[1] + " horas)");
            }
            
            Object[] palestranteMaisEventos = (Object[]) estatisticas.get("palestranteMaisEventos");
            if (palestranteMaisEventos != null) {
                addStatistic("Palestrante com Mais Eventos (GROUP BY, COUNT)", 
                    palestranteMaisEventos[0] + " (" + palestranteMaisEventos[1] + " eventos)");
            }
            
            // Atualizar UI
            statsPanel.revalidate();
            statsPanel.repaint();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar estatísticas: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addStatistic(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel lblTitle = new JLabel(label + ":");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.PLAIN, 14));
        
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblValue, BorderLayout.CENTER);
        
        // Adicionar separador
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(500, 1));
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(panel, BorderLayout.CENTER);
        wrapperPanel.add(separator, BorderLayout.SOUTH);
        
        statsPanel.add(wrapperPanel);
    }
}

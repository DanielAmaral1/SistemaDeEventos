package com.projeto.view;

import com.projeto.repository.CustomizerFactory;
import com.projeto.repository.EventoParticipantesRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaEventosParticipantes extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaEventosParticipantes() {
        // Configurações da janela
        setTitle("Eventos e Participantes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Modelo da tabela
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Evento");
        modelo.addColumn("Participante");
        
        // Tabela
        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBackground(new Color(13, 110, 253));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.addActionListener(e -> carregarDados());
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(108, 117, 125));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnFechar);
        
        // Adicionar componentes ao painel principal
        mainPanel.add(new JLabel("Relação de Eventos e Participantes (JOIN)", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adicionar painel principal à janela
        add(mainPanel);
        
        // Carregar dados
        carregarDados();
    }
    
    private void carregarDados() {
        try {
            // Limpar tabela
            modelo.setRowCount(0);
            
            // Buscar relação de eventos e participantes (usando JOIN)
            EventoParticipantesRepository repository = new EventoParticipantesRepository(CustomizerFactory.getEntityManager());
            List<Object[]> resultados = repository.buscarEventosComParticipantes();
            
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum participante cadastrado em eventos", 
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Adicionar à tabela
                for (Object[] resultado : resultados) {
                    modelo.addRow(new Object[]{
                        resultado[0], // Nome do evento
                        resultado[1]  // Nome do participante
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar dados: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

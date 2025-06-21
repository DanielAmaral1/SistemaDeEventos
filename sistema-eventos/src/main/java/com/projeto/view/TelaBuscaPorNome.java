package com.projeto.view;

import com.projeto.controller.ParticipanteController;
import com.projeto.entities.ParticipanteEntity;
import com.projeto.service.ParticipanteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaBuscaPorNome extends JFrame {
    private JTextField txtBusca;
    private JTable tabela;
    private DefaultTableModel modelo;
    private ParticipanteService service;

    public TelaBuscaPorNome() {
        service = new ParticipanteService();
        
        // Configurações da janela
        setTitle("Busca de Participantes por Nome");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Painel de busca
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        JLabel lblBusca = new JLabel("Digite o nome para busca:");
        txtBusca = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(13, 110, 253));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarParticipantes());
        
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputPanel.add(txtBusca, BorderLayout.CENTER);
        inputPanel.add(btnBuscar, BorderLayout.EAST);
        
        searchPanel.add(lblBusca, BorderLayout.NORTH);
        searchPanel.add(inputPanel, BorderLayout.CENTER);
        
        // Modelo da tabela
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Email");
        modelo.addColumn("Idade");
        modelo.addColumn("Número");
        
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
        mainPanel.add(new JLabel("Busca de Participantes por Nome (LIKE)", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adicionar painel principal à janela
        add(mainPanel);
    }
    
    private void buscarParticipantes() {
        try {
            String nomeBusca = txtBusca.getText().trim();
            if (nomeBusca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um nome para buscar", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Limpar tabela
            modelo.setRowCount(0);
            
            // Buscar participantes pelo nome (usando LIKE)
            List<ParticipanteEntity> participantes = service.buscarPorNomeParcial(nomeBusca);
            
            if (participantes.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum participante encontrado com o nome '" + nomeBusca + "'", 
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Adicionar à tabela
                for (ParticipanteEntity p : participantes) {
                    modelo.addRow(new Object[]{
                        p.getId_participante(),
                        p.getNome(),
                        p.getEmail(),
                        p.getIdade_participante(),
                        p.getNumero_participante()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao buscar participantes: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

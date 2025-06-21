package com.projeto.view;

import com.projeto.entities.ParticipanteEntity;
import com.projeto.service.ParticipanteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaFiltroParticipantesPorIdade extends JFrame {
    private JTextField txtIdadeMin;
    private JTextField txtIdadeMax;
    private JTable tabela;
    private DefaultTableModel modelo;
    private ParticipanteService service;

    public TelaFiltroParticipantesPorIdade() {
        service = new ParticipanteService();
        
        setTitle("Filtrar Participantes por Idade");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel filterPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        filterPanel.add(new JLabel("Idade Mínima:"));
        txtIdadeMin = new JTextField();
        filterPanel.add(txtIdadeMin);
        
        filterPanel.add(new JLabel("Idade Máxima:"));
        txtIdadeMax = new JTextField();
        filterPanel.add(txtIdadeMax);
        
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(13, 110, 253));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.addActionListener(e -> filtrarParticipantes());
        filterPanel.add(btnFiltrar);
        
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
        
        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(108, 117, 125));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.addActionListener(e -> dispose());
        buttonPanel.add(btnFechar);
        
        mainPanel.add(new JLabel("Filtrar Participantes por Intervalo de Idade", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void filtrarParticipantes() {
        try {
            String idadeMinStr = txtIdadeMin.getText().trim();
            String idadeMaxStr = txtIdadeMax.getText().trim();
            
            if (idadeMinStr.isEmpty() || idadeMaxStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha ambos os campos de idade", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idadeMin = Integer.parseInt(idadeMinStr);
            int idadeMax = Integer.parseInt(idadeMaxStr);
            
            if (idadeMin < 0 || idadeMax < 0) {
                JOptionPane.showMessageDialog(this, "A idade deve ser um valor positivo", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (idadeMin > idadeMax) {
                JOptionPane.showMessageDialog(this, "A idade mínima não pode ser maior que a máxima", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            modelo.setRowCount(0);
            
            List<ParticipanteEntity> participantes = service.buscarPorIntervaloDeIdade(idadeMin, idadeMax);
            
            if (participantes.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum participante encontrado no intervalo de idade especificado", 
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Digite apenas números válidos para a idade", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao filtrar participantes: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
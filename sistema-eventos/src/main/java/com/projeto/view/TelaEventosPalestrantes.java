package com.projeto.view;

import com.projeto.service.EventoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaEventosPalestrantes extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private EventoService service;

    public TelaEventosPalestrantes() {
        service = new EventoService();
        
        setTitle("Eventos com Palestrantes (JOIN)");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Evento");
        modelo.addColumn("Duração (h)");
        modelo.addColumn("Data");
        modelo.addColumn("Palestrante");
        modelo.addColumn("Especialidade");
        
        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
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
        
        mainPanel.add(new JLabel("JOIN: Eventos com seus Palestrantes", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        carregarDados();
    }
    
    private void carregarDados() {
        try {
            modelo.setRowCount(0);
            
            List<Object[]> resultados = service.buscarEventosComPalestrantes();
            
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum evento com palestrante encontrado", 
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                for (Object[] resultado : resultados) {
                    String data = resultado[2] != null ? sdf.format(resultado[2]) : "";
                    modelo.addRow(new Object[]{
                        resultado[0], // nome_evento
                        resultado[1], // duracao_evento
                        data,         // data_evento
                        resultado[3], // nome palestrante
                        resultado[4]  // especialidade
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
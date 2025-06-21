package com.projeto.view;

import com.projeto.entities.EventoEntity;
import com.projeto.service.EventoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaFiltroEventosPorDuracao extends JFrame {
    private JTextField txtDuracaoMin;
    private JTextField txtDuracaoMax;
    private JTable tabela;
    private DefaultTableModel modelo;
    private EventoService service;

    public TelaFiltroEventosPorDuracao() {
        service = new EventoService();
        
        setTitle("Filtrar Eventos por Duração");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel filterPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        filterPanel.add(new JLabel("Duração Mínima (horas):"));
        txtDuracaoMin = new JTextField();
        filterPanel.add(txtDuracaoMin);
        
        filterPanel.add(new JLabel("Duração Máxima (horas):"));
        txtDuracaoMax = new JTextField();
        filterPanel.add(txtDuracaoMax);
        
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(13, 110, 253));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.addActionListener(e -> filtrarEventos());
        filterPanel.add(btnFiltrar);
        
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
        
        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(108, 117, 125));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.addActionListener(e -> dispose());
        buttonPanel.add(btnFechar);
        
        mainPanel.add(new JLabel("Filtrar Eventos por Intervalo de Duração", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void filtrarEventos() {
        try {
            String duracaoMinStr = txtDuracaoMin.getText().trim();
            String duracaoMaxStr = txtDuracaoMax.getText().trim();
            
            if (duracaoMinStr.isEmpty() || duracaoMaxStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha ambos os campos de duração", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int duracaoMin = Integer.parseInt(duracaoMinStr);
            int duracaoMax = Integer.parseInt(duracaoMaxStr);
            
            if (duracaoMin < 0 || duracaoMax < 0) {
                JOptionPane.showMessageDialog(this, "A duração deve ser um valor positivo", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (duracaoMin > duracaoMax) {
                JOptionPane.showMessageDialog(this, "A duração mínima não pode ser maior que a máxima", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            modelo.setRowCount(0);
            
            List<EventoEntity> eventos = service.buscarPorIntervaloDeDuracao(duracaoMin, duracaoMax);
            
            if (eventos.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum evento encontrado no intervalo de duração especificado", 
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Digite apenas números válidos para a duração", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao filtrar eventos: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
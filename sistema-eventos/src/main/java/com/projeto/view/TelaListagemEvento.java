package com.projeto.view;

import com.projeto.controller.EventoController;
import com.projeto.entities.EventoEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaListagemEvento extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private EventoController controller;

    public TelaListagemEvento() {
        controller = new EventoController();
        
        // Configurações da janela
        setTitle("Listagem de Eventos");
        setSize(800, 400);
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
        modelo.addColumn("ID");
        modelo.addColumn("Nome do Evento");
        modelo.addColumn("Duração (horas)");
        modelo.addColumn("Data");
        modelo.addColumn("Palestrante");
        
        // Tabela
        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(e -> editarEvento());
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBackground(new Color(220, 53, 69));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.addActionListener(e -> excluirEvento());
        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBackground(new Color(13, 110, 253));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.addActionListener(e -> carregarEventos());
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(108, 117, 125));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnFechar);
        
        // Adicionar componentes ao painel principal
        mainPanel.add(new JLabel("Listagem de Eventos", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adicionar painel principal à janela
        add(mainPanel);
        
        // Carregar dados
        carregarEventos();
    }
    
    private void carregarEventos() {
        try {
            // Limpar tabela
            modelo.setRowCount(0);
            
            // Buscar eventos
            List<EventoEntity> eventos = controller.listarTodos();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            // Adicionar à tabela
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar eventos: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarEvento() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long id = (Long) modelo.getValueAt(selectedRow, 0);
        EventoEntity evento = controller.buscarPorId(id);
        
        if (evento != null) {
            TelaEditarEvento telaEditar = new TelaEditarEvento(evento);
            telaEditar.setVisible(true);
            telaEditar.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    carregarEventos();
                }
            });
        }
    }
    
    private void excluirEvento() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para excluir", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long id = (Long) modelo.getValueAt(selectedRow, 0);
        String nome = (String) modelo.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Tem certeza que deseja excluir o evento '" + nome + "'?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean sucesso = controller.excluir(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Evento excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarEventos();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir o evento", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
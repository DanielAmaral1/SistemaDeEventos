package com.projeto.view;

import com.projeto.controller.PalestranteController;
import com.projeto.entities.PalestranteEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaListagemPalestrante extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private PalestranteController controller;

    public TelaListagemPalestrante() {
        controller = new PalestranteController();
        
        // Configurações da janela
        setTitle("Listagem de Palestrantes");
        setSize(700, 400);
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
        modelo.addColumn("Nome");
        modelo.addColumn("Email");
        modelo.addColumn("Idade");
        modelo.addColumn("Especialidade");
        
        // Tabela
        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(e -> editarPalestrante());
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBackground(new Color(220, 53, 69));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.addActionListener(e -> excluirPalestrante());
        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBackground(new Color(13, 110, 253));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.addActionListener(e -> carregarPalestrantes());
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(108, 117, 125));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnFechar);
        
        // Adicionar componentes ao painel principal
        mainPanel.add(new JLabel("Listagem de Palestrantes", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adicionar painel principal à janela
        add(mainPanel);
        
        // Carregar dados
        carregarPalestrantes();
    }
    
    private void carregarPalestrantes() {
        try {
            // Limpar tabela
            modelo.setRowCount(0);
            
            // Buscar palestrantes
            List<PalestranteEntity> palestrantes = controller.listarTodos();
            
            // Adicionar à tabela
            for (PalestranteEntity p : palestrantes) {
                modelo.addRow(new Object[]{
                    p.getId_palestrante(),
                    p.getNome(),
                    p.getEmail(),
                    p.getIdade_palestrante(),
                    p.getEspecialidade_palestrante()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar palestrantes: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarPalestrante() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long id = (Long) modelo.getValueAt(selectedRow, 0);
        PalestranteEntity palestrante = controller.buscarPorId(id);
        
        if (palestrante != null) {
            TelaEditarPalestrante telaEditar = new TelaEditarPalestrante(palestrante);
            telaEditar.setVisible(true);
            telaEditar.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    carregarPalestrantes();
                }
            });
        }
    }
    
    private void excluirPalestrante() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante para excluir", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long id = (Long) modelo.getValueAt(selectedRow, 0);
        String nome = (String) modelo.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Tem certeza que deseja excluir o palestrante '" + nome + "'?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean sucesso = controller.excluir(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Palestrante excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarPalestrantes();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir o palestrante", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
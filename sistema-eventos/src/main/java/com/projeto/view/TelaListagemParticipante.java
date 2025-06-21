package com.projeto.view;

import com.projeto.controller.ParticipanteController;
import com.projeto.entities.ParticipanteEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaListagemParticipante extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(52, 58, 64);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private static final Color WARNING_COLOR = new Color(255, 193, 7);
    private static final Color DANGER_COLOR = new Color(220, 53, 69);
    private static final Color INFO_COLOR = new Color(13, 110, 253);
    private static final Color SECONDARY_COLOR = new Color(108, 117, 125);
    private static final Color LIGHT_BG = new Color(248, 249, 250);
    
    private JTable tabela;
    private DefaultTableModel modelo;
    private ParticipanteController controller;
    private JLabel lblTotal;

    public TelaListagemParticipante() {
        controller = new ParticipanteController();
        
        setTitle("Listagem de Participantes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT_BG);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_BG);
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        
        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Stats Panel
        JPanel statsPanel = createStatsPanel();
        
        // Table
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
        modelo.addColumn("Contato");
        
        tabela = createStyledTable();
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Action Panel
        JPanel actionPanel = createActionPanel();
        
        contentPanel.add(statsPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(actionPanel, BorderLayout.SOUTH);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        carregarParticipantes();
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        JLabel title = new JLabel("Listagem de Participantes");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Visualize e gerencie todos os participantes cadastrados");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(200, 200, 200));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);
        
        header.add(titlePanel, BorderLayout.WEST);
        return header;
    }
    
    private JPanel createStatsPanel() {
        JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stats.setBackground(Color.WHITE);
        stats.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        lblTotal = new JLabel("Total: 0 participantes");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotal.setForeground(PRIMARY_COLOR);
        
        stats.add(lblTotal);
        return stats;
    }
    
    private JTable createStyledTable() {
        JTable table = new JTable(modelo);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(35);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(13, 110, 253, 50));
        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);
        
        // Header styling
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(LIGHT_BG);
        table.getTableHeader().setForeground(PRIMARY_COLOR);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        // Center align numeric columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Idade
        
        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        
        return table;
    }
    
    private JPanel createActionPanel() {
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        actions.setBackground(Color.WHITE);
        
        JButton btnNovo = createStyledButton("Novo Participante", SUCCESS_COLOR);
        btnNovo.addActionListener(e -> new TelaCadastroParticipante().setVisible(true));
        
        JButton btnEditar = createStyledButton("Editar", WARNING_COLOR);
        btnEditar.addActionListener(e -> editarParticipante());
        
        JButton btnExcluir = createStyledButton("Excluir", DANGER_COLOR);
        btnExcluir.addActionListener(e -> excluirParticipante());
        
        JButton btnAtualizar = createStyledButton("Atualizar", INFO_COLOR);
        btnAtualizar.addActionListener(e -> carregarParticipantes());
        
        JButton btnFechar = createStyledButton("Fechar", SECONDARY_COLOR);
        btnFechar.addActionListener(e -> dispose());
        
        actions.add(btnNovo);
        actions.add(btnEditar);
        actions.add(btnExcluir);
        actions.add(btnAtualizar);
        actions.add(btnFechar);
        
        return actions;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 35));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void carregarParticipantes() {
        try {
            modelo.setRowCount(0);
            
            List<ParticipanteEntity> participantes = controller.listarTodos();
            
            for (ParticipanteEntity p : participantes) {
                modelo.addRow(new Object[]{
                    p.getId_participante(),
                    p.getNome(),
                    p.getEmail(),
                    p.getIdade_participante(),
                    p.getNumero_participante()
                });
            }
            
            // Atualizar estatísticas
            lblTotal.setText("Total: " + participantes.size() + " participantes");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar participantes: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarParticipante() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um participante para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long id = (Long) modelo.getValueAt(selectedRow, 0);
        ParticipanteEntity participante = controller.buscarPorId(id);
        
        if (participante != null) {
            TelaEditarParticipante telaEditar = new TelaEditarParticipante(participante);
            telaEditar.setVisible(true);
            telaEditar.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    carregarParticipantes();
                }
            });
        }
    }
    
    private void excluirParticipante() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um participante para excluir", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long id = (Long) modelo.getValueAt(selectedRow, 0);
        String nome = (String) modelo.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Tem certeza que deseja excluir o participante '" + nome + "'?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean sucesso = controller.excluir(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Participante excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarParticipantes();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir o participante", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
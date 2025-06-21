package com.projeto.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class TelaPrincipal extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(52, 58, 64);
    private static final Color ACCENT_COLOR = new Color(0, 123, 255);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private static final Color WARNING_COLOR = new Color(255, 193, 7);
    private static final Color DANGER_COLOR = new Color(220, 53, 69);
    private static final Color LIGHT_BG = new Color(248, 249, 250);

    public TelaPrincipal() {
        setTitle("Sistema de Gerenciamento de Eventos");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_BG);
        
        // Header moderno
        JPanel headerPanel = createHeaderPanel();
        
        // Dashboard central
        JPanel dashboardPanel = createDashboardPanel();
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(dashboardPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        JLabel title = new JLabel("Sistema de Gerenciamento de Eventos");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Gerencie eventos, palestrantes e participantes de forma eficiente");
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
    
    private JPanel createDashboardPanel() {
        JPanel dashboard = new JPanel(new GridBagLayout());
        dashboard.setBackground(LIGHT_BG);
        dashboard.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Cards principais
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1; gbc.weighty = 1;
        dashboard.add(createCard("Participantes", "Gerenciar participantes dos eventos", SUCCESS_COLOR, 
            new String[]{"Cadastrar", "Listar", "Buscar por Nome", "Filtrar por Idade"}, 
            new Runnable[]{() -> new TelaCadastroParticipante().setVisible(true),
                          () -> new TelaListagemParticipante().setVisible(true),
                          () -> new TelaBuscaPorNome().setVisible(true),
                          () -> new TelaFiltroParticipantesPorIdade().setVisible(true)}), gbc);
        
        gbc.gridx = 1;
        dashboard.add(createCard("Palestrantes", "Gerenciar palestrantes e especialidades", ACCENT_COLOR,
            new String[]{"Cadastrar", "Listar", "Filtrar por Idade"},
            new Runnable[]{() -> new TelaCadastroPalestrante().setVisible(true),
                          () -> new TelaListagemPalestrante().setVisible(true),
                          () -> new TelaFiltroPalestrantesPorIdade().setVisible(true)}), gbc);
        
        gbc.gridx = 2;
        dashboard.add(createCard("Eventos", "Organizar e agendar eventos", WARNING_COLOR,
            new String[]{"Cadastrar", "Listar", "Filtrar por Data", "Filtrar por Duração"},
            new Runnable[]{() -> new TelaCadastroEvento().setVisible(true),
                          () -> new TelaListagemEvento().setVisible(true),
                          () -> new TelaFiltroEventosPorData().setVisible(true),
                          () -> new TelaFiltroEventosPorDuracao().setVisible(true)}), gbc);
        
        // Cards secundários
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        dashboard.add(createCard("Consultas JOIN", "Relatórios com relacionamentos", new Color(111, 66, 193),
            new String[]{"Eventos + Participantes", "Eventos + Palestrantes"},
            new Runnable[]{() -> new TelaEventosParticipantes().setVisible(true),
                          () -> new TelaEventosPalestrantes().setVisible(true)}), gbc);
        
        gbc.gridx = 1;
        dashboard.add(createCard("Estatísticas", "Análises e métricas do sistema", new Color(220, 53, 69),
            new String[]{"Ver Estatísticas"},
            new Runnable[]{() -> new TelaEstatisticas().setVisible(true)}), gbc);
        
        return dashboard;
    }
    
    private JPanel createCard(String title, String description, Color color, String[] actions, Runnable[] callbacks) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(20, 20, 20, 20)));
        card.setPreferredSize(new Dimension(300, 250));
        
        // Header do card
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(color);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(108, 117, 125));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(descLabel);
        card.add(Box.createVerticalStrut(15));
        
        // Botões de ação
        for (int i = 0; i < actions.length; i++) {
            JButton btn = createModernButton(actions[i], color, callbacks[i]);
            card.add(btn);
            if (i < actions.length - 1) card.add(Box.createVerticalStrut(8));
        }
        
        return card;
    }
    
    private JButton createModernButton(String text, Color color, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 35));
        button.setPreferredSize(new Dimension(250, 35));
        button.addActionListener(e -> action.run());
        
        // Hover effect
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
    
    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(PRIMARY_COLOR);
        footer.setBorder(new EmptyBorder(15, 30, 15, 30));
        
        JLabel info = new JLabel("© 2024 Sistema de Eventos - Desenvolvido com Java Swing");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        info.setForeground(new Color(200, 200, 200));
        
        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSair.setBackground(DANGER_COLOR);
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setBorderPainted(false);
        btnSair.addActionListener(e -> System.exit(0));
        
        footer.add(info, BorderLayout.WEST);
        footer.add(btnSair, BorderLayout.EAST);
        
        return footer;
    }
}
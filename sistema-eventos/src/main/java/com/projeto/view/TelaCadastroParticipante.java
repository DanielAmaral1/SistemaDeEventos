package com.projeto.view;

import com.projeto.controller.ParticipanteController;
import com.projeto.entities.ParticipanteEntity;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class TelaCadastroParticipante extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(52, 58, 64);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private static final Color SECONDARY_COLOR = new Color(108, 117, 125);
    private static final Color LIGHT_BG = new Color(248, 249, 250);
    
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtIdade;
    private JTextField txtNumero;
    private ParticipanteController controller;

    public TelaCadastroParticipante() {
        controller = new ParticipanteController();
        
        setTitle("Cadastro de Participante");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT_BG);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_BG);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        JLabel titleLabel = new JLabel("Cadastro de Participante");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Preencha os dados do novo participante");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        // Campos do formulário
        formPanel.add(createFormField("Nome Completo:", txtNome = createStyledTextField()));
        formPanel.add(Box.createVerticalStrut(20));
        
        formPanel.add(createFormField("Email:", txtEmail = createStyledTextField()));
        formPanel.add(Box.createVerticalStrut(20));
        
        formPanel.add(createFormField("Idade:", txtIdade = createStyledTextField()));
        formPanel.add(Box.createVerticalStrut(20));
        
        formPanel.add(createFormField("Número de Contato:", txtNumero = createStyledTextField()));
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnSalvar = createStyledButton("Salvar Participante", SUCCESS_COLOR);
        btnSalvar.addActionListener(e -> salvarParticipante());
        
        JButton btnCancelar = createStyledButton("Cancelar", SECONDARY_COLOR);
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);
        
        // Adicionar ao form panel
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(buttonPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createFormField(String labelText, JTextField textField) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(PRIMARY_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        fieldPanel.add(label);
        fieldPanel.add(Box.createVerticalStrut(8));
        fieldPanel.add(textField);
        
        return fieldPanel;
    }
    
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(400, 40));
        textField.setMaximumSize(new Dimension(400, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            new EmptyBorder(10, 15, 10, 15)));
        return textField;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 45));
        
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
    
    private void salvarParticipante() {
        try {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            int idade = Integer.parseInt(txtIdade.getText().trim());
            String numero = txtNumero.getText().trim();
            
            if (nome.isEmpty() || email.isEmpty() || numero.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ParticipanteEntity participante = new ParticipanteEntity();
            participante.setNome(nome);
            participante.setEmail(email);
            participante.setIdade_participante(idade);
            participante.setNumero_participante(numero);
            
            controller.salvar(participante);
            
            JOptionPane.showMessageDialog(this, "Participante cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número inteiro!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtIdade.setText("");
        txtNumero.setText("");
        txtNome.requestFocus();
    }
}
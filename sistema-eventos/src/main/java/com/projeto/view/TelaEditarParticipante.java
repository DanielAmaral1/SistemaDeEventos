package com.projeto.view;

import com.projeto.controller.ParticipanteController;
import com.projeto.entities.ParticipanteEntity;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class TelaEditarParticipante extends JFrame {
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtIdade;
    private JTextField txtNumero;
    private ParticipanteController controller;
    private ParticipanteEntity participante;

    public TelaEditarParticipante(ParticipanteEntity participante) {
        this.participante = participante;
        controller = new ParticipanteController();
        
        // Configurações da janela
        setTitle("Editar Participante");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField(participante.getNome());
        formPanel.add(txtNome);
        
        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField(participante.getEmail());
        formPanel.add(txtEmail);
        
        formPanel.add(new JLabel("Idade:"));
        txtIdade = new JTextField(String.valueOf(participante.getIdade_participante()));
        formPanel.add(txtIdade);
        
        formPanel.add(new JLabel("Número de Contato:"));
        txtNumero = new JTextField(participante.getNumero_participante());
        formPanel.add(txtNumero);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(40, 167, 69));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.addActionListener(e -> salvarParticipante());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(108, 117, 125));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        // Adicionar componentes ao painel principal
        mainPanel.add(new JLabel("Editar Participante", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adicionar painel principal à janela
        add(mainPanel);
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
            
            participante.setNome(nome);
            participante.setEmail(email);
            participante.setIdade_participante(idade);
            participante.setNumero_participante(numero);
            
            controller.atualizar(participante);
            
            JOptionPane.showMessageDialog(this, "Participante atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número inteiro!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
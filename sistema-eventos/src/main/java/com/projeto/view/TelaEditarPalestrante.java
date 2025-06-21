package com.projeto.view;

import com.projeto.controller.PalestranteController;
import com.projeto.entities.PalestranteEntity;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class TelaEditarPalestrante extends JFrame {
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtIdade;
    private JTextField txtEspecialidade;
    private PalestranteController controller;
    private PalestranteEntity palestrante;

    public TelaEditarPalestrante(PalestranteEntity palestrante) {
        this.palestrante = palestrante;
        controller = new PalestranteController();
        
        // Configurações da janela
        setTitle("Editar Palestrante");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField(palestrante.getNome());
        formPanel.add(txtNome);
        
        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField(palestrante.getEmail());
        formPanel.add(txtEmail);
        
        formPanel.add(new JLabel("Idade:"));
        txtIdade = new JTextField(String.valueOf(palestrante.getIdade_palestrante()));
        formPanel.add(txtIdade);
        
        formPanel.add(new JLabel("Especialidade:"));
        txtEspecialidade = new JTextField(palestrante.getEspecialidade_palestrante());
        formPanel.add(txtEspecialidade);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(40, 167, 69));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.addActionListener(e -> salvarPalestrante());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(108, 117, 125));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        // Adicionar componentes ao painel principal
        mainPanel.add(new JLabel("Editar Palestrante", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adicionar painel principal à janela
        add(mainPanel);
    }
    
    private void salvarPalestrante() {
        try {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            int idade = Integer.parseInt(txtIdade.getText().trim());
            String especialidade = txtEspecialidade.getText().trim();
            
            if (nome.isEmpty() || email.isEmpty() || especialidade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            palestrante.setNome(nome);
            palestrante.setEmail(email);
            palestrante.setIdade_palestrante(idade);
            palestrante.setEspecialidade_palestrante(especialidade);
            
            controller.atualizar(palestrante);
            
            JOptionPane.showMessageDialog(this, "Palestrante atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número inteiro!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
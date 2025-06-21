package com.projeto.view;

import com.projeto.controller.PalestranteController;
import com.projeto.entities.PalestranteEntity;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class TelaCadastroPalestrante extends JFrame {
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtIdade;
    private JTextField txtEspecialidade;
    private PalestranteController controller;

    public TelaCadastroPalestrante() {
        controller = new PalestranteController();
        
        // Configurações da janela
        setTitle("Cadastro de Palestrante");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formPanel.add(txtNome);
        
        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);
        
        formPanel.add(new JLabel("Idade:"));
        txtIdade = new JTextField();
        formPanel.add(txtIdade);
        
        formPanel.add(new JLabel("Especialidade:"));
        txtEspecialidade = new JTextField();
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
        mainPanel.add(new JLabel("Cadastro de Palestrante", JLabel.CENTER), BorderLayout.NORTH);
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
            
            PalestranteEntity palestrante = new PalestranteEntity();
            palestrante.setNome(nome);
            palestrante.setEmail(email);
            palestrante.setIdade_palestrante(idade);
            palestrante.setEspecialidade_palestrante(especialidade);
            
            controller.salvar(palestrante);
            
            JOptionPane.showMessageDialog(this, "Palestrante cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
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
        txtEspecialidade.setText("");
        txtNome.requestFocus();
    }
}
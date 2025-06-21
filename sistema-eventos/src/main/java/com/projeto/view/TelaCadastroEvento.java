package com.projeto.view;

import com.projeto.controller.EventoController;
import com.projeto.controller.PalestranteController;
import com.projeto.entities.EventoEntity;
import com.projeto.entities.PalestranteEntity;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaCadastroEvento extends JFrame {
    private JTextField txtNome;
    private JTextField txtDuracao;
    private JTextField txtData;
    private JComboBox<PalestranteEntity> cbPalestrantes;
    private EventoController controller;
    private PalestranteController palestranteController;

    public TelaCadastroEvento() {
        controller = new EventoController();
        palestranteController = new PalestranteController();
        
        // Configurações da janela
        setTitle("Cadastro de Evento");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        formPanel.add(new JLabel("Nome do Evento:"));
        txtNome = new JTextField();
        formPanel.add(txtNome);
        
        formPanel.add(new JLabel("Duração (horas):"));
        txtDuracao = new JTextField();
        formPanel.add(txtDuracao);
        
        formPanel.add(new JLabel("Data (dd/mm/aaaa):"));
        txtData = new JTextField();
        formPanel.add(txtData);
        
        formPanel.add(new JLabel("Palestrante:"));
        cbPalestrantes = new JComboBox<>();
        carregarPalestrantes();
        formPanel.add(cbPalestrantes);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(40, 167, 69));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.addActionListener(e -> salvarEvento());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(108, 117, 125));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        // Adicionar componentes ao painel principal
        mainPanel.add(new JLabel("Cadastro de Evento", JLabel.CENTER), BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adicionar painel principal à janela
        add(mainPanel);
    }
    
    private void carregarPalestrantes() {
        try {
            List<PalestranteEntity> palestrantes = palestranteController.listarTodos();
            DefaultComboBoxModel<PalestranteEntity> model = new DefaultComboBoxModel<>();
            
            for (PalestranteEntity palestrante : palestrantes) {
                model.addElement(palestrante);
            }
            
            cbPalestrantes.setModel(model);
            cbPalestrantes.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof PalestranteEntity) {
                        setText(((PalestranteEntity) value).getNome());
                    }
                    return this;
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar palestrantes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarEvento() {
        try {
            String nome = txtNome.getText().trim();
            int duracao = Integer.parseInt(txtDuracao.getText().trim());
            String dataStr = txtData.getText().trim();
            PalestranteEntity palestrante = (PalestranteEntity) cbPalestrantes.getSelectedItem();
            
            if (nome.isEmpty() || dataStr.isEmpty() || palestrante == null) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = sdf.parse(dataStr);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            EventoEntity evento = new EventoEntity();
            evento.setNome_evento(nome);
            evento.setDuracao_evento(duracao);
            evento.setData_evento(sqlDate);
            evento.setPalestrante(palestrante);
            
            controller.salvar(evento);
            
            JOptionPane.showMessageDialog(this, "Evento cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Duração deve ser um número inteiro!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/mm/aaaa", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtDuracao.setText("");
        txtData.setText("");
        cbPalestrantes.setSelectedIndex(0);
        txtNome.requestFocus();
    }
}
package com.projeto.view;

public class Main {
    public static void main(String[] args) {
        // Configurar o look and feel para melhorar a aparência
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar a aplicação
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}
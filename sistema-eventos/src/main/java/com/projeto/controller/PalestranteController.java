package com.projeto.controller;

import java.util.List;
import java.util.Scanner;

import com.projeto.entities.PalestranteEntity;
import com.projeto.service.PalestranteService;

public class PalestranteController {
    static Scanner teclado = new Scanner(System.in);
    static PalestranteService service = new PalestranteService();
    
    private PalestranteService palestranteService;
    
    public PalestranteController() {
        this.palestranteService = new PalestranteService();
    }
    
    public List<PalestranteEntity> listarTodos() {
        return palestranteService.buscarTodos();
    }
    
    public void salvar(PalestranteEntity palestrante) {
        palestranteService.salvarPalestrante(palestrante);
    }
    
    public PalestranteEntity buscarPorId(Long id) {
        return palestranteService.buscarPorId(id);
    }
    
    public void atualizar(PalestranteEntity palestrante) {
        palestranteService.atualizarPalestrante(palestrante);
    }
    
    public boolean excluir(Long id) {
        return palestranteService.removerPalestrante(id);
    }

    public static void exibir() {
        System.out.println("===== Lista de Palestrantes =====");

        List<PalestranteEntity> palestrantes = service.buscarTodos();

        if (palestrantes.isEmpty()) {
            System.out.println("Nenhum palestrante encontrado.");
        } else {
            for (PalestranteEntity p : palestrantes) {
                System.out.println("ID: " + p.getId_palestrante());
                System.out.println("Nome: " + p.getNome());
                System.out.println("Idade: " + p.getIdade_palestrante());
                System.out.println("Especialidade: " + p.getEspecialidade_palestrante());
                System.out.println("----------------------------------");
            }
        }
    }
    // metodo de cadastrar palestrante
    public static void cadastrar() {
        String nome;
        int idade;

        System.out.println("===== Cadastro de Palestrante =====");

        do {
            System.out.print("Nome: ");
            nome = teclado.nextLine();
            if (nome.length() <= 3) {
                System.out.println("O nome precisa ter mais de 3 letras. Tente novamente.");
            }
        } while (nome.length() <= 3);

        do {
            System.out.print("Idade: ");
            idade = teclado.nextInt();
            if (idade < 18) {
                System.out.println("A idade precisa ser maior ou igual a 18. Tente novamente.");
            }
        } while (idade < 18);

        teclado.nextLine(); // consome a quebra de linha

        String email;
        do {
            System.out.print("Email: ");
            email = teclado.nextLine();

            boolean valido = email.contains("@") && email.contains(".") &&
                    email.indexOf("@") > 0 &&
                    email.lastIndexOf(".") > email.indexOf("@") + 1 &&
                    email.lastIndexOf(".") < email.length() - 1;

            if (!valido) {
                System.out.println("E-mail inválido. Tente novamente.");
            } else {
                break;
            }

        } while (true);

        System.out.print("Especialidade: ");
        String especialidade = teclado.nextLine();

        PalestranteEntity palestrante = new PalestranteEntity();
        palestrante.setNome(nome);
        palestrante.setIdade_palestrante(idade);
        palestrante.setEmail(email);
        palestrante.setEspecialidade_palestrante(especialidade);

        service.salvarPalestrante(palestrante);
        System.out.println("Palestrante " + nome + " cadastrado com sucesso!");
    }
    //metodo de remover palestrante
    public static void remover() {
        System.out.println("===== Remoção de Palestrante =====");
        //chama o metodo de buscar todos os palestrantes 
        List<PalestranteEntity> palestrantes = service.buscarTodos();

        if (palestrantes.isEmpty()) {
            System.out.println("Nenhum palestrante cadastrado.");
            return;
        }
        //mostra todos os palestrantes cadastrados
        for (PalestranteEntity p : palestrantes) {
            System.out.println("ID: " + p.getId_palestrante() + " - Nome: " + p.getNome());
        }

        System.out.print("Digite o ID do palestrante que deseja remover: ");
        Long id = teclado.nextLong();
        teclado.nextLine(); // consome quebra de linha

        boolean removido = service.removerPalestrante(id);

        if (removido) {
            System.out.println("Palestrante removido com sucesso!");
        } else {
            System.out.println("Palestrante com ID " + id + " não encontrado.");
        }
    }
    
    // Método para contar total de palestrantes (COUNT)
    public static void contarPalestrantes() {
        System.out.println("===== Total de Palestrantes =====");
        List<PalestranteEntity> palestrantes = service.buscarTodos();
        int total = palestrantes.size();
        System.out.println("Total de palestrantes cadastrados: " + total);
        System.out.println("----------------------------------");
    }
}
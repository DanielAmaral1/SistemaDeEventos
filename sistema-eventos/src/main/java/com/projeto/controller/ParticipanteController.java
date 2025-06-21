package com.projeto.controller;

import com.projeto.service.ParticipanteService;
import com.projeto.entities.PalestranteEntity;
import com.projeto.entities.ParticipanteEntity;
import java.util.List;
import java.util.Scanner;

public class ParticipanteController {
    static Scanner teclado = new Scanner(System.in);
    private static ParticipanteService service = new ParticipanteService();
    
    private ParticipanteService participanteService;
    
    public ParticipanteController() {
        this.participanteService = new ParticipanteService();
    }
    
    public List<ParticipanteEntity> listarTodos() {
        return participanteService.buscarTodos();
    }
    
    public void salvar(ParticipanteEntity participante) {
        participanteService.salvarParticipante(participante);
    }
    
    public ParticipanteEntity buscarPorId(Long id) {
        return participanteService.buscarPorId(id);
    }
    
    public void atualizar(ParticipanteEntity participante) {
        participanteService.atualizarParticipante(participante);
    }
    
    public boolean excluir(Long id) {
        return participanteService.removerParticipante(id);
    }

    //metodo de exibir os participantes
    public static void exibir() {
        System.out.println("===== Lista de Participantes =====");
        List<ParticipanteEntity> participantes = service.buscarTodos();

        if (participantes.isEmpty()) {
            System.out.println("Nenhum participante encontrado.");
        } else {
            for (ParticipanteEntity p : participantes) {
                System.out.println("ID: " + p.getId_participante());
                System.out.println("Nome: " + p.getNome()); // Usa o método herdado de Pessoa
                System.out.println("Idade: " + p.getIdade_participante());
                System.out.println("Email: " + p.getEmail()); // Usa o método herdado de Pessoa
                System.out.println("Número: " + p.getNumero_participante());
                System.out.println("----------------------------------");
            }
        }
    }
    //metodo de cadastrar um novo participante
    public static void cadastrar() {
        String nome;
        int idade;
        System.out.println("===== Cadastro de Participante =====");

        // Nome
        do {
            System.out.print("Nome: ");
            nome = teclado.nextLine();

            if (nome.length() <= 3) {
                System.out.println("O nome precisa ter mais de 3 letras. Tente novamente.");
            }

        } while (nome.length() <= 3);

        // Idade
        do {
            System.out.print("Idade: ");
            idade = teclado.nextInt();
            if (idade < 18) {
                System.out.println("A idade precisa ser maior ou igual a 18. Tente novamente.");
            }
        } while (idade < 18);

        teclado.nextLine(); // quebra de linha

        // Email
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

        // Número de telefone
        String numero;
        while (true) {
            System.out.print("Número de telefone (somente números, ex: 45991111476): ");
            numero = teclado.nextLine();

            // Verifica se tem exatamente 11 dígitos e se todos são números
            if (numero.matches("\\d{11}")) {
                break; // número válido, sai do loop
            } else {
                System.out.println("Número inválido. Digite exatamente 11 números.");
            }
        }

        // Criar e salvar participante
        ParticipanteEntity participante = new ParticipanteEntity();
        participante.setNome(nome); // Usa o método herdado de Pessoa
        participante.setIdade_participante(idade);
        participante.setEmail(email); // Usa o método herdado de Pessoa
        participante.setNumero_participante(numero);

        service.salvarParticipante(participante);
        System.out.println("Participante '" + nome + "' cadastrado com sucesso!");
    }

    //metodo de remover os participantes
    public static void remover() {
        System.out.println("===== Remoção de Participante =====");

        //busca todos os participantes que estão cadastrados
        List<ParticipanteEntity> participantes = service.buscarTodos();

        if (participantes.isEmpty()) {
            System.out.println("Nenhum participante cadastrado.");
            return;
        }

        for (ParticipanteEntity p : participantes) {
            System.out.println("ID: " + p.getId_participante() + " - Nome: " + p.getNome());
        }

        System.out.print("Digite o ID do participante que deseja remover: ");
        Long id = teclado.nextLong();
        teclado.nextLine(); // consome quebra de linha

        boolean removido = service.removerParticipante(id);

        if (removido) {
            System.out.println("Participante removido com sucesso!");
        } else {
            System.out.println("Participante com ID " + id + " não encontrado.");
        }
    }
    
        //metodo que busca os participantes por nome(com select e like)
    public static void buscarParticipantesPorNome() {
        System.out.println("===== Buscar Participantes por Nome =====");
        System.out.print("Digite parte do nome do participante: ");
        String nome = teclado.nextLine();
        // dentro do buscarPorNomeParcial esta o select com like
        List<ParticipanteEntity> participantes = service.buscarPorNomeParcial(nome);
    
        if (participantes.isEmpty()) {
            System.out.println("Nenhum participante encontrado com o nome informado.");
        } else {
            System.out.println("Participantes encontrados:");
            for (ParticipanteEntity participante : participantes) {
                System.out.println("ID: " + participante.getId_participante());
                System.out.println("Nome: " + participante.getNome());
                System.out.println("Email: " + participante.getEmail());
                System.out.println("----------------------------------");
            }
        }
    }
    
    // Método para contar total de participantes (COUNT)
    public static void contarParticipantes() {
        System.out.println("===== Total de Participantes =====");
        List<ParticipanteEntity> participantes = service.buscarTodos();
        int total = participantes.size();
        System.out.println("Total de participantes cadastrados: " + total);
        System.out.println("----------------------------------");
    }
}
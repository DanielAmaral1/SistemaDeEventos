package com.projeto;

import com.projeto.service.ParticipanteService;
import com.projeto.view.TelaPrincipal;
import com.projeto.controller.PalestranteController;
import com.projeto.controller.ParticipanteController;
import com.projeto.controller.EventoController;

import javax.swing.SwingUtilities;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("================= SISTEMA DE EVENTOS =================\n");
        System.out.println("Escolha o modo de execução:");
        System.out.println("(1) - Interface Gráfica (GUI)");
        System.out.println("(2) - Sistema em Linha de Comando (CMD)");
        System.out.print("Opção: ");
        
        int modoExecucao = teclado.nextInt();
        
        if (modoExecucao == 1) {
            // Modo GUI
            SwingUtilities.invokeLater(() -> {
                new TelaPrincipal().setVisible(true);
            });
            System.out.println("Interface gráfica iniciada!");
            return;
        } else if (modoExecucao == 2) {
            // Modo CMD
            executarSistemaCMD(teclado);
        } else {
            System.out.println("Opção inválida!");
        }
    }
    
    private static void executarSistemaCMD(Scanner teclado) {
        int opcao;

        // Loop para garantir que o menu principal continue até o usuário sair (qnd
        // digitado o numero 5)
        while (true) {
            System.out.println("================= SISTEMA DE EVENTOS =================\n");
            System.out.println("(1) - Cadastros");
            System.out.println("(2) - Agenda  de Eventos");
            System.out.println("(3) - Exibir Informações");
            System.out.println("(4) - Remover Cadastros e Eventos ");
            System.out.println("(5) - Estatísticas");
            System.out.println("(6) - Sair");
            System.out.println("======================================================  ");
            System.out.println("Escolha uma opção:  ");
            opcao = teclado.nextInt();

            switch (opcao) {

                case 1:
                    // Menu de cadastros
                    int opcaoCadastro;
                    while (true) {
                        System.out.println("================= (1) - Cadastros =================\n");
                        System.out.println("(1) - Cadastrar Participante");
                        System.out.println("(2) - Cadastrar Palestrante");
                        System.out.println("(3) - Cadatrar Eventos");
                        System.out.println("(4) - Cadastrar Inscrição");
                        System.out.println("(5) - Voltar");
                        System.out.println("Escolha uma opção:  ");
                        opcaoCadastro = teclado.nextInt();

                        switch (opcaoCadastro) {
                            case 1:
                                // Chama a função de cadastro de participante

                                System.out.println(
                                        "================= Página de Cadastros de Participantes =================\n");
                                ParticipanteController.cadastrar();
                                break;
                            case 2:
                                // Chama a função de cadastro de palestrante

                                System.out.println(
                                        "================= Página de Cadastros de Palestrantes =================\n");
                                PalestranteController.cadastrar();
                                break;
                            case 3:
                                // Chama a função de cadastro de evento

                                System.out.println(
                                        "================= Página de Cadastros de Eventos =================\n");
                                EventoController.cadastrar();
                                break;
                            case 4:
                                // Chama a função de cadastrar participantes em um evento (precisa ter criado o
                                // evento ja)
                                System.out.println(
                                        "================= Página de Cadastros de Inscriçoes =================\n");
                                EventoController.cadastrarParticipanteEmEvento();
                                break;
                            case 5:
                                // volta para o menu principal
                                System.out.println("Voltando para o menu principal...");
                                break;
                            default:
                                // Volta para o submenu de cadastros
                                System.out.println("Opção inválida no submenu de cadastros.");
                                continue;
                        }
                        if (opcaoCadastro == 5) {
                            break; // Sai do loop de cadastros quando "Voltar" é escolhido
                        }
                    }
                    break;

                case 2:
                    System.out.println("================= Página de Agenda de Eventos =================\n");
                    // Chama o metodo de exibir eventos
                    EventoController.exibirEventosComParticipantes();
                    break;

                case 3:
                    // Chama o submenu de exibição

                    System.out.println("================= Página de Exibir Informações =================\n");

                    int opcaoExibir;
                    while (true) {
                        System.out.println("(1) - Exibir Participantes");
                        System.out.println("(2) - Exibir Palestrantes");
                        System.out.println("(3) - Exibir Eventos");
                        System.out.println("(4) - Buscar Eventos por Intervalo de Datas");
                        System.out.println("(5) - Buscar Participantes por Nome");
                        System.out.println("(6) - Voltar para o Menu Principal");
                        System.out.println("Escolha uma opção:  ");
                        opcaoExibir = teclado.nextInt();

                        switch (opcaoExibir) {
                            case 1:
                                // Chamar a função de exibição de participantes

                                ParticipanteController.exibir();

                                break;
                            case 2:
                                // Chamar a função de exibição de palestrantes

                                PalestranteController.exibir();

                                break;
                            case 3:
                                // Chama a função de exibição de eventos
                                EventoController.exibir();

                                break;

                            case 4:

                                System.out.println(
                                        "================= Buscar Eventos por Intervalo de Datas =================\n");
                                // Chama o metodo de de buscar eventos por intervalo de datas
                                EventoController.buscarEventosPorIntervaloDeDatas();
                                break;

                            case 5:
                                // Chama o metodo de buscar participantes por nome (usando select com like)
                                System.out
                                        .println("================= Buscar Participantes por Nome =================\n");
                                ParticipanteController.buscarParticipantesPorNome();
                                break;

                            case 6:
                                System.out.println("Voltando para o menu principal...");
                                break;

                            default:
                                System.out.println("Opção inválida no submenu de exibição.");
                                continue; // Volta para o submenu de exibição
                        }
                        break; // Sai do loop de exibição quando uma opção válida é escolhida
                    }
                    break;

                case 4:
                    System.out
                            .println("================= Página de Remoção de Cadastros e Eventos =================\n");
                    int opcaoRemover;
                    System.out.println("(1) - Remover Participante");
                    System.out.println("(2) - Remover Palestrante");
                    System.out.println("(3) - Remover Evento");
                    System.out.println("(4) - Voltar");
                    System.out.print("Escolha uma opção: ");
                    opcaoRemover = teclado.nextInt();

                    switch (opcaoRemover) {
                        case 1:
                            // chama o metodo de remover participantes
                            ParticipanteController.remover();
                            break;
                        case 2:
                            // chama o metodo de remover palestrante
                            PalestranteController.remover();
                            break;
                        case 3:
                            // chama o metodo de remover evento
                            EventoController.remover();
                            break;
                        case 4:
                            System.out.println("Voltando para o menu principal...");
                            break;
                        default:
                            System.out.println("Opção inválida no submenu de remoção.");
                            break;
                    }
                    break;

                case 5:
                    // Menu de estatísticas
                    System.out.println("================= Página de Estatísticas =================\n");
                    int opcaoEstatistica;
                    while (true) {
                        System.out.println("(1) - Total de Participantes (COUNT)");
                        System.out.println("(2) - Total de Palestrantes (COUNT)");
                        System.out.println("(3) - Total de Eventos (COUNT)");
                        System.out.println("(4) - Média de Participantes por Evento (AVG)");
                        System.out.println("(5) - Evento com Mais Participantes (MAX)");
                        System.out.println("(6) - Voltar");
                        System.out.println("Escolha uma opção: ");
                        opcaoEstatistica = teclado.nextInt();

                        switch (opcaoEstatistica) {
                            case 1:
                                ParticipanteController.contarParticipantes();
                                break;
                            case 2:
                                PalestranteController.contarPalestrantes();
                                break;
                            case 3:
                                EventoController.contarEventos();
                                break;
                            case 4:
                                EventoController.calcularMediaParticipantesPorEvento();
                                break;
                            case 5:
                                EventoController.eventoComMaisParticipantes();
                                break;
                            case 6:
                                System.out.println("Voltando para o menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida no submenu de estatísticas.");
                                continue;
                        }
                        if (opcaoEstatistica == 6) {
                            break;
                        }
                    }
                    break;

                case 6:
                    System.out.println("Saindo do sistema...");
                    teclado.close();
                    return; // Finaliza o programa

                default:
                    System.out.println("Opção inválida no menu principal.");
                    continue; // Volta para o menu principal
            }
        }
    }
}
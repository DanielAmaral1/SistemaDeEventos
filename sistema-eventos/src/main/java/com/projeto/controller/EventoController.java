package com.projeto.controller;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.projeto.entities.EventoEntity;
import com.projeto.entities.EventoParticipantesEntity;
import com.projeto.entities.PalestranteEntity;
import com.projeto.entities.ParticipanteEntity;
import com.projeto.repository.CustomizerFactory;
import com.projeto.repository.EventoParticipantesRepository;
import com.projeto.service.EventoParticipantesService;
import com.projeto.service.EventoService;
import com.projeto.service.PalestranteService;
import com.projeto.service.ParticipanteService;

public class EventoController {
    static Scanner teclado = new Scanner(System.in);   //
    static EventoService eventoService = new EventoService();
    static PalestranteService palestranteService = new PalestranteService();
    
    private EventoService eventoServiceInstance;
    
    public EventoController() {
        this.eventoServiceInstance = new EventoService();
    }
    
    public List<EventoEntity> listarTodos() {
        return eventoServiceInstance.buscarTodos();
    }
    
    public void salvar(EventoEntity evento) {
        eventoServiceInstance.salvarEvento(evento);
    }
    
    public EventoEntity buscarPorId(Long id) {
        return eventoServiceInstance.buscarPorId(id);
    }
    
    public void atualizar(EventoEntity evento) {
        eventoServiceInstance.atualizarEvento(evento);
    }
    
    public boolean excluir(Long id) {
        return eventoServiceInstance.removerEvento(id);
    }

    //metodo para exibir os eventos
    public static void exibir() {
        System.out.println("===== Lista de Eventos =====");

        //chama o metodo de buscar todos na lista de Eventos
        List<EventoEntity> eventos = eventoService.buscarTodos();

        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento encontrado.");
        } else {

            //para cada evento mostrar essas informacoes
            for (EventoEntity e : eventos) {
                System.out.println("ID: " + e.getId_evento());
                System.out.println("Nome do Evento: " + e.getNome_evento());
                System.out.println("Duração: " + e.getDuracao_evento());
                System.out.println("Data: " + e.getData_evento());

                // Verifica se tem um palestrante associado
                if (e.getPalestrante() != null) {
                    System.out.println("Palestrante: " + e.getPalestrante().getNome()); // Usa o método herdado de Pessoa
                } else {
                    System.out.println("Palestrante: Não definido");
                }

                System.out.println("----------------------------------");
            }
        }
    }
    //metodo de cadastrar evento
    public static void cadastrar() {
        System.out.println("===== Cadastro de Evento =====");

        //solicita ao usuario o nome do evento e faz uma verificacao para que o evento tenha mais de 3 letras 
        String nome;
        do {
            System.out.print("Nome do Evento: ");
            nome = teclado.nextLine();
            if (nome.length() <= 3) {
                System.out.println("O nome do evento precisa ter mais de 3 letras.");
            }
        } while (nome.length() <= 3);

        // solicita a data do evento e faz um tratamento de erro (try-catch) 
        Date data = null;
        while (data == null) {
            try {
                System.out.print("Data do Evento (formato: yyyy-mm-dd): ");
                String dataStr = teclado.nextLine();
                data = Date.valueOf(dataStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Data inválida. Use o formato yyyy-mm-dd.");
            }
        }

        // solicita a duracao estimada do evento 
        int duracao;
        do {
            System.out.print("Duração Estimada do Evento (em minutos): ");
            while (!teclado.hasNextInt()) {
                System.out.print("Digite um número válido para a duração: ");
                teclado.next(); // limpa entrada inválida
            }
            duracao = teclado.nextInt();
            teclado.nextLine(); // quebra de linha
        } while (duracao <= 0);

        // chama o metodo de listar participantes de dentro do service para que mostre todos os palestrantes cadastrados para mostrar
        List<PalestranteEntity> palestrantes = palestranteService.listarPalestrantes();
        if (palestrantes == null || palestrantes.isEmpty()) {
            System.out.println("Nenhum palestrante cadastrado. Cadastre um antes de continuar.");
            return;
        }

        System.out.println("\nEscolha um Palestrante pelo ID:");
        for (PalestranteEntity p : palestrantes) {
            System.out.println("ID: " + p.getId_palestrante() + " | Nome: " + p.getNome()); // Usa o método herdado de Pessoa
        }

        PalestranteEntity palestranteSelecionado = null;
        while (palestranteSelecionado == null) {
            System.out.print("Digite o ID do palestrante: ");
            Long id = teclado.nextLong();
            teclado.nextLine(); // quebra de linha

            palestranteSelecionado = palestranteService.buscarPorId(id);
            if (palestranteSelecionado == null) {
                System.out.println("ID inválido. Tente novamente.");
            }
        }

        // Criar e salvar evento
        EventoEntity evento = new EventoEntity();
        evento.setNome_evento(nome);
        evento.setData_evento(data);
        evento.setDuracao_evento(duracao);
        evento.setPalestrante(palestranteSelecionado);

        eventoService.salvarEvento(evento);

        System.out.println("\nEvento '" + nome + "' cadastrado com sucesso!");
    }

    public static  boolean remover() {
        System.out.println("===== Remoção de Evento =====");
// chama o metodo de buscar todos de service para mostrar todos os eventos disponiveis 
        List<EventoEntity> eventos = eventoService.buscarTodos();

        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
        }
        // mostra todos os eventos cadastrados com Nome e ID
        for (EventoEntity p : eventos) {
            System.out.println("ID: " + p.getId_evento() + " - Nome: " + p.getNome_evento());
        }

        System.out.print("Digite o ID do evento que deseja remover: ");
        Long id = teclado.nextLong();
        teclado.nextLine(); // consome quebra de linha

        boolean removido = eventoService.removerEvento(id);

        if (removido) {
            System.out.println("Evento removido com sucesso!");
        } else {
            System.out.println("Evento com ID " + id + " não encontrado.");
        }
        return removido;
    }

    public static void cadastrarParticipanteEmEvento() {
    System.out.println("===== Cadastro de Participante em Evento =====");

    // Exibir eventos disponíveis
    List<EventoEntity> eventos = eventoService.buscarTodos();
    if (eventos.isEmpty()) {
        System.out.println("Nenhum evento disponível.");
        return;
    }
    //mostra os eventos disponiveis 
    System.out.println("Eventos disponíveis:");
    for (EventoEntity evento : eventos) {
        System.out.println("ID: " + evento.getId_evento() + " | Nome: " + evento.getNome_evento());
    }

    System.out.print("Digite o ID do evento: ");
    Long eventoId = teclado.nextLong();
    teclado.nextLine(); // Consome a quebra de linha

    EventoEntity eventoSelecionado = eventoService.buscarPorId(eventoId);
    if (eventoSelecionado == null) {
        System.out.println("Evento não encontrado.");
        return;
    }

    // Exibir participantes disponíveis
    List<ParticipanteEntity> participantes = new ParticipanteService().buscarTodos();
    if (participantes.isEmpty()) {
        System.out.println("Nenhum participante disponível.");
        return;
    }

    System.out.println("Participantes disponíveis:");
    for (ParticipanteEntity participante : participantes) {
        System.out.println("ID: " + participante.getId_participante() + " | Nome: " + participante.getNome());
    }

    System.out.print("Digite o ID do participante: ");
    Long participanteId = teclado.nextLong();
    teclado.nextLine(); // Consome a quebra de linha

    ParticipanteEntity participanteSelecionado = new ParticipanteService().buscarPorId(participanteId);
    if (participanteSelecionado == null) {
        System.out.println("Participante não encontrado.");
        return;
    }

    // Salvar a relação no banco de dados
    EventoParticipantesEntity eventoParticipante = new EventoParticipantesEntity(eventoSelecionado, participanteSelecionado);
    new EventoParticipantesService().salvarParticipacao(eventoParticipante);

    System.out.println("Participante '" + participanteSelecionado.getNome() + "' cadastrado no evento '" + eventoSelecionado.getNome_evento() + "' com sucesso!");
    }


    //chama a funcao de exibir todos os eventos com quem vai participar do evento
    public static void exibirEventosComParticipantes() {
    System.out.println("===== Lista de Eventos com Participantes =====");

    EventoParticipantesRepository repository = new EventoParticipantesRepository(CustomizerFactory.getEntityManager());
    List<Object[]> resultados = repository.buscarEventosComParticipantes();

    if (resultados.isEmpty()) {
        System.out.println("Nenhum evento com participantes encontrado.");
    } else {
        for (Object[] resultado : resultados) {
            System.out.println("Evento: " + resultado[0] + " | Participante: " + resultado[1]);
        }
    }
}
//metodo de buscar eventos por data
public static void buscarEventosPorIntervaloDeDatas() {
    System.out.println("===== Buscar Eventos por Intervalo de Datas =====");

    // Solicitar a data inicial
    Date dataInicio = null;
    while (dataInicio == null) {
        try {
            System.out.print("Digite a data inicial (formato: yyyy-mm-dd): ");
            String dataInicioStr = teclado.nextLine();
            dataInicio = Date.valueOf(dataInicioStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Data inválida. Use o formato yyyy-mm-dd.");
        }
    }

    // Solicitar a data final
    Date dataFim = null;
    while (dataFim == null) {
        try {
            System.out.print("Digite a data final (formato: yyyy-mm-dd): ");
            String dataFimStr = teclado.nextLine();
            dataFim = Date.valueOf(dataFimStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Data inválida. Use o formato yyyy-mm-dd.");
        }
    }

    // Buscar eventos no intervalo
    List<EventoEntity> eventos = eventoService.buscarPorIntervaloDeDatas(dataInicio, dataFim);

    // Exibir resultados
    if (eventos.isEmpty()) {
        System.out.println("Nenhum evento encontrado no intervalo especificado.");
    } else {
        System.out.println("Eventos encontrados:");
        for (EventoEntity evento : eventos) {
            System.out.println("ID: " + evento.getId_evento() + " | Nome: " + evento.getNome_evento() + " | Data: " + evento.getData_evento());
        }
    }
    
    // Método para contar total de eventos (COUNT)
    public static void contarEventos() {
        System.out.println("===== Total de Eventos =====");
        List<EventoEntity> eventos = eventoService.buscarTodos();
        int total = eventos.size();
        System.out.println("Total de eventos cadastrados: " + total);
        System.out.println("----------------------------------");
    }
    
    // Método para calcular média de participantes por evento (AVG)
    public static void calcularMediaParticipantesPorEvento() {
        System.out.println("===== Média de Participantes por Evento =====");
        
        EventoParticipantesRepository repository = new EventoParticipantesRepository(CustomizerFactory.getEntityManager());
        List<Object[]> resultados = repository.buscarEventosComParticipantes();
        
        if (resultados.isEmpty()) {
            System.out.println("Nenhum evento com participantes encontrado.");
            return;
        }
        
        // Contar participantes por evento
        java.util.Map<String, Integer> eventoParticipantes = new java.util.HashMap<>();
        for (Object[] resultado : resultados) {
            String nomeEvento = (String) resultado[0];
            eventoParticipantes.put(nomeEvento, eventoParticipantes.getOrDefault(nomeEvento, 0) + 1);
        }
        
        // Calcular média
        int totalParticipantes = eventoParticipantes.values().stream().mapToInt(Integer::intValue).sum();
        double media = (double) totalParticipantes / eventoParticipantes.size();
        
        System.out.println("Média de participantes por evento: " + String.format("%.2f", media));
        System.out.println("----------------------------------");
    }
    
    // Método para encontrar evento com mais participantes (MAX)
    public static void eventoComMaisParticipantes() {
        System.out.println("===== Evento com Mais Participantes =====");
        
        EventoParticipantesRepository repository = new EventoParticipantesRepository(CustomizerFactory.getEntityManager());
        List<Object[]> resultados = repository.buscarEventosComParticipantes();
        
        if (resultados.isEmpty()) {
            System.out.println("Nenhum evento com participantes encontrado.");
            return;
        }
        
        // Contar participantes por evento
        java.util.Map<String, Integer> eventoParticipantes = new java.util.HashMap<>();
        for (Object[] resultado : resultados) {
            String nomeEvento = (String) resultado[0];
            eventoParticipantes.put(nomeEvento, eventoParticipantes.getOrDefault(nomeEvento, 0) + 1);
        }
        
        // Encontrar evento com mais participantes
        String eventoComMais = "";
        int maxParticipantes = 0;
        
        for (java.util.Map.Entry<String, Integer> entry : eventoParticipantes.entrySet()) {
            if (entry.getValue() > maxParticipantes) {
                maxParticipantes = entry.getValue();
                eventoComMais = entry.getKey();
            }
        }
        
        System.out.println("Evento com mais participantes: " + eventoComMais);
        System.out.println("Número de participantes: " + maxParticipantes);
        System.out.println("----------------------------------");
    }
}
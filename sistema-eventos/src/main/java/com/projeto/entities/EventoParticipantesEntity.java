package com.projeto.entities;

import javax.persistence.*;

@Entity
@Table(name = "evento_participantes") //nome da tabela
public class EventoParticipantesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    //relacao de muitos para um (um evento pode ter varios participantes)
    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private EventoEntity evento;
    
    //relacao de muitos para um (um participante pode participar de varios eventos)
    @ManyToOne
    @JoinColumn(name = "participante_id", nullable = false)
    private ParticipanteEntity participante;

    //construtor vazio : hibernate usa para instanciar os objetos, ele nao consegue fazer isso caso tenha argumentos, como o de baixo.
    public EventoParticipantesEntity() {}

    public EventoParticipantesEntity(EventoEntity evento, ParticipanteEntity participante) {
        this.evento = evento;
        this.participante = participante;
    }

    
    //getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventoEntity getEvento() {
        return evento;
    }

    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }

    public ParticipanteEntity getParticipante() {
        return participante;
    }

    public void setParticipante(ParticipanteEntity participante) {
        this.participante = participante;
    }
}
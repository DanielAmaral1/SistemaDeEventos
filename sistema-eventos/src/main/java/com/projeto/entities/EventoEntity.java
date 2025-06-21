package com.projeto.entities;

import java.sql.Date;


import javax.persistence.*;


@Entity
@Table(name = "evento")  //nome da tabela no banco de dados
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private Long id_evento;

    @Column(name = "nome_evento")
    private String nome_evento;

    @Column(name = "duracao_evento")
    private int duracao_evento;

    @Column(name = "data_evento")
    private Date data_evento;

    // relacao de muitos para um (um evento pode ter apenas um palestrante; -- um palestrante pode participar de varios eventos)
    @ManyToOne
    @JoinColumn(name = "palestrante_id")
    private PalestranteEntity palestrante;


    //construtor vazio : hibernate usa para instanciar os objetos, ele nao consegue fazer isso caso tenha argumentos, como o de baixo.
    public EventoEntity() {}

    public EventoEntity(Long id_evento, String nome_evento, int duracao_evento, Date data_evento, PalestranteEntity palestrante) {
        this.id_evento = id_evento;
        this.nome_evento = nome_evento;
        this.duracao_evento = duracao_evento;
        this.data_evento = data_evento;
        this.palestrante = palestrante;
    }

    //getters e setters

    public Long getId_evento() {
        return id_evento;
    }

    public void setId_evento(Long id_evento) {
        this.id_evento = id_evento;
    }

    public String getNome_evento() {
        return nome_evento;
    }

    public void setNome_evento(String nome_evento) {
        this.nome_evento = nome_evento;
    }

    public int getDuracao_evento() {
        return duracao_evento;
    }

    public void setDuracao_evento(int duracao_evento) {
        this.duracao_evento = duracao_evento;
    }

    public Date getData_evento() {
        return data_evento;
    }

    public void setData_evento(Date data_evento) {
        this.data_evento = data_evento;
    }

    public PalestranteEntity getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(PalestranteEntity palestrante) {
        this.palestrante = palestrante;
    }
}

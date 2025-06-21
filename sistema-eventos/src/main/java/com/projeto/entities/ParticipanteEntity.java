package com.projeto.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "participante") //nome da tabela no banco de dados

// classe filha da classe pai (pessoa)
public class ParticipanteEntity extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_participante;

    @Column(name = "idade_participante")
    private int idade_participante;

    @Column(name = "numero_participante")
    private String numero_participante;

    public ParticipanteEntity() {
    }

    public ParticipanteEntity(Long id_participante, String nome, String email, int idade_participante, String numero_participante) {
        super(nome, email); // Chama o construtor da classe base
        this.id_participante = id_participante;
        this.idade_participante = idade_participante;
        this.numero_participante = numero_participante;
    }


    //getters e setters
    public Long getId_participante() {
        return id_participante;
    }

    public void setId_participante(Long id_participante) {
        this.id_participante = id_participante;
    }

    public int getIdade_participante() {
        return idade_participante;
    }

    public void setIdade_participante(int idade_participante) {
        this.idade_participante = idade_participante;
    }

    public String getNumero_participante() {
        return numero_participante;
    }

    public void setNumero_participante(String numero_participante) {
        this.numero_participante = numero_participante;
    }
}
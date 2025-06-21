package com.projeto.entities;

import javax.persistence.*;

@Entity
@Table(name = "palestrante")  //nome da tabela no banco de dados

//classe filha da classe pai(pessoa)
public class PalestranteEntity extends Pessoa {


        //os nomes das colunas no banco de dados e como elas sao chamadas como variaveis
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_palestrante;

    @Column(name = "idade_palestrante")
    private int idade_palestrante;

    @Column(name = "especialidade_palestrante")
    private String especialidade_palestrante;

    //    //construtor vazio : hibernate usa para instanciar os objetos, ele nao consegue fazer isso caso tenha argumentos, como o de baixo.

    public PalestranteEntity() {
    }

        //construtor 
    public PalestranteEntity(Long id_palestrante, String nome, String email, int idade_palestrante, String especialidade_palestrante) {
        super(nome, email);
        this.id_palestrante = id_palestrante;
        this.idade_palestrante = idade_palestrante;
        this.especialidade_palestrante = especialidade_palestrante;
    }

    public Long getId_palestrante() {
        return id_palestrante;
    }

    public void setId_palestrante(Long id_palestrante) {
        this.id_palestrante = id_palestrante;
    }

    public int getIdade_palestrante() {
        return idade_palestrante;
    }

    public void setIdade_palestrante(int idade_palestrante) {
        this.idade_palestrante = idade_palestrante;
    }

    public String getEspecialidade_palestrante() {
        return especialidade_palestrante;
    }

    public void setEspecialidade_palestrante(String especialidade_palestrante) {
        this.especialidade_palestrante = especialidade_palestrante;
    }
}

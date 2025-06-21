
package com.projeto.entities;

import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass // Indica que esta classe será usada como base para entidades JPA

//classe PAI
public abstract class Pessoa {
    private String nome;
    private String email;

    //    //construtor vazio : hibernate usa para instanciar os objetos, ele nao consegue fazer isso caso tenha argumentos, como o de baixo.

    public Pessoa() {
    }
    //construtor
    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }


    //getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(nome, pessoa.nome) && Objects.equals(email, pessoa.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

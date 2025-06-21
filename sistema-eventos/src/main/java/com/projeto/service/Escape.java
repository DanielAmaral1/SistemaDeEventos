package com.projeto.service;

import java.util.List;

import com.projeto.entities.Pessoa;

public interface Escape {

    void salvarPessoa(Pessoa pessoa);

    List<Pessoa> listarTodas();

}
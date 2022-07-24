package dev.andremr.bancodigital.models;

public class Cliente {
    final String cpf;
    final String nome;

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }
}

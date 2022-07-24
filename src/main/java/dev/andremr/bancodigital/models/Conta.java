package dev.andremr.bancodigital.models;

public abstract class Conta {
    Agencia agencia;
    Cliente cliente;
    int numero;
    String tipo;

    public String getTipo() {
        return tipo;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getNumero() {
        return numero;
    }

    public abstract double consultarSaldo();

    public abstract double sacar(double valor);

    public abstract boolean depositar(double valor);

    public abstract boolean transferir(Conta contaDestino, double valor);
}

package dev.andremr.bancodigital.models;

public class ContaCorrente extends Conta {
    double saldo;

    public double getSaldo() {
        return saldo;
    }

    public ContaCorrente(Agencia agencia, Cliente cliente, int numero) {
        this.agencia = agencia;
        this.cliente = cliente;
        this.numero = numero;
        this.saldo = 0;
        this.tipo = "Corrente";
    }

    @Override
    public double consultarSaldo() {
        return getSaldo();
    }

    @Override
    public double sacar(double valor) {
        if (valor > saldo) {
            return 0;
        }
        this.saldo -= valor;
        return valor;
    }

    @Override
    public boolean depositar(double valor) {
        this.saldo += valor;
        return true;
    }

    @Override
    public boolean transferir(Conta contaDestino, double valor) {
        return contaDestino.depositar(this.sacar(valor));
    }
}

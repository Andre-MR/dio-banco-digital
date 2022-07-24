package dev.andremr.bancodigital.models;

import java.util.Calendar;

public class ContaPoupanca extends Conta {
    private final double[] saldos;
    private int diaUltimaCorrecao;

    public ContaPoupanca(Agencia agencia, Cliente cliente, int numero) {
        this.saldos = new double[28];
        this.agencia = agencia;
        this.cliente = cliente;
        this.numero = numero;
        this.tipo = "Poupança";
    }

    @Override
    public double consultarSaldo() {
        double valor = 0;
        for (double saldo : saldos) {
            valor += saldo;
        }
        return valor;
    }

    public double sacar(double valor) {
        double saldo = consultarSaldo();
        if (valor > saldo) {
            return 0;
        }
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        if (dia > 28) {
            dia = 28;
        }
        double montante = 0;
        for (int i = dia - 1; i >= 0; i--) {
            double valorRestante = valor - montante;
            if (valorRestante < saldos[i]) {
                montante += valorRestante;
                saldos[i] -= valorRestante;
            } else {
                montante += saldos[i];
                saldos[i] = 0;
            }
            if (montante == valor) return montante;
        }
        return 0;
    }

    @Override
    public boolean depositar(double valor) {
        if (valor <= 0) {
            return false;
        }
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        if (dia > 28) {
            dia = 28;
        }

        saldos[dia - 1] += valor;
        return true;
    }

    @Override
    public boolean transferir(Conta contaDestino, double valor) {
        return contaDestino.depositar(this.sacar(valor));
    }

    public boolean aplicarRendimento(double indice) {
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        if (dia > 28) {
            dia = 28;
        }
        if (diaUltimaCorrecao != dia) {
            saldos[dia - 1] *= indice;
            diaUltimaCorrecao = dia;
            return true;
        }
        return false;
    }
}

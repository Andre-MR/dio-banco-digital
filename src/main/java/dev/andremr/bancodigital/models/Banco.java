package dev.andremr.bancodigital.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Banco {
    final List<Agencia> agencias;
    final List<Conta> contas;
    final List<Cliente> clientes;

    public Banco() {
        this.agencias = new ArrayList<>();
        this.contas = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public List<Agencia> getAgencias() {
        return agencias;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Conta localizarConta(int numeroAgencia, int numeroConta) {
        for (Conta conta : contas) {
            if (conta.agencia.numero == numeroAgencia && conta.numero == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    public boolean cadastrarCliente(String cpf, String nome) {
        for (Cliente cliente : clientes) {
            if (Objects.equals(cliente.cpf, cpf)) {
                return false;
            }
        }
        return clientes.add(new Cliente(cpf, nome));
    }

    public int cadastrarAgencia() {
        int ultimoNumero = 0;
        if (agencias.size() > 0) {
            ultimoNumero = agencias.get(agencias.size() - 1).numero;
        }
        agencias.add(new Agencia(ultimoNumero + 1));
        return ultimoNumero + 1;
    }

    public int cadastrarConta(int numeroAgencia, String cpf, int tipo) {
        for (Agencia agencia : agencias) {
            if (agencia.numero == numeroAgencia) {
                for (Cliente cliente : clientes) {
                    if (Objects.equals(cliente.cpf, cpf)) {
                        int ultimoNumero = 0;
                        for (Conta conta : contas) {
                            if (conta.agencia.numero == agencia.numero && conta.numero > ultimoNumero) {
                                ultimoNumero = conta.numero;
                            }
                        }
                        Conta novaConta = switch (tipo) {
                            case 1 -> new ContaCorrente(agencia, cliente, ultimoNumero + 1);
                            case 2 -> new ContaPoupanca(agencia, cliente, ultimoNumero + 1);
                            default -> null;
                        };
                        if (novaConta != null) {
                            contas.add(novaConta);
                            return novaConta.numero;
                        }
                    }
                }
            }
        }
        return 0;
    }
}

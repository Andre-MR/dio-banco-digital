package dev.andremr.bancodigital;

import dev.andremr.bancodigital.models.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BancoDigitalApplication {

    public final Banco banco = new Banco();

    public static void main(String[] args) {
        SpringApplication.run(BancoDigitalApplication.class, args);
    }

    @GetMapping("*")
    public MensagemJSON helloError() {
        return new MensagemJSON("Erro! Rota não encontrada.");
    }

    @GetMapping("/")
    public MensagemJSON hello() {
        return new MensagemJSON("Olá! Efetue as solicitações por url.");
    }

    @GetMapping("/banco")
    public MensagemJSON getBanco() {
        return new MensagemJSON(banco);
    }

    @GetMapping("/agencias")
    public MensagemJSON getAgencias() {
        return new MensagemJSON(banco.getAgencias());
    }

    @GetMapping("/contas")
    public MensagemJSON getContas() {
        return new MensagemJSON(banco.getContas());
    }

    @GetMapping("/clientes")
    public MensagemJSON getClientes() {
        return new MensagemJSON(banco.getClientes());
    }


    @GetMapping("/cadastro/cliente")
    public MensagemJSON cadastrarCliente(
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "nome", required = false) String nome) {
        if (cpf == null || nome == null) {
            return new MensagemJSON("Parâmetros inválidos...");
        }
        return banco.cadastrarCliente(cpf, nome)
                ? new MensagemJSON("Cliente cadastrado!")
                : new MensagemJSON("Falha no cadastramento!");
    }

    @GetMapping("/cadastro/agencia")
    public MensagemJSON cadastrarAgencia() {
        int resultado = banco.cadastrarAgencia();
        return resultado > 0
                ? new MensagemJSON(resultado)
                : new MensagemJSON("Erro!");
    }

    @GetMapping("/cadastro/conta")
    public MensagemJSON cadastrarConta(
            @RequestParam(value = "agencia", required = false) String agencia,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "tipo", required = false) String tipo) {
        if (agencia == null || cpf == null || tipo == null) {
            return new MensagemJSON("Parâmetros inválidos...");
        }
        int resultado = banco.cadastrarConta(Integer.parseInt(agencia), cpf, Integer.parseInt(tipo));
        return resultado > 0
                ? new MensagemJSON(resultado)
                : new MensagemJSON("Erro!");
    }

    @GetMapping("/saldo")
    public MensagemJSON saldo(
            @RequestParam(value = "agencia", required = false) String numeroAgencia,
            @RequestParam(value = "conta", required = false) String numeroConta) {
        if (numeroAgencia == null || numeroConta == null) {
            return new MensagemJSON("Parâmetros inválidos...");
        }
        Conta conta = banco.localizarConta(Integer.parseInt(numeroAgencia), Integer.parseInt(numeroConta));
        if (conta != null) {
            return new MensagemJSON(conta.consultarSaldo());
        }
        return new MensagemJSON("Erro na solicitação!");
    }

    @GetMapping("/saque")
    public MensagemJSON sacar(
            @RequestParam(value = "agencia", required = false) String numeroAgencia,
            @RequestParam(value = "conta", required = false) String numeroConta,
            @RequestParam(value = "valor", required = false) String valor) {
        if (numeroAgencia == null || numeroConta == null || valor == null) {
            return new MensagemJSON("Parâmetros inválidos...");
        }
        Conta conta = banco.localizarConta(Integer.parseInt(numeroAgencia), Integer.parseInt(numeroConta));
        if (conta != null) {
            return (conta.sacar(Double.parseDouble(valor)) > 0)
                    ? new MensagemJSON("Saque realizado!")
                    : new MensagemJSON("Saldo insuficiente!");
        }
        return new MensagemJSON("Parâmetros inválidos.");
    }

    @GetMapping("/deposito")
    public MensagemJSON depositar(
            @RequestParam(value = "agencia", required = false) String numeroAgencia,
            @RequestParam(value = "conta", required = false) String numeroConta,
            @RequestParam(value = "valor", required = false) String valor) {
        if (numeroAgencia == null || numeroConta == null || valor == null) {
            return new MensagemJSON("Parâmetros inválidos...");
        }
        Conta conta = banco.localizarConta(Integer.parseInt(numeroAgencia), Integer.parseInt(numeroConta));
        if (conta != null) {
            return conta.depositar(Double.parseDouble(valor))
                    ? new MensagemJSON("Deposito realizado!")
                    : new MensagemJSON("Ocorreu um erro!");
        }
        return new MensagemJSON("Parâmetros inválidos.");
    }

    @GetMapping("/transferencia")
    public MensagemJSON transferir(
            @RequestParam(value = "agenciaOrigem", required = false) String numeroAgenciaOrigem,
            @RequestParam(value = "contaOrigem", required = false) String numeroContaOrigem,
            @RequestParam(value = "agenciaDestino", required = false) String numeroAgenciaDestino,
            @RequestParam(value = "contaDestino", required = false) String numeroContaDestino,
            @RequestParam(value = "valor", required = false) String valor) {
        if (numeroAgenciaOrigem == null
                || numeroContaOrigem == null
                || numeroAgenciaDestino == null
                || numeroContaDestino == null
                || valor == null) {
            return new MensagemJSON("Parâmetros inválidos...");
        }
        Conta contaOrigem = banco.localizarConta(Integer.parseInt(numeroAgenciaOrigem), Integer.parseInt(numeroContaOrigem));
        Conta contaDestino = banco.localizarConta(Integer.parseInt(numeroAgenciaDestino), Integer.parseInt(numeroContaDestino));
        if (contaOrigem != null && contaDestino != null) {
            return contaOrigem.transferir(contaDestino, Double.parseDouble(valor))
                    ? new MensagemJSON("Transferência realizada.")
                    : new MensagemJSON("Falha na transferência.");
        }
        return new MensagemJSON("Erro!");
    }
}
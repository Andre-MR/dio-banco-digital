# 💻 DIO - Desafio Banco Digital

Desafio de projeto de bootcamp da DIO. Criação de um banco digital simples em Java.

## Observações

* Feito em modelo REST API, utilizando Spring Boot.  
Para fins de estudos, todas as requisições são feitas utilizando GetMapping para simplificar o projeto e permitir acesso pela barra de endereços do browser.
* Exemplos de requisições:
  * /banco
  * /agencias
  * /contas
  * /clientes
  * /cadastro/agencia
  * /cadastro/cliente?cpf=12345678900&nome=Fulano
  * /cadastro/conta?agencia=1&cpf=12345678900&tipo=1
  * /saldo?agencia=1&conta=1
  * /deposito?agencia=1&conta=1&valor=100
  * /saque?agencia=1&conta=1&valor=10
  * /transferencia?agenciaOrigem=1&contaOrigem=1&agenciaDestino=1&contaDestino=2&valor=10

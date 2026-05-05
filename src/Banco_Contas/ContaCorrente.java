package Banco_Contas;

import Excecoes.LimiteExcedidoException;
import Excecoes.SaldoInsuficienteException;

import java.util.Scanner;

public class ContaCorrente extends Contas {
    public ContaCorrente() {
        super();
    }

    public ContaCorrente(String titular, Integer numero, double balance) {
        super(titular, numero, balance);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Conta [Corrente] {");
        sb.append(" Nome: ").append(super.getTitular());
        sb.append(" Numero: ").append(super.getNumero());
        sb.append(" Saldo: ").append(balance).append("}");
        return sb.toString();
    }
}

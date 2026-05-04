package Banco_Contas;

import Excecoes.LimiteExcedidoException;
import Excecoes.SaldoInsuficienteException;

public class ContaCorrente extends Contas {
    public ContaCorrente() {
        super();
    }

    public ContaCorrente(String titular, Integer numero, double balance) {
        super(titular, numero, balance);
    }

    @Override
    public void Saque(double valor) {
        if (valor + 3.00 <= balance) {
            double taxa = valor + 3.00;
            balance -= taxa;
        } else {
            throw new SaldoInsuficienteException();
        }
    }

    @Override
    public void Depositar(double valor) {
        if (valor <= 10000) {
            balance += valor;
        } else {
            throw new LimiteExcedidoException();
        }
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

package Entities;

import ENUM.TipoOperacao;
import Excecoes.LimiteExcedidoException;
import Excecoes.SaldoInsuficienteException;
import Services.Tax;

public class ContaCorrente extends Contas implements Tax {
    public ContaCorrente(String titular, double balance) {
        super(titular, balance);
    }

    public ContaCorrente(String titular,Long id, double balance){super(id,titular,balance);}
    @Override
    public void sacar(double valor, long id){
        double taxaCorrente = 25.00;
        if (balance < valor + taxaCorrente) {
            throw new SaldoInsuficienteException();
        }
        if (valor >= 20000){
            throw new LimiteExcedidoException();
        }
        balance -= valor + taxaCorrente;
        addTransacao(new Transacao(TipoOperacao.OPERACAO_SAQUE, valor, balance,id));
    }
    @Override
    public void deposito(double valor, long id){
        if (tax(valor)+valor > 25000) {
            throw new LimiteExcedidoException();
        }
        balance += valor - tax(valor);
        addTransacao(new Transacao(TipoOperacao.OPERACAO_DEPOSITO, valor, balance ,id));
    }
    @Override
    public double tax(double valor) {
        return valor * 0.02;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nConta [Corrente]\n");
        sb.append(super.toString());
        return sb.toString();
    }
}

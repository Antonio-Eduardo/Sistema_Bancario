package Banco_Contas;

import ENUM.TipoOperacao;
import Excecoes.LimiteExcedidoException;
import Excecoes.SaldoInsuficienteException;

import java.util.UUID;

public final class ContaPoupanca extends Contas {
    private static final double TAXA_JUROS = 1.1;


    public ContaPoupanca(String titular, String idConta, double balance) {
        super(titular, idConta, balance);

    }

    @Override
    public void sacar(double valor, String id){
        if (balance < valor) {
            throw new SaldoInsuficienteException();
        }
        balance -= valor;
        addTransacao(new Transacao(TipoOperacao.OPERACAO_SAQUE, valor, balance,id));
    }
    @Override
    public void deposito(double valor, String id){
        if (valor > 10000) {
            throw new LimiteExcedidoException();
        }
        balance += valor;
        addTransacao(new Transacao(TipoOperacao.OPERACAO_DEPOSITO, valor, balance,id));
    }

    public double rendimentoTotal() {
       return balance *= TAXA_JUROS;
    }

    public double attSaldo(){
        return balance *= TAXA_JUROS;
    }

    public double getJuros() {
        return TAXA_JUROS;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nContas [Poupanca]\n");
        sb.append(super.toString());
        sb.append("\nrendimento previsto= ").append(balance * TAXA_JUROS);

        return sb.toString();
    }
}

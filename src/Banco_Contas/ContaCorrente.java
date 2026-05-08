package Banco_Contas;

import ENUM.TipoOperacao;
import Excecoes.LimiteExcedidoException;
import Excecoes.SaldoInsuficienteException;

import java.util.Scanner;
import java.util.UUID;

public class ContaCorrente extends Contas {


    public ContaCorrente(String titular, String idConta, double balance) {
        super(titular, idConta, balance);
    }

    @Override
    public void sacar(double valor, String id){
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
    public void deposito(double valor, String id){
        double taxaCorrente = 25.00;
        if (taxaCorrente+ valor > 25000) {
            throw new LimiteExcedidoException();
        }
        balance += valor - taxaCorrente;
        addTransacao(new Transacao(TipoOperacao.OPERACAO_DEPOSITO, valor, balance ,id));
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Conta [Corrente]\n");
        sb.append(super.toString());
        return sb.toString();
    }
}

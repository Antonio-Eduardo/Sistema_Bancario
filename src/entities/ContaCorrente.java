package entities;

import enums.TipoOperacao;
import exceptions.LimiteExcedidoException;
import exceptions.SaldoInsuficienteException;
import service.Tax;

public class ContaCorrente extends Conta implements Tax {
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
    public void transferencia( Double valorTx, Conta contaY) {
        if (balance >= valorTx + tax(valorTx)) {
            balance -= valorTx + tax(valorTx);
            contaY.creditar(valorTx);

            addTransacao(new Transacao(TipoOperacao.OPERACAO_TRANSFERENCIA, valorTx, balance, getIdConta()));
            contaY.addTransacao(new Transacao(TipoOperacao.OPERACAO_TRANSFERENCIA, valorTx, contaY.getBalance(), contaY.getIdConta()));
        } else {
            throw new SaldoInsuficienteException();
        }
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

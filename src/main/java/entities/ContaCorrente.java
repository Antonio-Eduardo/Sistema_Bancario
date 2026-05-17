package entities;

import enums.TipoOperacao;
import exceptions.LimiteExcedidoException;
import exceptions.SaldoInsuficienteException;
import jakarta.persistence.Entity;
import service.Tax;

@Entity
public class ContaCorrente extends Conta implements Tax {

    public ContaCorrente(){}
    public ContaCorrente(String titular, double balance) {
        super(titular, balance);
    }
    public ContaCorrente(String titular,Long id, double balance){super(id,titular,balance);}

    @Override
    public void sacar(double valor){
        double taxaCorrente = 25.00;
        if (balance < valor + taxaCorrente) {
            throw new SaldoInsuficienteException();
        }
        if (valor >= 20000){
            throw new LimiteExcedidoException();
        }
        balance -= valor + taxaCorrente;
        addTransacao(new Transacao(TipoOperacao.OPERACAO_SAQUE, valor, balance));
    }
    @Override
    public void deposito(double valor){
        if (tax(valor)+valor > 25000) {
            throw new LimiteExcedidoException();
        }
        balance += valor - tax(valor);
        addTransacao(new Transacao(TipoOperacao.OPERACAO_DEPOSITO, valor, balance));
    }
    @Override
    public void transferencia( Double valor, Conta contaDestino) {
        if (balance >= valor + tax(valor)) {
            balance -= valor + tax(valor);
            contaDestino.creditar(valor);

            addTransacao(new Transacao(TipoOperacao.OPERACAO_TRANSFERENCIA, valor, balance));
            contaDestino.addTransacao(new Transacao(TipoOperacao.OPERACAO_TRANSFERENCIA, valor, contaDestino.getBalance()));
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

package entities;

import enums.TipoOperacao;
import exceptions.LimiteExcedidoException;
import exceptions.SaldoInsuficienteException;
import jakarta.persistence.Entity;
import service.Tax;

@Entity
public final class ContaPoupanca extends Conta implements Tax {
    private static final double JUROS_RENDIMENTO = 0.008;

    public ContaPoupanca(){}
    public ContaPoupanca(String titular, Long idConta, double balance) {
        super(idConta,titular, balance);
    }

    public ContaPoupanca(String titular, double balance) {
        super(titular, balance);
    }

    @Override
    public void sacar(double valor){
        if (balance < valor + tax(valor)) {
            throw new SaldoInsuficienteException();
        }
        balance -= valor + tax(valor);
        addTransacao(new Transacao(TipoOperacao.OPERACAO_SAQUE, valor, balance));
    }
    @Override
    public void deposito(double valor){
        if (tax(valor) + valor > 10000) {
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
    public double getRendimento(){
        return balance * JUROS_RENDIMENTO;
    }
    @Override
    public double tax(double valor) {
        return valor * 0.005;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nContas [Poupanca]\n");
        sb.append(super.toString());
        sb.append("\nRendimento previsto= ").append(balance * JUROS_RENDIMENTO);

        return sb.toString();
    }
}

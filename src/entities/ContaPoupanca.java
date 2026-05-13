package entities;

import enums.TipoOperacao;
import exceptions.LimiteExcedidoException;
import exceptions.SaldoInsuficienteException;
import service.Tax;

public final class ContaPoupanca extends Conta implements Tax {
    private static final double JUROS_RENDIMENTO = 0.008;
    public ContaPoupanca(String titular, Long idConta, double balance) {
        super(idConta,titular, balance);
    }

    public ContaPoupanca(String titular, double balance) {
        super(titular, balance);
    }

    @Override
    public void sacar(double valor, long id){
        if (balance < valor + tax(valor)) {
            throw new SaldoInsuficienteException();
        }
        balance -= valor + tax(valor);
        addTransacao(new Transacao(TipoOperacao.OPERACAO_SAQUE, valor, balance,id));
    }
    @Override
    public void deposito(double valor, long id){
        if (tax(valor) + valor > 10000) {
            throw new LimiteExcedidoException();
        }
        balance += valor - tax(valor);
        addTransacao(new Transacao(TipoOperacao.OPERACAO_DEPOSITO, valor, balance,id));
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

package entities;

import enums.TipoOperacao;
import exceptions.LimiteExcedidoException;
import exceptions.SaldoInsuficienteException;
import service.Tax;

public final class  ContaEmpresarial extends Conta implements Tax {
    private double emprestimo;

    public ContaEmpresarial(String titular, long idConta, double balance, double emprestimo) {
        super(idConta, titular,balance);
        this.emprestimo = emprestimo;
    }

    public ContaEmpresarial(String titular, double balance, double emprestimo) {
        super(titular, balance);
        this.emprestimo = emprestimo;
    }

    @Override
    public void sacar(double valor, long id){
        double taxaEmpresa = 50.00;
        if (balance < valor + taxaEmpresa) {
            throw new SaldoInsuficienteException();
        }
        if (valor > 20000){
            throw new LimiteExcedidoException();
        }
        balance -= valor + taxaEmpresa;
        addTransacao(new Transacao(TipoOperacao.OPERACAO_SAQUE, valor, balance,id));
    }
    @Override
    public void deposito(double valor, long id){

        if (tax(valor) + valor > 35000) {
            throw new LimiteExcedidoException();
        }
        balance += valor - tax(valor);
        addTransacao(new Transacao(TipoOperacao.OPERACAO_DEPOSITO, valor, balance,id));;
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
        return valor * 0.07;
    }
    public void addEmprestimo(double valor){
        emprestimo += valor;
    }
    public double getEmprestimo() {
        return emprestimo;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nConta [Empresa]\n");
        sb.append(super.toString());
        sb.append("\nEmprestimo= ").append(emprestimo);
        return sb.toString();
    }
}

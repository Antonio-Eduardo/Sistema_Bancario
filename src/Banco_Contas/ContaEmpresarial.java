package Banco_Contas;

import ENUM.TipoOperacao;
import Excecoes.LimiteExcedidoException;
import Excecoes.SaldoInsuficienteException;

import java.util.UUID;

public final class  ContaEmpresarial extends Contas{
    private double emprestimo;


    public ContaEmpresarial(String titular, String idConta, double balance, double emprestimo) {
        super(titular, idConta, balance);
        this.emprestimo = emprestimo;
    }
    @Override
    public void sacar(double valor, String id){
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
    public void deposito(double valor, String id){
        double taxaEmpresa = 50.00;
        if (taxaEmpresa + valor > 35000) {
            throw new LimiteExcedidoException();
        }
        balance += valor - taxaEmpresa;
        addTransacao(new Transacao(TipoOperacao.OPERACAO_DEPOSITO, valor, balance,id));;
    }
    public void addEmprestimo(double valor){
        emprestimo += valor;
    }

    public double getEmprestimo() {
        return emprestimo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Conta [Empresa]\n");
        sb.append(super.toString());
        sb.append("\nemprestimo= ").append(emprestimo);
        return sb.toString();
    }
}

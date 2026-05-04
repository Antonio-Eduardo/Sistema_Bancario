package Banco_Contas;

import Excecoes.SaldoInsuficienteException;

public final class  ContaEmpresarial extends Contas{
    private double emprestimo;

    public ContaEmpresarial(){
        super();
    }

    public ContaEmpresarial(String titular, Integer numero, double balance, double emprestimo) {
        super(titular, numero, balance);
        this.emprestimo = emprestimo;
    }
    public void addEmprestimo(double valor){
        emprestimo += valor;
    }

    public double getEmprestimo() {
        return emprestimo;
    }


    @Override
    public void Saque(double valor) {
        if (valor + 6.00 <= balance){
        double saque = valor + 6.00;
        balance -= saque;}
        else {
            throw new SaldoInsuficienteException();
        }
    }

    @Override
    public void Depositar(double valor){
        if (valor <= 30000){balance += valor;}
        else {
            throw new SaldoInsuficienteException();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Conta [Empresa] {");
        sb.append(super.toString());
        sb.append(" emprestimo= ").append(emprestimo);
        sb.append('}');
        return sb.toString();
    }
}

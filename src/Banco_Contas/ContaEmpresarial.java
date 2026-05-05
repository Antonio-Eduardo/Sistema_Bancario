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
    public String toString() {
        final StringBuilder sb = new StringBuilder("Conta [Empresa] {");
        sb.append(super.toString());
        sb.append(" emprestimo= ").append(emprestimo);
        sb.append('}');
        return sb.toString();
    }
}

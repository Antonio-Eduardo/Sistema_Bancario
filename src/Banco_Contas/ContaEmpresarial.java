package Banco_Contas;

import java.util.List;

public final class  ContaEmpresarial extends Contas{
    private double emprestimo;

    public ContaEmpresarial(){
        super();
    }

    public ContaEmpresarial(String titular, Integer numero, double balance, double emprestimo) {
        super(titular, numero, balance);
        this.emprestimo = emprestimo;
    }
    public void Emprestimo(double valor){
        emprestimo += valor;
    }

    public double getEmprestimo() {
        return emprestimo;
    }


    @Override
    public void Saque(double valor) {
        super.Saque(valor);
        balance -= 2.0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contas Empresa{");
        sb.append("titular='").append(getTitular()).append('\'');
        sb.append(", numero=").append(getNumero());
        sb.append(", balance=").append(getBalance());
        sb.append(" emprestimo=").append(emprestimo);
        sb.append('}');
        return sb.toString();
    }
}

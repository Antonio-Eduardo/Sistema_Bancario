package Banco_Contas;

import java.util.List;

public final class ContaPoupanca extends Contas{
    private double juros;

    public ContaPoupanca(){
        super();
    }

    public ContaPoupanca(String titular, Integer numero, double balance, double juros) {
        super(titular, numero, balance);
        this.juros = juros;
    }
    public void JurosValor(){
        balance += balance * juros;
    }

    public double getJuros() {
        return juros;
    }
@Override

    public void Saque(double valor) {
    balance -= valor;
}


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contas Poupanca{");
        sb.append("titular='").append(getTitular()).append('\'');
        sb.append(", numero=").append(getNumero());
        sb.append(", balance=").append(getBalance());
        sb.append(" Juros=").append(juros);
        sb.append('}');
        return sb.toString();
    }
}

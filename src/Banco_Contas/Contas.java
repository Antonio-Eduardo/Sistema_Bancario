package Banco_Contas;

import java.util.ArrayList;
import java.util.List;

public class Contas {
    private Integer numero;
    private String titular;
    protected double balance;

    public Contas() {
    }

    public Contas(String titular, Integer numero, double balance) {
        this.titular = titular;
        this.numero = numero;
        this.balance = balance;
    }

    public void Depositar(double deposito) {
        balance += deposito;
    }

    public void Saque(double saque) {
        if (saque <= balance) {
            balance -= saque + 5.0;
        } else {
            System.out.println("Saldo insuficiente !");
        }
    }

    public double getBalance() {
        return balance;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contas{");
        sb.append("titular='").append(titular).append('\'');
        sb.append(", numero=").append(numero);
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }
}

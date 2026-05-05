package Banco_Contas;

public abstract class Contas {
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
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("titular='").append(titular).append('\'');
        sb.append(", numero=").append(numero);
        sb.append(", Saldo=").append(balance);
        return sb.toString();
    }
}

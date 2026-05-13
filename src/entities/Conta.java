package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Conta {
    private long idConta;
    private String titular;
    protected double balance;
    private final List<Transacao> historicoTransacoes = new ArrayList<>();

    public void addTransacao(Transacao transacao){
        historicoTransacoes.add(transacao);
    }

    public Conta(String titular, double balance) {
        this.titular = titular;
        this.balance = balance;
    }

    public Conta(long idConta, String titular, double balance) {
        this.idConta = idConta;
        this.titular = titular;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
    public abstract void sacar(double valor, long id);
    public abstract void deposito(double valor, long id);
    public abstract void transferencia( Double valorTx, Conta contaY);
    public Long getIdConta() {
        return idConta;
    }
    public String getTitular() {
        return titular;
    }
    public Transacao getUltimaTransacao() {
        if (historicoTransacoes.isEmpty()) return null;
        return historicoTransacoes.get(historicoTransacoes.size() - 1);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Conta contas)) return false;
        return Objects.equals(idConta, contas.idConta);
    }
    protected void creditar(double valor){
        balance += valor;
    }

    public void setIdConta(long idConta) {
        this.idConta = idConta;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idConta);
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\ntitular= ").append(titular).append('\'');
        sb.append("\nnumero= ").append(idConta);
        sb.append("\nSaldo= ").append(balance);
        sb.append("\n--- Transacoes ---\n");
        for (Transacao t : historicoTransacoes){
            sb.append(t).append("\n");
        }
        return sb.toString();
    }
 }

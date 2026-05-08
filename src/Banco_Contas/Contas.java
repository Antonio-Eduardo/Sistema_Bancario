package Banco_Contas;

import Excecoes.SaldoInsuficienteException;
import Util.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Contas implements Comparable<Contas> {
    private String idConta;
    private String titular;
    protected double balance;
    List<Transacao> historicoTransacoes = new ArrayList<>();
    public void addTransacao(Transacao transacao){
        historicoTransacoes.add(transacao);
    }
    public Contas(String titular, String idConta, double balance) {
        this.titular = titular;
        this.idConta = idConta;
        this.balance = balance;
    }
    public double getBalance() {
        return balance;
    }
    public abstract void sacar(double valor, String id);
    public abstract void deposito(double valor, String id);
    public String getIdConta() {
        return idConta;
    }
    public String getTitular() {
        return titular;
    }
    public Transacao getUltimaTransacao() {
        return historicoTransacoes.getLast();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Contas contas)) return false;
        return Objects.equals(idConta, contas.idConta);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idConta);
    }

    @Override
    public int compareTo(Contas o) {
        return getTitular().compareTo(o.getTitular());
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("titular= ").append(titular).append('\'');
        sb.append("\nnumero= ").append(idConta);
        sb.append("\nSaldo= ").append(balance);
        sb.append("\n--- Transacoes ---");
        ListUtils.printLista(historicoTransacoes);
        return sb.toString();
    }
 }

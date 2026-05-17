package entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idConta;
    private String titular;
    protected double balance;

    @Transient
    private final List<entities.Transacao> historicoTransacoes = new ArrayList<>();

    public void addTransacao(entities.Transacao transacao){
        historicoTransacoes.add(transacao);
    }

    public Conta(){}

    public Conta(String titular, double balance) {
        this.titular = titular;
        this.balance = balance;
    }
    public Conta(Long idConta, String titular, double balance) {
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
    public entities.Transacao getUltimaTransacao() {
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

    public void setIdConta(Long idConta) {
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
        for (entities.Transacao t : historicoTransacoes){
            sb.append(t).append("\n");
        }
        return sb.toString();
    }
 }

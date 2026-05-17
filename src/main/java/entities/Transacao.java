package entities;

import enums.TipoOperacao;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Transacao{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iD;
    @Enumerated(EnumType.STRING)
    private TipoOperacao tipoOperacao;
    private double valor;
    private double saldoApos;
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    private Conta conta;

    public Transacao(TipoOperacao tipoOperacao, double valor, double saldoApos) {
        this.tipoOperacao = tipoOperacao;
        this.valor = valor;
        this.saldoApos = saldoApos;
        this.data = LocalDateTime.now();
    }
    public Transacao() {
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Transacao(Long iD, TipoOperacao tipoOperacao, double valor, double saldoApos , LocalDateTime data) {
        this.iD = iD;
        this.tipoOperacao = tipoOperacao;
        this.valor = valor;
        this.data = data;
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public Long getiD() {
        return iD;
    }

    public void setiD(Long iD) {
        this.iD = iD;
    }

    public LocalDateTime getData() {
        return data;
    }

    public double getSaldoApos() {
        return saldoApos;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        final StringBuilder sb = new StringBuilder(" |tipoOperacao= ").append(tipoOperacao);
        sb.append(" |valor= ").append(valor);
        sb.append(" |saldoApos= ").append(saldoApos);
        sb.append(" |data= ").append(data.format(fmt));
        return sb.toString();
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}

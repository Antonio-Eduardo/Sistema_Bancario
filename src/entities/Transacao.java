package entities;

import enums.TipoOperacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transacao{
    private long iD;
    private String idTransacao;
    private TipoOperacao tipoOperacao;
    private double valor;
    private double saldoApos;
    private LocalDateTime data;

    public Transacao(TipoOperacao tipoOperacao, double valor, double saldoApos, long iD) {
        this.idTransacao = UUID.randomUUID().toString();
        this.tipoOperacao = tipoOperacao;
        this.valor = valor;
        this.saldoApos = saldoApos;
        this.iD = iD;
        this.data = LocalDateTime.now();

    }

    public Transacao() {
    }

    public Transacao(long iD, TipoOperacao tipoOperacao, double valor, double saldoApos , LocalDateTime data) {
        this.iD = iD;
        this.tipoOperacao = tipoOperacao;
        this.valor = valor;
        this.data = data;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public long getiD() {
        return iD;
    }

    public void setiD(long iD) {
        this.iD = iD;
    }

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
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

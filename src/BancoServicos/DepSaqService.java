package BancoServicos;

import Banco_Contas.*;
import Excecoes.LimiteExcedidoException;
import Excecoes.SaldoInsuficienteException;

public class DepSaqService {
    private ServicoTaxaConta tax;
    private double amount;

    public DepSaqService(double amount) {
        this.amount = amount;
        this.tax = new ServicoTaxaConta();
    }
    public void processDeposito(Contas conta){
        double valorFinal = amount - buscarTaxa(conta);
        double saldoAtualizado = conta.getBalance() + valorFinal;
        if (saldoAtualizado <= 10000) {
            conta.setBalance(saldoAtualizado);
        } else {
            System.out.println("SALDO: " + conta.getBalance());
            throw new LimiteExcedidoException();
        }
    }
    public void processSaque(Contas conta){
        double valorFinal = amount + buscarTaxa(conta);
        double saldoAtualizado = conta.getBalance() - valorFinal;
        if (saldoAtualizado >= 0.0) {
            conta.setBalance(saldoAtualizado);
        }else {
            System.out.println("SALDO: " + conta.getBalance());
            throw new SaldoInsuficienteException();
        }
    }
    private double buscarTaxa(Contas conta){
        if (conta instanceof ContaCorrente){return tax.taxaCorrente();}
        if (conta instanceof ContaEmpresarial) {return tax.taxaEmpresa();}
        if (conta instanceof ContaPoupanca) {return tax.taxaPoupanca();}
        return 0.0;
    }
    public double getSaldo() {
        return amount;
    }

    public void setSaldo(double saldo) {
        this.amount = saldo;
    }

    public ServicoTaxaConta getTax() {
        return tax;
    }

    public void setTax(ServicoTaxaConta tax) {
        this.tax = tax;
    }
}

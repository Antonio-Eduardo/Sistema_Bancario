package factory;

import entities.ContaCorrente;
import entities.ContaEmpresarial;
import entities.ContaPoupanca;
import entities.Conta;

public class ContaFactory {
    public static Conta criarContaCorrente(String titular, double balance){
        return new ContaCorrente(titular,balance);
    }
    public static Conta criarContaEmpresa(String titular, double balance, double emprestimo){
        return new ContaEmpresarial(titular,balance,emprestimo);
    }
    public static Conta criarContaPoupanca(String titular, double balance){
        return new ContaPoupanca(titular,balance);
    }
}

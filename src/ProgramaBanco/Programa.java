package ProgramaBanco;

import Banco_Contas.ContaEmpresarial;
import Banco_Contas.ContaPoupanca;
import Banco_Contas.Contas;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        List<Contas> todasContas = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.print("Quantidade de contas a serem registradas: ");
        int quantidade = sc.nextInt();
        int limite = 0;
        sc.nextLine();
        int opcao;
        do {
            System.out.println("Selecione o tipo de conta (1-Conta corrente|2-Conta empresarial|3-Conta poupanca)");
            opcao = sc.nextInt();
            sc.nextLine();
            switch(opcao){
                case 1:
                    System.out.print("Nome do titular: ");
                    String nome = sc.nextLine();
                    System.out.print("iD da conta: ");
                    int numero = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Saldo inicial : 0,00");
                    double saldo = 0.00;
                    Contas accCorrente = new Contas(nome,numero,saldo);
                    todasContas.add(accCorrente);
                    limite++;
                    break;
                case 2:
                    System.out.print("Nome do titular: ");
                    String nomeEmp = sc.nextLine();
                    System.out.print("iD da conta: ");
                    int numeroEmp = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Saldo inicial : 0,00");
                    double saldoEmp = 0.00;
                    System.out.print("Emprestimo inicial: ");
                    double emprestimo = sc.nextDouble();
                    Contas accEmp = new ContaEmpresarial(nomeEmp,numeroEmp,saldoEmp,emprestimo);
                    todasContas.add(accEmp);
                    limite++;
                    break;
                case 3:
                    System.out.print("Nome do titular: ");
                    String nomePoup = sc.nextLine();
                    System.out.print("iD da conta: ");
                    int numeroPoup = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Saldo inicial : 0,00");
                    double saldoPoup = 0.00;
                    System.out.print("Emprestimo inicial: ");
                    double juros = sc.nextDouble();
                    Contas accPoup = new ContaPoupanca(nomePoup,numeroPoup,saldoPoup,juros);
                    todasContas.add(accPoup);
                    limite++;
                    break;
            }
        }while (limite < quantidade);


        for (Contas f : todasContas){
            System.out.println(f);
        }


    }
}

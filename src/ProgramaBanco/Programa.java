package ProgramaBanco;

import BancoServicos.SistemaOperacaoBanco;
import Banco_Contas.*;
import ENUM.TipoOperacao;
import Excecoes.ConsoleException;
import Excecoes.NegocioException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        List<Contas> todasContas = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        final int quantidade = ConsoleException.lerInteiros(sc, "quantidade de cadastros: ");
        int limite = 0;
        int opcao;
        while (limite < quantidade) {
            String nome = ConsoleException.lerString(sc, "Nome titular: ");
            int numero = ConsoleException.lerInteiros(sc, "iD da conta:");
            double depositoInicial = ConsoleException.lerDouble(sc, "Realize um deposito iniciaL: ");
            opcao = ConsoleException.lerInteiros(sc, "Selecione o tipo de conta (1-Conta corrente|2-Conta empresarial|3-Conta poupanca)\n");
            switch (opcao) {
                case 1:
                    NegocioException.executar(() -> {
                        Contas accCorrente = new ContaCorrente(nome, numero, depositoInicial);
                        todasContas.add(accCorrente);

                    });
                    limite++;
                    break;
                case 2:
                    double emprestimo = ConsoleException.lerDouble(sc, "Emprestimo inicial: ");
                    NegocioException.executar(() -> {
                        Contas accEmp = new ContaEmpresarial(nome, numero, depositoInicial, emprestimo);
                        todasContas.add(accEmp);
                    });
                    limite++;
                    break;
                case 3:
                    NegocioException.executar(() -> {
                        Contas accPoup = new ContaPoupanca(nome, numero, depositoInicial);
                        todasContas.add(accPoup);
                    });
                    limite++;
                    break;
                default:
                    System.out.println("Opção inválida! tente novamente");
            }
        }
        for (Contas f : todasContas) {
            System.out.println(f);
        }
        int opcao2 = 0;

        while (opcao2 != 3 && quantidade > 0) {
            System.out.println("1-Deposito | 2-Saque | 3-sair");
            opcao2 = sc.nextInt();
            sc.nextLine();
            switch (opcao2) {
                case 1:
                    int numeroContaDep = ConsoleException.lerInteiros(sc, "Digite o numero da conta que deseja realizar a operacao: ");
                    Contas procuraDep = todasContas.stream().filter(c -> c.getNumero().equals(numeroContaDep)).findFirst().orElse(null);

                    if (procuraDep == null) {
                        System.out.println("numero invalido");
                        opcao2 = 0;
                        break;
                    } else {
                        double valorDep = ConsoleException.lerDouble(sc, "Ola " + procuraDep.getTitular() + " digite o valor a ser depositado: ");
                        SistemaOperacaoBanco service = new SistemaOperacaoBanco();
                        NegocioException.executar(() -> service.processDeposito(procuraDep,valorDep));
                        break;
                    }
                case 2:
                    int numeroContaSaq = ConsoleException.lerInteiros(sc, "Digite o numero da conta que deseja realizar o saque: ");
                    Contas procuraSaq = todasContas.stream().filter(c -> c.getNumero().equals(numeroContaSaq)).findFirst().orElse(null);

                    if (procuraSaq == null) {
                        System.out.println("numero invalido");
                        opcao2 = 0;
                        break;
                    } else {
                        double valorSaq = ConsoleException.lerDouble(sc, "Ola " + procuraSaq.getTitular() + " digite o valor a ser sacado: ");
                        SistemaOperacaoBanco service = new SistemaOperacaoBanco();
                        NegocioException.executar(() -> service.processSaque(procuraSaq,valorSaq));
                        break;
                    }
                case 3:
                    break;
            }
        }
        String path = "C:\\temp\\bancoTeste.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Contas f : todasContas) {
                bw.write(String.valueOf(f));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Contas f : todasContas) {
            System.out.println(f);
        }
    }
}

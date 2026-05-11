package Main;

import Repository.HistoricoTransacaoTxtRepositorio;
import Application.SistemaOperacaoBanco;
import Entities.*;
import Application.ConsoleException;
import Excecoes.NegocioException;
import Services.HistoricoTransacaoTxT;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        SistemaOperacaoBanco service = new SistemaOperacaoBanco();
        HistoricoTransacaoTxT repo = new HistoricoTransacaoTxtRepositorio();

        Map<String, Contas> todasContas = new HashMap<>();

        Scanner sc = new Scanner(System.in);
        final int quantidade = ConsoleException.lerInteiros(sc, "quantidade de cadastros: ");
        int limite = 0;
        int opcao;
        while (limite < quantidade) {
            String nome = ConsoleException.lerString(sc, "Nome titular: ");
            System.out.print("iD da conta: ");
            String idConta = sc.nextLine();
            double depositoInicial = ConsoleException.lerDouble(sc, "Realize um deposito iniciaL: ");
            opcao = ConsoleException.lerInteiros(sc, "Selecione o tipo de conta (1-Conta corrente|2-Conta empresarial|3-Conta poupanca)\n");
            switch (opcao) {
                case 1:
                    NegocioException.executar(() -> {
                        Contas accCorrente = new ContaCorrente(nome, idConta, depositoInicial);
                        todasContas.put(accCorrente.getIdConta(), accCorrente);
                    });
                    limite++;
                    break;
                case 2:
                    double emprestimo = ConsoleException.lerDouble(sc, "Emprestimo inicial: ");
                    NegocioException.executar(() -> {
                        Contas accEmp = new ContaEmpresarial(nome, idConta, depositoInicial, emprestimo);
                        todasContas.put(accEmp.getIdConta(), accEmp);
                    });
                    limite++;
                    break;
                case 3:
                    NegocioException.executar(() -> {
                        Contas accPoup = new ContaPoupanca(nome, idConta, depositoInicial);
                        todasContas.put(accPoup.getIdConta(), accPoup);
                    });
                    limite++;
                    break;
                default:
                    System.out.println("Opção inválida! tente novamente");
            }
        }
        while (true) {
            int op2 = ConsoleException.lerInteiros(sc, "1-DEPOSITAR |2-SACAR |3-EXTRATO |4-SAIR\n");
            if (op2 == 4) {
                break;
            }
            if (op2 == 3) {
                System.out.println("Digite o ID da conta: ");
                String idBusca = sc.nextLine();

                Contas conta = todasContas.get(idBusca);
                if (conta == null) {
                    System.out.println("Conta nao encontrada");
                    continue;
                }
                List<Transacao> transacoes = repo.listarPorConta(idBusca);
                transacoes.forEach(System.out::println);
            }
            if (op2 == 1 || op2 == 2) {
                System.out.println("Digite o numero da conta que deseja realizar a operacao: ");
                String numeroConta = sc.nextLine();
                Contas procura = todasContas.get(numeroConta);
                if (procura == null) {
                    System.out.println("numero invalido");
                    continue;
                }
                System.out.println("Ola " + procura.getTitular() + " digite o valor: ");
                double valor = sc.nextDouble();
                String idContaT = procura.getIdConta();
                if (op2 == 1) {
                    NegocioException.executar(() -> service.processDeposito(procura, valor, idContaT));
                } else {
                    NegocioException.executar(() -> service.processSaque(procura, valor, idContaT));
                }
            }
        }
        System.out.println(todasContas);
    }
}

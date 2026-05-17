
import dao.ContaDAO;
import dao.TransacaoDAO;
import entities.Transacao;
import factory.ContaFactory;
import factory.DaoFactory;
import entities.Conta;
import service.OperacaoBanco;
import factory.OperacaoFactory;
import util.ConsoleInput;
import exceptions.NegocioException;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ContaDAO repoContasSQL = DaoFactory.criarContaDAO();
        TransacaoDAO repoTransacoesSQL = DaoFactory.criarTransDAO();
        OperacaoBanco service = OperacaoFactory.operacaoBanco(repoContasSQL,repoTransacoesSQL);

        Map<Long, Conta> todasContas = new HashMap<>();

        Scanner sc = new Scanner(System.in);

        int escolhaUsu = 0;
        while (escolhaUsu != 2) {
            escolhaUsu = ConsoleInput.lerInteiros(sc, "[1]Deseja iniciar um cadastro?\n[2]Usuario ja cadastrado?\n");
            switch (escolhaUsu) {
                case 1:
                    String nome = ConsoleInput.lerString(sc, "Nome titular: ");
                    double depositoInicial = ConsoleInput.lerDouble(sc, "Realize um deposito iniciaL: ");
                    int opcao = ConsoleInput.lerInteiros(sc, "Selecione o tipo da conta\n[1]-Conta corrente\n[2]-Conta empresarial\n[3]-Conta poupanca)\n");
                    switch (opcao) {
                        case 1:
                            NegocioException.executar(() -> {
                                Conta accCorrente = ContaFactory.criarContaCorrente(nome, depositoInicial);
                                todasContas.put(accCorrente.getIdConta(), accCorrente);
                                repoContasSQL.salvar(accCorrente);
                                System.out.print("Seu iD é: " + accCorrente.getIdConta()+ "\n");
                            });
                            break;
                        case 2:
                            double emprestimo = ConsoleInput.lerDouble(sc, "Emprestimo inicial: ");
                            NegocioException.executar(() -> {
                                Conta accEmp = ContaFactory.criarContaEmpresa(nome, depositoInicial, emprestimo);
                                todasContas.put(accEmp.getIdConta(), accEmp);
                                repoContasSQL.salvar(accEmp);
                                System.out.print("Seu iD é: " + accEmp.getIdConta()+ "\n");
                            });
                            break;
                        case 3:
                            NegocioException.executar(() -> {
                                Conta accPoup = ContaFactory.criarContaPoupanca(nome, depositoInicial);
                                todasContas.put(accPoup.getIdConta(), accPoup);
                                repoContasSQL.salvar(accPoup);
                                System.out.print("Seu iD é: " + accPoup.getIdConta() + "\n");
                            });
                            break;
                        default:
                            System.out.println("Opção inválida! tente novamente");
                    }
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Numero invalido!");
                    break;
            }
        }
        while (true) {
            int op2 = ConsoleInput.lerInteiros(sc, "1-DEPOSITAR |2-SACAR |3-EXTRATO |4-TRANSFERENCIA |5-SAIR\n");
            if (op2 == 5) {
                break;
            }
            if (op2 == 3) {
                System.out.println("Digite o ID da conta: ");
                long idBusca = sc.nextLong();

                Conta conta = repoContasSQL.buscarPorId(idBusca);
                if (conta == null) {
                    System.out.println("Conta nao encontrada");
                    continue;
                }
                List<Transacao> transacoes = repoTransacoesSQL.extrato(conta.getIdConta());
                for (Transacao t : transacoes){
                    System.out.println(t + "\n");
                }
            }
            if (op2 == 1 || op2 == 2 || op2 == 4) {
                System.out.println("Digite o numero da conta que deseja realizar a operacao: ");
                Long numeroConta = sc.nextLong();
                Conta procura = repoContasSQL.buscarPorId(numeroConta);
                if (procura == null) {
                    System.out.println("numero invalido");
                    continue;
                }
                System.out.println("Ola " + procura.getTitular() + " digite o valor: ");
                double valor = sc.nextDouble();
                if (op2 == 1) {
                    NegocioException.executar(() -> service.processDeposito(procura, valor));
                    System.out.println("[DEPOSITO CONCLUIDO] FINALIZADO!\n");
                } else if (op2 == 2){
                    NegocioException.executar(() -> service.processSaque(procura, valor));
                    System.out.println("[SAQUE CONCLUIDO] FINALIZADO!\n");
                } else {
                    System.out.println("Digite o numero da conta para que deseja realizar a transferencia: ");
                    Long numeroContaTransf = sc.nextLong();
                    Conta procuraTransf = repoContasSQL.buscarPorId(numeroContaTransf);
                    int escolhaTransf = ConsoleInput.lerInteiros(sc,"Desejar realizar uma transferencia para conta [" + procuraTransf.getTitular() + "] ? [1-SIM|2-CANCELAR]");
                    if (escolhaTransf == 1) {
                        NegocioException.executar(() -> service.processTransferencia(procura, valor, procuraTransf));
                        System.out.println("[TRANSFERENCIA CONCLUIDO] FINALIZADO!\n");
                    }else {
                        System.out.println("OPERACAO CANCELADA!");
                    }
                }
            }
        }
        todasContas.forEach((iD, conta) ->
                System.out.println(iD + " = " + conta));
    }
}

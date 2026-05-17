
import dao.ContaDAO;
import dao.TransacaoDAO;
import entities.Transacao;
import exceptions.DBException;
import factory.ContaFactory;
import factory.DaoFactory;
import entities.Conta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import service.OperacaoBanco;
import factory.OperacaoFactory;
import util.ConsoleInput;
import exceptions.NegocioException;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sistema-bancario-jpa");
        EntityManager em = emf.createEntityManager();
        ContaDAO repoContasSQL = DaoFactory.criarContaDAO(em);
        TransacaoDAO repoTransacoesSQL = DaoFactory.criarTransDAO(em);
        OperacaoBanco service = OperacaoFactory.operacaoBanco(repoContasSQL,em);

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
                                try {
                                    em.getTransaction().begin();
                                    Conta accCorrente = ContaFactory.criarContaCorrente(nome, depositoInicial);
                                    repoContasSQL.salvar(accCorrente);
                                    em.getTransaction().commit();

                                    System.out.print("Seu iD é: " + accCorrente.getIdConta() + "\n");
                                }catch (Exception e){
                                    if (em.getTransaction() != null && em.getTransaction().isActive()){
                                        em.getTransaction().rollback();
                                    }
                                    throw new DBException();
                                }
                            break;
                        case 2:
                            double emprestimo = ConsoleInput.lerDouble(sc, "Emprestimo inicial: ");
                            try {
                                em.getTransaction().begin();
                                Conta accEmp = ContaFactory.criarContaEmpresa(nome, depositoInicial, emprestimo);
                                repoContasSQL.salvar(accEmp);
                                em.getTransaction().commit();

                                System.out.print("Seu iD é: " + accEmp.getIdConta() + "\n");
                            }catch (Exception e){
                                if (em.getTransaction() != null && em.getTransaction().isActive()){
                                    em.getTransaction().rollback();
                                }
                                throw new DBException();
                            }
                            break;
                        case 3:
                            try {
                                em.getTransaction().begin();
                                Conta accPoup = ContaFactory.criarContaPoupanca(nome, depositoInicial);
                                repoContasSQL.salvar(accPoup);
                                em.getTransaction().commit();

                                System.out.print("Seu iD é: " + accPoup.getIdConta() + "\n");
                            }catch (Exception e){
                                if (em.getTransaction() != null && em.getTransaction().isActive()){
                                    em.getTransaction().rollback();
                                }
                                throw new DBException();
                            }

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
                long idBusca = ConsoleInput.lerLong(sc,"Digite o ID da conta: ");

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
                long numeroConta = ConsoleInput.lerLong(sc,"Digite o numero da conta que deseja realizar a operacao: ");
                Conta procura = repoContasSQL.buscarPorId(numeroConta);
                if (procura == null) {
                    System.out.println("numero invalido");
                    continue;
                }
                double valor = ConsoleInput.lerDouble(sc,"Ola " + procura.getTitular() + " digite o valor: ");

                try {
                    if (op2 == 1) {
                         service.processDeposito(procura, valor);
                        System.out.println("[DEPOSITO CONCLUIDO] FINALIZADO!\n");
                    } else if (op2 == 2) {
                         service.processSaque(procura, valor);
                        System.out.println("[SAQUE CONCLUIDO] FINALIZADO!\n");
                    } else {
                        long numeroContaTransf = ConsoleInput.lerLong(sc, "Digite o numero da conta para que deseja realizar a transferencia: ");
                        Conta procuraTransf = repoContasSQL.buscarPorId(numeroContaTransf);
                        if (procuraTransf == null){
                            System.out.println("Conta destino nao encontrada");
                            continue;
                        }
                        int escolhaTransf = ConsoleInput.lerInteiros(sc, "Desejar realizar uma transferencia para conta [" + procuraTransf.getTitular() + "] ? [1-SIM|2-CANCELAR]");
                        if (escolhaTransf == 1) {
                            service.processTransferencia(procura, valor, procuraTransf);
                            System.out.println("[TRANSFERENCIA CONCLUIDO] FINALIZADO!\n");
                        } else {
                            System.out.println("OPERACAO CANCELADA!");
                        }
                    }
                }catch (NegocioException e) {
                    System.out.println("\n[AVISO DE NEGÓCIO] " + e.getMessage() + "\n");
                } catch (DBException e) {
                    System.out.println("\n[SISTEMA] Erro crítico no banco de dados. Operação cancelada.\n");
                }
            }
        }
        emf.close();
    }
}

package service.impl;

import dao.ContaDAO;
import entities.Conta;
import exceptions.DBException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import service.OperacaoBanco;

public class OperacaoBancoImpl implements OperacaoBanco {
    private EntityManager em;

    public OperacaoBancoImpl(EntityManager em) {
        this.em = em;

    }
    public void executarEmTransacao(Runnable operacao){
        try {
            em.getTransaction().begin();
            operacao.run();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new DBException();
        }
    }
    public void processDeposito(Conta conta, double valor) {
        executarEmTransacao(()-> {
            Conta contaGerenciada = em.merge(conta);
            contaGerenciada.deposito(valor);
        });
    }
    public void processSaque(Conta conta, double valor) {
        executarEmTransacao(()-> {
            Conta contaGerenciada = em.merge(conta);
            contaGerenciada.sacar(valor);
        });
    }
    public void processTransferencia(Conta contaOrigem, Double valorT, Conta contaDestino){
        executarEmTransacao(()-> {
            Conta origemGerenciada = em.merge(contaOrigem);
            Conta destinoGerenciada = em.merge(contaDestino);
            origemGerenciada.transferencia(valorT, destinoGerenciada);
        });
    }
}

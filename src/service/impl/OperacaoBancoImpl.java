package service.impl;

import dao.ContaDAO;
import dao.TransacaoDAO;
import service.OperacaoBanco;
import entities.*;
import dao.impl.TransacaoDAOImpl;
import dao.impl.ContaDAOImpl;

public class OperacaoBancoImpl implements OperacaoBanco {
    private final ContaDAO contaDAO;
    private final TransacaoDAO transacaoDAO;

    public OperacaoBancoImpl(
            ContaDAO contaDAO,
            TransacaoDAO transacaoDAO
    ) {
        this.contaDAO = contaDAO;
        this.transacaoDAO = transacaoDAO;

    }

    public void processDeposito(Conta conta, double valor) {
        conta.deposito(valor, conta.getIdConta());
        Transacao t = conta.getUltimaTransacao();
        if (t != null) {
            transacaoDAO.salvar(t);
            contaDAO.updateSaldo(conta.getIdConta(),conta.getBalance());
        }
    }
    public void processSaque(Conta conta, double valor) {
        conta.sacar(valor, conta.getIdConta());
        Transacao t = conta.getUltimaTransacao();
        if (t != null) {
            transacaoDAO.salvar(t);
            contaDAO.updateSaldo(conta.getIdConta(),conta.getBalance());
        }
    }
    public void processTransferencia(Conta contaOrigem, Double valorT, Conta contaDestino){
        contaOrigem.transferencia(valorT,contaDestino);
        Transacao tOrigem = contaOrigem.getUltimaTransacao();
        Transacao tDestino = contaDestino.getUltimaTransacao();
        if (tOrigem != null && tDestino != null){
            transacaoDAO.salvar(tOrigem); transacaoDAO.salvar(tDestino);
            contaDAO.transferenciaOperacao(contaOrigem,contaDestino);
        }
    }
}

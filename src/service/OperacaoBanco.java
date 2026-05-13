package service;

import entities.Conta;

public interface OperacaoBanco {
    public void processDeposito(Conta conta, double valor);
    public void processSaque(Conta conta, double valor);
    public void processTransferencia(Conta contaOrigem, Double valorT, Conta contaDestino);
}

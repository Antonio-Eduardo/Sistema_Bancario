package factory;

import dao.ContaDAO;
import dao.TransacaoDAO;
import service.OperacaoBanco;
import service.impl.OperacaoBancoImpl;

public interface OperacaoFactory {
    public static OperacaoBanco operacaoBanco(ContaDAO contaDAO, TransacaoDAO transacaoDAO){
        return new OperacaoBancoImpl(contaDAO,transacaoDAO);
    }
}

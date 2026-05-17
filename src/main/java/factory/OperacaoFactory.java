package factory;

import dao.ContaDAO;
import jakarta.persistence.EntityManager;
import service.OperacaoBanco;
import service.impl.OperacaoBancoImpl;

public interface OperacaoFactory {
    public static OperacaoBanco operacaoBanco(ContaDAO contaDAO,EntityManager em){
        return new OperacaoBancoImpl(em);
    }
}

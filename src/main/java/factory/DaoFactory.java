package factory;

import dao.ContaDAO;
import dao.TransacaoDAO;
import dao.impl.ContaDAOImpl;
import dao.impl.TransacaoDAOImpl;
import jakarta.persistence.EntityManager;

public interface DaoFactory {
    public static ContaDAO criarContaDAO(EntityManager em){
        return new ContaDAOImpl(em);
    }
    public static TransacaoDAO criarTransDAO(EntityManager em){
        return  new TransacaoDAOImpl(em);
    }

}

package dao;

import dao.impl.ContaDAOImpl;
import dao.impl.TransacaoDAOImpl;

public interface DaoFactory {
    public static ContaDAO criarContaDAO(){
        return new ContaDAOImpl();
    }
    public static TransacaoDAO criarTransDAO(){
        return  new TransacaoDAOImpl();
    }

}

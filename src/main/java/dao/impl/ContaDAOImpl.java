package dao.impl;

import dao.ContaDAO;
import entities.Conta;
import jakarta.persistence.EntityManager;

public class ContaDAOImpl implements ContaDAO {
    private final EntityManager em;

    public ContaDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void salvar(Conta t) {
        em.persist(t);
    }

    @Override
    public Conta buscarPorId(Long id) {
        return em.find(Conta.class, id);
    }
}


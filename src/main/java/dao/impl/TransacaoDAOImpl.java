package dao.impl;

import dao.TransacaoDAO;
import entities.Transacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class TransacaoDAOImpl implements TransacaoDAO {
    private final EntityManager em;

    public TransacaoDAOImpl(EntityManager em) {
        this.em = em;
    }

    public List<Transacao> extrato(Long id) {
        String jpql = "SELECT t FROM Transacao t WHERE t.conta.idConta = :idParam ORDER BY t.data DESC";
        TypedQuery<Transacao> query = em.createQuery(jpql, Transacao.class);
        query.setParameter("idParam", id);
        return query.getResultList();
    }
}

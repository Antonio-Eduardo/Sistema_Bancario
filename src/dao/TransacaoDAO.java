package dao;

import entities.Transacao;

import java.util.List;

public interface TransacaoDAO {
    public void salvar(Transacao t);
    public List<Transacao> extrato(Long id);
}

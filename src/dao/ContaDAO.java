package dao;

import entities.Conta;

import java.sql.Connection;

public interface ContaDAO {
    public void salvar(Conta t);
    public Conta buscarPorId(Long id);
    public void updateSaldo(Long id, Double saldo);
    public void transferenciaOperacao(Conta origem, Conta destino);
    public void updateSaldoTransferencia(Connection conn, Long id, Double saldo);
    public void deleteConta(Long id);
}

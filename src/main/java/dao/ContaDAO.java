package dao;
import entities.Conta;

public interface ContaDAO {
    void salvar(Conta t);
    Conta buscarPorId(Long id);

}

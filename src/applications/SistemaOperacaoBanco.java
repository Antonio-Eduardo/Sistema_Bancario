package applications;

import Repository.RepositoryTransacaoTxT;
import entities.*;
import Services.Repository;
import Repository.RepositoryTransacaoMySQL;
import Repository.RepositoryContasMySQL;

public class SistemaOperacaoBanco {
    private final Repository<Transacao> repo = new RepositoryTransacaoTxT();
    private final RepositoryContasMySQL repoMySQL = new RepositoryContasMySQL();
    private final RepositoryTransacaoMySQL repoTransacoesSQL = new RepositoryTransacaoMySQL();

    public void processDeposito(Conta conta, double valor) {
        conta.deposito(valor, conta.getIdConta());
        Transacao t = conta.getUltimaTransacao();
        if (t != null) {
            repo.salvar(t);
            repoTransacoesSQL.salvar(t);
            repoMySQL.updateSaldo(conta.getIdConta(),conta.getBalance());
        }
    }
    public void processSaque(Conta conta, double valor) {
        conta.sacar(valor, conta.getIdConta());
        Transacao t = conta.getUltimaTransacao();
        if (t != null) {
            repo.salvar(t);
            repoTransacoesSQL.salvar(t);
            repoMySQL.updateSaldo(conta.getIdConta(),conta.getBalance());
        }
    }
}

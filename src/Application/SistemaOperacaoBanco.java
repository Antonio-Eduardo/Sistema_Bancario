package Application;

import Repository.RepositoryTransacaoTxT;
import Entities.*;
import Services.Repository;
import Repository.RepositoryTransacaoMySQL;

public class SistemaOperacaoBanco {
    private final Repository<Transacao> repo = new RepositoryTransacaoTxT();
    private final RepositoryTransacaoMySQL repoTransacoesSQL = new RepositoryTransacaoMySQL();
    public void processDeposito(Contas conta, double valor, Long id) {
        conta.deposito(valor, id);
        Transacao t = conta.getUltimaTransacao();
        if (t != null) {
            repo.salvar(t);
            repoTransacoesSQL.salvar(t);
        }
    }
    public void processSaque(Contas conta, double valor, Long id) {
        conta.sacar(valor, id);
        Transacao t = conta.getUltimaTransacao();
        if (t != null) {
            repo.salvar(t);
            repoTransacoesSQL.salvar(t);
        }
    }
}

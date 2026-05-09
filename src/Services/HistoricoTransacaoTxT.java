package Services;

import Banco_Contas.Transacao;
import java.util.List;

public interface HistoricoTransacaoTxT {
    void salvar(Transacao t);
    List<Transacao>listarPorConta(String iD);
}

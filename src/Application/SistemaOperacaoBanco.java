package Application;

import Repository.HistoricoTransacaoTxtRepositorio;
import Banco_Contas.*;
import Services.HistoricoTransacaoTxT;

public class SistemaOperacaoBanco {
    private ServicoTaxaConta tax;

    private HistoricoTransacaoTxT repo = new HistoricoTransacaoTxtRepositorio();

    public void processDeposito(Contas conta, double valor, String id) {
        conta.deposito(valor, id);
        Transacao t = conta.getUltimaTransacao();
        if (t != null) {
            repo.salvar(t);
        }
    }

    public void processSaque(Contas conta, double valor, String id) {
        conta.sacar(valor, id);
        Transacao t = conta.getUltimaTransacao();
        if (t != null) {
            repo.salvar(t);
        }
    }

    private double buscarTaxa(Contas conta) {
        if (conta instanceof ContaCorrente) {
            return tax.taxaCorrente();
        }
        if (conta instanceof ContaEmpresarial) {
            return tax.taxaEmpresa();
        }
        if (conta instanceof ContaPoupanca) {
            return tax.taxaPoupanca();
        }
        return 0.0;
    }

    public ServicoTaxaConta getTax() {
        return tax;
    }
}

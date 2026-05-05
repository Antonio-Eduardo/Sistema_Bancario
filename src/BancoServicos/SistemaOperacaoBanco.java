package BancoServicos;

import Banco_Contas.*;
import Excecoes.LimiteExcedidoException;
import Excecoes.SaldoInsuficienteException;

public class SistemaOperacaoBanco {
    private ServicoTaxaConta tax;


    public void processDeposito(Contas conta, double valor) {
        conta.deposito(valor);
    }

    public void processSaque(Contas conta, double valor) {
        conta.sacar(valor);
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

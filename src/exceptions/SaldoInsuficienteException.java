package exceptions;

public class SaldoInsuficienteException extends ValidacaoException {

    public SaldoInsuficienteException() {
        super(ErrorCode.SALDO_BAIXO, "Saldo insuficiente para concluir a operacao");
    }
}

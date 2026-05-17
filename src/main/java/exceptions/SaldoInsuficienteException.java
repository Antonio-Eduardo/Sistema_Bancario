package exceptions;

import enums.ErrorCode;

public class SaldoInsuficienteException extends NegocioException{

    public SaldoInsuficienteException() {
        super(ErrorCode.SALDO_BAIXO, "Saldo insuficiente para concluir a operacao");
    }
}

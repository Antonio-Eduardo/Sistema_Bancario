package exceptions;

import enums.ErrorCode;

public class LimiteExcedidoException extends NegocioException {

    public LimiteExcedidoException() {
        super(ErrorCode.LIMITE_EXCEDIDO, "Valor excedeu o limite permitido");
    }
}

package Excecoes;

public class LimiteExcedidoException extends ValidacaoException {

    public LimiteExcedidoException() {
        super(ErrorCode.LIMITE_EXCEDIDO, "Valor excedeu o limite permitido");
    }
}

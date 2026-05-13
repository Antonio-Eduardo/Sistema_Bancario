package exceptions;

public class ValidacaoException extends RuntimeException{
    private final ErrorCode codigo;

    public ValidacaoException(ErrorCode codigo, String msg){
        super(msg);
        this.codigo = codigo;
    }
    public ErrorCode getError(){
        return codigo;
    }
}

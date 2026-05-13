package exceptions;

public class DBException extends  ValidacaoException{
    public DBException() {
        super(ErrorCode.DB_EXCEPTION, "Erro no DB");
    }
}

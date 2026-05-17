package exceptions;

import enums.ErrorCode;

public class DBException extends  ValidacaoException{
    public DBException() {
        super(ErrorCode.DB_EXCEPTION, "Erro no DB");
    }
}

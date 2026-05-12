package ENUM;

public enum TipoOperacao {
    OPERACAO_DEPOSITO(1),
    OPERACAO_SAQUE(2);

    private final int id;

    TipoOperacao(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

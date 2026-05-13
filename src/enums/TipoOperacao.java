package enums;

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
    public static TipoOperacao deId(int id){
        for (TipoOperacao tipo : values()){
            if (tipo.getId() == id){
                return  tipo;
            }
        }
        throw new IllegalArgumentException("ID invalido: " + id);
    }
}

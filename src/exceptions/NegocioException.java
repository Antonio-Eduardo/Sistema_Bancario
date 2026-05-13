package exceptions;

public class NegocioException {
    public static void executar(Runnable acao) {
        try {
            acao.run();
        } catch (SaldoInsuficienteException e) {
            System.out.println("[BANCO] Atenção: " + e.getMessage());
        } catch (LimiteExcedidoException e ){
            System.out.println("[BANCO] Atenção: " + e.getMessage());
        }
    }

}


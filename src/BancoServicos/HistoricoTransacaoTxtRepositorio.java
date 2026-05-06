package BancoServicos;
import Banco_Contas.Transacao;
import ENUM.TipoOperacao;
import Interfaces.HistoricoTransacaoTxT;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistoricoTransacaoTxtRepositorio implements HistoricoTransacaoTxT {
    private static final String path = "HistoricoDeTransacoes.txt";

    @Override
    public void salvar(Transacao t) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path,true))){
            String linha = t.getiD() + ";" +
                    t.getTipoOperacao() + ";" +
                    t.getValor() + ";" +
                    t.getSaldoApos() + ";" +
                    t.getData();
            bw.write(linha);
            bw.newLine();
        }catch (IOException e){
            throw new RuntimeException("Erro ao salvar transacao ", e);
        }
    }
    @Override
    public List<Transacao> listarPorConta(String iD) {
        List<Transacao> lista = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String linha;
            while ((linha = br.readLine()) != null){
                String[] partes = linha.split(";");
                String contaId = partes[0];
                if (contaId.equals(iD)){
                            Transacao t = new Transacao(
                            partes[0],
                            TipoOperacao.valueOf(partes[1]),
                            Double.parseDouble(partes[2]),
                            Double.parseDouble(partes[3]),
                            LocalDateTime.parse(partes[4])
                    );
                    lista.add(t);
                }
            }

        }catch(FileNotFoundException e){
            throw new RuntimeException("Erro ao listar conta ", e);
        }catch (IOException e){
            throw new RuntimeException("Erro: ",e);
        }
        return lista;
    }
}

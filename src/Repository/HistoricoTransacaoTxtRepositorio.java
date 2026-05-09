package Repository;
import Banco_Contas.Transacao;
import ENUM.TipoOperacao;
import Services.HistoricoTransacaoTxT;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoricoTransacaoTxtRepositorio implements HistoricoTransacaoTxT {
    private static final String path = "HistoricoDeTransacoes.txt";
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    @Override
    public void salvar(Transacao t) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path,true))){
            String linha = t.getiD() + ";" +
                    t.getTipoOperacao() + ";" +
                    t.getValor() + ";" +
                    t.getSaldoApos() + ";" +
                    t.getData().format(fmt);
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
                if (partes.length < 5) continue;
                String contaId = partes[0];
                if (contaId.equals(iD)){
                            Transacao t = new Transacao(
                            partes[0],
                            TipoOperacao.valueOf(partes[1]),
                            Double.parseDouble(partes[2]),
                            Double.parseDouble(partes[3]),
                            LocalDateTime.parse(partes[4], fmt)
                    );
                    lista.add(t);
                }
            }
            Collections.sort(lista);

        }catch(FileNotFoundException e){
            throw new RuntimeException("Erro ao listar conta ", e);
        }catch (IOException e){
            throw new RuntimeException("Erro: ",e);
        }
        return lista;
    }
}

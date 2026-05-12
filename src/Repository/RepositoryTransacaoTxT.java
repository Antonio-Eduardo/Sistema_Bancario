package Repository;
import Entities.Transacao;
import ENUM.TipoOperacao;
import Services.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepositoryTransacaoTxT implements Repository<Transacao>{
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
    public List<Transacao> listarPorConta(long iD) {
        List<Transacao> lista = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String linha;
            while ((linha = br.readLine()) != null){
                String[] partes = linha.split(";");
                if (partes.length < 5) continue;
                Long contaId = Long.parseLong(partes[0]);
                if (contaId.equals(iD)){
                            Transacao t = new Transacao(
                            Long.parseLong(partes[0]),
                            TipoOperacao.valueOf(partes[1]),
                            Double.parseDouble(partes[2]),
                            Double.parseDouble(partes[3]),
                            LocalDateTime.parse(partes[4], fmt)
                    );
                    lista.add(t);
                }
            }
            lista.sort(Comparator.comparing(Transacao::getiD));

        }catch(FileNotFoundException e){
            throw new RuntimeException("Erro ao listar conta ", e);
        }catch (IOException e){
            throw new RuntimeException("Erro: ",e);
        }
        return lista;
    }
}

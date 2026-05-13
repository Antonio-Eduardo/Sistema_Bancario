package Repository;

import enums.TipoOperacao;
import entities.Transacao;
import Services.Repository;
import db.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTransacaoMySQL implements Repository<Transacao> {
    @Override
    public void salvar(Transacao t) {
        String sql = "INSERT INTO transacoes (id_operacao, valor, id_conta) VALUES (?,?,?)";
        try(Connection conn = DB.getConnection();
        PreparedStatement st = conn.prepareStatement(sql)){
            st.setInt(1,t.getTipoOperacao().getId());
            st.setDouble(2,t.getValor());
            st.setLong(3,t.getiD());

            int rowsAffected = st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Transacao> extrato(Long id){
        String sql = "SELECT * FROM transacoes WHERE id_conta = ?";
        List<Transacao> extrato = new ArrayList<>();
        try (Connection conn = DB.getConnection();
        PreparedStatement st = conn.prepareStatement(sql)) {
            st.setLong(1,id);

            try(ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    Transacao t = new Transacao();
                    t.setiD(rs.getLong("idTransacao"));
                    t.setValor(rs.getDouble("valor"));
                    int idNoBanco = rs.getInt("id_operacao");
                    t.setTipoOperacao(TipoOperacao.deId(idNoBanco));
                    Timestamp ts = rs.getTimestamp("data_registro");
                    if (ts != null) {
                        t.setData(ts.toLocalDateTime());
                    }
                    extrato.add(t);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return extrato;
    }
}

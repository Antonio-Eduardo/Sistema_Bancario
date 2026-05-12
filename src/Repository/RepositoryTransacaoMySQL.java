package Repository;

import Entities.Transacao;
import Excecoes.DBException;
import Services.Repository;
import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositoryTransacaoMySQL implements Repository<Transacao> {
    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    @Override
    public void salvar(Transacao t) {
        try {
            conn = DB.getConnection();
            st = conn.prepareStatement(
                    "INSERT INTO transacoes"
                    + "(id_operacao, valor, id_conta) "
                    + "VALUES "
                    +"(?,?,?)");
            st.setInt(1,t.getTipoOperacao().getId());
            st.setDouble(2,t.getValor());
            st.setLong(3,t.getiD());

            int rowsAffected = st.executeUpdate();

        } catch (SQLException e) {
            throw new DBException();
        }
    }
}

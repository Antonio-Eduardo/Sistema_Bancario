package Repository;

import Entities.ContaCorrente;
import Entities.ContaEmpresarial;
import Entities.ContaPoupanca;
import Entities.Contas;
import Excecoes.DBException;
import Services.Repository;
import db.DB;

import java.sql.*;

public class RepositoryContasMySQL implements Repository<Contas> {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement st = null;

    @Override
    public void salvar(Contas t) {
        try {
            Connection conn;
            ResultSet rs;
            PreparedStatement st;

            conn = DB.getConnection();
            st = conn.prepareStatement(
                    "INSERT INTO contas"
                            + "(titular, balance, emprestimo, rendimento, id_tipo)"
                            + "VALUES "
                            + "(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            Double emprestimo = null;
            Double rendimento = null;
            if (t instanceof ContaEmpresarial contaEmpresarial) {
                emprestimo = contaEmpresarial.getEmprestimo();
                st.setInt(5, 3);
            } else if (t instanceof ContaPoupanca contaPoupanca) {
                rendimento = contaPoupanca.getRendimento();
                st.setInt(5, 2);
            } else {
                st.setInt(5, 1);
            }
            st.setString(1, t.getTitular());
            st.setDouble(2, t.getBalance());

            if (emprestimo != null) {
                st.setDouble(3, emprestimo);
            } else {
                st.setNull(3, Types.DOUBLE);
            }
            if (rendimento != null) {
                st.setDouble(4, rendimento);
            } else {
                st.setNull(4, Types.DOUBLE);
            }
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    long idGerado = rs.getLong(1);
                    t.setIdConta(idGerado);
                }
            }
        } catch (SQLException e) {
            throw new DBException();
        }finally {
            DB.closeStatement(st);
            DB.closeResult(rs);
            DB.closeConnection();
        }
    }
    public Contas buscarPorId(Long id) {
        String sql = "SELECT * FROM contas WHERE idConta = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    String titular = rs.getString("titular");
                    double saldo = rs.getDouble("balance");
                    int tipo = rs.getInt("id_tipo");
                    if (tipo == 3) {
                        double emprestimo = rs.getDouble("emprestimo");
                        return new ContaEmpresarial(titular, id, saldo, emprestimo);
                    } else if (tipo == 2) {
                        double rendimento = rs.getDouble("rendimento");
                        return new ContaPoupanca(titular, id, saldo);
                    } else {
                        return new ContaCorrente(titular, id, saldo);
                    }
                }
            }
        }
    catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeConnection();
            DB.closeStatement(st);
            DB.closeResult(rs);
        }
        return  null;
    }
    }


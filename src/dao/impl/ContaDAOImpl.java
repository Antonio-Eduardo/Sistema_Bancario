package dao.impl;

import dao.ContaDAO;
import entities.ContaCorrente;
import entities.ContaEmpresarial;
import entities.ContaPoupanca;
import entities.Conta;
import exceptions.DBException;
import db.DB;
import exceptions.DBIntegrityException;

import java.sql.*;

public class ContaDAOImpl implements ContaDAO {

    @Override
    public void salvar(Conta t) {
        String sql = "INSERT INTO contas (titular, balance, emprestimo, rendimento, id_tipo) VALUES(?,?,?,?,?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
                try (ResultSet rsGenerated = st.getGeneratedKeys()) {
                    if (rsGenerated.next()) {
                        t.setIdConta(rsGenerated.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    public Conta buscarPorId(Long id) {
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
        } catch (SQLException e) {
            throw new DBException();
        }
        return null;
    }

    public void updateSaldo(Long id, Double saldo) {
        String sql = "UPDATE contas SET balance = ? WHERE idConta = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDouble(1, saldo);
            st.setLong(2, id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0){
                System.out.println("Nenhuma linha afetada.");
            }
            System.out.println("Linhas afetadas: "+rowsAffected);
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    public void transferenciaOperacao(Conta origem, Conta destino) {
        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);
            try {
                //origem
                updateSaldoTransferencia(conn,origem.getIdConta(),origem.getBalance());
                //destino
                updateSaldoTransferencia(conn,destino.getIdConta(), destino.getBalance());
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateSaldoTransferencia(Connection conn, Long id, Double saldo) {
        String sql = "UPDATE contas SET balance = ? WHERE idConta = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDouble(1, saldo);
            st.setLong(2, id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0){
                System.out.println("Nenhuma linha afetada.");
            }
            System.out.println("Linhas afetadas: "+rowsAffected);
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    public void deleteConta(Long id) {
        String sql = "DELETE FROM contas WHERE idConta = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setLong(1, id);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0){
                System.out.println("Nenhuma linha afetada.");
            }
            System.out.println("Linhas afetadas: "+rowsAffected);
        } catch (SQLException e) {
            throw new DBIntegrityException(e.getMessage());
        }
    }
}


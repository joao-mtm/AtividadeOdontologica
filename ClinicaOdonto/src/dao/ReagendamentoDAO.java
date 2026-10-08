package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Consulta;
import model.Reagendamento;
import util.Datas;

public class ReagendamentoDAO {

    // Grava o histórico e muda a data da consulta juntos: ou faz os dois, ou nenhum
    public void reagendar(Reagendamento reagendamento) throws SQLException {

        String sqlHistorico = "INSERT INTO reagendamentos "
                + "(consulta_id, data_anterior, horario_anterior, nova_data, novo_horario, motivo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String sqlConsulta = "UPDATE consultas SET data = ?, horario = ?, status = ? WHERE id = ?";

        try (Connection conexao = Conexao.getConexao()) {

            conexao.setAutoCommit(false);

            try (PreparedStatement stmtHistorico = conexao.prepareStatement(sqlHistorico);
                 PreparedStatement stmtConsulta = conexao.prepareStatement(sqlConsulta)) {

                stmtHistorico.setInt(1, reagendamento.getConsultaId());
                stmtHistorico.setDate(2, Datas.dataParaBanco(reagendamento.getDataAnterior()));
                stmtHistorico.setTime(3, Datas.horarioParaBanco(reagendamento.getHorarioAnterior()));
                stmtHistorico.setDate(4, Datas.dataParaBanco(reagendamento.getNovaData()));
                stmtHistorico.setTime(5, Datas.horarioParaBanco(reagendamento.getNovoHorario()));
                stmtHistorico.setString(6, reagendamento.getMotivo());
                stmtHistorico.executeUpdate();

                stmtConsulta.setDate(1, Datas.dataParaBanco(reagendamento.getNovaData()));
                stmtConsulta.setTime(2, Datas.horarioParaBanco(reagendamento.getNovoHorario()));
                stmtConsulta.setString(3, Consulta.REAGENDADA);
                stmtConsulta.setInt(4, reagendamento.getConsultaId());
                stmtConsulta.executeUpdate();

                conexao.commit();

            } catch (SQLException e) {

                conexao.rollback();
                throw e;
            }
        }
    }

    public List<Reagendamento> listarPorConsulta(int consultaId) throws SQLException {

        String sql = "SELECT id, consulta_id, data_anterior, horario_anterior, nova_data, novo_horario, motivo "
                + "FROM reagendamentos WHERE consulta_id = ? ORDER BY id";

        List<Reagendamento> reagendamentos = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, consultaId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Reagendamento reagendamento = new Reagendamento(
                            rs.getInt("consulta_id"),
                            Datas.dataParaTela(rs.getDate("data_anterior")),
                            Datas.horarioParaTela(rs.getTime("horario_anterior")),
                            Datas.dataParaTela(rs.getDate("nova_data")),
                            Datas.horarioParaTela(rs.getTime("novo_horario")),
                            rs.getString("motivo")
                    );
                    reagendamento.setId(rs.getInt("id"));

                    reagendamentos.add(reagendamento);
                }
            }
        }

        return reagendamentos;
    }
}

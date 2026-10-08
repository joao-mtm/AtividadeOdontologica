package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Consulta;
import util.Datas;

public class ConsultaDAO {

    public void inserir(Consulta consulta) throws SQLException {

        String sql = "INSERT INTO consultas (paciente_id, dentista_id, data, horario, motivo, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, consulta.getPacienteId());
            stmt.setInt(2, consulta.getDentistaId());
            stmt.setDate(3, Datas.dataParaBanco(consulta.getData()));
            stmt.setTime(4, Datas.horarioParaBanco(consulta.getHorario()));
            stmt.setString(5, consulta.getMotivo());
            stmt.setString(6, consulta.getStatus());
            stmt.executeUpdate();

            // Guarda o id gerado pelo banco no objeto
            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    consulta.setId(chaves.getInt(1));
                }
            }
        }
    }

    public List<Consulta> listar() throws SQLException {

        String sql = "SELECT c.id, c.paciente_id, p.nome AS paciente, c.dentista_id, d.nome AS dentista, "
                + "c.data, c.horario, c.motivo, c.status "
                + "FROM consultas c "
                + "JOIN pacientes p ON p.id = c.paciente_id "
                + "JOIN dentistas d ON d.id = c.dentista_id "
                + "ORDER BY c.data, c.horario";

        List<Consulta> consultas = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                consultas.add(new Consulta(
                        rs.getInt("id"),
                        rs.getInt("paciente_id"),
                        rs.getString("paciente"),
                        rs.getInt("dentista_id"),
                        rs.getString("dentista"),
                        Datas.dataParaTela(rs.getDate("data")),
                        Datas.horarioParaTela(rs.getTime("horario")),
                        rs.getString("motivo"),
                        rs.getString("status")
                ));
            }
        }

        return consultas;
    }

    public void atualizarStatus(int id, String status) throws SQLException {

        String sql = "UPDATE consultas SET status = ? WHERE id = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    // Verifica se o dentista já tem outra consulta (não cancelada) no mesmo dia e horário
    public boolean horarioOcupado(int dentistaId, String data, String horario, int ignorarConsultaId) throws SQLException {

        String sql = "SELECT COUNT(*) FROM consultas "
                + "WHERE dentista_id = ? AND data = ? AND horario = ? AND status <> ? AND id <> ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, dentistaId);
            stmt.setDate(2, Datas.dataParaBanco(data));
            stmt.setTime(3, Datas.horarioParaBanco(horario));
            stmt.setString(4, Consulta.CANCELADA);
            stmt.setInt(5, ignorarConsultaId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}

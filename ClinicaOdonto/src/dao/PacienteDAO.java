package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;

public class PacienteDAO {

    // A tela usa dd/MM/yyyy e o banco usa yyyy-MM-dd
    private static final DateTimeFormatter FORMATO_TELA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void inserir(Paciente paciente) throws SQLException {

        String sql = "INSERT INTO pacientes (nome, cpf, telefone, data_nascimento) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getTelefone());
            stmt.setDate(4, Date.valueOf(LocalDate.parse(paciente.getDataNascimento(), FORMATO_TELA)));
            stmt.executeUpdate();

            // Guarda o id gerado pelo banco no objeto
            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    paciente.setId(chaves.getInt(1));
                }
            }
        }
    }

    public List<Paciente> listar() throws SQLException {

        String sql = "SELECT id, nome, cpf, telefone, data_nascimento FROM pacientes ORDER BY nome";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Date data = rs.getDate("data_nascimento");

                pacientes.add(new Paciente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        data == null ? "" : data.toLocalDate().format(FORMATO_TELA)
                ));
            }
        }

        return pacientes;
    }

    public void excluir(int id) throws SQLException {

        String sql = "DELETE FROM pacientes WHERE id = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

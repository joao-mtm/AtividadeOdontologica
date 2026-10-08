package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Dentista;

public class DentistaDAO {

    public void inserir(Dentista dentista) throws SQLException {

        String sql = "INSERT INTO dentistas (nome, cro) VALUES (?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, dentista.getNome());
            stmt.setString(2, dentista.getCro());
            stmt.executeUpdate();

            // Guarda o id gerado pelo banco no objeto
            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    dentista.setId(chaves.getInt(1));
                }
            }
        }
    }

    public List<Dentista> listar() throws SQLException {

        String sql = "SELECT id, nome, cro FROM dentistas ORDER BY nome";
        List<Dentista> dentistas = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                dentistas.add(new Dentista(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cro")
                ));
            }
        }

        return dentistas;
    }

    public void excluir(int id) throws SQLException {

        String sql = "DELETE FROM dentistas WHERE id = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

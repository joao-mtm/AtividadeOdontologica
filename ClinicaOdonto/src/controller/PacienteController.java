package controller;

import java.sql.SQLException;
import java.util.List;

import dao.PacienteDAO;
import model.Paciente;

public class PacienteController {

    private PacienteDAO pacienteDAO;

    public PacienteController() {
        pacienteDAO = new PacienteDAO();
    }

    public void cadastrarPaciente(Paciente paciente) throws SQLException {
        pacienteDAO.inserir(paciente);
    }

    public List<Paciente> listarPacientes() throws SQLException {
        return pacienteDAO.listar();
    }

    public void excluirPaciente(int id) throws SQLException {
        pacienteDAO.excluir(id);
    }
}

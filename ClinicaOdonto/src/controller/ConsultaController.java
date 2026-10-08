package controller;

import java.sql.SQLException;
import java.util.List;

import dao.ConsultaDAO;
import model.Consulta;

public class ConsultaController {

    private ConsultaDAO consultaDAO;

    public ConsultaController() {
        consultaDAO = new ConsultaDAO();
    }

    public void agendarConsulta(Consulta consulta) throws SQLException {
        consultaDAO.inserir(consulta);
    }

    public List<Consulta> listarConsultas() throws SQLException {
        return consultaDAO.listar();
    }

    public void alterarStatus(int id, String status) throws SQLException {
        consultaDAO.atualizarStatus(id, status);
    }

    public boolean horarioOcupado(int dentistaId, String data, String horario, int ignorarConsultaId) throws SQLException {
        return consultaDAO.horarioOcupado(dentistaId, data, horario, ignorarConsultaId);
    }
}

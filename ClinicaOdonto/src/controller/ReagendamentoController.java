package controller;

import java.sql.SQLException;
import java.util.List;

import dao.ReagendamentoDAO;
import model.Reagendamento;

public class ReagendamentoController {

    private ReagendamentoDAO reagendamentoDAO;

    public ReagendamentoController() {
        reagendamentoDAO = new ReagendamentoDAO();
    }

    public void reagendar(Reagendamento reagendamento) throws SQLException {
        reagendamentoDAO.reagendar(reagendamento);
    }

    public List<Reagendamento> listarHistorico(int consultaId) throws SQLException {
        return reagendamentoDAO.listarPorConsulta(consultaId);
    }
}

package controller;

import java.sql.SQLException;
import java.util.List;

import dao.DentistaDAO;
import model.Dentista;

public class DentistaController {

    private DentistaDAO dentistaDAO;

    public DentistaController() {
        dentistaDAO = new DentistaDAO();
    }

    public void cadastrarDentista(Dentista dentista) throws SQLException {
        dentistaDAO.inserir(dentista);
    }

    public List<Dentista> listarDentistas() throws SQLException {
        return dentistaDAO.listar();
    }

    public void excluirDentista(int id) throws SQLException {
        dentistaDAO.excluir(id);
    }
}

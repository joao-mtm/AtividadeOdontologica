package controller;

import java.util.ArrayList;
import java.util.List;

import model.Paciente;

public class PacienteController {

    private List<Paciente> pacientes;

    public PacienteController() {
        pacientes = new ArrayList<>();
    }

    public void cadastrarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacientes;
    }
}

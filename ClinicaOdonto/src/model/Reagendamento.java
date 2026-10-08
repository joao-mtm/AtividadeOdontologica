package model;

public class Reagendamento {

    private int id;
    private int consultaId;
    private String dataAnterior;
    private String horarioAnterior;
    private String novaData;
    private String novoHorario;
    private String motivo;

    public Reagendamento(
            int consultaId,
            String dataAnterior,
            String horarioAnterior,
            String novaData,
            String novoHorario,
            String motivo
    ) {
        this.consultaId = consultaId;
        this.dataAnterior = dataAnterior;
        this.horarioAnterior = horarioAnterior;
        this.novaData = novaData;
        this.novoHorario = novoHorario;
        this.motivo = motivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsultaId() {
        return consultaId;
    }

    public String getDataAnterior() {
        return dataAnterior;
    }

    public String getHorarioAnterior() {
        return horarioAnterior;
    }

    public String getNovaData() {
        return novaData;
    }

    public String getNovoHorario() {
        return novoHorario;
    }

    public String getMotivo() {
        return motivo;
    }
}

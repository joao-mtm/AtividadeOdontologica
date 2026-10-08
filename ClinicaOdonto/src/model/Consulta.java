package model;

public class Consulta {

    public static final String AGENDADA = "Agendada";
    public static final String REALIZADA = "Realizada";
    public static final String CANCELADA = "Cancelada";
    public static final String REAGENDADA = "Reagendada";

    public static final String[] STATUS = {
            AGENDADA,
            REALIZADA,
            CANCELADA,
            REAGENDADA
    };

    private int id;
    private int pacienteId;
    private String pacienteNome;
    private int dentistaId;
    private String dentistaNome;
    private String data;
    private String horario;
    private String motivo;
    private String status;

    public Consulta(
            int id,
            int pacienteId,
            String pacienteNome,
            int dentistaId,
            String dentistaNome,
            String data,
            String horario,
            String motivo,
            String status
    ) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.pacienteNome = pacienteNome;
        this.dentistaId = dentistaId;
        this.dentistaNome = dentistaNome;
        this.data = data;
        this.horario = horario;
        this.motivo = motivo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public int getDentistaId() {
        return dentistaId;
    }

    public String getDentistaNome() {
        return dentistaNome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

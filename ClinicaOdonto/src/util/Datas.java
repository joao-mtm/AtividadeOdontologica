package util;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

// Converte datas e horários entre o formato da tela e o do banco
public class Datas {

    private static final DateTimeFormatter DATA_TELA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter HORARIO_TELA =
            DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

    private Datas() {
    }

    public static boolean dataValida(String data) {
        try {
            LocalDate.parse(data, DATA_TELA);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean horarioValido(String horario) {
        try {
            LocalTime.parse(horario, HORARIO_TELA);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static Date dataParaBanco(String data) {
        return Date.valueOf(LocalDate.parse(data, DATA_TELA));
    }

    public static String dataParaTela(Date data) {
        return data == null ? "" : data.toLocalDate().format(DATA_TELA);
    }

    public static Time horarioParaBanco(String horario) {
        return Time.valueOf(LocalTime.parse(horario, HORARIO_TELA));
    }

    public static String horarioParaTela(Time horario) {
        return horario == null ? "" : horario.toLocalTime().format(HORARIO_TELA);
    }
}

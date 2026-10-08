package model;

public class Dentista {

    private int id;
    private String nome;
    private String cro;

    public Dentista(String nome, String cro) {
        this.nome = nome;
        this.cro = cro;
    }

    public Dentista(int id, String nome, String cro) {
        this(nome, cro);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    // Texto exibido nas caixas de seleção
    @Override
    public String toString() {
        return nome;
    }
}

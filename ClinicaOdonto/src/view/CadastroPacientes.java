package view;

import javax.swing.*;
import java.awt.*;

public class CadastroPacientes extends JFrame {

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private JTextField txtDataNascimento;

    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;

    public CadastroPacientes() {

        setTitle("Sistema Odontológico - Cadastro de Pacientes");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCpf = new JLabel("CPF:");
        JLabel lblTelefone = new JLabel("Telefone:");
        JLabel lblData = new JLabel("Data de Nascimento:");

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtTelefone = new JTextField();
        txtDataNascimento = new JTextField();

        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        painel.add(lblNome);
        painel.add(txtNome);

        painel.add(lblCpf);
        painel.add(txtCpf);

        painel.add(lblTelefone);
        painel.add(txtTelefone);

        painel.add(lblData);
        painel.add(txtDataNascimento);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Botão Salvar
        btnSalvar.addActionListener(e -> salvarPaciente());

        // Botão Limpar
        btnLimpar.addActionListener(e -> limparCampos());

        // Botão Sair
        btnSair.addActionListener(e -> dispose());
    }

    private void salvarPaciente() {

        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String telefone = txtTelefone.getText();
        String dataNascimento = txtDataNascimento.getText();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Digite o nome do paciente!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Paciente cadastrado com sucesso!\n\n"
                + "Nome: " + nome
                + "\nCPF: " + cpf
                + "\nTelefone: " + telefone
                + "\nData de nascimento: " + dataNascimento
        );

        limparCampos();
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtDataNascimento.setText("");
        txtNome.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            CadastroPacientes tela = new CadastroPacientes();
            tela.setVisible(true);
        });
    }
}

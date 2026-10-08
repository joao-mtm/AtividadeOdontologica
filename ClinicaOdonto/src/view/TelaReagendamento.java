package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.ConsultaController;
import controller.ReagendamentoController;
import model.Consulta;
import model.Reagendamento;
import util.Datas;

public class TelaReagendamento extends JDialog {

    private JPanel contentPane;

    private ConsultaController consultaController = new ConsultaController();
    private ReagendamentoController reagendamentoController = new ReagendamentoController();

    private Consulta consulta;
    private boolean reagendado = false;

    private JTextField txtDataAtual;
    private JTextField txtHorarioAtual;
    private JFormattedTextField txtNovaData;
    private JFormattedTextField txtNovoHorario;
    private JTextField txtMotivo;

    private DefaultTableModel modeloHistorico;

    private JButton btnSalvar;
    private JButton btnCancelar;

    public TelaReagendamento(JFrame dono, Consulta consulta) {

        super(dono, "OdontoCare - Reagendamento", true);
        this.consulta = consulta;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Tema.FUNDO);
        contentPane.setPreferredSize(new Dimension(718, 540));
        setContentPane(contentPane);

        JLabel lblSecao = new JLabel("REAGENDAMENTO");
        lblSecao.setFont(Tema.espacada(new Font(Tema.FONTE, Font.PLAIN, 13), 0.08f));
        lblSecao.setForeground(Tema.TEXTO_SECUNDARIO);
        lblSecao.setBounds(32, 24, 400, 22);
        contentPane.add(lblSecao);

        JLabel lblTitulo = new JLabel("Reagendar consulta");
        lblTitulo.setFont(new Font(Tema.FONTE, Font.BOLD, 32));
        lblTitulo.setForeground(Tema.TEXTO_ESCURO);
        lblTitulo.setBounds(30, 44, 654, 46);
        contentPane.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel(
                consulta.getPacienteNome() + " com " + consulta.getDentistaNome()
        );
        lblSubtitulo.setFont(new Font(Tema.FONTE, Font.PLAIN, 18));
        lblSubtitulo.setForeground(Tema.TEXTO_SECUNDARIO);
        lblSubtitulo.setBounds(32, 92, 654, 28);
        contentPane.add(lblSubtitulo);

        JPanel painelDados = new JPanel();
        painelDados.setLayout(null);
        painelDados.setBackground(Tema.CARD_CLARO);
        painelDados.setBounds(32, 132, 654, 150);
        contentPane.add(painelDados);

        txtDataAtual = new JTextField(consulta.getData());
        txtHorarioAtual = new JTextField(consulta.getHorario());
        txtNovaData = Tema.criarCampoComMascara("##/##/####");
        txtNovoHorario = Tema.criarCampoComMascara("##:##");
        txtMotivo = new JTextField();

        Tema.adicionarCampo(painelDados, "Data atual", txtDataAtual, 24, 16, 140);
        Tema.adicionarCampo(painelDados, "Horário atual", txtHorarioAtual, 175, 16, 140);
        Tema.adicionarCampo(painelDados, "Nova data", txtNovaData, 339, 16, 140);
        Tema.adicionarCampo(painelDados, "Novo horário", txtNovoHorario, 490, 16, 140);
        Tema.adicionarCampo(painelDados, "Motivo do reagendamento", txtMotivo, 24, 82);

        // Data e horário atuais são só para consulta
        txtDataAtual.setEditable(false);
        txtDataAtual.setBackground(Tema.CARD_CLARO_HOVER);
        txtHorarioAtual.setEditable(false);
        txtHorarioAtual.setBackground(Tema.CARD_CLARO_HOVER);

        btnCancelar = Tema.criarBotao("Cancelar", false);
        btnCancelar.setBounds(400, 102, 110, 36);
        painelDados.add(btnCancelar);

        btnSalvar = Tema.criarBotao("Salvar", true);
        btnSalvar.setBounds(520, 102, 110, 36);
        painelDados.add(btnSalvar);

        JLabel lblHistorico = new JLabel("HISTÓRICO DE REAGENDAMENTOS");
        lblHistorico.setFont(Tema.espacada(new Font(Tema.FONTE, Font.PLAIN, 13), 0.08f));
        lblHistorico.setForeground(Tema.TEXTO_SECUNDARIO);
        lblHistorico.setBounds(32, 298, 400, 22);
        contentPane.add(lblHistorico);

        modeloHistorico = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "Data anterior",
                        "Horário anterior",
                        "Nova data",
                        "Novo horário",
                        "Motivo"
                }
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        JTable tabelaHistorico = new JTable(modeloHistorico);
        Tema.estilizarTabela(tabelaHistorico);

        JScrollPane scrollPane = new JScrollPane(tabelaHistorico);
        scrollPane.setBorder(BorderFactory.createLineBorder(Tema.BORDA));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBounds(32, 324, 654, 184);
        contentPane.add(scrollPane);

        // Eventos dos botões
        btnSalvar.addActionListener(e -> salvarReagendamento());

        btnCancelar.addActionListener(e -> dispose());

        carregarHistorico();

        pack();
        setLocationRelativeTo(dono);
    }

    // Diz para a tela de agendamentos se a consulta foi reagendada
    public boolean isReagendado() {
        return reagendado;
    }

    private void carregarHistorico() {

        try {

            for (Reagendamento r : reagendamentoController.listarHistorico(consulta.getId())) {

                modeloHistorico.addRow(
                        new Object[] {
                                r.getDataAnterior(),
                                r.getHorarioAnterior(),
                                r.getNovaData(),
                                r.getNovoHorario(),
                                r.getMotivo()
                        }
                );
            }

        } catch (SQLException e) {

            mostrarErroBanco("carregar o histórico", e);
        }
    }

    private void salvarReagendamento() {

        String novaData = txtNovaData.getText().trim();
        String novoHorario = txtNovoHorario.getText().trim();
        String motivo = txtMotivo.getText().trim();

        // Verifica a nova data
        if (!Datas.dataValida(novaData)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite uma nova data válida!"
            );

            txtNovaData.requestFocus();
            return;
        }

        // Verifica o novo horário
        if (!Datas.horarioValido(novoHorario)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite um novo horário válido (ex.: 14:30)!"
            );

            txtNovoHorario.requestFocus();
            return;
        }

        // Precisa mudar pelo menos a data ou o horário
        if (novaData.equals(consulta.getData()) && novoHorario.equals(consulta.getHorario())) {

            JOptionPane.showMessageDialog(
                    this,
                    "A nova data e horário são iguais aos atuais!"
            );

            txtNovaData.requestFocus();
            return;
        }

        Reagendamento reagendamento = new Reagendamento(
                consulta.getId(),
                consulta.getData(),
                consulta.getHorario(),
                novaData,
                novoHorario,
                motivo
        );

        try {

            // O dentista não pode ter duas consultas no mesmo horário
            if (consultaController.horarioOcupado(
                    consulta.getDentistaId(), novaData, novoHorario, consulta.getId())) {

                JOptionPane.showMessageDialog(
                        this,
                        consulta.getDentistaNome() + " já tem uma consulta em "
                                + novaData + " às " + novoHorario + "!"
                );
                return;
            }

            reagendamentoController.reagendar(reagendamento);

        } catch (SQLException e) {

            mostrarErroBanco("reagendar a consulta", e);
            return;
        }

        reagendado = true;

        JOptionPane.showMessageDialog(
                this,
                "Consulta reagendada com sucesso!"
        );
        dispose();
    }

    private void mostrarErroBanco(String acao, SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Erro ao " + acao + " no banco de dados:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

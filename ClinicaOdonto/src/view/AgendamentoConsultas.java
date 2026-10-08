package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.ConsultaController;
import controller.DentistaController;
import controller.PacienteController;
import model.Consulta;
import model.Dentista;
import model.Paciente;
import util.Datas;

public class AgendamentoConsultas extends JFrame {

    // Posição das colunas no modelo da tabela
    private static final int COL_STATUS = 6;

    private JPanel contentPane;

    private ConsultaController consultaController = new ConsultaController();
    private PacienteController pacienteController = new PacienteController();
    private DentistaController dentistaController = new DentistaController();

    // Mesma ordem das linhas da tabela
    private List<Consulta> consultas = new ArrayList<>();

    private JComboBox<Paciente> cmbPaciente;
    private JComboBox<Dentista> cmbDentista;
    private JFormattedTextField txtData;
    private JFormattedTextField txtHorario;
    private JTextField txtMotivo;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JButton btnAgendar;
    private JButton btnLimpar;

    public AgendamentoConsultas() {

        setTitle("OdontoCare - Agendamento de Consultas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Tema.FUNDO);
        contentPane.setPreferredSize(
                new Dimension(Tema.LARGURA_TELA, Tema.ALTURA_TELA)
        );
        setContentPane(contentPane);

        JPanel painelLateral = Tema.criarPainelLateral(
                "Agendamento<br>de consultas",
                "Organize e acompanhe as consultas."
        );
        contentPane.add(painelLateral);

        JLabel lblVoltar = new JLabel("← Voltar ao menu");
        lblVoltar.setFont(new Font(Tema.FONTE, Font.PLAIN, 14));
        lblVoltar.setForeground(Tema.TEXTO_LATERAL_APAGADO);
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblVoltar.setBounds(30, 560, 200, 22);
        lblVoltar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                voltarAoMenu();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblVoltar.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblVoltar.setForeground(Tema.TEXTO_LATERAL_APAGADO);
            }
        });
        painelLateral.add(lblVoltar);

        JLabel lblSecao = new JLabel("CONSULTAS");
        lblSecao.setFont(Tema.espacada(new Font(Tema.FONTE, Font.PLAIN, 13), 0.08f));
        lblSecao.setForeground(Tema.TEXTO_SECUNDARIO);
        lblSecao.setBounds(408, 33, 400, 22);
        contentPane.add(lblSecao);

        JLabel lblTitulo = new JLabel("Nova consulta");
        lblTitulo.setFont(new Font(Tema.FONTE, Font.BOLD, 38));
        lblTitulo.setForeground(Tema.TEXTO_ESCURO);
        lblTitulo.setBounds(406, 55, 660, 56);
        contentPane.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Escolha o paciente, o dentista responsável e o horário.");
        lblSubtitulo.setFont(new Font(Tema.FONTE, Font.PLAIN, 18));
        lblSubtitulo.setForeground(Tema.TEXTO_SECUNDARIO);
        lblSubtitulo.setBounds(408, 112, 654, 28);
        contentPane.add(lblSubtitulo);

        JPanel painelDados = new JPanel();
        painelDados.setLayout(null);
        painelDados.setBackground(Tema.CARD_CLARO);
        painelDados.setBounds(408, 156, 654, 196);
        contentPane.add(painelDados);

        cmbPaciente = new JComboBox<>();
        cmbDentista = new JComboBox<>();
        txtData = Tema.criarCampoComMascara("##/##/####");
        txtHorario = Tema.criarCampoComMascara("##:##");
        txtMotivo = new JTextField();

        Tema.adicionarCampo(painelDados, "Paciente", cmbPaciente, 24, 16);
        Tema.adicionarCampo(painelDados, "Dentista responsável", cmbDentista, 339, 16);
        Tema.adicionarCampo(painelDados, "Data", txtData, 24, 82, 140);
        Tema.adicionarCampo(painelDados, "Horário", txtHorario, 175, 82, 140);
        Tema.adicionarCampo(painelDados, "Motivo", txtMotivo, 339, 82);

        btnLimpar = Tema.criarBotao("Limpar", false);
        btnLimpar.setBounds(400, 144, 110, 36);
        painelDados.add(btnLimpar);

        btnAgendar = Tema.criarBotao("Agendar", true);
        btnAgendar.setBounds(520, 144, 110, 36);
        painelDados.add(btnAgendar);

        modeloTabela = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "ID",
                        "Paciente",
                        "Dentista",
                        "Data",
                        "Horário",
                        "Motivo",
                        "Status"
                }
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                // Só o status pode ser alterado, pela caixa de seleção
                return coluna == COL_STATUS;
            }

            @Override
            public void setValueAt(Object valor, int linha, int coluna) {

                if (coluna == COL_STATUS) {
                    // A tabela só muda depois que o banco for atualizado
                    String novoStatus = (String) valor;
                    SwingUtilities.invokeLater(() -> alterarStatus(linha, novoStatus));
                    return;
                }

                super.setValueAt(valor, linha, coluna);
            }
        };

        tabela = new JTable(modeloTabela);
        Tema.estilizarTabela(tabela);

        // O ID fica no modelo, mas não aparece na tela
        tabela.removeColumn(tabela.getColumnModel().getColumn(0));

        tabela.getColumn("Paciente").setPreferredWidth(130);
        tabela.getColumn("Dentista").setPreferredWidth(140);
        tabela.getColumn("Data").setPreferredWidth(90);
        tabela.getColumn("Horário").setPreferredWidth(65);
        tabela.getColumn("Motivo").setPreferredWidth(115);
        tabela.getColumn("Status").setPreferredWidth(115);

        configurarColunaStatus();

        // Clicar numa consulta reagendada abre a tela de reagendamento
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int linha = tabela.rowAtPoint(e.getPoint());
                int coluna = tabela.columnAtPoint(e.getPoint());

                if (linha == -1 || coluna == -1
                        || tabela.convertColumnIndexToModel(coluna) == COL_STATUS) {
                    return;
                }

                Consulta consulta = consultas.get(tabela.convertRowIndexToModel(linha));

                if (Consulta.REAGENDADA.equals(consulta.getStatus())) {
                    abrirReagendamento(consulta);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(Tema.BORDA));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBounds(408, 368, 654, 180);
        contentPane.add(scrollPane);

        // Eventos dos botões
        btnAgendar.addActionListener(e -> agendarConsulta());

        btnLimpar.addActionListener(e -> limparCampos());

        carregarPacientesEDentistas();
        carregarConsultas();
        limparCampos();

        pack();
        setLocationRelativeTo(null);
    }

    private void configurarColunaStatus() {

        TableColumn colunaStatus = tabela.getColumn("Status");

        JComboBox<String> cmbStatus = new JComboBox<>(Consulta.STATUS);
        cmbStatus.setFont(new Font(Tema.FONTE, Font.PLAIN, 14));
        cmbStatus.setBackground(Color.WHITE);
        colunaStatus.setCellEditor(new DefaultCellEditor(cmbStatus));

        // Mostra o status com uma setinha, para indicar que dá para mudar
        colunaStatus.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable tabela, Object valor, boolean selecionado,
                    boolean foco, int linha, int coluna
            ) {

                super.getTableCellRendererComponent(
                        tabela, valor + "  ▾", selecionado, false, linha, coluna
                );

                setFont(new Font(Tema.FONTE, Font.BOLD, 14));
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

                if (Consulta.CANCELADA.equals(valor)) {
                    setForeground(Tema.TEXTO_ALERTA);
                } else if (Consulta.REALIZADA.equals(valor)) {
                    setForeground(Tema.CARD_ESCURO);
                } else {
                    setForeground(Tema.TEXTO_ESCURO);
                }

                return this;
            }
        });
    }

    private void voltarAoMenu() {

        MenuPrincipal menu = new MenuPrincipal();

        menu.setLocationRelativeTo(this);

        menu.setVisible(true);

        dispose();
    }

    private void carregarPacientesEDentistas() {

        try {

            for (Paciente paciente : pacienteController.listarPacientes()) {
                cmbPaciente.addItem(paciente);
            }

            for (Dentista dentista : dentistaController.listarDentistas()) {
                cmbDentista.addItem(dentista);
            }

        } catch (SQLException e) {

            mostrarErroBanco("carregar pacientes e dentistas", e);
        }
    }

    private void carregarConsultas() {

        modeloTabela.setRowCount(0);
        consultas.clear();

        try {

            consultas.addAll(consultaController.listarConsultas());

        } catch (SQLException e) {

            mostrarErroBanco("carregar as consultas", e);
        }

        for (Consulta consulta : consultas) {

            modeloTabela.addRow(
                    new Object[] {
                            consulta.getId(),
                            consulta.getPacienteNome(),
                            consulta.getDentistaNome(),
                            consulta.getData(),
                            consulta.getHorario(),
                            consulta.getMotivo(),
                            consulta.getStatus()
                    }
            );
        }
    }

    private void agendarConsulta() {

        Paciente paciente = (Paciente) cmbPaciente.getSelectedItem();
        Dentista dentista = (Dentista) cmbDentista.getSelectedItem();

        String data = txtData.getText().trim();
        String horario = txtHorario.getText().trim();
        String motivo = txtMotivo.getText().trim();

        // Verifica o paciente
        if (paciente == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione o paciente!"
            );

            cmbPaciente.requestFocus();
            return;
        }

        // Verifica o dentista
        if (dentista == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione o dentista responsável!"
            );

            cmbDentista.requestFocus();
            return;
        }

        // Verifica a data
        if (!Datas.dataValida(data)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite uma data válida!"
            );

            txtData.requestFocus();
            return;
        }

        // Verifica o horário
        if (!Datas.horarioValido(horario)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite um horário válido (ex.: 14:30)!"
            );

            txtHorario.requestFocus();
            return;
        }

        Consulta consulta = new Consulta(
                0,
                paciente.getId(),
                paciente.getNome(),
                dentista.getId(),
                dentista.getNome(),
                data,
                horario,
                motivo,
                Consulta.AGENDADA
        );

        try {

            // O dentista não pode ter duas consultas no mesmo horário
            if (consultaController.horarioOcupado(dentista.getId(), data, horario, 0)) {

                JOptionPane.showMessageDialog(
                        this,
                        dentista.getNome() + " já tem uma consulta em " + data + " às " + horario + "!"
                );
                return;
            }

            consultaController.agendarConsulta(consulta);

        } catch (SQLException e) {

            mostrarErroBanco("agendar a consulta", e);
            return;
        }

        carregarConsultas();

        JOptionPane.showMessageDialog(
                this,
                "Consulta agendada com sucesso!"
        );
        limparCampos();
    }

    private void alterarStatus(int linha, String novoStatus) {

        Consulta consulta = consultas.get(linha);

        // Reagendar abre a tela própria, que muda data, horário e status juntos
        if (Consulta.REAGENDADA.equals(novoStatus)) {
            abrirReagendamento(consulta);
            return;
        }

        if (novoStatus.equals(consulta.getStatus())) {
            return;
        }

        if (Consulta.CANCELADA.equals(novoStatus)) {

            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente cancelar a consulta de " + consulta.getPacienteNome() + "?",
                    "Cancelar consulta",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {

            // Ao reativar uma consulta cancelada, o horário pode já ter sido ocupado
            if (Consulta.CANCELADA.equals(consulta.getStatus())
                    && consultaController.horarioOcupado(
                            consulta.getDentistaId(),
                            consulta.getData(),
                            consulta.getHorario(),
                            consulta.getId())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Esse horário já foi ocupado por outra consulta. Reagende esta consulta."
                );
                return;
            }

            consultaController.alterarStatus(consulta.getId(), novoStatus);

        } catch (SQLException e) {

            mostrarErroBanco("alterar o status", e);
            return;
        }

        carregarConsultas();
    }

    private void abrirReagendamento(Consulta consulta) {

        TelaReagendamento tela = new TelaReagendamento(this, consulta);

        tela.setVisible(true);

        if (tela.isReagendado()) {
            carregarConsultas();
        }
    }

    private void mostrarErroBanco(String acao, SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Erro ao " + acao + " no banco de dados:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void limparCampos() {

        cmbPaciente.setSelectedIndex(-1);

        cmbDentista.setSelectedIndex(-1);

        txtData.setValue(null);

        txtHorario.setValue(null);

        txtMotivo.setText("");

        cmbPaciente.requestFocus();
    }
}

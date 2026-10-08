package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.DentistaController;
import model.Dentista;

public class CadastroDentistas extends JFrame {

    private JPanel contentPane;

    private DentistaController dentistaController = new DentistaController();

    private JTextField txtNome;
    private JFormattedTextField txtCro;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JButton btnExcluir;

    public CadastroDentistas() {

        setTitle("OdontoCare - Cadastro de Dentistas");
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
                "Cadastro de<br>dentistas",
                "Gerencie a equipe da clínica."
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

        JLabel lblSecao = new JLabel("DENTISTAS");
        lblSecao.setFont(Tema.espacada(new Font(Tema.FONTE, Font.PLAIN, 13), 0.08f));
        lblSecao.setForeground(Tema.TEXTO_SECUNDARIO);
        lblSecao.setBounds(408, 33, 400, 22);
        contentPane.add(lblSecao);

        JLabel lblTitulo = new JLabel("Novo dentista");
        lblTitulo.setFont(new Font(Tema.FONTE, Font.BOLD, 38));
        lblTitulo.setForeground(Tema.TEXTO_ESCURO);
        lblTitulo.setBounds(406, 55, 660, 56);
        contentPane.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Preencha os dados abaixo e clique em Cadastrar.");
        lblSubtitulo.setFont(new Font(Tema.FONTE, Font.PLAIN, 18));
        lblSubtitulo.setForeground(Tema.TEXTO_SECUNDARIO);
        lblSubtitulo.setBounds(408, 112, 600, 28);
        contentPane.add(lblSubtitulo);

        JPanel painelDados = new JPanel();
        painelDados.setLayout(null);
        painelDados.setBackground(Tema.CARD_CLARO);
        painelDados.setBounds(408, 156, 654, 134);
        contentPane.add(painelDados);

        txtNome = new JTextField();
        // CRO-UF seguido do número (ex.: CRO-SC 12345)
        txtCro = Tema.criarCampoComMascara("CRO-UU #####");

        Tema.adicionarCampo(painelDados, "Nome", txtNome, 24, 16);
        Tema.adicionarCampo(painelDados, "CRO", txtCro, 339, 16);

        btnLimpar = Tema.criarBotao("Limpar", false);
        btnLimpar.setBounds(400, 82, 110, 36);
        painelDados.add(btnLimpar);

        btnCadastrar = Tema.criarBotao("Cadastrar", true);
        btnCadastrar.setBounds(520, 82, 110, 36);
        painelDados.add(btnCadastrar);

        modeloTabela = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "ID",
                        "Nome",
                        "CRO"
                }
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        Tema.estilizarTabela(tabela);

        // O ID fica no modelo (para excluir no banco), mas não aparece na tela
        tabela.removeColumn(tabela.getColumnModel().getColumn(0));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(Tema.BORDA));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBounds(408, 306, 654, 242);
        contentPane.add(scrollPane);

        btnExcluir = Tema.criarBotao("Excluir", false);
        btnExcluir.setBounds(952, 558, 110, 36);
        contentPane.add(btnExcluir);

        // Eventos dos botões
        btnCadastrar.addActionListener(e -> cadastrarDentista());

        btnLimpar.addActionListener(e -> limparCampos());

        btnExcluir.addActionListener(e -> excluirDentista());

        carregarDentistas();

        pack();
        setLocationRelativeTo(null);
    }

    private void voltarAoMenu() {

        MenuPrincipal menu = new MenuPrincipal();

        menu.setLocationRelativeTo(this);

        menu.setVisible(true);

        dispose();
    }

    private void cadastrarDentista() {

        String nome =
                txtNome.getText().trim();

        String cro =
                txtCro.getText().trim();

        // Verifica o nome
        if (nome.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite o nome do dentista!"
            );

            txtNome.requestFocus();
            return;
        }

        // Verifica se o CRO está completo
        if (cro.contains("_")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite o CRO completo!"
            );

            txtCro.requestFocus();
            return;
        }

        Dentista dentista =
                new Dentista(nome, cro);

        try {

            dentistaController.cadastrarDentista(dentista);

        } catch (SQLException e) {

            mostrarErroBanco("cadastrar o dentista", e);
            return;
        }

        adicionarNaTabela(dentista);

        JOptionPane.showMessageDialog(
                this,
                "Dentista cadastrado com sucesso!"
        );
        limparCampos();
    }

    private void carregarDentistas() {

        modeloTabela.setRowCount(0);

        try {

            for (Dentista dentista : dentistaController.listarDentistas()) {
                adicionarNaTabela(dentista);
            }

        } catch (SQLException e) {

            mostrarErroBanco("carregar os dentistas", e);
        }
    }

    private void adicionarNaTabela(Dentista dentista) {

        modeloTabela.addRow(
                new Object[] {
                        dentista.getId(),
                        dentista.getNome(),
                        dentista.getCro()
                }
        );
    }

    private void excluirDentista() {

        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um dentista na tabela para excluir!"
            );
            return;
        }

        int linhaModelo = tabela.convertRowIndexToModel(linhaSelecionada);

        int id = (int) modeloTabela.getValueAt(linhaModelo, 0);
        String nome = (String) modeloTabela.getValueAt(linhaModelo, 1);

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir o dentista " + nome + "?",
                "Excluir dentista",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            dentistaController.excluirDentista(id);

        } catch (SQLException e) {

            mostrarErroBanco("excluir o dentista", e);
            return;
        }

        modeloTabela.removeRow(linhaModelo);

        JOptionPane.showMessageDialog(
                this,
                "Dentista excluído com sucesso!"
        );
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

        txtNome.setText("");

        txtCro.setValue(null);

        txtNome.requestFocus();
    }
}

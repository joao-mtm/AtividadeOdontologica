package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.MaskFormatter;

public class CadastroPacientes extends JFrame {

    private JPanel contentPane;

    private JTextField txtNome;
    private JFormattedTextField txtCpf;
    private JFormattedTextField txtTelefone;
    private JFormattedTextField txtDataNascimento;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JButton btnCadastrar;
    private JButton btnLimpar;

    public CadastroPacientes() {

        setTitle("OdontoCare - Cadastro de Pacientes");
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
                "Cadastro de<br>pacientes",
                "Consulte e mantenha os dados dos pacientes."
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

        JLabel lblSecao = new JLabel("PACIENTES");
        lblSecao.setFont(Tema.espacada(new Font(Tema.FONTE, Font.PLAIN, 13), 0.08f));
        lblSecao.setForeground(Tema.TEXTO_SECUNDARIO);
        lblSecao.setBounds(408, 33, 400, 22);
        contentPane.add(lblSecao);

        JLabel lblTitulo = new JLabel("Novo paciente");
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
        painelDados.setBounds(408, 156, 654, 196);
        contentPane.add(painelDados);

        // Campo normal
        txtNome = new JTextField();

        // Campos com máscara
        txtCpf = criarCampoComMascara("###.###.###-##");
        txtTelefone = criarCampoComMascara("(##) #####-####");
        txtDataNascimento = criarCampoComMascara("##/##/####");

        adicionarCampo(painelDados, "Nome", txtNome, 24, 16);
        adicionarCampo(painelDados, "CPF", txtCpf, 339, 16);
        adicionarCampo(painelDados, "Telefone", txtTelefone, 24, 82);
        adicionarCampo(painelDados, "Data de nascimento", txtDataNascimento, 339, 82);

        btnLimpar = criarBotao("Limpar", false);
        btnLimpar.setBounds(400, 144, 110, 36);
        painelDados.add(btnLimpar);

        btnCadastrar = criarBotao("Cadastrar", true);
        btnCadastrar.setBounds(520, 144, 110, 36);
        painelDados.add(btnCadastrar);

        modeloTabela = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "Nome",
                        "CPF",
                        "Telefone",
                        "Data de Nascimento"
                }
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        estilizarTabela(tabela);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(Tema.BORDA));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBounds(408, 368, 654, 222);
        contentPane.add(scrollPane);

        // Eventos dos botões
        btnCadastrar.addActionListener(e -> cadastrarPaciente());

        btnLimpar.addActionListener(e -> limparCampos());

        pack();
        setLocationRelativeTo(null);
    }

    private void adicionarCampo(
            JPanel painel,
            String texto,
            JComponent campo,
            int x,
            int y
    ) {

        JLabel label = new JLabel(texto);
        label.setFont(new Font(Tema.FONTE, Font.BOLD, 13));
        label.setForeground(Tema.TEXTO_CARD_CLARO);
        label.setBounds(x, y, 291, 20);
        painel.add(label);

        campo.setFont(new Font(Tema.FONTE, Font.PLAIN, 15));
        campo.setForeground(Tema.TEXTO_ESCURO);
        campo.setBackground(Color.WHITE);
        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Tema.BORDA),
                        BorderFactory.createEmptyBorder(4, 10, 4, 10)
                )
        );
        campo.setBounds(x, y + 22, 291, 34);
        painel.add(campo);
    }

    private JButton criarBotao(String texto, boolean principal) {

        Color fundo = principal ? Tema.CARD_ESCURO : Color.WHITE;
        Color fundoHover = principal ? Tema.CARD_ESCURO_HOVER : Tema.CARD_CLARO_HOVER;

        JButton botao = new JButton(texto);
        botao.setFont(new Font(Tema.FONTE, Font.BOLD, 14));
        botao.setForeground(principal ? Color.WHITE : Tema.CARD_ESCURO);
        botao.setBackground(fundo);
        botao.setOpaque(true);
        botao.setFocusPainted(false);
        botao.setBorder(
                principal
                        ? BorderFactory.createEmptyBorder()
                        : BorderFactory.createLineBorder(Tema.CARD_ESCURO)
        );
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(fundoHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(fundo);
            }
        });

        return botao;
    }

    private void estilizarTabela(JTable tabela) {

        tabela.setFont(new Font(Tema.FONTE, Font.PLAIN, 14));
        tabela.setForeground(Tema.TEXTO_ESCURO);
        tabela.setRowHeight(32);
        tabela.setShowVerticalLines(false);
        tabela.setGridColor(Tema.CARD_CLARO);
        tabela.setSelectionBackground(Tema.CARD_CLARO_HOVER);
        tabela.setSelectionForeground(Tema.TEXTO_ESCURO);
        tabela.setFillsViewportHeight(true);

        JTableHeader cabecalho = tabela.getTableHeader();
        cabecalho.setReorderingAllowed(false);
        cabecalho.setPreferredSize(new Dimension(0, 36));
        cabecalho.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable tabela, Object valor, boolean selecionado,
                    boolean foco, int linha, int coluna
            ) {

                super.getTableCellRendererComponent(
                        tabela, valor, selecionado, foco, linha, coluna
                );

                setFont(new Font(Tema.FONTE, Font.BOLD, 13));
                setForeground(Color.WHITE);
                setBackground(Tema.VERDE_ESCURO);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return this;
            }
        });

        // Espaço à esquerda do texto das células
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable tabela, Object valor, boolean selecionado,
                    boolean foco, int linha, int coluna
            ) {

                super.getTableCellRendererComponent(
                        tabela, valor, selecionado, false, linha, coluna
                );

                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
    }

    private JFormattedTextField criarCampoComMascara(String mascara) {

        try {

            MaskFormatter formatter =
                    new MaskFormatter(mascara);

            // Mostra "_" nos espaços que ainda não foram preenchidos
            formatter.setPlaceholderCharacter('_');

            JFormattedTextField campo =
                    new JFormattedTextField(formatter);

            return campo;

        } catch (java.text.ParseException e) {

            throw new RuntimeException(
                    "Erro ao criar máscara: " + mascara,
                    e
            );
        }
    }
    
    private void voltarAoMenu() {

        MenuPrincipal menu = new MenuPrincipal();

        menu.setLocationRelativeTo(this);

        menu.setVisible(true);

        dispose();
    }

    private void cadastrarPaciente() {

        String nome =
                txtNome.getText().trim();

        String cpf =
                txtCpf.getText().trim();

        String telefone =
                txtTelefone.getText().trim();

        String dataNascimento =
                txtDataNascimento.getText().trim();

        // Verifica o nome
        if (nome.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite o nome do paciente!"
            );

            txtNome.requestFocus();
            return;
        }

        // Verifica se o CPF está completo
        if (cpf.contains("_")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite o CPF completo!"
            );

            txtCpf.requestFocus();
            return;
        }

        // Verifica se o telefone está completo
        if (telefone.contains("_")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite o telefone completo!"
            );

            txtTelefone.requestFocus();
            return;
        }

        // Verifica se a data está completa
        if (dataNascimento.contains("_")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite a data de nascimento completa!"
            );

            txtDataNascimento.requestFocus();
            return;
        }

        modeloTabela.addRow(
                new Object[] {
                        nome,
                        cpf,
                        telefone,
                        dataNascimento
                }
        );

        JOptionPane.showMessageDialog(
                this,
                "Paciente cadastrado com sucesso!"
        );
        limparCampos();
    }

    private void limparCampos() {

        txtNome.setText("");

        txtCpf.setValue(null);

        txtTelefone.setValue(null);

        txtDataNascimento.setValue(null);

        txtNome.requestFocus();
    }
}

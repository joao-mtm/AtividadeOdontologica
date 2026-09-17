package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CadastroPacientes extends JFrame {

    private JPanel contentPane;

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private JTextField txtDataNascimento;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JButton btnCadastrar;
    private JButton btnLimpar;

    public CadastroPacientes() {

        setTitle("Sistema Odontológico - Cadastro de Pacientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 330);

        contentPane = new JPanel();
        setContentPane(contentPane);

        JPanel painelDados = new JPanel();

        painelDados.setBorder(
                new TitledBorder(null,
                        "Dados do paciente",
                        TitledBorder.LEADING,
                        TitledBorder.TOP,
                        null,
                        null)
        );

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCpf = new JLabel("CPF:");
        JLabel lblTelefone = new JLabel("Telefone:");
        JLabel lblDataNascimento =
                new JLabel("Data de Nascimento:");

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtTelefone = new JTextField();
        txtDataNascimento = new JTextField();

        btnCadastrar = new JButton("Cadastrar");
        btnLimpar = new JButton("Limpar");

        javax.swing.GroupLayout gl_painelDados =
                new javax.swing.GroupLayout(painelDados);

        painelDados.setLayout(gl_painelDados);

        gl_painelDados.setHorizontalGroup(
                gl_painelDados.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING
                )
                .addGroup(
                        gl_painelDados.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                                gl_painelDados.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING
                                )
                                .addComponent(lblNome)
                                .addComponent(lblCpf)
                                .addComponent(lblTelefone)
                                .addComponent(lblDataNascimento)
                        )
                        .addGap(18)
                        .addGroup(
                                gl_painelDados.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING
                                )
                                .addComponent(txtNome)
                                .addComponent(txtCpf)
                                .addComponent(txtTelefone)
                                .addComponent(txtDataNascimento)
                                .addGroup(
                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                        gl_painelDados.createSequentialGroup()
                                        .addComponent(btnCadastrar)
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                        )
                                        .addComponent(btnLimpar)
                                )
                        )
                        .addContainerGap()
                )
        );

        gl_painelDados.setVerticalGroup(
                gl_painelDados.createSequentialGroup()
                .addContainerGap()
                .addGroup(
                        gl_painelDados.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(lblNome)
                        .addComponent(txtNome,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED
                )
                .addGroup(
                        gl_painelDados.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(lblCpf)
                        .addComponent(txtCpf,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED
                )
                .addGroup(
                        gl_painelDados.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(lblTelefone)
                        .addComponent(txtTelefone,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED
                )
                .addGroup(
                        gl_painelDados.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(lblDataNascimento)
                        .addComponent(txtDataNascimento,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED
                )
                .addGroup(
                        gl_painelDados.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(btnLimpar)
                        .addComponent(btnCadastrar)
                )
                .addContainerGap()
        );

        modeloTabela = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "Nome",
                        "CPF",
                        "Telefone",
                        "Data de Nascimento"
                }
        );

        tabela = new JTable(modeloTabela);

        JScrollPane scrollPane = new JScrollPane(tabela);

        javax.swing.GroupLayout gl_contentPane =
                new javax.swing.GroupLayout(contentPane);

        contentPane.setLayout(gl_contentPane);

        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING
                )
                .addComponent(painelDados,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                .addComponent(scrollPane,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        580,
                        Short.MAX_VALUE)
        );

        gl_contentPane.setVerticalGroup(
                gl_contentPane.createSequentialGroup()
                .addComponent(painelDados,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED
                )
                .addComponent(scrollPane,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        150,
                        Short.MAX_VALUE)
        );

        btnCadastrar.addActionListener(e -> cadastrarPaciente());

        btnLimpar.addActionListener(e -> limparCampos());
    }

    private void cadastrarPaciente() {

        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String dataNascimento =
                txtDataNascimento.getText().trim();

        if (nome.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Digite o nome do paciente!"
            );
            return;
        }

        if (cpf.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Digite o CPF do paciente!"
            );
            return;
        }

        if (telefone.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Digite o telefone do paciente!"
            );
            return;
        }

        if (dataNascimento.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Digite a data de nascimento!"
            );
            return;
        }

        modeloTabela.addRow(new Object[] {
                nome,
                cpf,
                telefone,
                dataNascimento
        });

        limparCampos();
    }

    private void limparCampos() {

        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtDataNascimento.setText("");

        txtNome.requestFocus();
    }
}

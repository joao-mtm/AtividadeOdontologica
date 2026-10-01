package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame {

    private JPanel contentPane;

    private JPanel cardConsultas;
    private JPanel cardPacientes;
    private JPanel cardDentistas;
    private JPanel cardTratamentos;

    public MenuPrincipal() {

        setTitle("OdontoCare - Clínica Odontológica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Tema.FUNDO);
        contentPane.setPreferredSize(new java.awt.Dimension(Tema.LARGURA_TELA, Tema.ALTURA_TELA));
        setContentPane(contentPane);

        JPanel painelLateral = Tema.criarPainelLateral(
                "Clínica<br>odontológica",
                "Tudo o que você precisa para cuidar dos seus pacientes."
        );
        contentPane.add(painelLateral);

        // Rodapé lateral
        JLabel lblMenu = new JLabel("Menu principal");
        lblMenu.setFont(new Font(Tema.FONTE, Font.PLAIN, 14));
        lblMenu.setForeground(Color.WHITE);
        lblMenu.setBounds(30, 560, 150, 22);
        painelLateral.add(lblMenu);

        JLabel lblSair = new JLabel("Sair");
        lblSair.setFont(new Font(Tema.FONTE, Font.PLAIN, 14));
        lblSair.setForeground(Tema.TEXTO_LATERAL_APAGADO);
        lblSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSair.setBounds(300, 560, 50, 22);
        lblSair.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sairSistema();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblSair.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblSair.setForeground(Tema.TEXTO_LATERAL_APAGADO);
            }
        });
        painelLateral.add(lblSair);

        JLabel lblBemVindo = new JLabel("BEM-VINDO À ODONTOCARE");
        lblBemVindo.setFont(Tema.espacada(new Font(Tema.FONTE, Font.PLAIN, 13), 0.08f));
        lblBemVindo.setForeground(Tema.TEXTO_SECUNDARIO);
        lblBemVindo.setBounds(408, 33, 400, 22);
        contentPane.add(lblBemVindo);

        JLabel lblTitulo = new JLabel("Acesse uma área para começar");
        lblTitulo.setFont(new Font(Tema.FONTE, Font.BOLD, 38));
        lblTitulo.setForeground(Tema.TEXTO_ESCURO);
        lblTitulo.setBounds(406, 55, 660, 56);
        contentPane.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Escolha uma opção abaixo.");
        lblSubtitulo.setFont(new Font(Tema.FONTE, Font.PLAIN, 18));
        lblSubtitulo.setForeground(Tema.TEXTO_SECUNDARIO);
        lblSubtitulo.setBounds(408, 122, 400, 28);
        contentPane.add(lblSubtitulo);
        
        cardConsultas = criarCard(
                "Agendamentos de consultas",
                "Organize e acompanhe as consultas.",
                true,
                true
        );
        cardConsultas.setBounds(408, 178, 320, 174);
        contentPane.add(cardConsultas);

        cardPacientes = criarCard(
                "Cadastro de pacientes",
                "Consulte e mantenha os dados dos pacientes.",
                false,
                true
        );
        cardPacientes.setBounds(742, 178, 320, 174);
        contentPane.add(cardPacientes);

        cardDentistas = criarCard(
                "Cadastro de dentistas",
                "Gerencie a equipe da clínica.",
                false,
                false
        );
        cardDentistas.setBounds(408, 366, 320, 116);
        contentPane.add(cardDentistas);

        cardTratamentos = criarCard(
                "Tratamentos",
                "Acesse os tratamentos oferecidos.",
                true,
                false
        );
        cardTratamentos.setBounds(742, 366, 320, 116);
        contentPane.add(cardTratamentos);

        aoClicar(cardConsultas, () -> mensagemEmDesenvolvimento("Consultas"));
        aoClicar(cardPacientes, this::abrirPacientes);
        aoClicar(cardDentistas, () -> mensagemEmDesenvolvimento("Dentistas"));
        aoClicar(cardTratamentos, () -> mensagemEmDesenvolvimento("Tratamentos"));

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel criarCard(
            String titulo,
            String descricao,
            boolean escuro,
            boolean duasLinhas
    ) {

        Color fundo = escuro ? Tema.CARD_ESCURO : Tema.CARD_CLARO;
        Color fundoHover = escuro ? Tema.CARD_ESCURO_HOVER : Tema.CARD_CLARO_HOVER;
        Color corTitulo = escuro ? Color.WHITE : Tema.TEXTO_CARD_CLARO;
        Color corDescricao = escuro ? Color.WHITE : Tema.TEXTO_CARD_CLARO;

        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(fundo);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblTitulo = new JLabel(
                "<html>" + titulo + "</html>"
        );
        lblTitulo.setFont(new Font(Tema.FONTE, Font.PLAIN, 26));
        lblTitulo.setForeground(corTitulo);
        lblTitulo.setVerticalAlignment(JLabel.TOP);
        lblTitulo.setBounds(24, 22, 240, 80);
        card.add(lblTitulo);

        JLabel lblDescricao = new JLabel(
                "<html>" + descricao + "</html>"
        );
        lblDescricao.setFont(new Font(Tema.FONTE, escuro ? Font.BOLD : Font.PLAIN, 14));
        lblDescricao.setForeground(corDescricao);
        lblDescricao.setVerticalAlignment(JLabel.TOP);
        card.add(lblDescricao);

        // Títulos em duas linhas empurram a descrição para baixo
        lblDescricao.setBounds(24, duasLinhas ? 106 : 66, 270, 44);
        if (!duasLinhas) {
            lblTitulo.setBounds(24, 22, 290, 40);
        }

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(fundoHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(fundo);
            }
        });

        return card;
    }

    private void aoClicar(JPanel card, Runnable acao) {

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                acao.run();
            }
        });
    }

    private void abrirPacientes() {

        CadastroPacientes tela =
                new CadastroPacientes();

        tela.setLocationRelativeTo(this);

        tela.setVisible(true);

        this.setVisible(false);
    }

    private void mensagemEmDesenvolvimento(
            String tela
    ) {

        JOptionPane.showMessageDialog(
                this,
                "A tela de " + tela +
                " ainda está em desenvolvimento.",
                "OdontoCare",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void sairSistema() {

        int resposta =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente sair do sistema?",
                        "Sair",
                        JOptionPane.YES_NO_OPTION
                );

        if (resposta == JOptionPane.YES_OPTION) {

            System.exit(0);
        }
    }
}

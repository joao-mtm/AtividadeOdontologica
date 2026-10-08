package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.text.MaskFormatter;

// ============================================================
// CORES E FONTES COMPARTILHADAS ENTRE AS TELAS
// ============================================================

public class Tema {

    public static final Color VERDE_ESCURO = new Color(22, 48, 32);
    public static final Color FUNDO = new Color(244, 248, 242);
    public static final Color CARD_ESCURO = new Color(31, 122, 61);
    public static final Color CARD_ESCURO_HOVER = new Color(25, 104, 51);
    public static final Color CARD_CLARO = new Color(226, 237, 224);
    public static final Color CARD_CLARO_HOVER = new Color(212, 228, 209);
    public static final Color BORDA = new Color(196, 214, 198);
    public static final Color TEXTO_ESCURO = new Color(20, 38, 26);
    public static final Color TEXTO_CARD_CLARO = new Color(52, 70, 56);
    public static final Color TEXTO_SECUNDARIO = new Color(70, 84, 74);
    public static final Color TEXTO_LATERAL_APAGADO = new Color(180, 205, 186);
    public static final Color TEXTO_ALERTA = new Color(170, 45, 45);

    public static final String FONTE = "Segoe UI";

    public static final int LARGURA_TELA = 1100;
    public static final int ALTURA_TELA = 620;
    public static final int LARGURA_LATERAL = 366;

    private Tema() {
    }

    public static Font espacada(Font fonte, float espacamento) {

        return fonte.deriveFont(
                Map.of(TextAttribute.TRACKING, espacamento)
        );
    }

    // ============================================================
    // PAINEL LATERAL VERDE COM LOGO, TÍTULO E DESCRIÇÃO
    // ============================================================

    public static JPanel criarPainelLateral(
            String tituloHtml,
            String descricao
    ) {

        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(null);
        painelLateral.setBackground(VERDE_ESCURO);
        painelLateral.setBounds(0, 0, LARGURA_LATERAL, ALTURA_TELA);

        // Logo (selo branco com a imagem da clínica)
        JComponent iconeLogo = criarSeloLogo(44);
        iconeLogo.setBounds(24, 23, 44, 44);
        painelLateral.add(iconeLogo);

        JLabel lblLogo = new JLabel("ODONTOCARE");
        lblLogo.setFont(espacada(new Font(FONTE, Font.BOLD, 14), 0.1f));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBounds(80, 33, 200, 24);
        painelLateral.add(lblLogo);

        // Título lateral
        JLabel lblTitulo = new JLabel("<html>" + tituloHtml + "</html>");
        lblTitulo.setFont(new Font(FONTE, Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 225, 320, 100);
        painelLateral.add(lblTitulo);

        JLabel lblDescricao = new JLabel("<html>" + descricao + "</html>");
        lblDescricao.setFont(new Font(FONTE, Font.PLAIN, 18));
        lblDescricao.setForeground(Color.WHITE);
        lblDescricao.setBounds(30, 330, 290, 72);
        painelLateral.add(lblDescricao);

        return painelLateral;
    }

    // ============================================================
    // CAMPOS, BOTÕES E TABELAS NO PADRÃO DAS TELAS DE CADASTRO
    // ============================================================

    public static void adicionarCampo(
            JPanel painel,
            String texto,
            JComponent campo,
            int x,
            int y
    ) {

        adicionarCampo(painel, texto, campo, x, y, 291);
    }

    public static void adicionarCampo(
            JPanel painel,
            String texto,
            JComponent campo,
            int x,
            int y,
            int largura
    ) {

        JLabel label = new JLabel(texto);
        label.setFont(new Font(Tema.FONTE, Font.BOLD, 13));
        label.setForeground(Tema.TEXTO_CARD_CLARO);
        label.setBounds(x, y, largura, 20);
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
        campo.setBounds(x, y + 22, largura, 34);
        painel.add(campo);
    }

    public static JButton criarBotao(String texto, boolean principal) {

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

    public static void estilizarTabela(JTable tabela) {

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

    public static JFormattedTextField criarCampoComMascara(String mascara) {

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

    // ============================================================
    // LOGO DA CLÍNICA
    // ============================================================

    private static final String CAMINHO_LOGO = "Imagens/logo.png";

    // A imagem é preparada uma vez só e reaproveitada em todas as telas
    private static BufferedImage logoRecortada;

    public static JComponent criarSeloLogo(int tamanho) {

        BufferedImage logo = carregarLogo();

        // Deixa uma margem entre a logo e a borda do selo
        int espacoInterno = tamanho - 8;
        Image logoReduzida = null;

        if (logo != null) {

            double escala = Math.min(
                    (double) espacoInterno / logo.getWidth(),
                    (double) espacoInterno / logo.getHeight()
            );

            logoReduzida = logo.getScaledInstance(
                    (int) Math.round(logo.getWidth() * escala),
                    (int) Math.round(logo.getHeight() * escala),
                    Image.SCALE_SMOOTH
            );
        }

        Image imagem = logoReduzida;

        return new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                if (imagem != null) {
                    int largura = imagem.getWidth(null);
                    int altura = imagem.getHeight(null);
                    g2.drawImage(imagem, (getWidth() - largura) / 2, (getHeight() - altura) / 2, null);
                }

                g2.dispose();
            }
        };
    }

    private static BufferedImage carregarLogo() {

        if (logoRecortada == null) {

            try {

                BufferedImage original = ImageIO.read(new File(CAMINHO_LOGO));

                if (original != null) {
                    logoRecortada = recortarFundo(original);
                }

            } catch (IOException e) {

                // Sem a imagem, o selo aparece só branco
                System.err.println("Não foi possível carregar " + CAMINHO_LOGO);
            }
        }

        return logoRecortada;
    }

    // Corta o fundo claro em volta do desenho e deixa ele transparente
    private static BufferedImage recortarFundo(BufferedImage original) {

        int fundo = original.getRGB(0, 0);
        int largura = original.getWidth();
        int altura = original.getHeight();

        int minX = largura;
        int minY = altura;
        int maxX = -1;
        int maxY = -1;

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {

                // Pixels bem diferentes do fundo fazem parte do desenho
                if (diferencaDeCor(original.getRGB(x, y), fundo) > 40) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }

        if (maxX < 0) {
            return original;
        }

        BufferedImage recortada = new BufferedImage(
                maxX - minX + 1,
                maxY - minY + 1,
                BufferedImage.TYPE_INT_ARGB
        );

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {

                int cor = original.getRGB(x, y);

                // Quanto mais parecido com o fundo, mais transparente
                int alfa = Math.min(255, diferencaDeCor(cor, fundo) * 6);

                recortada.setRGB(x - minX, y - minY, (alfa << 24) | (cor & 0x00FFFFFF));
            }
        }

        return recortada;
    }

    private static int diferencaDeCor(int cor1, int cor2) {

        int r = Math.abs(((cor1 >> 16) & 0xFF) - ((cor2 >> 16) & 0xFF));
        int g = Math.abs(((cor1 >> 8) & 0xFF) - ((cor2 >> 8) & 0xFF));
        int b = Math.abs((cor1 & 0xFF) - (cor2 & 0xFF));

        return Math.max(r, Math.max(g, b));
    }
}

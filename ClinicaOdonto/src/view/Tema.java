package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

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

        // Logo
        JPanel iconeLogo = new JPanel();
        iconeLogo.setBackground(Color.WHITE);
        iconeLogo.setBounds(14, 34, 14, 22);
        painelLateral.add(iconeLogo);

        JLabel lblLogo = new JLabel("ODONTOCARE");
        lblLogo.setFont(espacada(new Font(FONTE, Font.BOLD, 14), 0.1f));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBounds(32, 33, 200, 24);
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
}

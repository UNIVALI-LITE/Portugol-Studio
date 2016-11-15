package br.univali.ps.ui.inspetor;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.ps.ui.ColorController;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.Locale;
import javax.swing.JComponent;

/**
 *
 * @author elieser
 */
abstract class RenderizadorBase extends JComponent {

    protected static final Color COR_GRADE = new Color(1, 1, 1, 0.35f);
    protected static final Color COR_TEXTO = Color.GRAY;
    protected static final Color COR_NOME = Color.LIGHT_GRAY;
    protected static final Color COR_TEXTO_DESTACADO = Color.WHITE;
    protected static final Color COR_DO_CABECALHO_DESTACADO = COR_TEXTO_DESTACADO;
    protected static final Color COR_DO_FUNDO_EM_DESTAQUE = ColorController.FUNDO_ESCURO;// new Color(1, 0, 0, 0.3f);//vermelho claro
    protected static final Stroke TRACEJADO = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4, 2, 3, 2, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1}, 0);

    static final String STRING_VAZIA = "    ";//usada para representar posições em branco dos vetores e matrizes

    protected static final int MARGEM = 5;

    protected ItemDaLista itemDaLista;

    protected static Font FONTE_NORMAL = Font.decode(Font.SANS_SERIF + "-12");
    protected static Font FONTE_DESTAQUE;
    protected static Font FONTE_CABECALHO;
    protected static Font FONTE_CABECALHO_DESTAQUE;

    public RenderizadorBase() {
        super();
        setTamanhoDaFonte(12f);
    }

    static void setTamanhoDaFonte(float tamanho) {
        assert (FONTE_NORMAL != null);
        FONTE_NORMAL = FONTE_NORMAL.deriveFont(tamanho);
        FONTE_DESTAQUE = FONTE_NORMAL.deriveFont(Font.BOLD);

        if (FONTE_DESTAQUE == null) {
            FONTE_DESTAQUE = FONTE_NORMAL;
        }

        FONTE_CABECALHO = FONTE_NORMAL.deriveFont(12f);

        if (FONTE_CABECALHO == null) {
            FONTE_CABECALHO = FONTE_NORMAL;
        }

        FONTE_CABECALHO_DESTAQUE = FONTE_CABECALHO.deriveFont(Font.BOLD);

        if (FONTE_CABECALHO_DESTAQUE == null) {
            FONTE_CABECALHO_DESTAQUE = FONTE_NORMAL;
        }
    }

    /**
     * @param g
     * @param topo a posição onde o nome será desenha no eixo y
     * @return a largura da string do nome para que ela possa ser usada como
     * margem para o resto do desenho
     */
    protected int desenhaNome(Graphics g, int x, int topo) {
        g.setFont(itemDaLista.podeDesenharDestaque() ? FONTE_DESTAQUE : FONTE_NORMAL);
        String stringDoNome = itemDaLista.getNome();
        FontMetrics metrics = g.getFontMetrics();
        int larguraDoNome = metrics.stringWidth(stringDoNome);

        g.drawString(stringDoNome, x, topo + metrics.getAscent());
        return larguraDoNome;
    }

    void atualizaDimensoes() {
        setPreferredSize(new Dimension(100, getAlturaPreferida()));
    }

    void setItemDaLista(ItemDaLista itemDaLista) {
        this.itemDaLista = itemDaLista;
        atualizaDimensoes();
    }

    protected abstract int getAlturaPreferida();

    protected String processaStringDoValor(Object valor) {
        if (valor != null) {
            if (itemDaLista.getTipo() == TipoDado.LOGICO) {
                return (Boolean) valor ? "verdadeiro" : "falso";
            } else if (itemDaLista.getTipo() == TipoDado.REAL) {
                //usando Locale.English para usar o ponto ao invés da vírgula como separador das casas decimais
                return String.format(Locale.ENGLISH, "%.1f", valor);
            } else if (itemDaLista.getTipo() == TipoDado.CADEIA) {
                String string = valor.toString();
                if (string.length() > 7) {
                    return string.substring(0, 7) + "...";//retorna somente os primeiros 7 caracteres
                }
                return string;
            }
            return valor.toString();
        }
        return STRING_VAZIA;
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        if (isFocusOwner()) {
            gr.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

    }
}

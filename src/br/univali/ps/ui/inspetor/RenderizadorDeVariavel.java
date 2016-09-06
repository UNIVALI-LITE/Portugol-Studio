package br.univali.ps.ui.inspetor;

import static br.univali.ps.ui.inspetor.RenderizadorBase.FONTE_NORMAL;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;

/**
 *
 * @author elieser
 */
class RenderizadorDeVariavel extends RenderizadorBase {


    @Override
    protected int getAlturaPreferida() {
        FontMetrics metrics = getFontMetrics(FONTE_NORMAL);
        return metrics.getHeight();
    }

    @Override
    void setItemDaLista(ItemDaLista itemDaLista) {
        if (!(itemDaLista instanceof ItemDaListaParaVariavel)) {
            throw new IllegalArgumentException("Item da lista não é um item de variável!");
        }
        super.setItemDaLista(itemDaLista);
    }

    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        super.paintComponent(g);
        if (itemDaLista == null) {
            return;
        }
        Icon icone = itemDaLista.getIcone();
        icone.paintIcon(this, g, 0, getHeight() / 2 - icone.getIconHeight() / 2);
        int larguraDoNome = desenhaNome(g, icone.getIconWidth() + MARGEM_HORIZONTAL, 0);

        //desenha valor
        String stringDoValor = processaStringDoValor(((ItemDaListaParaVariavel) itemDaLista).getValor());

        g.setFont((itemDaLista.podeDesenharDestaque()) ? FONTE_DESTAQUE : FONTE_NORMAL);
        FontMetrics metrics = g.getFontMetrics();
        int larguraDoValor = metrics.stringWidth(stringDoValor);
        int larguraDaCaixa = MARGEM_HORIZONTAL + larguraDoValor + MARGEM_HORIZONTAL;

        //pinta fundo de vermelho para destacar
        if (itemDaLista.podeDesenharDestaque()) {
            g.setColor(COR_DO_FUNDO_EM_DESTAQUE);
            g.fillRect(icone.getIconWidth() + larguraDoNome + MARGEM_HORIZONTAL + 1, 0, larguraDaCaixa - 1, getHeight() - 1);

            //desenha caixa do valor
            g.setColor(COR_DA_GRADE);
            g.drawRect(icone.getIconWidth() + larguraDoNome + MARGEM_HORIZONTAL, 0, larguraDaCaixa, getHeight() - 1);
        }

        //desenha valor
        g.setColor(Color.BLACK);
        g.drawString(stringDoValor, icone.getIconWidth() + larguraDoNome + MARGEM_HORIZONTAL + MARGEM_HORIZONTAL, metrics.getAscent());

    }

}

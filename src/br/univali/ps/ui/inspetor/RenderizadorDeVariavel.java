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
        
        int x = MARGEM_HORIZONTAL; // x inicial
        
        icone.paintIcon(this, g, x, getHeight() / 2 - icone.getIconHeight() / 2);
        
        x += icone.getIconWidth() + MARGEM_HORIZONTAL;
        g.setColor(corTexto);
        int larguraNome = desenhaNome(g, x, 0);

        String stringDoValor = processaStringDoValor(((ItemDaListaParaVariavel) itemDaLista).getValor());

        g.setFont((itemDaLista.podeDesenharDestaque()) ? FONTE_DESTAQUE : FONTE_NORMAL);
        FontMetrics metrics = g.getFontMetrics();
        int larguraValor = metrics.stringWidth(stringDoValor);
        int larguraCaixa = MARGEM_HORIZONTAL + larguraValor + MARGEM_HORIZONTAL;

        int xCaixaValor = x + larguraCaixa;// + MARGEM_HORIZONTAL * 2;
        
        //pinta fundo de vermelho para destacar
        if (itemDaLista.podeDesenharDestaque()) {
            g.setColor(COR_DO_FUNDO_EM_DESTAQUE);
            g.fillRect(xCaixaValor + 1, 0, larguraCaixa - 1, getHeight() - 1);

            //desenha caixa do valor
            g.setColor(corGrade);
            g.drawRect(xCaixaValor, 0, larguraCaixa, getHeight() - 1);
        }

        //desenha valor
        g.setColor(corTexto);
        g.drawString(stringDoValor, xCaixaValor + MARGEM_HORIZONTAL, metrics.getAscent());

    }

}

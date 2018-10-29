package br.univali.ps.ui.inspetor;

import java.awt.Color;
import java.awt.Font;
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
        Font fonteNormal = getFonte(TipoFonte.NORMAL);

        FontMetrics metrics = getFontMetrics(fonteNormal);
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
        
        Font fonteNormal = getFonte(TipoFonte.NORMAL);
        Font fonteDestaque = getFonte(TipoFonte.DESTAQUE);
        
        Icon icone = itemDaLista.getIcone();
        
        int x = MARGEM; // x inicial
        
        icone.paintIcon(this, g, x, getHeight() / 2 - icone.getIconHeight() / 2);
        
        boolean podeDestacar = itemDaLista.podeDesenharDestaque();
        
        x += icone.getIconWidth() + MARGEM;
        
        Color corTexto = itemDaLista.estaNoEscopoAtual() ? (podeDestacar ? COR_TEXTO_DESTACADO : COR_NOME) : COR_FORA_ESCOPO;
        g.setColor(corTexto);
        int larguraNome = desenhaNome(g, x, 0);

        String stringDoValor = processaStringDoValor(((ItemDaListaParaVariavel) itemDaLista).getValor());

        g.setFont(itemDaLista.estaNoEscopoAtual() ? fonteDestaque : fonteNormal);
        FontMetrics metrics = g.getFontMetrics();
        int larguraValor = metrics.stringWidth(stringDoValor);
        int larguraCaixa = MARGEM + larguraValor + MARGEM;

        int xCaixaValor = x + larguraNome + MARGEM;

        if (podeDestacar) 
        {
            g.setColor(COR_DO_FUNDO_EM_DESTAQUE);
            g.fillRect(xCaixaValor + 1, 0, larguraCaixa - 1, getHeight() - 1);

            //desenha caixa do valor
            g.setColor(COR_GRADE);
            g.drawRect(xCaixaValor, 0, larguraCaixa, getHeight() - 1);
        }

        //desenha valor
        Color corValor = itemDaLista.estaNoEscopoAtual() ? (podeDestacar ? COR_TEXTO_DESTACADO : COR_TEXTO) : COR_FORA_ESCOPO;
        g.setColor(corValor);
        g.drawString(stringDoValor, xCaixaValor + MARGEM, metrics.getAscent());
    }

}

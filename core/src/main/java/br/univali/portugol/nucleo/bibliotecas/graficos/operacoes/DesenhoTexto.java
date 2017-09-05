package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class DesenhoTexto extends OperacaoDesenho
{    
    private String texto;    
    private int altura;

    public DesenhoTexto(CacheOperacoesGraficas<DesenhoTexto> cache)
    {
        super(cache);
    }

    @Override
    public void liberarRecursos()
    {
        texto = null;
    }
    
    void setParametros(int x, int y, String texto, FontMetrics dimensoesFonte, double rotacao, int opacidade)
    {
        this.x = x;
        this.y = y;
        this.texto = texto;
        this.rotacao = rotacao;
        this.opacidade = opacidade;
        this.altura = dimensoesFonte.getAscent() - dimensoesFonte.getDescent() + dimensoesFonte.getLeading() + 1;
        this.centroX = x + (dimensoesFonte.stringWidth(texto) >> 1);
        this.centroY = y + (altura >> 1);
    }

    @Override
    public void desenhar(Graphics2D graficos)
    {
        graficos.drawString(texto, x, y + altura);
    }
}

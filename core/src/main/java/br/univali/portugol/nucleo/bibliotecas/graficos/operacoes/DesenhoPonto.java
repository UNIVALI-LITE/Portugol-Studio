package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.Graphics2D;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class DesenhoPonto extends OperacaoDesenho
{
    public DesenhoPonto(CacheOperacoesGraficas<DesenhoPonto> cache)
    {
        super(cache);
    }

    void setParametros(int x, int y, int opacidade)
    {
        this.x = x;
        this.y = y;
        this.opacidade = opacidade; 
    }

    @Override
    public void desenhar(Graphics2D graficos)
    {
        graficos.drawLine(x, y, x, y);
    }
}

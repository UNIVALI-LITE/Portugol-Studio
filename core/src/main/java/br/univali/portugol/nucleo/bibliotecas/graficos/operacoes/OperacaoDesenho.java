package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.Graphics2D;

/**
 *
 * @author Elieser
 */
public abstract class OperacaoDesenho extends OperacaoGrafica
{
    protected double rotacao;
    protected int x;
    protected int y;
    protected int centroX;
    protected int centroY;
    protected int opacidade;

    public OperacaoDesenho(CacheOperacoesGraficas cache)
    {
        super(cache);
    }

    @Override
    public void executar(Graphics2D graficos)
    {
        boolean precisaRotacionar = rotacao != 0;
        
        if (precisaRotacionar)
        {
            graficos.rotate(rotacao, centroX, centroY);
        }
        desenhar(graficos);
        
        if (precisaRotacionar)
        {
            graficos.rotate(-rotacao, centroX, centroY);
        }
    }

}

package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.SuperficieDesenho;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class OperacaoDefinirCor extends OperacaoGrafica
{
    private SuperficieDesenho superficieDesenho;
    private Color cor;

    public OperacaoDefinirCor(CacheOperacoesGraficas<OperacaoDefinirCor> cache)
    {
        super(cache);
    }
    
    void setParametros(SuperficieDesenho superficieDesenho, Color cor)
    {
        this.superficieDesenho = superficieDesenho;
        this.cor = cor;
    }

    @Override
    public void desenhar(Graphics2D graficos)
    {
        superficieDesenho.removerGradiente();
        graficos.setColor(cor);
    }
}

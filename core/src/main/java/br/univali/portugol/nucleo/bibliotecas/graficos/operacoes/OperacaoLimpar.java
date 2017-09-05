package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.Graphics2D;

/**
 *
 * @author Luiz Fernando Noschhang
 */
public final class OperacaoLimpar extends OperacaoGrafica
{
    private int largura;
    private int altura;
    
    public OperacaoLimpar(CacheOperacoesGraficas<OperacaoLimpar> cache)
    {
        super(cache);
    }
    
    void setParametros(int largura, int altura)
    {
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public void desenhar(Graphics2D graficos)
    {
        graficos.fillRect(0, 0, largura, altura);
    }
}

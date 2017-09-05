package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDesenhoElipse;
import java.awt.Graphics2D;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class DesenhoElipse extends OperacaoDesenho
{
    private boolean preencher;
    private int largura;
    private int altura;
    
    public DesenhoElipse(CacheOperacoesDesenhoElipse cache)
    {
        super(cache);
    }

    void setParametros(int x, int y, int largura, int altura, boolean preencher, double rotacao, int opacidade)
    {
        this.x = x;
        this.y = y;
        this.preencher = preencher;
        this.rotacao = rotacao;
        this.opacidade = opacidade;
        this.largura = largura;
        this.altura = altura;
        this.centroX = x + (largura >> 1);
        this.centroY = y + (altura >> 1);
    }
    
    @Override
    public void desenhar(Graphics2D graficos)
    {
        if (preencher)
        {
            graficos.fillOval(x, y, largura, altura);
        }
        else
        {
            graficos.drawOval(x, y, largura, altura);
        }
    }
}

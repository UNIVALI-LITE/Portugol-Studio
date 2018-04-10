package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.SuperficieDesenho;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.Graphics2D;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class DesenhoLinha extends OperacaoDesenho
{
    private int x2;
    private int y2;
    private SuperficieDesenho superficieDesenho;
    public DesenhoLinha(CacheOperacoesGraficas<DesenhoLinha> cache)
    {
        super(cache);
    }

    void setParametros(SuperficieDesenho superficieDesenho,int x, int y, int x2, int y2, double rotacao, int opacidade)
    {
        this.superficieDesenho = superficieDesenho;
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.rotacao = rotacao;
        this.opacidade = opacidade;
        this.centroX = x + (Math.abs(x - x2) >> 1);
        this.centroY = y + (Math.abs(y - y2) >> 1);
    }

    @Override
    public void desenhar(Graphics2D graficos)
    {
        SuperficieDesenho.InformacaoGradiente gradientInfo = superficieDesenho.getInformacaoGradiente();
        GradientUtils.doGradient(graficos, gradientInfo, this, this.y2-this.y, this.x2-this.x);
        
        graficos.drawLine(x, y, x2, y2);
    }
}

package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.SuperficieDesenho;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class DesenhoPoligono extends OperacaoDesenho
{
    private boolean preencher;
    private Polygon poligono;
    private SuperficieDesenho superficieDesenho;
    private int xMaximo = Integer.MIN_VALUE;
    private int xMinimo = Integer.MAX_VALUE;
    private int yMaximo = Integer.MIN_VALUE;
    private int yMinimo = Integer.MAX_VALUE;
    
    public DesenhoPoligono(CacheOperacoesGraficas<DesenhoPoligono> cache)
    {
        super(cache);
    }

    void setParametros(SuperficieDesenho superficieDesenho, int[][] pontos, boolean preencher, double rotacao, int opacidade)
    {
        this.superficieDesenho = superficieDesenho;
        this.rotacao = rotacao;
        this.opacidade = opacidade;
        this.preencher = preencher;

        this.centroX = calculaCentroX(pontos);
        this.centroY = calculaCentroY(pontos);
        this.poligono = criarPoligono(pontos);
        this.x = xMinimo;
        this.y = yMinimo;
    }

    private Polygon criarPoligono(int[][] pontos)
    {
        Polygon novoPoligono = new Polygon();

        for (int i = 0; i < pontos.length; i++)
        {
            novoPoligono.addPoint(pontos[i][0], pontos[i][1]);
        }

        return novoPoligono;
    }

    private int calculaCentroX(int[][] pontos)
    {
        int lx;
        xMaximo = Integer.MIN_VALUE;
        xMinimo = Integer.MAX_VALUE;

        for (int i = 0; i < pontos.length; i++)
        {
            lx = pontos[i][0];

            if (rotacao != 0.0)
            {
                if (lx > xMaximo)
                {
                    xMaximo = lx;
                }
                else if (lx < xMinimo)
                {
                    xMinimo = lx;
                }
            }
        }

        return xMinimo + (Math.abs(xMaximo - xMinimo) >> 1);
    }

    private int calculaCentroY(int[][] pontos)
    {
        int ly;
        yMaximo = Integer.MIN_VALUE;
        yMinimo = Integer.MAX_VALUE;

        for (int i = 0; i < pontos.length; i++)
        {
            ly = pontos[i][1];

            if (rotacao != 0.0)
            {
                if (ly > yMaximo)
                {
                    yMaximo = ly;
                }
                else if (ly < yMinimo)
                {
                    yMinimo = ly;
                }
            }
        }

        return yMinimo + (Math.abs(yMaximo - yMinimo) >> 1);
    }

    @Override
    public void desenhar(Graphics2D graficos)
    {
        SuperficieDesenho.InformacaoGradiente gradientInfo = superficieDesenho.getInformacaoGradiente();
        GradientUtils.doGradient(graficos, gradientInfo, this, yMaximo-yMinimo, xMaximo-xMinimo);
        if (preencher)
        {
            graficos.fill(poligono);
        }
        else
        {
            graficos.draw(poligono);
        }
    }
}

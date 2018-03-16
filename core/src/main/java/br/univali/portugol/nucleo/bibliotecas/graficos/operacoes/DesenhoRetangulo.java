package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.Graficos;
import br.univali.portugol.nucleo.bibliotecas.graficos.SuperficieDesenho;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class DesenhoRetangulo extends OperacaoDesenho
{
    private SuperficieDesenho superficieDesenho;
    private boolean preencher;
    private boolean arredondarCantos;
    private int canto;
    private int largura;
    private int altura;

    public DesenhoRetangulo(CacheOperacoesGraficas cache)
    {
        super(cache);
    }
    
    void setParametros(SuperficieDesenho superficieDesenho, int x, int y, int largura, int altura, boolean preencher, boolean arredondarCantos, double rotacao, int opacidade)
    {
        this.superficieDesenho = superficieDesenho;
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.preencher = preencher;
        this.arredondarCantos = arredondarCantos;
        this.rotacao = rotacao;
        this.opacidade = opacidade;
        this.centroX = x + (largura >> 1);
        this.centroY = y + (altura >> 1);
        this.canto = (int) (Math.min(largura, altura) * 0.2);
    }
    
    @Override
    public void desenhar(Graphics2D graficos)
    {
        SuperficieDesenho.InformacaoGradiente gradientInfo = superficieDesenho.getInformacaoGradiente();
        GradientUtils.doGradient(graficos, gradientInfo, this, altura, largura);
        if (preencher)
        {
            if (arredondarCantos)
            {
                graficos.fillRoundRect(x, y, largura, altura, canto, canto);
            }
            else
            {
                graficos.fillRect(x, y, largura, altura);
            }
        }
        else
        {
            if (arredondarCantos)
            {
                graficos.drawRoundRect(x, y, largura, altura, canto, canto);
            }
            else
            {
                graficos.drawRect(x, y, largura, altura);
            }
        }
    }
}

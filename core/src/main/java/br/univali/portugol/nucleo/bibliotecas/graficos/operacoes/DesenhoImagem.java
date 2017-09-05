package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDesenhoImagem;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class DesenhoImagem extends OperacaoDesenho
{
    private BufferedImage imagem;
    private float nivelTransparencia;

    public DesenhoImagem(CacheOperacoesDesenhoImagem cache)
    {
        super(cache);        
    }

    @Override
    public void liberarRecursos()
    {
        imagem = null;
    }

    void setParametros(int x, int y, BufferedImage imagem, double rotacao, int opacidade)
    {
        this.x = x;
        this.y = y;
        this.nivelTransparencia = opacidade / 255.0f;
        this.rotacao = rotacao;
        this.imagem = imagem;
        this.opacidade = opacidade;
        this.centroX = x + (imagem.getWidth() >> 1);
        this.centroY = y + (imagem.getHeight() >> 1);
    }

    @Override
    public void desenhar(Graphics2D graficos)
    {
        if (opacidade == 255)
        {
            graficos.drawImage(imagem, x, y, null);
        }
        else
        {   
            Composite original = graficos.getComposite();
            Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, nivelTransparencia);
            
            graficos.setComposite(alpha);
            graficos.drawImage(imagem, x, y, null);
            graficos.setComposite(original);
        }       
    }
}

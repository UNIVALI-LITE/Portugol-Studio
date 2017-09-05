package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class DesenhoPorcaoImagem extends OperacaoDesenho
{
    private int xi;
    private int yi;
    private BufferedImage imagem;
    private int direitaOrigem;
    private int baseOrigem;
    private int direitaDestino;
    private int baseDestino;
    private float nivelTransparencia = 1.0f;

    public DesenhoPorcaoImagem(CacheOperacoesGraficas<DesenhoPorcaoImagem> cache)
    {
        super(cache);
    }

    @Override
    public void liberarRecursos()
    {
        imagem = null;
    }

    void setParametros(int x, int y, BufferedImage imagem, int xi, int yi, int largura, int altura, double rotacao, int opacidade)
    {
        this.x = x;
        this.y = y;
        this.imagem = imagem;
        this.xi = xi;
        this.yi = yi;
        this.rotacao = rotacao;
        this.opacidade = opacidade;
        this.centroX = x + (largura >> 1);
        this.centroY = y + (altura >> 1);

        this.direitaDestino = x + largura;
        this.baseDestino = y + altura;
        this.direitaOrigem = xi + largura;
        this.baseOrigem = yi + altura;

        nivelTransparencia = opacidade / 255.0f;
    }

    @Override
    public void desenhar(Graphics2D graficos)
    {
        if (opacidade == 255)
        {
            graficos.drawImage(imagem, x, y, direitaDestino, baseDestino, xi, yi, direitaOrigem, baseOrigem, null);
        }
        else
        {

            Composite original = graficos.getComposite();
            Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, nivelTransparencia);

            graficos.setComposite(alpha);
            graficos.drawImage(imagem, x, y, direitaDestino, baseDestino, xi, yi, direitaOrigem, baseOrigem, null);
            graficos.setComposite(original);
        }
    }
}

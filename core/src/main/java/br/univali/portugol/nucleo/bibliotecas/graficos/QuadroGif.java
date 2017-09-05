package br.univali.portugol.nucleo.bibliotecas.graficos;

/**
 *
 * @author Adson Esteves
 * @author Luiz Fernando Noschang
 */
import java.awt.image.BufferedImage;

public class QuadroGif 
{
    public final int intervalo;
    public final BufferedImage imagem;
    public final String disposicao;
    public final int largura, altura;

    public QuadroGif (BufferedImage imagem, int intervalo, String disposicao, int largura, int altura)
    {
        this.imagem = imagem;
        this.intervalo = intervalo;
        this.disposicao = disposicao;
        this.largura = largura;
        this.altura = altura;
    }

    public QuadroGif (BufferedImage imagem)
    {
        this.imagem = imagem;
        this.intervalo = -1;
        this.disposicao = null;
        this.largura = -1;
        this.altura = -1;
    }
}
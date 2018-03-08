package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.SuperficieDesenho;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDefinirCor;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDefinirFonte;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDefinirGradiente;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDesenhoElipse;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDesenhoImagem;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDesenhoLinha;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDesenhoPoligono;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDesenhoPonto;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDesenhoPorcaoImagem;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDesenhoRetangulo;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesDesenhoTexto;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesLimpar;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class PoolOperacoesGraficas
{
    public static final int QUANTIDADE_MAXIMA_OPERACOES = 131072;
    private static final int QUANTIDADE_INICIAL_OPERACOES = 8;

    private final CacheOperacoesDesenhoElipse CACHE_OPERACOES_DESENHO_ELIPSE;
    private final CacheOperacoesDesenhoImagem CACHE_OPERACOES_DESENHO_IMAGEM;
    private final CacheOperacoesDesenhoLinha CACHE_OPERACOES_DESENHO_LINHA;
    private final CacheOperacoesDesenhoPoligono CACHE_OPERACOES_DESENHO_POLIGONO;
    private final CacheOperacoesDesenhoPonto CACHE_OPERACOES_DESENHO_PONTO;
    private final CacheOperacoesDesenhoPorcaoImagem CACHE_OPERACOES_DESENHO_PORCAO_IMAGEM;
    private final CacheOperacoesDesenhoRetangulo CACHE_OPERACOES_DESENHO_RETANGULO;
    private final CacheOperacoesDesenhoTexto CACHE_OPERACOES_DESENHO_TEXTO;
    private final CacheOperacoesDefinirCor CACHE_OPERACOES_DEFINIR_COR;
    private final CacheOperacoesDefinirFonte CACHE_OPERACOES_DEFINIR_FONTE;
    private final CacheOperacoesLimpar CACHE_OPERACOES_LIMPAR;
    private final CacheOperacoesDefinirGradiente CACHE_OPERACOES_DEFINIR_GRADIENTE;

    public PoolOperacoesGraficas()
    {
        CACHE_OPERACOES_DESENHO_ELIPSE = new CacheOperacoesDesenhoElipse(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_DESENHO_IMAGEM = new CacheOperacoesDesenhoImagem(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_DESENHO_LINHA = new CacheOperacoesDesenhoLinha(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_DESENHO_POLIGONO = new CacheOperacoesDesenhoPoligono(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_DESENHO_PONTO = new CacheOperacoesDesenhoPonto(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_DESENHO_PORCAO_IMAGEM = new CacheOperacoesDesenhoPorcaoImagem(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_DESENHO_RETANGULO = new CacheOperacoesDesenhoRetangulo(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_DESENHO_TEXTO = new CacheOperacoesDesenhoTexto(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_DEFINIR_COR = new CacheOperacoesDefinirCor(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_DEFINIR_FONTE = new CacheOperacoesDefinirFonte(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_LIMPAR = new CacheOperacoesLimpar(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
        CACHE_OPERACOES_DEFINIR_GRADIENTE = new CacheOperacoesDefinirGradiente(QUANTIDADE_MAXIMA_OPERACOES, QUANTIDADE_INICIAL_OPERACOES);
    }

    public DesenhoImagem obterOperacaoDesenhoImagem(int x, int y, BufferedImage imagem, int opacidade, double rotacao)
    {
        DesenhoImagem desenhoImagem = CACHE_OPERACOES_DESENHO_IMAGEM.obter();

        desenhoImagem.setParametros(x, y, imagem, rotacao, opacidade);

        return desenhoImagem;
    }

    public OperacaoDefinirCor obterOperacaoDefinirCor(SuperficieDesenho superficieDesenho, Color cor)
    {
        OperacaoDefinirCor operacaoDefinirCor = CACHE_OPERACOES_DEFINIR_COR.obter();

        operacaoDefinirCor.setParametros(superficieDesenho, cor);

        return operacaoDefinirCor;
    }

    public OperacaoDefinirGradiente obterOperacaoDefinirGradiente(SuperficieDesenho superficieDesenho, int tipo, int cor1, int cor2)
    {
        OperacaoDefinirGradiente operacaoDefinirGradiente = CACHE_OPERACOES_DEFINIR_GRADIENTE.obter();

        operacaoDefinirGradiente.setParametros(superficieDesenho, tipo, cor1, cor2);

        return operacaoDefinirGradiente;
    }
    
    public DesenhoRetangulo obterOperacaoDesenhoRetangulo(SuperficieDesenho superficieDesenho, int x, int y, int largura, int altura, boolean arredondarCantos, boolean preencher, double rotacao, int opacidade)
    {
        DesenhoRetangulo desenhoRetangulo = CACHE_OPERACOES_DESENHO_RETANGULO.obter();

        desenhoRetangulo.setParametros(superficieDesenho, x, y, largura, altura, preencher, arredondarCantos, rotacao, opacidade);

        return desenhoRetangulo;
    }

    public DesenhoElipse obterOperacaoDesenhoElipse(SuperficieDesenho superficieDesenho, int x, int y, int largura, int altura, boolean preencher, double rotacao, int opacidade)
    {
        DesenhoElipse desenhoElipse = CACHE_OPERACOES_DESENHO_ELIPSE.obter();

        desenhoElipse.setParametros(superficieDesenho,x, y, largura, altura, preencher, rotacao, opacidade);

        return desenhoElipse;
    }

    public DesenhoLinha obterOperacaoDesenhoLinha(SuperficieDesenho superficieDesenho,int x1, int y1, int x2, int y2, double rotacao, int opacidade)
    {
        DesenhoLinha operacaoDesenhoLinha = CACHE_OPERACOES_DESENHO_LINHA.obter();

        operacaoDesenhoLinha.setParametros(superficieDesenho, x1, y1, x2, y2, rotacao, opacidade);

        return operacaoDesenhoLinha;
    }

    public DesenhoPoligono obterOperacaoDesenhoPoligono(SuperficieDesenho superficieDesenho, int[][] pontos, boolean preencher, double rotacao, int opacidade)
    {
        DesenhoPoligono operacaoDesenhoPoligono = CACHE_OPERACOES_DESENHO_POLIGONO.obter();

        operacaoDesenhoPoligono.setParametros(superficieDesenho, pontos, preencher, rotacao, opacidade);

        return operacaoDesenhoPoligono;
    }

    public DesenhoPonto obterOperacaoDesenhoPonto(int x, int y, int opacidade)
    {
        DesenhoPonto operacaoDesenhoPonto = CACHE_OPERACOES_DESENHO_PONTO.obter();

        operacaoDesenhoPonto.setParametros(x, y, opacidade);

        return operacaoDesenhoPonto;
    }

    public DesenhoPorcaoImagem obterOperacaoDesenhoPorcaoImagem(int x, int y, int xi, int yi, int largura, int altura, BufferedImage imagem, int opacidade, double rotacao)
    {
        DesenhoPorcaoImagem operacaoDesenhoPorcaoImagem = CACHE_OPERACOES_DESENHO_PORCAO_IMAGEM.obter();

        operacaoDesenhoPorcaoImagem.setParametros(x, y, imagem, xi, yi, largura, altura, rotacao, opacidade);

        return operacaoDesenhoPorcaoImagem;
    }

    public OperacaoGrafica obterOperacaoDesenhoTexto(int x, int y, String texto, FontMetrics dimensoesFonte, double rotacao, int opacidade)
    {
        DesenhoTexto operacaoDesenhoTexto = CACHE_OPERACOES_DESENHO_TEXTO.obter();

        operacaoDesenhoTexto.setParametros(x, y, texto, dimensoesFonte, rotacao, opacidade);

        return operacaoDesenhoTexto;
    }
    
    public OperacaoDefinirFonte obterOperacaoDefinirFonte(Font fonte)
    {
        OperacaoDefinirFonte operacaoDefinirFonte = CACHE_OPERACOES_DEFINIR_FONTE.obter();

        operacaoDefinirFonte.setParametros(fonte);

        return operacaoDefinirFonte;
    }

    public OperacaoLimpar obterOperacaoLimpar(int largura, int altura)
    {
        OperacaoLimpar operacaoLimpar = CACHE_OPERACOES_LIMPAR.obter();

        operacaoLimpar.setParametros(largura, altura);

        return operacaoLimpar;
    }
}

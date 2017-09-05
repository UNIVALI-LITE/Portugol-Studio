package br.univali.portugol.nucleo.bibliotecas.graficos;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheImagens
{
    private static final int NUMERO_MAXIMO_IMAGENS = 128;

    private final Imagem[] imagens = new Imagem[NUMERO_MAXIMO_IMAGENS];

    private CacheImagens()
    {

    }

    public static CacheImagens criar()
    {
        return new CacheImagens();
    }

    private int obterProximoIndiceLivre() throws ErroExecucaoBiblioteca
    {
        for (int indice = NUMERO_MAXIMO_IMAGENS - 1; indice >= 0; indice--)
        {
            if (imagens[indice] == null)
            {
                return indice;
            }
        }

        throw new ErroExecucaoBiblioteca("O número máximo de imagens que podem ser carregadas foi atingido");
    }

    public ImagemGif obterGif(int endereco) throws ErroExecucaoBiblioteca
    {
       Imagem imagem = obterImagem(endereco);
       
       if (imagem instanceof ImagemGif)
       {
           return (ImagemGif) imagem;
       }
           
       throw new ErroExecucaoBiblioteca("O endereço de memória especificado aponta para uma imagem, mas esta imagem não é um GIF animado");
    }
    
    public Imagem obterImagem(int endereco) throws ErroExecucaoBiblioteca
    {
        if (endereco >= 0 && endereco < NUMERO_MAXIMO_IMAGENS)
        {
            Imagem imagem = imagens[endereco];

            if (imagem != null)
            {
                return imagem;
            }
        }

        throw new ErroExecucaoBiblioteca("O endereço de memória especificado não aponta para uma imagem");
    }

    public void liberar()
    {
        for (int indice = 0; indice < NUMERO_MAXIMO_IMAGENS; indice++)
        {
            imagens[indice] = null;
        }
    }

    public void liberarImagem(int endereco) 
    {
        if (endereco >=0 && endereco < NUMERO_MAXIMO_IMAGENS)
        {
            imagens[endereco] = null;
        }
    }

    public int adicionarImagem(Imagem imagem) throws ErroExecucaoBiblioteca
    {
        int indiceImagem = obterProximoIndiceLivre();

        imagens[indiceImagem] = imagem;

        return indiceImagem;
    }
}

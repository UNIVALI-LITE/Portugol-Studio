package br.univali.portugol.nucleo.bibliotecas.graficos;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import java.awt.image.BufferedImage;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ImagemGenerica extends Imagem
{
    private final BufferedImage imagem;
    
    public ImagemGenerica(BufferedImage imagem)
    {
        this.imagem = Utils.criarImagemCompativel(imagem, true);
    }

    @Override
    public BufferedImage getImagem() throws ErroExecucaoBiblioteca
    {
        return imagem;
    }
}

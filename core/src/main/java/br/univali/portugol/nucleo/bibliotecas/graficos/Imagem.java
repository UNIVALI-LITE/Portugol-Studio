package br.univali.portugol.nucleo.bibliotecas.graficos;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Luiz Fernando Noschang
 */
public abstract class Imagem
{
    public static Imagem carregar(File arquivo) throws IOException, ErroExecucaoBiblioteca
    {
        if (imagemEhGifAnimado(arquivo))
        {
            return new ImagemGif(arquivo);
        }
        else
        {
            return new ImagemGenerica(ImageIO.read(arquivo));
        }
    }
    
    private static boolean imagemEhGifAnimado(File arquivo) throws IOException
    {
        ImageReader leitorImagem = ImageIO.getImageReadersBySuffix("GIF").next();
        
        try (final ImageInputStream streamImagem = ImageIO.createImageInputStream(arquivo))
        {
            leitorImagem.setInput(streamImagem);
            
            return leitorImagem.getNumImages(true) > 1;
        }
        finally
        {
            leitorImagem.dispose();
        }
    }
    
    public abstract BufferedImage getImagem() throws ErroExecucaoBiblioteca;
}

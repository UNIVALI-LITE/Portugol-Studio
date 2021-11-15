package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.ProgramaVazio;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.objetos.CacheObjetos;
import br.univali.portugol.nucleo.bibliotecas.objetos.Objeto;
import br.univali.portugol.nucleo.programa.Programa;
import org.junit.Test;

/**
 * @author Gabriel Porto
 */
public class BibliotecaObjetosTest
{
   private static final Programa programa = ProgramaVazio.novaInstancia();

   @Test
   public void liberacaoDeObjetos() throws ErroExecucaoBiblioteca
   {
       final int quantidadeEnderecos = 1024;

       CacheObjetos cache = CacheObjetos.criar();
       int enderecos[] = new int[quantidadeEnderecos];
       for( int i = 0; i < quantidadeEnderecos; i++) {
           enderecos[i] = cache.criarObjeto(new Objeto());
       }
       for (int endereco : enderecos) {
           cache.liberarObjeto(endereco);
       }
   }

    @Test(expected = ErroExecucaoBiblioteca.class)
    public void acessarObjetoInvalido() throws ErroExecucaoBiblioteca
    {
        CacheObjetos cache = CacheObjetos.criar();
        int enderecos[] = new int[128];
        for( int i = 0; i < 128; i++) {
            enderecos[i] = cache.criarObjeto(new Objeto());
        }
        cache.obterObjeto(129);
    }

    @Test
    public void reusoDeEnderecos() throws ErroExecucaoBiblioteca
    {
        final int quantidadeEnderecos = 1024;

        CacheObjetos cache = CacheObjetos.criar();
        int enderecos[] = new int[quantidadeEnderecos];
        for( int i = 0; i < quantidadeEnderecos; i++) {
            enderecos[i] = cache.criarObjeto(new Objeto());
        }
        for (int endereco : enderecos) {
            cache.liberarObjeto(endereco);
        }
        for( int i = 0; i < quantidadeEnderecos; i++) {
            enderecos[i] = cache.criarObjeto(new Objeto());
        }

        if (enderecos[enderecos.length - 1] > quantidadeEnderecos) {
            throw new ErroExecucaoBiblioteca("Esperava-se o reuso dos endereços reciclados");
        }
    }

}

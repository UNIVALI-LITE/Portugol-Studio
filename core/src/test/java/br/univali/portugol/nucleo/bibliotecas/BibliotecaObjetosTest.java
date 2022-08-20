package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.objetos.CacheObjetos;
import br.univali.portugol.nucleo.bibliotecas.objetos.Objeto;
import org.junit.Test;

import javax.naming.directory.InvalidAttributeValueException;

/**
 * @author Gabriel Porto
 */
public class BibliotecaObjetosTest
{
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
        for (int i = 0; i < 128; i++) {
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
        for (int i = 0; i < quantidadeEnderecos; i++) {
            enderecos[i] = cache.criarObjeto(new Objeto());
        }
        for (int endereco : enderecos) {
            cache.liberarObjeto(endereco);
        }
        for (int i = 0; i < quantidadeEnderecos; i++) {
            enderecos[i] = cache.criarObjeto(new Objeto());
        }

        if (enderecos[enderecos.length - 1] > quantidadeEnderecos) {
            throw new ErroExecucaoBiblioteca("Esperava-se o reuso dos endereços reciclados");
        }
    }

    @Test
    public void persistenciaDeObjetos() throws ErroExecucaoBiblioteca, InvalidAttributeValueException {
        final int quantidadeEnderecos = 1024;

        CacheObjetos cache = CacheObjetos.criar();
        int enderecos[] = new int[quantidadeEnderecos];
        for(int i = 0; i < quantidadeEnderecos; i++) {
            enderecos[i] = cache.criarObjeto(new Objeto());
            cache.obterObjeto(enderecos[i]).atribuirPropriedade("id", Integer.toString(i));
        }
        for (int i = quantidadeEnderecos / 2; i < quantidadeEnderecos; i++) {
            cache.liberarObjeto(enderecos[i]);
            enderecos[i] = -1;
        }
        for (int i = 0; i < quantidadeEnderecos; i ++) {
            if (enderecos[i] == -1) {
                enderecos[i] = cache.criarObjeto(new Objeto());
            }
        }
        for (int i = 0; i < quantidadeEnderecos / 2; i++) {
            String id = cache.obterObjeto(enderecos[i]).obterPropriedadeCadeia("id");
            if (!id.matches(Integer.toString(i))) {
                throw new ErroExecucaoBiblioteca("ID para " + i + " diferente do esperado, obteve-se " + id);
            }
        }

        if (enderecos[enderecos.length - 1] > quantidadeEnderecos) {
            throw new ErroExecucaoBiblioteca("Esperava-se o reuso dos endereços reciclados");
        }
    }

    @Test
    public void multiplasSessoes() throws ErroExecucaoBiblioteca
    {
        final int quantidadeEnderecos = 1024;
        CacheObjetos cache = CacheObjetos.criar();
        for (int i = 0; i < quantidadeEnderecos; i++) {
            cache.criarObjeto(new Objeto());
        }
        for (int i = quantidadeEnderecos / 2; i < quantidadeEnderecos; i++) {
            cache.liberarObjeto(i);
        }
        cache.liberar();
        for (int i = 0; i < quantidadeEnderecos; i++) {
            if (i != cache.criarObjeto(new Objeto())) {
                throw new ErroExecucaoBiblioteca("Esperava-se que todos os enderecos fossem iguais ao index");
            }
        }
    }

}

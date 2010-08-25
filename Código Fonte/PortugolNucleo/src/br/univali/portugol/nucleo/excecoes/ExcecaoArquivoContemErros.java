package br.univali.portugol.nucleo.excecoes;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public class ExcecaoArquivoContemErros extends Exception
{
    public ExcecaoArquivoContemErros()
    {
        super("O algoritmo contém erros sintáticos e semânticos!");
    }
}

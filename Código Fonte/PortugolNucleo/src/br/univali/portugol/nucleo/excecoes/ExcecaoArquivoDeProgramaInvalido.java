package br.univali.portugol.nucleo.excecoes;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public class ExcecaoArquivoDeProgramaInvalido extends Exception
{
    private static final long serialVersionUID = -5140991284935191204L;

    public ExcecaoArquivoDeProgramaInvalido()
    {
        super("O arquivo informado não é um programa válido.");
    }
}

package br.univali.portugol.nucleo.excecoes;


public class ExcecaoArquivoContemErros extends Exception
{
    public ExcecaoArquivoContemErros()
    {
        super("O algoritmo contém erros sintáticos e semânticos!");
    }
}

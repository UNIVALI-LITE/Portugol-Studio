package br.univali.portugol.nucleo.excecoes;


public class ExcecaoArquivoDeProgramaInvalido extends Exception
{
	private static final long serialVersionUID = -5140991284935191204L;

	public ExcecaoArquivoDeProgramaInvalido()
	{
		super("Erro: O arquivo informado não é um programa válido.");
	}
}

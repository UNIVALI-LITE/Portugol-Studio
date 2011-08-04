package br.univali.portugol.nucleo.asa;

public class Token
{
	private int linha;
	private int coluna;
	private int tamanhoTexto;
	
	public Token(int linha, int coluna, int tamanhoTexto)
	{
		this.linha = linha;
		this.coluna = coluna;
		this.tamanhoTexto = tamanhoTexto;
	}

	public int getColuna()
	{
		return coluna;
	}

	public int getLinha()
	{
		return linha;
	}

	public int getTamanhoTexto()
	{
		return tamanhoTexto;
	}
}
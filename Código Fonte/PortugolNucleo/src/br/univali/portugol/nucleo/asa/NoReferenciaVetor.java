package br.univali.portugol.nucleo.asa;

public final class NoReferenciaVetor extends NoReferencia
{	
	private NoExpressao noIndice;
		
	public NoReferenciaVetor(String apelido, String nome, NoExpressao indice)
	{
		super(apelido, nome);
		this.noIndice = indice;
	}	
	
	public NoExpressao getIndice()
	{
		return noIndice;
	}
	
	@Override
	protected Token montarToken()
	{
		int line = 0;
		int column = 0;
		int textLength = 0;
		
		if (getTokenApelido() != null)
		{
			line = getTokenApelido().getLinha();
			column = getTokenApelido().getColuna();
		}
		
		else
		{
			line = getTokenNome().getLinha();
			column = getTokenNome().getColuna();
		}		
		
		textLength = (getTokenApelido() != null)? getTokenApelido().getTamanhoTexto() + 1 : 0;
		textLength = textLength + getTokenNome().getTamanhoTexto() + 2 + noIndice.getToken().getTamanhoTexto();		
		
		return new Token(line, column, textLength);
	}
}

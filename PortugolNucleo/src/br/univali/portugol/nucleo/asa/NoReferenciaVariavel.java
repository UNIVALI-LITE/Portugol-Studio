package br.univali.portugol.nucleo.asa;

public final class NoReferenciaVariavel extends NoReferencia
{
	public NoReferenciaVariavel(String apelido, String nome)
	{
		super(apelido, nome);
	}
	
	
	@Override
	protected Token montarToken()
	{
		int linha = 0;
		int coluna = 0;
		int tamanhoTexto = 0;
		
		if (getTokenApelido() != null)
		{
			linha = getTokenApelido().getLinha();
			coluna = getTokenApelido().getColuna();			
		}
		
		else			
		{
			linha = getTokenNome().getLinha();
			coluna = getTokenNome().getColuna();
		}
		
		tamanhoTexto = (getTokenApelido() != null)? getTokenApelido().getTamanhoTexto() + 1 : 0;
		tamanhoTexto = tamanhoTexto + getTokenNome().getTamanhoTexto();
		
		return new Token(linha, coluna, tamanhoTexto);
	}
}

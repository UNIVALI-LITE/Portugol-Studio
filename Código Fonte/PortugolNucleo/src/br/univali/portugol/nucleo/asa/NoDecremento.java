package br.univali.portugol.nucleo.asa;

public final class NoDecremento extends NoExpressao
{
	private NoExpressao expressao;
	
	public NoDecremento(NoExpressao expressao)
	{
		this.expressao = expressao;
	}
	
	public NoExpressao getExpressao()
	{
		return expressao;
	}
	
	@Override
	protected Token montarToken()
	{
		Token token = expressao.getToken();
		
		int linha = token.getLinha();
		int coluna = token.getColuna();
		int tamanhoTexto = token.getTamanhoTexto() + 2;
		
		return new Token(linha, coluna, tamanhoTexto);
	}
}

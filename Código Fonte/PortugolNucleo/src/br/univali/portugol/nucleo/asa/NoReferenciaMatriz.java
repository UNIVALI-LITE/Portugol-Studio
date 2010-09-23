package br.univali.portugol.nucleo.asa;

public final class NoReferenciaMatriz extends NoReferencia
{		
	private NoExpressao linha;
	private NoExpressao coluna;
	
	public NoReferenciaMatriz(String apelido, String nome, NoExpressao linha, NoExpressao coluna)
	{
		super(apelido, nome);
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public NoExpressao getLinha()
	{
		return linha;
	}
	
	public NoExpressao getColuna()
	{
		return coluna;
	}

	@Override
	protected Token montarToken()
	{
		int mLinha = 0;
		int mColuna = 0;
		int tamanhoTexto = 0;
		
		if (getTokenApelido() != null)
		{
			mLinha = getTokenApelido().getLinha();
			mColuna = getTokenApelido().getColuna();
		}
		
		else			
		{
			mLinha = getTokenNome().getLinha();
			mColuna = getTokenNome().getColuna();			
		}
		
		tamanhoTexto = (getTokenApelido() != null)? getTokenApelido().getTamanhoTexto() + 1 : 0;
		tamanhoTexto = tamanhoTexto + getTokenNome().getTamanhoTexto() + linha.getToken().getTamanhoTexto();
		tamanhoTexto = tamanhoTexto + 4 + coluna.getToken().getTamanhoTexto();
		
		return new Token(mLinha, mColuna, tamanhoTexto);
	}
}

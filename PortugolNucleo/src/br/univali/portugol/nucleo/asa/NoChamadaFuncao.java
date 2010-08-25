package br.univali.portugol.nucleo.asa;

import java.util.List;

public final class NoChamadaFuncao extends NoReferencia
{
	private List<NoExpressao> parametros;	
	
	public NoChamadaFuncao(String apelido, String nome)
	{
		super(apelido, nome);
	}
	
	public List<NoExpressao> getParametros()
	{
		return parametros;
	}
	
	public void setParametros(List<NoExpressao> parametros)
	{
		this.parametros = parametros;
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
		
		tamanhoTexto = (getTokenApelido() != null)? getTokenApelido().getTamanhoTexto()+ 1 : 0;
		tamanhoTexto = tamanhoTexto + getTokenNome().getTamanhoTexto() + 2;
		
		if (parametros != null)
		{
			for (NoExpressao parametro: parametros)
				tamanhoTexto = tamanhoTexto + parametro.getToken().getTamanhoTexto();
		}
		
		return new Token(linha, coluna, tamanhoTexto);
	}
}

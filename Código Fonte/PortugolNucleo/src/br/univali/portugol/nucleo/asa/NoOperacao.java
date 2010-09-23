package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.Operacao;

public final class NoOperacao extends NoExpressao
{
	private Operacao operacao;
	private NoExpressao operandoEsquerdo;
	private NoExpressao operandoDireito;
	
	private Token tokenOperador;
	
	public NoOperacao(Operacao operacao, NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
	{
		this.operacao = operacao;
		this.operandoEsquerdo = operandoEsquerdo;
		this.operandoDireito = operandoDireito;
	}
	
	public Operacao getOperacao()
	{
		return operacao;
	}
	
	public NoExpressao getOperandoEsquerdo()
	{
		return operandoEsquerdo;
	}
	
	public NoExpressao getOperandoDireito()
	{
		return operandoDireito;
	}
	
	public void setTokenOperador(Token token)
	{
		this.tokenOperador = token;
	}
	
	public Token getTokenOperador()
	{
		return tokenOperador;
	}	
	
	@Override
	protected Token montarToken()
	{
		Token token = operandoEsquerdo.getToken();
		
		int linha = token.getLinha();
		int coluna = token.getColuna();
		int tamanhoTexto = token.getTamanhoTexto() + ((getTokenOperador() != null)? getTokenOperador().getTamanhoTexto():0) + operandoDireito.getToken().getTamanhoTexto();
		
		return new Token(linha, coluna, tamanhoTexto);
	}
}

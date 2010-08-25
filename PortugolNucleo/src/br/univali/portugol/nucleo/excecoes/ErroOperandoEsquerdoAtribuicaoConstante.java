package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoOperacao;
import br.univali.portugol.nucleo.asa.Token;

public class ErroOperandoEsquerdoAtribuicaoConstante extends Erro
{
	private static final long serialVersionUID = 7301383275508488802L;
	private NoOperacao atribuicao;
	
	public ErroOperandoEsquerdoAtribuicaoConstante(NoOperacao atribuicao)
	{
		this.atribuicao = atribuicao;
		
		Token token = atribuicao.getOperandoEsquerdo().getToken();		
		setLinha(token.getLinha());
		setColuna(token.getColuna());
	}
	
	public NoOperacao getAtribuicao()
	{
		return atribuicao;
	}
	
	@Override
	protected String construirMensagem()
	{
		StringBuilder construtorString = new StringBuilder();
		
		construtorString.append("Erro: o operando esquerdo da atribuição não pode ser uma expressão constante. Linha: ");
		construtorString.append(getLinha());
		construtorString.append(" Coluna: ");
		construtorString.append(getColuna());
		
		return construtorString.toString();
	}
}

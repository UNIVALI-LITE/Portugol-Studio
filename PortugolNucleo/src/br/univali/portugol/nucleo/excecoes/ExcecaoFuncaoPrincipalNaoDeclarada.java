package br.univali.portugol.nucleo.excecoes;

public class ExcecaoFuncaoPrincipalNaoDeclarada extends Exception
{
	private static final long serialVersionUID = 6751504464638080224L;
	
	private String nome;
	

	public ExcecaoFuncaoPrincipalNaoDeclarada(String nome)
	{
		super(construirMensagem(nome));
		this.nome = nome;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	private static String construirMensagem(String nome)
	{
		StringBuilder construtorString = new StringBuilder();
		
		construtorString.append("Erro: A função principal \"");
		construtorString.append(nome);
		construtorString.append("\" não foi declarada.");

		return construtorString.toString();
	}
}

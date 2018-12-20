package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.mensagens.ErroAnalise;

/**
 *
 * @author Fillipi Domingos Pelz
 */
public class ErroVariavelPodeNaoTerSidoInicializada extends ErroAnalise
{
	private static final long serialVersionUID = 1L;
	private String codigo = "ErroSemantico.ErroVariavelPodeNaoTerSidoInicializada";
	private String variavel = "";

	public ErroVariavelPodeNaoTerSidoInicializada(String mensagem)
	{
		variavel = mensagem.split("error: variable ")[1];
		variavel = variavel.split(" might not have")[0];
		super.setCodigo(codigo);
	}

	@Override
	protected String construirMensagem()
	{
		return "Existem trechos de código onde a variável " + variavel + " pode não ter sido inicializada";
	}

}

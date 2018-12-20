package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroComandoNaoSuportado extends ErroSemantico
{
	private String codigo = "ErroSemantico.ErroComandoNaoSuportado";
	
    public ErroComandoNaoSuportado(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte);
        super.setCodigo(codigo);
    }

    @Override
    protected String construirMensagem()
    {
        return "Este comando não é suportado pela linguagem Portugol";
    }
}

package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroComandoNaoSuportado extends ErroSemantico
{
    public ErroComandoNaoSuportado(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte);
    }

    @Override
    protected String construirMensagem()
    {
        return "Este comando não é suportado pela linguagem Portugol";
    }
}

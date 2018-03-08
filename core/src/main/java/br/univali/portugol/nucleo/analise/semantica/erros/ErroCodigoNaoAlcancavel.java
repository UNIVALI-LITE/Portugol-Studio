package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.mensagens.ErroAnalise;

/**
 *
 * @author fillipi
 */
public class ErroCodigoNaoAlcancavel extends ErroAnalise
{
	private static final long serialVersionUID = 1L;

	@Override
    protected String construirMensagem()
    {
        return "Existem trechos de código que não são alcançáveis. Verifique se existe algum código após um laço de repetição infinito ou um retorne";
    }
    
}

package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.mensagens.ErroAnalise;


/**
 *
 * @author fillipi
 */
public class ErroCodigoNaoAlcancavel extends ErroAnalise
{
    
    @Override
    protected String construirMensagem()
    {
        return "Existem trechos de código que não são alcançáveis. Verifique se existe algum código após um laço de repetição infinito ou um retorne";
    }
    
}

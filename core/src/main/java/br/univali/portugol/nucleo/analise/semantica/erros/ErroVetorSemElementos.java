package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroVetorSemElementos extends ErroSemantico {

    public ErroVetorSemElementos(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte,"ErroSemantico.ErroVetorSemElementos");
    }

    @Override
    protected String construirMensagem()
    {
        return "A inicialização do vetor não possui elementos";
    }
    
}

package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroDefinirTipoDadoVetorLiteral extends ErroSemantico {

    public ErroDefinirTipoDadoVetorLiteral(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte,"ErroSemantico.ErroDefinirTipoDadoVetorLiteral");
    }

    @Override
    protected String construirMensagem()
    {
        return "A inicialização do vetor possui mais de um tipo de dado";
    }
    
}

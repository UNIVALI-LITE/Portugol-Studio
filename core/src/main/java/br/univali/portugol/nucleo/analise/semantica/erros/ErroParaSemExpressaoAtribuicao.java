package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroParaSemExpressaoAtribuicao extends ErroSemantico {

    public ErroParaSemExpressaoAtribuicao(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte,"ErroSemantico.ErroParaSemExpressaoAtribuicao");
    }

    @Override
    protected String construirMensagem()
    {
        return "O comando 'para' quando há uma atribuição utiliza uma das seguintes sintaxes: i=i+1 / i++ / i+=1";
    }
    
}

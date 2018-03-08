package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroParaSemExpressaoComparacao extends ErroSemantico {

    public ErroParaSemExpressaoComparacao(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte,"ErroSemantico.ErroParaSemExpressaoComparacao");
    }

    @Override
    protected String construirMensagem()
    {
        return "O comando 'para' necessita ao menos de uma condição de parada. Utilize a seguinte construção para corrigir o problema: 'para( ; <condicao> ; ){ <comandos> }'";
    }
    
}

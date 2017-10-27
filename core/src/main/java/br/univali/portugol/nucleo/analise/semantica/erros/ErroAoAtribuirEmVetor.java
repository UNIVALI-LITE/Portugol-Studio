package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroAoAtribuirEmVetor extends ErroSemantico {

    public ErroAoAtribuirEmVetor(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte, "ErroSemantico.ErroAoAtribuirEmVetor");
    }

    @Override
    protected String construirMensagem()
    {
        return "Deve-se atribuir um valor à uma posição do vetor";
    }
    
}

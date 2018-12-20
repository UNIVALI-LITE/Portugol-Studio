package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroAtribuirMatrizVetorEmVariavel extends ErroSemantico {
	private String codigo = "ErroSemantico.ErroAtribuirMatrizVetorEmVariavel";

    public ErroAtribuirMatrizVetorEmVariavel(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte);
        super.setCodigo(codigo);
    }

    @Override
    protected String construirMensagem()
    {
        return "não é possível atribuir uma matriz ou um vetor a uma variável";
    }
    
}

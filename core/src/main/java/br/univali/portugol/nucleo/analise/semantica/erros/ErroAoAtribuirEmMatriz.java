package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroAoAtribuirEmMatriz extends ErroSemantico {
	private String codigo = "ErroSemantico.ErroAoAtribuirEmMatriz";

    public ErroAoAtribuirEmMatriz(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte);
        super.setCodigo(codigo);
    }

    @Override
    protected String construirMensagem()
    {
        return "Deve-se atribuir um valor à uma posição da matriz";
    }
    
}

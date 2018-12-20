package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroInicializacaoMatrizEmBranco extends ErroSemantico {
	private String codigo = "ErroSemantico.ErroInicializacaoMatrizEmBranco";

    public ErroInicializacaoMatrizEmBranco(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte);
        super.setCodigo(codigo);
    }

    @Override
    protected String construirMensagem()
    {
        return "A inicialização da matriz não possui elementos";
    }
    
}

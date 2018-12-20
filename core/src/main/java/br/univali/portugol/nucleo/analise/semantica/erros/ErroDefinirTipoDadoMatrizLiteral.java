package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroDefinirTipoDadoMatrizLiteral extends ErroSemantico {
	private String codigo = "ErroSemantico.ErroDefinirTipoDadoMatrizLiteral";

    public ErroDefinirTipoDadoMatrizLiteral(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte);
        super.setCodigo(codigo);
    }

    @Override
    protected String construirMensagem()
    {
        return "A inicialização da matriz possui mais de um tipo de dado";
    }
    
}

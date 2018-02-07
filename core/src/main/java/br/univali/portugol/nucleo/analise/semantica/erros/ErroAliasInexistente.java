package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author Adson
 */
public class ErroAliasInexistente extends ErroSemantico {
    private final String alias;

    public ErroAliasInexistente(TrechoCodigoFonte trechoCodigoFonte, String alias)
    {
        super(trechoCodigoFonte);
        this.alias = alias;
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("\"%s.\" pode ser apenas utilizado ao se referir a uma biblioteca como alias, porém nenhuma biblioteca o utiliza como alias. \n"+
                                "Para utiliza-lo como alias, defina uma biblioteca para ele.\n"+
                                "Exemplo: inclua biblioteca Graficos --> \"%s\"", alias, alias);
    }
    
}

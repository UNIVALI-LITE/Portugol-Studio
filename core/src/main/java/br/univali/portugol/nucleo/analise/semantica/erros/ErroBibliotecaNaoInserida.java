package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroBibliotecaNaoInserida extends ErroSemantico {
    private final String escopo;

    public ErroBibliotecaNaoInserida(TrechoCodigoFonte trechoCodigoFonte, String escopo)
    {
        super(trechoCodigoFonte);
        this.escopo = escopo;
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("A biblioteca \"%s\" não foi incluída no programa", escopo);
    }
    
}

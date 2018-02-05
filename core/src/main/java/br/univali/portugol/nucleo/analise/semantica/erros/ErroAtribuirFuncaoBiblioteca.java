package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosConstante;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroAtribuirFuncaoBiblioteca extends ErroSemantico {
    private final MetaDadosBiblioteca metaDadosBiblioteca;

    public ErroAtribuirFuncaoBiblioteca(TrechoCodigoFonte trechoCodigoFonte, MetaDadosBiblioteca metaDadosBiblioteca)
    {
        super(trechoCodigoFonte);
        this.metaDadosBiblioteca = metaDadosBiblioteca;
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("\"%s\" é uma biblioteca e portanto não pode receber valores", metaDadosBiblioteca.getNome());
    }
    
}

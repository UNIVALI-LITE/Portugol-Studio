package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author Adson
 */
public class ErroAtribuirFuncaoBiblioteca extends ErroSemantico {
    private final MetaDadosBiblioteca metaDadosBiblioteca;
    private String codigo = "ErroSemantico.ErroAtribuirFuncaoBiblioteca";

    public ErroAtribuirFuncaoBiblioteca(TrechoCodigoFonte trechoCodigoFonte, MetaDadosBiblioteca metaDadosBiblioteca)
    {
        super(trechoCodigoFonte);
        this.metaDadosBiblioteca = metaDadosBiblioteca;
        super.setCodigo(codigo);
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("\"%s\" é uma biblioteca e portanto não pode receber valores", metaDadosBiblioteca.getNome());
    }
    
}

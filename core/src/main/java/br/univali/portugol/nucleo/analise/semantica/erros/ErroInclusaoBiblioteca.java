package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroInclusaoBiblioteca extends ErroSemantico
{
    private Exception causa;
    private String codigo = "ErroSemantico.ErroInclusaoBiblioteca";

    public ErroInclusaoBiblioteca(TrechoCodigoFonte trechoCodigoFonte, Exception causa)
    {
        super(trechoCodigoFonte);
        this.causa = causa;
        super.setCodigo(codigo);
    }

    @Override
    protected String construirMensagem()
    {
        return causa.getMessage();
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosConstante;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroAtribuirConstanteBiblioteca extends ErroSemantico {
    private final MetaDadosConstante metaDadosConstante;
    private final MetaDadosBiblioteca metaDadosBiblioteca;

    public ErroAtribuirConstanteBiblioteca(TrechoCodigoFonte trechoCodigoFonte, MetaDadosConstante metaDadosConstante, MetaDadosBiblioteca metaDadosBiblioteca)
    {
        super(trechoCodigoFonte);
        this.metaDadosConstante = metaDadosConstante;
        this.metaDadosBiblioteca = metaDadosBiblioteca;
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("\"%s\" é uma constante da biblioteca \"%s\", e portanto, não pode ter seu valor alterado", metaDadosConstante.getNome(), metaDadosBiblioteca.getNome());
    }
    
}

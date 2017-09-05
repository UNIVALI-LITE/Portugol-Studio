/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroConstanteNaoEncontradaNaBiblioteca extends ErroSemantico {
    private final String nome;
    private final MetaDadosBiblioteca metaDadosBiblioteca;

    public ErroConstanteNaoEncontradaNaBiblioteca(TrechoCodigoFonte trechoCodigoFonte, String nome, MetaDadosBiblioteca metaDadosBiblioteca)
    {
        super(trechoCodigoFonte);
        this.nome = nome;
        this.metaDadosBiblioteca = metaDadosBiblioteca;
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("A constante \"%s\" não existe na biblioteca \"%s\"", nome, metaDadosBiblioteca.getNome());
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroAtribuirMatrizVetorEmVariavel extends ErroSemantico {

    public ErroAtribuirMatrizVetorEmVariavel(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte);
    }

    @Override
    protected String construirMensagem()
    {
        return "não é possível atribuir uma matriz ou um vetor a uma variável";
    }
    
}

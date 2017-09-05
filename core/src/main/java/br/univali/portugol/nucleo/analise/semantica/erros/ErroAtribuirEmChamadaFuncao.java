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
public class ErroAtribuirEmChamadaFuncao extends ErroSemantico {

    public ErroAtribuirEmChamadaFuncao(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte,"ErroSemantico.ErroAtribuirEmChamadaFuncao");
    }

    @Override
    protected String construirMensagem()
    {
        return "Não é possível atribuir uma expressão a uma chamada de função.";
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author fillipi
 */
public class ErroDivisaoPorZero extends ErroExecucao
{
    
    @Override
    protected String construirMensagem()
    {
        return "Ocorreu uma divisão por zero";
    }
    
}

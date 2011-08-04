/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.acoes;

/**
 *
 * @author Fillipi Pelz
 */
public interface AcaoListener {

    public abstract void acaoExecutadaSucesso(Acao acao, String mensagem);
    public void acaoFalhou(Acao acao, Exception motivoE);

}

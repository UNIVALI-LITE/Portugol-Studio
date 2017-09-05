package br.univali.portugol.nucleo.analise.semantica;

import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 * Observa a análise semântica de um código fonte e trata os erros e avisos semânticos 
 * encontrados durante esta análise.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 */
public interface ObservadorAnaliseSemantica
{
    /**
     * Este método é chamado automaticamente pelo analisador semântico no qual este observador foi registrado
     * quando um erro semântico é encontrado.
     * 
     * @param erroSemantico     um erro semântico encontrado durante a análise.
     * @since 1.0
     */
    public void tratarErroSemantico(ErroSemantico erroSemantico);

    /**
     * Este método é chamado automaticamente pelo analisador semântico no qual este observador foi registrado
     * quando um aviso semântico é encontrado.

     * @param aviso     um aviso semântico encontrado durante a análise.
     * @since 1.0
     */
    public void tratarAviso(AvisoAnalise aviso);    
}

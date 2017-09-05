package br.univali.portugol.nucleo.analise.sintatica;

import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 * Observa a análise sintática de um código fonte e trata os erros encontrados durante esta análise.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */
public interface ObservadorAnaliseSintatica
{
    /**
     * Este método é chamado automaticamente pelo analisador sintático no qual este observador foi registrado
     * quando um erro sintático é encontrado.
     * 
     * @param erroSintatico     o erro sintático encontrado durante a análise.
     * @since 1.0
     */
    public void tratarErroSintatico(ErroSintatico erroSintatico);
}

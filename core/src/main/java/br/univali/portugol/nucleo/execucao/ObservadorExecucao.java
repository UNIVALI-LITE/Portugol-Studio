package br.univali.portugol.nucleo.execucao;

import br.univali.portugol.nucleo.programa.Programa;

/**
 * Observa a execução de um programa, sendo notificado a respeito do estado da
 * execuação.
 * <p>
 * No Portugol cada programa executa em sua própria thread de forma assíncrona.
 * Isto significa que todos os comandos após a chamada do método
 * {@link Programa#executar(java.lang.String[])} serão imediatamente executados,
 * dificultando o controle e monitoramento da execução do programa.
 * <p>
 * Esta interface provê às aplicações que utilizam os serviços do Portugol, um
 * meio de monitorar e controlar a execução dos programas de forma assíncrona
 * mais facilmente. Os observadores de execução registrados com um programa, são
 * chamados automaticamente pelo interpretador do Portugol quando eventos de
 * execução ocorrem.
 * <p>
 * As aplicações que utilizam os serviços do Portugol não são obrigadas a
 * implementar esta interface para executar ou interromper um programa.
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public interface ObservadorExecucao
{
    /**
     * Este método é chamado antes do início da execução do programa,
     * notificando os observadores de que a execução está prestes a ser
     * iniciada.
     *
     * @param programa o programa que está sendo executado.
     * @since 1.0
     */
    void execucaoIniciada(Programa programa);

    /**
     * Este método é chamado após o término da execução do programa, notificando
     * os observadores de que a execução terminou.
     *
     * @param programa o programa que foi executado.
     * @param resultadoExecucao contém informações referentes à execução do
     * programa como, por exemplo, o tempo total da execução.
     * @since 1.0
     */
    void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao);

    
    void execucaoPausada();  // disparado quando um comando leia é executado
    
    void execucaoResumida(); // disparado quando um comando leia é encerrado
    
    void highlightLinha(int linha);

    void highlightDetalhadoAtual(int linha, int coluna, int tamanho);
    
    void escopoModificado(String escopo); // disparado quando a execução do programa entrada em um novo escopo

}

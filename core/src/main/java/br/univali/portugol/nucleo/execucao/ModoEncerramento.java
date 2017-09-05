package br.univali.portugol.nucleo.execucao;

/**
 * Esta enumeração define os possíveis modos de encerramento
 * de um programa
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public enum ModoEncerramento
{
    /**
     * Esta enumeração indica que todos os comandos do programa foram
     * executados corretamente do início ao fim.
     * 
     * @since 1.0
     */
    NORMAL,
    /**
     * Esta enumeração indica que ocorreu algum erro durante a execução do programa.
     * Exemplo: acesso a uma posição de vetor inválida.
     * 
     * @since 1.0
     */
    ERRO,
    /**
     * Esta enumeração indica que o programa foi interrompido manualmente pelo usuário.
     * Exemplo: o programa entrou em loop infinito e o usuário decidiu interromper a execução.
     */
    INTERRUPCAO
}

package br.univali.portugol.nucleo.bibliotecas.base;

import br.univali.portugol.nucleo.Programa;

/**
 * Define como as bibliotecas serão manipuladas em memória pelo {@link GerenciadorBibliotecas}
 *
 * @author Luiz Fernando Noschang
 * 
 */
public enum TipoBiblioteca
{
    /**
     * Define que haverá somente uma instância da biblioteca, a qual será compartilhada
     * entre todos os programas em execução
     * 
     * <w
     * <p>
     *      <strong>Importante:</strong>
     *      Esta opção deve ser utilizada quando nenhuma das funções da biblioteca precisar armazenar
     *      informações nos atributos da classe durante a execução dos programas. Caso contrário,
     *      os valores armazenados poderão ser sobreescritos por diferentes instâncias de 
     *      {@link Programa} executando ao mesmo tempo.
     * </p>
     */
    COMPARTILHADA,    
    
    /**
     * Define que cada programa em execução terá uma instância própria da biblioteca, a qual
     * não estará acessível ou vísivel aos outros programas.
     * 
     * <p>
     *      <strong>Importante:</strong>
     *      Esta opção deve ser utilozada quando funções da biblioteca precisarem armazenar informações nos 
     *      atributos da classe durante a execução dos programas.
     * </p>
     */    
    RESERVADA
}

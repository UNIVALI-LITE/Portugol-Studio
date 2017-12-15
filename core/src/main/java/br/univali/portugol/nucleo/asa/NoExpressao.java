package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.programa.Estado;

/**
 * Representa uma expressão no código fonte.
 * <p>
 * No Portugol, uma expressão é qualquer valor ou comando que possa ser avaliado
 * e retorne um valor. Uma operação de soma, por exemplo, é uma expressão, pois
 * ao ser avaliada, retorna o resultado da soma. Uma referência de variável,
 * também é uma expressão, pois ao ser avaliada retorna o valor contido na
 * variável. Os tipos primitivos, como 1, "texto", 23.12, também são expressões,
 * pois ao serem avaliados, retornam o seu próprio valor.
 * <p>
 * Uma expressão, pode ser composta por subexpressões, como é o caso das
 * operações aritméticas. As operações aritméticas possuem um operando esquerdo
 * e um operando direito, que por sua vez podem ser outras operações
 * aritméticas.
 * <p>
 * Esta classe serve como base para todos os tipos de expressão do Portugol.
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 * @see NoCadeia
 * @see NoCaracter
 * @see NoDecremento
 * @see NoIncremento
 * @see NoInteiro
 * @see NoLogico
 * @see NoMatriz
 * @see NoMenosUnario
 * @see NoNao
 * @see NoOperacao
 * @see NoReal
 * @see NoReferencia
 * @see NoVetor
 */
public abstract class NoExpressao extends NoBloco
{
    private boolean entreParentes = false;
    //private TipoDado tipoResultante;
    
    public void setEstaEntreParenteses(boolean estaEntreParentes)
    {
        this.entreParentes = estaEntreParentes;
    }
    
    public boolean estaEntreParenteses(){
        return this.entreParentes;
    }
    
//    public void setTipoResultante(TipoDado tipo)
//    {
//        this.tipoResultante = tipo;
//    }
    
    public abstract TipoDado getTipoResultante();
    
    /**
     * Obtém o trecho do código fonte no qual esta expressão se encontra.
     *
     * @return o trecho do código fonte no qual esta expressão se encontra.
     * @since 1.0
     */
    @Override
    public final TrechoCodigoFonte getTrechoCodigoFonte()
    {
        if (trechoCodigoFonte == null || trechoCodigoFonte == TRECHO_NULO)
        {
            trechoCodigoFonte = montarTrechoCodigoFonte();
        }

        return trechoCodigoFonte;
    }

    /**
     * Este método cria e retorna um objeto contendo as informações referentes à
     * localização desta expressão no código fonte.
     *
     * @return a trecho do código fonte no qual esta expressão se encontra.
     * @since 1.0
     */
    protected abstract TrechoCodigoFonte montarTrechoCodigoFonte();

    @Override
    public boolean ehParavel(Estado estado)
    {
        return false;
    }
}

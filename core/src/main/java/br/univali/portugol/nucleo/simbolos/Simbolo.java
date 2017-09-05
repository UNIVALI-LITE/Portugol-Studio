package br.univali.portugol.nucleo.simbolos;

import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import java.util.Objects;

/**
 * Representa um símbolo alocado em memória durante a execução do programa.
 * <p>
 * No Portugol, um símbolo é qualquer estrutura que possa ser acessada através
 * de um nome (identificador) durante a execução do programa.
 * <p>
 * Geralmente o símbolo armazena um valor que será recuperado posteriormente, o
 * qual deve ser do tipo de dado definido na declaração do símbolo,
 *
 * @author Luiz Fernando Noschang
 *
 * @version 1.0
 */
public abstract class Simbolo
{

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Simbolo other = (Simbolo) obj;
        return true;
    }
    private String nome;
    protected TipoDado tipoDado;
    private TrechoCodigoFonte trechoCodigoFonteNome;
    private TrechoCodigoFonte trechoCodigoFonteTipoDado;
    private boolean constante = false;
    private boolean utilizado = false;
    private boolean inicializado = false;
    private boolean redeclarado = false;
    private NoDeclaracao origemDoSimbolo = null;

    /**
     *
     * @param nome o nome deste símbolo.
     *
     * @param tipoDado o tipo de dado armazenado/tratado por este símbolo.
     *
     * @param declaracaoOrigem a declaração que originou o símbolo
     */
    public Simbolo(String nome, TipoDado tipoDado, NoDeclaracao declaracaoOrigem)
    {
        setNome(nome);
        setTipoDado(tipoDado);
        setOrigemDoSimbolo(declaracaoOrigem);
    }

    /**
     * Obtém o nome deste símbolo.
     *
     * @return o nome deste símbolo.
     *
     * @since 1.0
     */
    public final String getNome()
    {
        return nome;
    }

    /**
     * Obtém o tipo de dado armazenado/tratado por este símbolo.
     *
     * @return o tipo de dado armazenado/tratado por este símbolo.
     *
     * @since 1.0
     */
    public final TipoDado getTipoDado()
    {
        return tipoDado;
    }

    /**
     * Verifica se o símbolo foi inicializado.
     *
     * @return          <code>true</code> se o símbolo foi inicializado, caso contrário
     * retorna <code>false</code>.
     *
     * @since 1.0
     *
     * @deprecated nem todos os símbolos possuem inicialização, pos isso nas
     * versões futuras este método será removido da classe base e utilizado
     * somente nas classes em que for necessário.
     */
    @Deprecated
    public final boolean inicializado()
    {
        return inicializado;
    }

    /**
     * Verifica se o símbolo está sendo utilizado em algum local do programa.
     *
     * @return     <code>true</code> se o símbolo está sendo utilizado, caso
     * contrário retorna <code>false</code>.
     * @since 1.0
     */
    public final boolean utilizado()
    {
        return utilizado;
    }

    /**
     * Verifica se o símbolo foi declarado mais de uma vez no mesmo escopo.
     *
     * @return     <code>true</code> se o símbolo foi declarado mais de uma vez,
     * caso contrário retorna <code>false</code>.
     * @since 1.0
     */
    public final boolean redeclarado()
    {
        return redeclarado;
    }

    /**
     * Verifica se o valor armazenado/tratado pelo símbolo é constante, ou seja,
     * se não poderá ser alterado após sua inicialização.
     *
     * @return          <code>true</code> se o símbolo for constante, caso contrário
     * retorna <code>false</code>.
     * @since 1.0
     * @deprecated nem todos os símbolos podem ser declarados como constantes,
     * pos isso nas versões futuras este método será removido da classe base e
     * utilizado somente nas classes em que for necessário.
     */
    public boolean constante()
    {
        return constante;
    }

    /**
     * Obtém o trecho do código fonte no qual o nome deste símbolo se encontra.
     *
     * @return o trecho do código fonte no qual o nome deste símbolo se encontra
     * @since 1.0
     */
    public TrechoCodigoFonte getTrechoCodigoFonteNome()
    {
        return trechoCodigoFonteNome;
    }

    /**
     * Define o trecho do código fonte no qual o nome deste símbolo se encontra.
     *
     * @param trechoCodigoFonteNome o trecho do código fonte no qual o nome
     * deste símbolo se encontra.
     * @since 1.0
     */
    public void setTrechoCodigoFonteNome(TrechoCodigoFonte trechoCodigoFonteNome)
    {
        this.trechoCodigoFonteNome = trechoCodigoFonteNome;
    }

    /**
     * Obtém o trecho do código fonte no qual o tipo de dado deste símbolo se
     * encontra.
     *
     * @return o trecho do código fonte no qual o tipo de dado deste símbolo se
     * encontra
     * @since 1.0
     */
    public TrechoCodigoFonte getTrechoCodigoFonteTipoDado()
    {
        return trechoCodigoFonteTipoDado;
    }

    /**
     * Define o trecho do código fonte no qual o tipo de dado deste símbolo se
     * encontra.
     *
     * @param trechoCodigoFonteTipoDado o trecho do código fonte no qual o tipo
     * de dado deste símbolo se encontra.
     * @since 1.0
     */
    public void setTrechoCodigoFonteTipoDado(TrechoCodigoFonte trechoCodigoFonteTipoDado)
    {
        this.trechoCodigoFonteTipoDado = trechoCodigoFonteTipoDado;
    }

    /**
     * Define o nome deste símbolo.
     *
     * @param nome o nome deste símbolo.
     * @since 1.0
     */
    private void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * Define o tipo de dado deste símbolo.
     *
     * @param tipoDado o tipo de dado deste símbolo.
     * @since 1.0
     */
    private void setTipoDado(TipoDado tipoDado)
    {
        this.tipoDado = tipoDado;
    }

    /**
     * Define se este símbolo foi inicializado ou não.
     *
     * @param inicializado flag indicando se este símbolo foi inicializado ou
     * não.
     * @since 1.0
     */
    public final void setInicializado(boolean inicializado)
    {
        this.inicializado = inicializado;
    }

    /**
     * Define se este símbolo está sendo utilizado ou não.
     *
     * @param utilizado flag indicando se este símbolo está sendo utilizado ou
     * não.
     * @since 1.0
     */
    public final void setUtilizado(boolean utilizado)
    {
        this.utilizado = utilizado;
    }

    /**
     * Define se este símbolo foi declarado mais de uma vez no mesmo escopo ou
     * não.
     *
     * @param redeclarado flag indicando se este símbolo foi declarado mais de
     * uma vez no mesmo escopo ou não.
     * @since 1.0
     */
    public final void setRedeclarado(boolean redeclarado)
    {
        this.redeclarado = redeclarado;
    }

    /**
     * Define se o valor armazenado/tratado por este símbolo é constante ou não.
     *
     * @param constante flag indicando se o valor armazenado/tratado por este
     * símbolo é constante ou não.
     * @since 1.0
     */
    public final void setConstante(boolean constante)
    {
        this.constante = constante;
    }

    public NoDeclaracao getOrigemDoSimbolo()
    {
        return origemDoSimbolo;
    }

    final void setOrigemDoSimbolo(NoDeclaracao origemDoSimbolo)
    {
        this.origemDoSimbolo = origemDoSimbolo;
    }

    /**
     * Cria uma cópia deste símbolo em memória. Este método é utilizado durante
     * a chamada de funções para passar o símbolo por valor. A cópia terá os
     * mesmos valores e características do símbolo original, com exceção de
     * nome.
     *
     * @param novoNome o nome atribuído a esta cópia do símbolo. Geralmente é o
     * nome do parâmetro da função para o qual este símbolo está sendo passado.
     * @return uma cópia deste símbolo.
     * @since 1.0
     */
    public abstract Simbolo copiar(String novoNome);
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.nome);
        hash = 83 * hash + Objects.hashCode(this.tipoDado);
        hash = 83 * hash + Objects.hashCode(this.trechoCodigoFonteNome);
        hash = 83 * hash + Objects.hashCode(this.trechoCodigoFonteTipoDado);
        hash = 83 * hash + (this.constante ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.origemDoSimbolo);
        return hash;
    }
}

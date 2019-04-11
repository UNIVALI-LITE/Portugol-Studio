package br.univali.portugol.nucleo.asa;

import java.util.ArrayList;
import java.util.List;

/**
 * Class base para todos os tipos de declaração do Portugol.
 * <p>
 * Todas os nós da ASA que representam algum tipo de declaração, como por
 * exemplo, uma declaração de variável, devem estender desta classe.
 *
 * @author Luiz Fernando Noschang
 *
 * @version 1.0
 */
public abstract class NoDeclaracaoBase extends NoBloco implements NoDeclaracao
{
    private String nome;
    private final boolean constante;
    private final TipoDado tipoDado;
    private TrechoCodigoFonte trechoCodigoFonteNome;
    private TrechoCodigoFonte trechoCodigoFonteTipoDado;
    private final List<NoReferencia> referencias = new ArrayList<>();

    /**
     * @param nome o nome do símbolo que está sendo declarado.
     *
     * @param tipoDado o tipo de dado do símbolo que está sendo declarado.
     *
     * @param constante flag indicando se o símbolo terá valor constante ou
     * variável. Se for<code>true</code>, o valor do símbolo será constante e
     * não poderá ser alterado após sua inicialização.
     *
     * @since 1.0
     */
    public NoDeclaracaoBase(String nome, TipoDado tipoDado, boolean constante)
    {
        this.nome = nome;
        this.tipoDado = tipoDado;
        this.constante = constante;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * Obtém o nome do símbolo que está sendo declarado. O nome definido na
     * declaração do símbolo será o mesmo nome utilizado para acessá-lo em
     * outros locais do código fonte.
     *
     * @return o nome do símbolo sendo declarado.
     *
     * @since 1.0
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * Verifica se o símbolo que está sendo declarado será constante ou não. Se
     * o símbolo for constante, seu valor não poderá ser alterado após a
     * inicialização.
     *
     * @return <code>true</code> se o símbolo for constante.
     *
     * @since 1.0
     */
    public boolean constante()
    {
        return constante;
    }

    /**
     * Obtém o tipo de dado do símbolo que está sendo declarado.
     *
     * @return o tipo de dado
     *
     * @since 1.0
     */
    public TipoDado getTipoDado()
    {
        return tipoDado;
    }

    /**
     * Obtém o trecho do código fonte no qual o nome do símbolo se encontra.
     *
     * @return o trecho do código fonte
     *
     * @since 1.0
     */
    public TrechoCodigoFonte getTrechoCodigoFonteNome()
    {
        return trechoCodigoFonteNome;
    }

    /**
     * Define o trecho do código fonte no qual o nome do símbolo se encontra.
     *
     * @param trechoCodigoFonteNome Define o trecho do código fonte no qual o
     * nome do símbolo se encontra.
     *
     * @since 1.0
     */
    public void setTrechoCodigoFonteNome(TrechoCodigoFonte trechoCodigoFonteNome)
    {
        this.trechoCodigoFonteNome = trechoCodigoFonteNome;
    }

    /**
     * Define o trecho do código fonte no qual o tipo de dado do símbolo se
     * encontra.
     *
     * @param trechoCodigoFonteTipoDado Define o trecho do código fonte no qual
     * o tipo de dado do símbolo se encontra.
     *
     * @since 1.0
     */
    public void setTrechoCodigoFonteTipoDado(TrechoCodigoFonte trechoCodigoFonteTipoDado)
    {
        this.trechoCodigoFonteTipoDado = trechoCodigoFonteTipoDado;
    }

    /**
     * Obtém o trecho do código fonte no qual o tipo de dado do símbolo se
     * encontra.
     *
     * @return o trecho do código fonte no qual o tipo de dado do símbolo se
     * encontra.
     */
    public TrechoCodigoFonte getTrechoCodigoFonteTipoDado()
    {
        return trechoCodigoFonteTipoDado;
    }

    /**
     * Obtém uma lista dos nós da árvore que representam referências a esta
     * declaração
     *
     * @return a lista de nós que referenciam esta declaração
     */
    public List<NoReferencia> getReferencias()
    {
        return new ArrayList<>(referencias);
    }

    public final void adicionarReferencia(NoReferencia referencia)
    {
        referencias.add(referencia);
        referencia.setOrigemDaReferencia(this);
    }
    
    @Override
    public String getEscopo()
    {
        return super.getEscopo() + String.format("(%s)", getNome());
    }
}

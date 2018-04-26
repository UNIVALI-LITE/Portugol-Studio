package br.univali.portugol.nucleo.asa;

/**
 * Esta classe é a base para todos os tipos de referências no Portugol.
 * <p>
 * Uma referência no Portugol é um identificador que permite a um símbolo ser
 * acessado e utilizado em comandos, operações e expressões. Toda referência no
 * Portugol, possui um nome, no entanto, a forma como cada tipo de símbolo é
 * acessado é diferente. Por isso, a ASA possui nós específicos para cada tipo
 * de referência.
 *
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 * @param <T>
 *
 * @see NoChamadaFuncao
 * @see NoReferenciaMatriz
 * @see NoReferenciaVariavel
 * @see NoReferenciaVetor
 */
public abstract class NoReferencia<T extends NoDeclaracao> extends NoExpressao
{
    private String nome;
    private String escopoBiblioteca;
    private TrechoCodigoFonte trechoCodigoFonteNome;
    protected T origemDaReferencia;

    /**
     * @param nome o nome do símbolo referenciado.
     *
     * @param escopo o escopo da referência. Quando for uma referência a um
     * símbolo do programa, será nulo, quando for uma referência a um símbolo de
     * uma biblioteca, o escopo será o nome ou apelido da bibliotecas.
     *
     * @since 1.0
     */
    public NoReferencia(String escopo, String nome)
    {
        this.escopoBiblioteca = escopo;
        this.nome = nome;
    }

    public void setEscopo(String escopo)
    {
        this.escopoBiblioteca = escopo;
    }
    
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    @Override
    public TipoDado getTipoResultante()
    {
        if (origemDaReferencia != null)
        {
            return origemDaReferencia.getTipoDado();
        }
        return null;
    }

    /**
     * Obtém o nome do símbolo referenciado.
     *
     * @return o nome do símbolo referenciado.
     * @since 1.0
     */
    public final String getNome()
    {
        return nome;
    }

    /**
     * Obtém o escopo do símbolo referenciado. É utilizado apenas para acessar
     * símbolos que estão em bibliotecas
     *
     * @return o escopo do símbolo referenciado.
     *
     */
    public final String getEscopoBiblioteca()
    {
        return escopoBiblioteca;
    }

    /**
     * Obtém o trecho do código fonte no qual o nome do símbolo referenciado se
     * encontra.
     *
     * @return o trecho do código fonte no qual o nome do símbolo referenciado
     * se encontra.
     *
     * @since 1.0
     */
    public TrechoCodigoFonte getTrechoCodigoFonteNome()
    {
        return trechoCodigoFonteNome;
    }

    /**
     * Define o trecho do código fonte no qual o nome do símbolo referenciado se
     * encontra.
     *
     * @param trechoCodigoFonteNome o trecho do código fonte no qual o nome do
     * símbolo referenciado se encontra.
     *
     * @since 1.0
     */
    public void setTrechoCodigoFonteNome(TrechoCodigoFonte trechoCodigoFonteNome)
    {
        this.trechoCodigoFonteNome = trechoCodigoFonteNome;
    }

    public final void setOrigemDaReferencia(T origemDaReferencia)
    {
        this.origemDaReferencia = origemDaReferencia;
    }
    
    public NoDeclaracao getOrigemDaReferencia()
    {
        return origemDaReferencia;
    }
}

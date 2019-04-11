package br.univali.portugol.nucleo.asa;

/**
 *
 * @author Luiz Fernando Noschang
 */
public abstract class NoDeclaracaoInicializavel extends NoDeclaracaoBase implements NoDeclaracaoInspecionavel
{
    private NoExpressao inicializacao;
    
    private int idParaInspecao = -1; // usado para implementar a inspeção de símbolos

    public NoDeclaracaoInicializavel(String nome, TipoDado tipoDado, boolean constante)
    {
        super(nome, tipoDado, constante);
    }

    public void setIdParaInspecao(int idParaInspecao)
    {
        this.idParaInspecao = idParaInspecao;
    }

    @Override
    public int getIdParaInspecao()
    {
        return idParaInspecao;
    }

    /**
     * Obtém a expressão utilizada para inicializar o símbolo declarado.
     *
     * @return a expressão de inicialização.
     *
     * @since 1.0
     */
    public final NoExpressao getInicializacao()
    {
        return inicializacao;
    }
    
    public boolean temInicializacao()
    {
        return inicializacao != null;
    }

    /**
     * Define a expressão de inicialização do símbolo.
     *
     * @param inicializacao a expressão de inicialização.
     *
     * @since 1.0
     */
    public final void setInicializacao(NoExpressao inicializacao)
    {
        this.inicializacao = inicializacao;
    }
}

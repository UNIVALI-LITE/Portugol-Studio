package br.univali.portugol.nucleo.asa;

/**
 * Representa uma declaração de matriz no código fonte.
 * <p>
 * Uma matriz é uma estrutura de dados que armazena valores em linhas e colunas.
 * No Portugol, as matrizes são declaradas colocando o símbolo "[][]" após o
 * nome da variável. É possível declarar matrizes de várias formas diferentes:
 *
 * <code><pre>
 *
 *      funcao exemploDeclaracaoMatriz()
 *      {
 *            /*
 *             * Declara uma matriz com 3 linhas e 3 colunas. Esta matriz não possui
 *             * inicialização, logo, seus valores estarão zerados.
 *             *&#47;
 *           inteiro matA[3][3]
 *
 *            /*
 *             * Declara uma matriz com 3 linhas e 3 colunas. Esta matriz será inicializada
 *             * com os valores definidos entre as chaves "{}". Os valores 1, 2 e 3 estarão
 *             * na primeira linha. Os valores 4, 5 e 6 estarão na segunda linha. Os valores
 *             * 7, 8 e 9 estarão na terceira linha.
 *             *&#47;
 *           inteiro matB[3][3] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}
 *
 *            /*
 *             * Declara uma matriz sem definir suas dimensões. Este tipo de declaração obriga
 *             * que a matriz seja inicializada. As dimensões da matriz serão determinadas
 *             * pela inicialização. Neste caso, a matriz terá 2 linhas e 5 colunas. Os valores
 *             * 1, 2, 3, 4 e 5 estarão na primeir linha. Os valores 6, 7, 8, 9 e 10 estarão na
 *             * segunda linha.
 *             *&#47;
 *           inteiro matC[][] = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}}
 *      }
 *
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoDeclaracaoMatriz extends NoDeclaracaoInicializavel
{
    private final NoExpressao numeroLinhas;
    private final NoExpressao numeroColunas;

    /**
     *
     * @param nome o nome desta matriz.
     *
     * @param tipoDado o tipo de dado desta matriz.
     *
     * @param numeroLinhas a expressão que define o número de linhas desta
     * matriz.
     *
     * @param numeroColunas a expressão que define o número de colunas desta
     * matriz.
     *
     * @param constante flag indicando se esta matriz terá valores constantes ou
     * não. Se o valor for <code>true</code> os valores da matriz não poderão
     * ser alterados após a inicialização.
     *
     * @since 1.0
     */
    public NoDeclaracaoMatriz(String nome, TipoDado tipoDado, NoExpressao numeroLinhas, NoExpressao numeroColunas, boolean constante)
    {
        super(nome, tipoDado, constante);
        this.numeroLinhas = numeroLinhas;
        this.numeroColunas = numeroColunas;
    }

    /**
     * Obtém a expressão que define o número de linhas desta matriz. Pode ser
     * qualquer expressão do tipo {@link TipoDado#INTEIRO}.
     *
     * @return a expressão que define o número de linhas desta matriz.
     *
     * @since 1.0
     */
    public NoExpressao getNumeroLinhas()
    {
        return numeroLinhas;
    }

    /**
     * Obtém a expressão que define o número de colunas desta matriz. Pode ser
     * qualquer expressão do tipo {@link TipoDado#INTEIRO}.
     *
     * @return a expressão que define o número de colunas desta matriz.
     *
     * @since 1.0
     */
    public NoExpressao getNumeroColunas()
    {
        return numeroColunas;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }
}
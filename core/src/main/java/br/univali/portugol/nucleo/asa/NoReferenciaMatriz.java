package br.univali.portugol.nucleo.asa;

/**
 * Representa uma referência de matriz no código fonte.
 * <p>
 * Uma referência de matriz é utilizada para obter um valor contido na matriz.
 * Uma referência de matriz é composta pelo nome da matriz e por duas expressões
 * entre os operadores "[][]", as quais defininem a linha e a coluna da matriz
 * que estão sendo acessados.
 * <p>
 * Exemplo:  <code><pre>
 *
 *      funcao exemploReferenciaMatriz()
 *      {
 *           inteiro matriz[3][3] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}
 *
 *           escreva("Isto é uma referência de matriz: ", matriz[2][1])
 *      }
 *
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoReferenciaMatriz extends NoReferencia<NoDeclaracaoMatriz>
{
    private final NoExpressao linha;
    private final NoExpressao coluna;

    /**
     * @param escopo o escopo da matriz sendo referenciada. Se o escopo for
     * nulo, então é uma matriz do programa, senão é uma matriz de
     * biblioteca.
     *
     * @param nome o nome da matriz que está sendo referenciada.
     *
     * @param linha a expressão que define qual linha da matriz está sendo
     * acessada.
     *
     * @param coluna a expressão que define qual coluna da matriz está sendo
     * acessada.
     *
     * @since 1.0
     */
    public NoReferenciaMatriz(String escopo, String nome, NoExpressao linha, NoExpressao coluna)
    {
        super(escopo, nome);
        this.linha = linha;
        this.coluna = coluna;
    }

    /**
     * Obtém a expressão que define qual linha da matriz está sendo acessada.
     *
     * @return a expressão que define qual linha da matriz está sendo acessada.
     *
     * @since 1.0
     */
    public NoExpressao getLinha()
    {
        return linha;
    }

    /**
     * Obtém a expressão que define qual coluna da matriz está sendo acessda.
     *
     * @return a expressão que define qual coluna da matriz está sendo acessda.
     *
     * @since 1.0
     */
    public NoExpressao getColuna()
    {
        return coluna;
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

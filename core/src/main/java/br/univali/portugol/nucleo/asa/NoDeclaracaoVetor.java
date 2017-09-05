package br.univali.portugol.nucleo.asa;

/**
 * Representa a declaração de um vetor no código fonte.
 * <p>
 * Um vetor é uma estrutura de dados que armazena vários valores, de forma
 * semelhante a uma lista. No Portugol, os vetores sao declarados colocando o
 * símbolo "[]" após o nome da variável. É possível declarar vetores de várias
 * formas diferentes:
 *
 * <code><pre>
 *
 *       funcao exemploDeclaracaoVetor()
 *       {
 *            /*
 *             * Declara um vetor com capacidade para 5 elementos. Este vetor não possui
 *             * inicilização, logo, seus valores estarão zerados.
 *             *&#47;
 *            inteiro vetA[5]
 *
 *            /*
 *             * Declara um vetor com capacidade para 5 elementos. Este vetor será inicializado
 *             * com os valores entre chaves "{}".
 *             *&#47;
 *            inteiro vetB[5] = {1, 2, 3, 4, 5}
 *
 *             /*
 *             * Declara um vetor sem definir seu tamanho. Este tipo de declaração obriga
 *             * que o vetor seja inicializado. O tamaho do vetor será determinado pela
 *             * inicilização. Neste caso o vetor terá capacidade para 8 elementos.
 *             *&#47;
 *            inteiro vetC[] = {8, 7, 6, 5, 4, 3, 2, 1}
 *
 *       }
 *
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoDeclaracaoVetor extends NoDeclaracaoInicializavel
{
    private final NoExpressao tamanho;

    /**
     *
     * @param nome o nome deste vetor.
     *
     * @param tipoDado o tipo de dado deste vetor.
     *
     * @param tamanho a expressão que define o número de elementos deste vetor.
     *
     * @param constante define se os valores detse vetor serão constantes. Se
     * <code>true</code> os valores não poderão ser alterados após a
     * inicialização do vetor.
     *
     * @since 1.0
     */
    public NoDeclaracaoVetor(String nome, TipoDado tipoDado, NoExpressao tamanho, boolean constante)
    {
        super(nome, tipoDado, constante);
        this.tamanho = tamanho;
    }

    /**
     * Obtém a expressão que define o tamanho, deste vetor, isto é, o número de
     * elementos que ele terá.
     *
     * @return a expressão que define o tamanho deste vetor.
     *
     * @since 1.0
     */
    public NoExpressao getTamanho()
    {
        return tamanho;
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

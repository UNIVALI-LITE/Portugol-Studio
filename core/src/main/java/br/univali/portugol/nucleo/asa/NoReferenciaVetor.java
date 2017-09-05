package br.univali.portugol.nucleo.asa;

/**
 * Representa uma referência de vetor no código fonte.
 * <p>
 * Uma referência de vetor é utilizada para obter um valor contido no vetor. Uma
 * referência de vetor é composta pelo nome do vetor e por uma expressão entre
 * os operadores "[]", a qual define a posição do vetor que está sendo acessada.
 * <p>
 * Exemplo:  <code><pre>
 *
 *      funcao exemploReferenciaVetor()
 *      {
 *           inteiro vetor[5] = [1, 2, 3, 4, 5}
 *
 *           escreva("Isto é uma referência de vetor: ", vetor[3])
 *      }
 *
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @since 1.0
 */
public final class NoReferenciaVetor extends NoReferencia<NoDeclaracaoVetor>
{
    private final NoExpressao noIndice;

    /**
     * @param escopo o escopo do vetor sendo referenciada. Se o escopo for nulo,
     * então é um vetor do programa, senão é um vetor de biblioteca.
     *
     * @param nome o nome do vetor que está sendo referenciado.
     *
     * @param indice a expressão que define qual posição do vetor está sendo
     * acessada.
     *
     * @since 1.0
     */
    public NoReferenciaVetor(String escopo, String nome, NoExpressao indice)
    {
        super(escopo, nome);
        this.noIndice = indice;
    }

    /**
     * Obtém a expressão que define qual posição do vetor está sendo acessada.
     *
     * @return a expressão que define qual posição do vetor está sendo acessada.
     * @since 1.0
     */
    public NoExpressao getIndice()
    {
        return noIndice;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected TrechoCodigoFonte montarTrechoCodigoFonte()
    {
        int tamanhoTexto = 0;

        int linha = getTrechoCodigoFonteNome().getLinha();
        int coluna = getTrechoCodigoFonteNome().getColuna();

        tamanhoTexto = tamanhoTexto + getTrechoCodigoFonteNome().getTamanhoTexto() + 2 + noIndice.getTrechoCodigoFonte().getTamanhoTexto();

        return new TrechoCodigoFonte(linha, coluna, tamanhoTexto);
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

package br.univali.portugol.nucleo.asa;

/**
 * Armaneza informações sobre a localização no código fonte do bloco/expressão representado pelo nó da ASA.
 * <p>
 * Todo nó da ASA é gerado no momento do parsing a partir de um ou mais tokens no código fonte. Esta classe armazena
 * as informações referentes a estes tokens.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 * 1)     programa                       
 * 2)     {
 * 3)          inteiro var = 45
 * 4)     }
 * 
 * </pre></code>
 * <p>
 * No exemplo acima, temos uma declaração de variável comum. Esta declaração é formada por quatro tokens: o tipo de dado, 
 * o nome da variável, o sinal de atribuição e o valor inteiro. Ao realizar o parsing deste programa, o Portugol irá gerar 
 * um objeto {@link NoDeclaracaoVariavel}, representando esta declaração.
 * <p>
 * Se chamarmos o método <code>getTrechoCodigoFonteTipoDado()</code> neste objeto, será retornado um {@link TrechoCodigoFonte} 
 * cuja linha é 3, a coluna é 6 e o tamanho do texto é 7, ou seja, a localização e o tamanho do token 'inteiro'.
 * <p>
 * Se chamarmos o método <code>getTrechoCodigoFonteNome()</code>, será retornado um {@link TrechoCodigoFonte} cuja linha é 3,
 * a coluna é 14 e o tamanho do texto é 3, ou seja, a localização e o tamanho do token 'var'.
 * <p>
 * Se chamarmos o método <code>getInicializacao().getTrechoCodigoFonte()</code>, será retornado um {@link TrechoCodigoFonte} cuja 
 * linha é 3, a coluna é 20 e o tamanho do texto é 2, ou seja, a localização e o tamanho do token '45'.
 * <p>
 * Se chamarmos o método <code>getTrechoCodigoFonte()</code>, será retornado um {@link TrechoCodigoFonte} cuja linha é 3, a coluna 
 * é 6 e o tamanho do texto é 16, ou seja, a combinação de todos os tokens anteriores.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class TrechoCodigoFonte
{
    private final int linha;
    private final int coluna;
    private final int tamanhoTexto;

    /**
     * 
     * @param linha            a linha onde o token inicia.
     * @param coluna           a coluna onde o token inicia.
     * @param tamanhoTexto     o tamanho do token.
     * @since 1.0
     */
    public TrechoCodigoFonte(int linha, int coluna, int tamanhoTexto)
    {
        this.linha = linha;
        this.coluna = coluna;
        this.tamanhoTexto = tamanhoTexto;
    }
    
    public TrechoCodigoFonte(TrechoCodigoFonte outroTrecho, int tamanho)
    {
        this.linha = outroTrecho.getLinha();
        this.coluna = outroTrecho.getColuna();
        this.tamanhoTexto = tamanho;
    }
    
    /** Obtém a coluna onde o token inicia.
     * 
     * @return     a coluna onde o token inicia.
     * @since 1.0
     */
    public int getColuna()
    {
        return coluna;
    }

    /**
     * Obtém a linha onde o token inicia.
     * 
     * @return     a linha onde o token inicia.
     * @since 1.0
     */
    public int getLinha()
    {
        return linha;
    }

    /**
     * Obtém o tamanho do token.
     * 
     * @return     o tamanho do token.
     * @since 1.0
     */
    public int getTamanhoTexto()
    {
        return tamanhoTexto;
    }
    
    public boolean ehValido()
    {
        return linha >= 0 && coluna >= 0;
    }
}
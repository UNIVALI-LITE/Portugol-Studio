package br.univali.portugol.nucleo.asa;

/**
 * Representa uma declaração de variável no código fonte.
 * <p>
 * Exemplo:  <code><pre>
 *      funcao exemploDeclaracaoVariavel()
 *      {
 *           inteiro a              // Isso é uma declaração de variável
 *           inteiro b = 20         // Isso é uma declaração de variável com inicialização
 *           inteiro c, d = 10, e   // Isso é a declaração de várias variáveis em uma única linha
 *      }
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoDeclaracaoVariavel extends NoDeclaracaoInicializavel
{
    private int indiceReferencia = -1;
    
    
    /**
     *
     * @param nome define o nome detsa variável.
     *
     * @param tipoDado define o tipo de dado desta variável.
     *
     * @param constante define se o valor desta variável será constante ou não.
     *
     * Se <code>true</code>, o valor não poderá ser alterado após a
     * inicialização.
     *
     * @since 1.0
     */
    public NoDeclaracaoVariavel(String nome, TipoDado tipoDado, boolean constante)
    {
        super(nome, tipoDado, constante);
    }
    
    public NoDeclaracaoVariavel(String nome, TipoDado tipoDado)
    {
        super(nome, tipoDado, false);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }

//    @Override
//    public TrechoCodigoFonte getTrechoCodigoFonte()
//    {
//        if (super.getTrechoCodigoFonte() == TRECHO_NULO)
//        {
//            NoExpressao inicializacao = getInicializacao();
//            if (inicializacao != null)
//            {
//                return inicializacao.getTrechoCodigoFonte();
//            }
//        }
//        return TRECHO_NULO;
//    }

    public boolean ehPassadaPorReferencia()
    {
        return indiceReferencia >= 0;
    }

    public int getIndiceReferencia()
    {
        return indiceReferencia;
    }

    public void setIndiceReferencia(int indiceReferencia)
    {
        this.indiceReferencia = indiceReferencia;
    }
}

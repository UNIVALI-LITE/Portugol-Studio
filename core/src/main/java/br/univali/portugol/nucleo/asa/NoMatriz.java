package br.univali.portugol.nucleo.asa;

import java.util.List;

/**
 * Representa uma matriz no código fonte.
 * <p>
 * Este nó da ASA representa uma matriz literal no código fonte. Uma matriz
 * literal é declarada da seguinte forma:
 *
 * <code><pre>
 *
 *    funcao exemploMatrizLiteral()
 *    {
 *        inteiro matriz[][] =
 *        {
 *          { 1, 2, 3 },
 *          { 4, 5, 6 },
 *          { 7, 8, 9 }
 *        }
 *    }
 *
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoMatriz extends NoExpressao
{
    private List<List<Object>> valores;
    private TrechoCodigoFonte trechoCodigoFonte;

    /**
     *
     * @param valores uma lista de listas contendo os valores da matriz.
     * @since 1.0
     */
    public NoMatriz(List<List<Object>> valores)
    {
        this.valores = valores;
    }

    /**
     * Obtém os valores armazenados nesta matriz. O valor retornado é uma lista
     * contendo outras listas. Cada lista representa uma linha da matriz e cada
     * valor da lista corresponde a uma coluna.
     *
     * @return os valores armazenados na matriz.
     * @since 1.0
     */
    public List<List<Object>> getValores()
    {
        return valores;
    }

    /**
     * Define o trecho do código fonte no qual este vetor se encontra.
     *
     * @param trechoCodigoFonte o trecho do código fonte no qual este vetor se
     * encontra.
     * @since 1.0
     */
    public void setTrechoCodigoFonte(TrechoCodigoFonte trechoCodigoFonte)
    {
        this.trechoCodigoFonte = trechoCodigoFonte;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected TrechoCodigoFonte montarTrechoCodigoFonte()
    {
        return trechoCodigoFonte;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }

    @Override
    public TipoDado getTipoResultante()
    {
        
        if (!valores.isEmpty() && !valores.get(0).isEmpty())
        {
            Object valor = valores.get(0).get(0);
            if (valor instanceof NoExpressaoLiteral)
            {
                return ((NoExpressaoLiteral)valor).getTipoResultante();
            }
            else if(valor instanceof NoReferencia){
                return ((NoReferencia)valor).getOrigemDaReferencia().getTipoDado();
            }
            
        }
        
        return TipoDado.VAZIO;
    }

    public NoMatriz getInicializacao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

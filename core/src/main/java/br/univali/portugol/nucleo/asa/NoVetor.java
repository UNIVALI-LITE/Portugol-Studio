package br.univali.portugol.nucleo.asa;

import java.util.List;

/**
 * Representa um vetor no código fonte.
 * <p>
 * Este nó da ASA representa um vetor literal no código fonte.
 * Um vetor literal é declarado da seguinte forma:
 * 
 * <code><pre>
 * 
 *      funcao exemploVetorLiteral()
 *      {
 *           inteiro vetor[5] = {1, 2, 3, 4, 5}
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoVetor extends NoExpressao
{
    private TrechoCodigoFonte trechoCodigoFonte;
    private List<Object> valores;

    /**
     * 
     * @param valores     uma lista contendo os valores do vetor.
     * @since 1.0
     */
    public NoVetor(List<Object> valores)
    {
        this.valores = valores;
    }

    /**
     * Obtém os valores armazenados neste vetor.
     * 
     * @return     os valores armazenados neste vetor.
     * @since 1.0
     */
    public List<Object> getValores()
    {
        return valores;
    }

    /**
     * Define o trecho do código fonte no qual este vetor se encontra.
     * 
     * @param trechoCodigoFonte     o trecho do código fonte no qual este vetor se encontra.
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
        if (!valores.isEmpty())
        {
            Object valor = valores.get(0);
            if (valor instanceof NoExpressao)
            {
                return ((NoExpressao)valor).getTipoResultante();
            }
            else if(valor instanceof NoReferencia){
                return ((NoReferencia)valor).getOrigemDaReferencia().getTipoDado();
            }
        }
        return TipoDado.VAZIO;
    }
}

package br.univali.portugol.nucleo.asa;

/**
 * Representa o comando <code>retorne</code> no código fonte.
 * <p>
 * O comando <code>retorne</code> é utilizado dentro das funções para interromper
 * o processamento da função e retornar ao comando que chamou a função, isto significa
 * que, qualquer comando declarado no código fonte após o comando <code>retorne</code> 
 * não será executado.
 * <p> 
 * Este comando também é utilizado para retornar valores nas funções que esperam retorno
 * de valores e pode ser utilizado ainda para interromper laços de repetição quando uma 
 * determinada condição é satisfeita.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      programa
 *      {
 *           funcao exemploRetorne()
 *           {
 *                funcaoComRetorno()
 *                funcaoSemRetorno()
 *           }
 *  
 *           funcao inteiro funcaoComRetorno(inteiro a)
 *           {
 *                retorne a - 1
 *           }
 * 
 *           funcao funcaoSemRetorno()
 *           {
 *                escreva("Isto será executado! :-)")
 *                retorne
 *                escreva("Isto não será executado! :-( ")
 *           }
 * 
 *           funcao interromperLaco()
 *           {
 *               enquanto (verdadeiro)
 *               {
 *                    escreva("Isto só será executado uma vez")
 *                    retorne
 *               }
 *           }
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * @see NoPare
 */
public final class NoRetorne extends NoBloco
{
    private NoExpressao expressao;
    private TrechoCodigoFonte trechoRetorne = null;
    private TrechoCodigoFonte trechoExpressao = null;
    /**
     * 
     * @param expressao     a expressão que será retornada.
     */
    public NoRetorne(TrechoCodigoFonte trechoCodigoFonte, NoExpressao expressao)
    {
        this.trechoRetorne = trechoCodigoFonte;
        this.expressao = expressao;
    }

    /**
     * Obtém a expressão que será retornada.
     * 
     * @return     a expressão que será retornada.
     */
    public NoExpressao getExpressao()
    {
        return expressao;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }
    
    /**
     * Obtém o trecho do código fonte no qual esta expressão se encontra.
     * 
     * @return     o trecho do código fonte no qual esta expressão se encontra.
     * @since 1.0
     */    
    public final TrechoCodigoFonte getTrechoCodigoFonte()
    {
        if (expressao != null && trechoExpressao == null)
        {
            int linha = trechoRetorne.getLinha();
            int coluna = trechoRetorne.getColuna();
            trechoExpressao = expressao.getTrechoCodigoFonte();
        
            int colunaOpDireito = trechoExpressao.getColuna();
            int tamanhoOpDireito = trechoExpressao.getTamanhoTexto();
            int terminoOpDireito = colunaOpDireito + tamanhoOpDireito;

            int tamanhoTexto = terminoOpDireito - coluna;
            
            trechoRetorne = new TrechoCodigoFonte(linha, coluna, tamanhoTexto);
        }

        return trechoRetorne;
    }
    
    
}
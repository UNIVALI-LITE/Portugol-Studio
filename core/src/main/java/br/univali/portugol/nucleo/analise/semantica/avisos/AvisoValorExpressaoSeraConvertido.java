package br.univali.portugol.nucleo.analise.semantica.avisos;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;

/**
 * Aviso gerado pelo analisador semântico quando uma expressão será convertida
 * para outro tipo de dado.
 * <p>
 * Consulte o documento <a href='../asa/doc-files/compatibilidade_tipos.xls' target='blank'>Compatibilidade de tipos do Portugol</a> 
 * para obter mais detalhes.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 * @see TipoDado
 */
public final class AvisoValorExpressaoSeraConvertido extends AvisoAnalise
{
    private NoExpressao expressao;
    private TipoDado tipoExpressao;
    private TipoDado tipoConversao;
    private NoRetorne noRetorne = null;
    private Object[] detalhes;

    /**
     * 
     * @param expressao     a expressão que está sendo convertida.
     * @since 1.0
     */
    public AvisoValorExpressaoSeraConvertido(NoExpressao expressao, TipoDado tipoExpressao, TipoDado tipoConversao, Object...detalhes)
    {
        super(expressao.getTrechoCodigoFonte());
        
        this.expressao = expressao;
        this.tipoExpressao = tipoExpressao;
        this.tipoConversao = tipoConversao;
        this.detalhes = detalhes;                
    }

    public AvisoValorExpressaoSeraConvertido(TrechoCodigoFonte trechoCodigoFonte, NoExpressao expressao, TipoDado tipoExpressao, TipoDado tipoConversao, Object...detalhes)
    {
        super(trechoCodigoFonte);
        
        this.expressao = expressao;
        this.tipoExpressao = tipoExpressao;
        this.tipoConversao = tipoConversao;
        this.detalhes = detalhes;                
    }
    
    public AvisoValorExpressaoSeraConvertido(NoRetorne noRetorne, TipoDado tipoExpressao, TipoDado tipoConversao, Object...detalhes)
    {
        super(noRetorne.getTrechoCodigoFonte());
        this.noRetorne = noRetorne;
        this.expressao = noRetorne.getExpressao();
        this.tipoExpressao = tipoExpressao;
        this.tipoConversao = tipoConversao;
        this.detalhes = detalhes;                
    }
    
    /**
     * Obtém a expressão que está sendo convertida.
     * 
     * @return     a expressão que está sendo convertida.
     * @since 1.0
     */
    public NoExpressao getExpressao()
    {
        return expressao;
    }

    /**
     * O tipo de dado atual da expressão.
     * 
     * @return  o tipo de dado da expressão.
     */
    public TipoDado getTipoExpressao()
    {
        return tipoExpressao;
    }

    /**
     * O tipo de dado para o qual a expressão
     * será convertida.
     * 
     * @return  o tipo de dado da expressão após a conversão.
     */
    public TipoDado getTipoConversao()
    {
        return tipoConversao;
    }
    
    /**
     * {@inheritDoc }
     */    
    @Override
    protected String construirMensagem()
    {
        return new AvisoValorExpressaoSeraConvertido.ConstrutorMensagem().construirMensagem();
    }
    
    private class ConstrutorMensagem extends VisitanteASABasico
    {
        public ConstrutorMensagem()
        {

        }
        
        public String construirMensagem()
        {
            try
            {
                if (noRetorne == null) {
                    return (String) expressao.aceitar(this);
                } else {
                    return (String) noRetorne.aceitar(this);
                }
            }
            catch (ExcecaoVisitaASA e)
            {
                return e.getMessage();
            }
        }

        @Override
        public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA
        {
            String nomeFuncao = (String) detalhes[0];
                    
            if (tipoExpressao == TipoDado.REAL && tipoConversao == TipoDado.INTEIRO)
            {
                return String.format("O valor da expressão retornada na função \"%s\" será truncado", nomeFuncao);
            }
            return String.format("O valor da expressão retornada na função \"%s\" será automaticamente convertido de \"%s\" para \"%s\"", nomeFuncao, tipoExpressao, tipoConversao);
        }

        @Override
        public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA
        {
            if (noOperacaoAtribuicao.getOperandoDireito() instanceof NoVetor)
            {
                if (tipoExpressao == TipoDado.REAL && tipoConversao == TipoDado.INTEIRO)
                {
                    return "O valores do vetor à direita da atribuição serão truncados";
                }

                return String.format("O valores do vetor à direita da atribuição serão automaticamente convertidos de \"%s\" para \"%s\"", tipoExpressao, tipoConversao);
            }
            else if (noOperacaoAtribuicao.getOperandoDireito() instanceof NoMatriz)
            {
                if (tipoExpressao == TipoDado.REAL && tipoConversao == TipoDado.INTEIRO)
                {
                    return "O valores da matriz à direita da atribuição serão truncados";
                }

                return String.format("O valores da matriz à direita da atribuição serão automaticamente convertidos de \"%s\" para \"%s\"", tipoExpressao, tipoConversao);
            }
            else
            {            
                if (tipoExpressao == TipoDado.REAL && tipoConversao == TipoDado.INTEIRO)
                {
                    return "O valor da expressão á direita da atribuição será truncado";
                }

                return String.format("O valor da expressão á direita da atribuição será automaticamente convertido de \"%s\" para \"%s\"", tipoExpressao, tipoConversao);
            }
        }

        @Override
        public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
        {
            String nomeFuncao = chamadaFuncao.getNome();
            String nomeParametro = (String) detalhes[0];
                    
            if (tipoExpressao == TipoDado.REAL && tipoConversao == TipoDado.INTEIRO)
            {
                return String.format("O valor da expressão passada para o parâmetro \"%s\" da função \"%s\" será truncado", nomeParametro, nomeFuncao);
            }
            
            return String.format("O valor da expressão passada para o parâmetro \"%s\" da função \"%s\" será automaticamente convertido de \"%s\" para \"%s\"", nomeParametro, nomeFuncao, tipoExpressao, tipoConversao);
        }
    }
}
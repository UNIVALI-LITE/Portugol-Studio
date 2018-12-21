package br.univali.portugol.nucleo.analise.semantica.avisos;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
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
    private String codigo = "AvisoSemantico.AvisoValorExpressaoSeraConvertido";

    /**
     * 
     * @param expressao     a expressão que está sendo convertida.
     * @since 1.0
     */
    public AvisoValorExpressaoSeraConvertido(
            NoExpressao expressao,
            TipoDado tipoExpressao,
            TipoDado tipoConversao,
            Object...detalhes)
    {
        super(expressao.getTrechoCodigoFonte());
        
        this.expressao = expressao;
        this.tipoExpressao = tipoExpressao;
        this.tipoConversao = tipoConversao;
        this.detalhes = detalhes;   
        super.setCodigo(codigo);
    }

    public AvisoValorExpressaoSeraConvertido(
            TrechoCodigoFonte trechoCodigoFonte,
            NoExpressao expressao,
            TipoDado tipoExpressao,
            TipoDado tipoConversao,
            Object...detalhes)
    {
        super(trechoCodigoFonte);
        
        this.expressao = expressao;
        this.tipoExpressao = tipoExpressao;
        this.tipoConversao = tipoConversao;
        this.detalhes = detalhes; 
        super.setCodigo(codigo);
    }
    
    public AvisoValorExpressaoSeraConvertido(
            NoRetorne noRetorne,
            TipoDado tipoExpressao,
            TipoDado tipoConversao,
            Object...detalhes)
    {
        super(noRetorne.getTrechoCodigoFonte());
        this.noRetorne = noRetorne;
        this.expressao = noRetorne.getExpressao();
        this.tipoExpressao = tipoExpressao;
        this.tipoConversao = tipoConversao;
        this.detalhes = detalhes;   
        super.setCodigo(codigo);
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
        return new ConstrutorMensagem(this).getMensagem();
    }

    NoRetorne getNoRetorne() {
        return noRetorne;
    }

    Object getDetalhe(final int index) {
        return detalhes[index];
    }
}
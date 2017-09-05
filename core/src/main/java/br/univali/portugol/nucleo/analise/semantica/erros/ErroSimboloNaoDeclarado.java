package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoReferencia;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 * Erro gerado pelo analisador semântico ao encontrar uma referência a um símbolo que não foi declarado ou não
 * está acessível no escopo atual. Por símbolo entende-se, uma variável, vetor, matriz ou função.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploSimboloNaoDeclarado()
 *      {
 *           inteiro num1
 * 
 *           num1 = num1 + 5        // Não gera erro, pois a variável 'num1' foi declarada neste escopo
 *           num2 = num2 - 4        // Gera erro pois a variável 'num2' não foi declarada neste escopo
 *          
 *           enquanto (verdadeiro)
 *           { 
 *                inteiro num3
 *                num3 = num3 * 4   // Não gera erro, pois a variável 'num3' foi declarada neste escopo
 *           }
 * 
 *           num3 = num3 * 4        // Gera erro, pois a variável 'num3' só existe no escopo do laço 'enquanto'
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 * @see NoDeclaracao
 */

public final class ErroSimboloNaoDeclarado extends ErroSemantico
{
    private NoReferencia referencia;
    private String codigo = "ErroSemantico.ErroSimboloNaoDeclarado.";

    /**
     * @param referencia     a referência ao símbolo inexistente.
     */
    public ErroSimboloNaoDeclarado(NoReferencia referencia)
    {
        super(referencia.getTrechoCodigoFonteNome());

        this.referencia = referencia;
    }

    /**
     * Obtém a referência ao símbolo inexistente.
     * 
     * @return     a referência ao símbolo inexistente.
     * @since 1.0
     */
    public NoReferencia getReferencia()
    {
        return referencia;
    }

    
     /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {        
        String msg = new ErroSimboloNaoDeclarado.ConstrutorMensagem().construirMensagem();
        super.setCodigo(codigo);
        return msg;
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
                return (String) referencia.aceitar(this);
            }
            catch (ExcecaoVisitaASA e)
            {
                return e.getMessage();
            }
        }
        
        /**
         * Constrói uma mensagem de erro personalizada para uma declaração de vetor.
         *
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         *
         * @see NoDeclaracaoVetor
         */    
        @Override
        public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
        {
            StringBuilder construtorTexto = new StringBuilder();

            construtorTexto.append("O vetor \"");
            construtorTexto.append(referencia.getNome());
            construtorTexto.append("\" não foi declarado neste escopo.");

            codigo += "1";
            return construtorTexto.toString();
        }

        /**
         * Constrói uma mensagem de erro personalizada para uma declaração de matriz.
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoDeclaracaoMatriz
         */        
        @Override
        public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("A matriz \"");
            construtorString.append(referencia.getNome());
            construtorString.append("\" não foi declarada neste escopo.");

            codigo += "2";
            return construtorString.toString();
        }  

        /**
         * Constrói uma mensagem de erro personalizada para uma declaração de variável.
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoDeclaracaoVariavel
         */        
        @Override
        public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("A variável \"");
            construtorString.append(referencia.getNome());
            construtorString.append("\" não foi declarada neste escopo.");

            codigo += "3";
            return construtorString.toString();
        }

        /**
         * Constrói uma mensagem de erro personalizada para uma declaração de função.
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoDeclaracaoFuncao
         */        
        @Override
        public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("A função \"");
            construtorString.append(referencia.getNome());
            
            if (referencia.getEscopo() == null)
            {
                
                codigo += "4";
                construtorString.append("\" não foi declarada no programa");
            }
            else 
            {
                construtorString.append(String.format("\" não existe na biblioteca \"%s\"", chamadaFuncao.getEscopo()));
            }

            return construtorString.toString();
        }
    }
}
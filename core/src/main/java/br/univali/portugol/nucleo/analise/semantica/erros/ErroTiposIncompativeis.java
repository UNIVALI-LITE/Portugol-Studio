package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCaso;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoEscolha;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoMenosUnario;
import br.univali.portugol.nucleo.asa.NoNao;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseLeftShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseRightShift;
import br.univali.portugol.nucleo.asa.NoOperacaoDivisao;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaDiferenca;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaE;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaIgualdade;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaior;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaiorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenor;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaOU;
import br.univali.portugol.nucleo.asa.NoOperacaoModulo;
import br.univali.portugol.nucleo.asa.NoOperacaoMultiplicacao;
import br.univali.portugol.nucleo.asa.NoOperacaoSoma;
import br.univali.portugol.nucleo.asa.NoOperacaoSubtracao;
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 * Erro gerado pelo analisador semântico quando um comando utiliza dois ou mais 
 * tipos de dados incompatíveis entre si
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploTiposIncompativeis()
 *      {
 *           inteiro valor1 = 10
 *           logico valor2 = falso
 *           real valor3 = 2.34
 * 
 *           valor3 = valor1 + valor2       // Gera erro, pois a operação de soma não é suportada entre um inteiro e um lógico
 *           valor2 = valor3                // Gera erro, pois a operação de atribuição não é suportada entre um lógico e um real
 *           valor3 = valor1                // Não gera erro pois a operação de atribuição entre um real e um inteiro é suportada
 * 
 *           enquanto(5.46)                 // Gera erro, pois o comando enquanto espera uma expressão do tipo lógico, mas
 *           {                              // foi passada uma expressão do tipo real
 * 
 *           }
 *      }
 * 
 * </pre></code>
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 */
public final class ErroTiposIncompativeis extends ErroSemantico
{
    private NoBloco bloco;
    private TipoDado[] tiposDado;
    private String[] detalhes;
    private String codigo = "ErroSemantico.ErroTiposIncompativeis.";
    
    /**
     * 
     * @param bloco                        o bloco que gerou o erro
     * @since 1.0
     */
    public ErroTiposIncompativeis(NoBloco bloco, TipoDado...tiposDado)
    {
        this.bloco = bloco;
        this.tiposDado = tiposDado;
        getMensagem();
    }

    
    /**
     * Construtor alternativo que permite incluir informações adicionais sobre o 
     * erro que não puderam ser obtidas apenas a partir do nó da árvore
     * 
     * @param bloco                         o nó que gerou o erro
     * @param detalhes                      as informações adicionais sobre o erro
     * @param tiposDado                     os tipos de dados que são imcompatíveis entre si
     * 
     * @return      a própria instancia do erro.
     * @since 1.0
     */
    public ErroTiposIncompativeis(NoBloco bloco, String detalhes[], TipoDado...tiposDado)
    {
        this.bloco = bloco;
        this.tiposDado = tiposDado;
        this.detalhes = detalhes;
        getMensagem();
    }    
    
    /**
     * Obtém o bloco onde ocorreu o erro.
     * 
     * @return      o bloco onde ocorreu o erro.
     * @since 1.0
     */
    public NoBloco getBloco()
    {
        return bloco;
    }
    
    /**
     * Obtém os tipos de dados envolvidos no erro.
     * 
     * @return      os tipos de dado envolvidos no erro.
     * @since 1.0
     */
    public TipoDado[] getTiposDado()
    {
        return tiposDado;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {        
        String msg = new ConstrutorMensagem().construirMensagem();
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
                return (String) bloco.aceitar(this);
            }
            catch (ExcecaoVisitaASA e)
            {
                return e.getMessage();
            }
        }        

        /**
         * Constrói uma mensagem de erro personalizada para a operação de atribuição (=).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoAtribuicao
         */        
        @Override
        public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível atribuir uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\" à uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");
            
            codigo +="1";
            
            setLinha(noOperacaoAtribuicao.getOperandoDireito().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoAtribuicao.getOperandoDireito().getTrechoCodigoFonte().getColuna());
            setTrechoCodigoFonte(noOperacaoAtribuicao.getOperandoDireito().getTrechoCodigoFonte());
            return construtorString.toString();
        }

        @Override
        public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();
            construtorString.append("Tipos incompatíveis! ");
            if(tiposDado[0] != TipoDado.INTEIRO){
                construtorString.append("Não é possível deslocar os bits de um valor não inteiro. ");
                setLinha(noOperacaoBitwiseLeftShift.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
                setColuna(noOperacaoBitwiseLeftShift.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
                setTrechoCodigoFonte(noOperacaoBitwiseLeftShift.getOperandoEsquerdo().getTrechoCodigoFonte());
            }
            if(tiposDado[1] != TipoDado.INTEIRO){
                construtorString.append("É necessário um valor inteiro de bits a ser deslocado.");
                setLinha(noOperacaoBitwiseLeftShift.getOperandoDireito().getTrechoCodigoFonte().getLinha());
                setColuna(noOperacaoBitwiseLeftShift.getOperandoDireito().getTrechoCodigoFonte().getColuna());
                setTrechoCodigoFonte(noOperacaoBitwiseLeftShift.getOperandoDireito().getTrechoCodigoFonte());
            }
            codigo +="1";
            if(tiposDado[0] == TipoDado.INTEIRO){
                construtorString.append(" Exemplo: "+noOperacaoBitwiseLeftShift.getOperandoEsquerdo().toString()+" << 2");
            }else{
                construtorString.append(" Exemplo: variavel << 2");
            }
            return construtorString.toString();
        }

        @Override
        public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();
            construtorString.append("Tipos incompatíveis!");
            if(tiposDado[0] != TipoDado.INTEIRO){
                construtorString.append(" Não é possível deslocar os bits de um valor não inteiro.");
                setLinha(noOperacaoBitwiseRightShift.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
                setColuna(noOperacaoBitwiseRightShift.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
                setTrechoCodigoFonte(noOperacaoBitwiseRightShift.getOperandoEsquerdo().getTrechoCodigoFonte());
                
            }
            if(tiposDado[1] != TipoDado.INTEIRO){
                construtorString.append(" É necessário um valor inteiro de bits a ser deslocado.");
                setLinha(noOperacaoBitwiseRightShift.getOperandoDireito().getTrechoCodigoFonte().getLinha());
                setColuna(noOperacaoBitwiseRightShift.getOperandoDireito().getTrechoCodigoFonte().getColuna());
                setTrechoCodigoFonte(noOperacaoBitwiseRightShift.getOperandoDireito().getTrechoCodigoFonte());
            }
            if(tiposDado[0] == TipoDado.INTEIRO){
                construtorString.append(" Exemplo: "+noOperacaoBitwiseRightShift.getOperandoEsquerdo().toString()+" >> 2");
            }else{
                construtorString.append(" Exemplo: variavel >> 2");
            }
            
            codigo +="1";
            
            
            return construtorString.toString();
        }
        
        
        

        @Override
        public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA
        {
           StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! A operação \"menos unário\" espera uma expressão do tipo \"");
            if (tiposDado.length == 3){
                construtorString.append(tiposDado[1]);
                construtorString.append("\" ou \"");
                construtorString.append(tiposDado[2]);
            }
            construtorString.append("\" mas foi passada uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");

            codigo +="2";
            
            setLinha(noMenosUnario.getExpressao().getTrechoCodigoFonte().getLinha());
            setColuna(noMenosUnario.getExpressao().getTrechoCodigoFonte().getColuna());
            setTrechoCodigoFonte(noMenosUnario.getTrechoCodigoFonte());
            return construtorString.toString();
        }

        @Override
        public Object visitar(NoNao noNao) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! A operação de negação espera uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\" mas foi passada uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");
            
            codigo +="3";
            
            setLinha(noNao.getTrechoCodigoFonte().getLinha());
            setColuna(noNao.getTrechoCodigoFonte().getColuna());
            setTrechoCodigoFonte(noNao.getTrechoCodigoFonte());
            return construtorString.toString();
        }

        
        /**
         * Constrói uma mensagem de erro personalizada para a operação de diferença (!=).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoLogicaDiferenca
         */
        @Override
        public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível comparar a diferença entre uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" e uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");
            
            codigo +="4";
            
            setLinha(noOperacaoLogicaDiferenca.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoLogicaDiferenca.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            setTrechoCodigoFonte(noOperacaoLogicaDiferenca.getTrechoCodigoFonte());
            return construtorString.toString();
        }        

        /**
         * Constrói uma mensagem de erro personalizada para a operação de divisão (/).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoDivisao
         */
        @Override
        public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível dividir uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" por uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");
            
            codigo +="5";
            
            setLinha(noOperacaoDivisao.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoDivisao.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            setTrechoCodigoFonte(noOperacaoDivisao.getTrechoCodigoFonte());
            return construtorString.toString();
        }

        /**
         * Constrói uma mensagem de erro personalizada para a operação lógica E (e).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoLogicaE
         */
        @Override
        public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível executar a operação lógica E entre uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" e uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");
                        
            codigo +="6";
            
            setLinha(noOperacaoLogicaE.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoLogicaE.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            setTrechoCodigoFonte(noOperacaoLogicaE.getTrechoCodigoFonte());
            return construtorString.toString();
        }
        
        /**
         * Constrói uma mensagem de erro personalizada para a comparação de igualdade (==).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoLogicaIgualdade
         */
        @Override
        public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível comparar a igualdade entre uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" e uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");

            codigo +="7";
            
            setLinha(noOperacaoLogicaIgualdade.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoLogicaIgualdade.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            setTrechoCodigoFonte(noOperacaoLogicaIgualdade.getTrechoCodigoFonte());
            return construtorString.toString();
        }
        
        /**
        * Constrói uma mensagem de erro personalizada para a comparação lógica MAIOR (&gt;).
        * 
        * @return     a mensagem de erro personalizada.
        * @since 1.0
        * 
        * @see NoOperacaoLogicaMaior
        */ 
        @Override
        public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível comparar uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" com uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");

            codigo +="8";
            
            setLinha(noOperacaoLogicaMaior.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoLogicaMaior.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            setTrechoCodigoFonte(noOperacaoLogicaMaior.getTrechoCodigoFonte());
            return construtorString.toString();
        }

        /**
         * Constrói uma mensagem de erro personalizada para a comparação lógica MAIOR OU IGUAL (&gt;=).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoLogicaMaiorIgual
         */        
        @Override
        public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível comparar uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" com uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");
            
            codigo +="9";
            
            setLinha(noOperacaoLogicaMaiorIgual.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoLogicaMaiorIgual.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            setTrechoCodigoFonte(noOperacaoLogicaMaiorIgual.getTrechoCodigoFonte());
            return construtorString.toString();
        }

        /**
         * Constrói uma mensagem de erro personalizada para a operação lógica MENOR (&lt;).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoLogicaMenor
         */
        @Override
        public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível comparar uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" com uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");

            codigo +="10";
            
            setLinha(noOperacaoLogicaMenor.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoLogicaMenor.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            
            setTrechoCodigoFonte(noOperacaoLogicaMenor.getTrechoCodigoFonte());
            return construtorString.toString();
        }

        /**
         * Constrói uma mensagem de erro personalizada para a comparação lógica MENOR OU IGUAL (&lt;=).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoLogicaMenorIgual
         */
        @Override
        public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível comparar uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" com uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");

            codigo +="11";
            
            setLinha(noOperacaoLogicaMenorIgual.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoLogicaMenorIgual.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            
            setTrechoCodigoFonte(noOperacaoLogicaMenorIgual.getTrechoCodigoFonte());
            return construtorString.toString();
        }
        
        /**
         * Constrói uma mensagem de erro personalizada para a operação de módulo (%).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoModulo
         */
        @Override
        public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível obter o módulo entre uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" e uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");
            
            codigo +="12";
            
            setLinha(noOperacaoModulo.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoModulo.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());

            setTrechoCodigoFonte(noOperacaoModulo.getTrechoCodigoFonte());
            return construtorString.toString();
        }

        /**
         * Constrói uma mensagem de erro personalizada para a operação de multiplicação (*).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoMultiplicacao
         */
        @Override
        public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível multiplicar uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" por uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");

            codigo +="13";
            
            setLinha(noOperacaoMultiplicacao.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoMultiplicacao.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            
            setTrechoCodigoFonte(noOperacaoMultiplicacao.getTrechoCodigoFonte());
            return construtorString.toString();
        }

        /**
         * Constrói uma mensagem de erro personalizada para a operação lógica OU (ou).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoLogicaOU
         */ 
        @Override
        public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível executar a operação lógica OU entre uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" e uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");

            codigo +="14";
            
            setLinha(noOperacaoLogicaOU.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoLogicaOU.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            
            setTrechoCodigoFonte(noOperacaoLogicaOU.getTrechoCodigoFonte());
            return construtorString.toString();
        }

        /**
         * Constrói uma mensagem de erro personalizada para a operação de soma (+).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoSoma
         */ 
        @Override
        public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível somar uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\" à uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");

            codigo +="15";
            
            setLinha(noOperacaoSoma.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoSoma.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            
            setTrechoCodigoFonte(noOperacaoSoma.getTrechoCodigoFonte());
            return construtorString.toString();
        }
        
        /**
         * Constrói uma mensagem de erro personalizada para a operação de subtração (-).
         * 
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         * 
         * @see NoOperacaoSubtracao
         */
        @Override
        public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! Não é possível subtrair uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\" de uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");

            codigo +="16";
            
            setLinha(noOperacaoSubtracao.getOperandoEsquerdo().getTrechoCodigoFonte().getLinha());
            setColuna(noOperacaoSubtracao.getOperandoEsquerdo().getTrechoCodigoFonte().getColuna());
            
            setTrechoCodigoFonte(noOperacaoSubtracao.getTrechoCodigoFonte());
            return construtorString.toString();
        }
        
        @Override
        public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! O comando \"escolha\" espera uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\" ou \"");
            construtorString.append(tiposDado[2]);
            construtorString.append("\" mas foi passada uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");

            codigo +="17";
            
            setLinha(noEscolha.getExpressao().getTrechoCodigoFonte().getLinha());
            setColuna(noEscolha.getExpressao().getTrechoCodigoFonte().getColuna());
            
            setTrechoCodigoFonte(noEscolha.getExpressao().getTrechoCodigoFonte());
            return construtorString.toString();
        }

        @Override
        public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! A expressão esperada para esse caso deveria ser do tipo \"");
            if (tiposDado.length == 3){
                construtorString.append(tiposDado[1]);
                construtorString.append("\" ou \"");
                construtorString.append(tiposDado[2]);
            } else {
                construtorString.append(tiposDado[1]);
            }
            construtorString.append("\" mas foi passada uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");

            codigo +="18";
            
            setLinha(noCaso.getExpressao().getTrechoCodigoFonte().getLinha());
            setColuna(noCaso.getExpressao().getTrechoCodigoFonte().getColuna());
            
            setTrechoCodigoFonte(noCaso.getExpressao().getTrechoCodigoFonte());
            return construtorString.toString();
        }

        
        
        @Override
        public Object visitar(NoSe noSe) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! O comando \"se\" espera uma expressão do tipo \"");
            construtorString.append(TipoDado.LOGICO);
            construtorString.append("\" mas foi passada uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");
            
            codigo +="19";
            
            setLinha(noSe.getCondicao().getTrechoCodigoFonte().getLinha());
            setColuna(noSe.getCondicao().getTrechoCodigoFonte().getColuna());

            setTrechoCodigoFonte(noSe.getCondicao().getTrechoCodigoFonte());
            return construtorString.toString();
        }

        @Override
        public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! O comando \"enquanto\" espera uma expressão do tipo \"");
            construtorString.append(TipoDado.LOGICO);
            construtorString.append("\" mas foi passada uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");
            
            codigo +="20";
            
            setLinha(noEnquanto.getCondicao().getTrechoCodigoFonte().getLinha());
            setColuna(noEnquanto.getCondicao().getTrechoCodigoFonte().getColuna());

            setTrechoCodigoFonte(noEnquanto.getCondicao().getTrechoCodigoFonte());
            return construtorString.toString();
        }
        
        @Override
        public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! A condição do comando \"faca enquanto\" espera uma expressão do tipo \"");
            construtorString.append(TipoDado.LOGICO);
            construtorString.append("\" mas foi passada uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");
            
            codigo +="21";
            
            setLinha(noFacaEnquanto.getCondicao().getTrechoCodigoFonte().getLinha());
            setColuna(noFacaEnquanto.getCondicao().getTrechoCodigoFonte().getColuna());

            setTrechoCodigoFonte(noFacaEnquanto.getCondicao().getTrechoCodigoFonte());
            return construtorString.toString();
        }

        @Override
        public Object visitar(NoPara noPara) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! A expressão utilizada na condição do comando \"para\" espera uma expressão do tipo \"");
            construtorString.append(TipoDado.LOGICO);
            construtorString.append("\" mas foi passada uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");
            
            codigo +="22";
            
            setLinha(noPara.getCondicao().getTrechoCodigoFonte().getLinha());
            setColuna(noPara.getCondicao().getTrechoCodigoFonte().getColuna());

            setTrechoCodigoFonte(noPara.getCondicao().getTrechoCodigoFonte());
            return construtorString.toString();
        }

        @Override
        public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! A linha e coluna da matriz devem ser uma expressão do tipo \"");          
            construtorString.append(tiposDado[1]);
            construtorString.append("\" e \"");
            construtorString.append(tiposDado[3]);
            construtorString.append("\" mas foi passada uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\" e \"");
            construtorString.append(tiposDado[2]);
            construtorString.append("\".");
            
            codigo +="23";
            
            setLinha(noReferenciaMatriz.getTrechoCodigoFonte().getLinha());
            setColuna(noReferenciaMatriz.getTrechoCodigoFonte().getColuna());

            setTrechoCodigoFonte(noReferenciaMatriz.getTrechoCodigoFonte());
            return construtorString.toString();
        }

        
        
        @Override
        public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! O índice do vetor deve ser uma expressão do tipo \"");          
            construtorString.append(tiposDado[1]);
            construtorString.append("\" mas foi passada uma expressão do tipo \"");
            construtorString.append(tiposDado[0]);
            construtorString.append("\".");
            
            codigo +="24";
            
            setLinha(noReferenciaVetor.getTrechoCodigoFonte().getLinha());
            setColuna(noReferenciaVetor.getTrechoCodigoFonte().getColuna());

            setTrechoCodigoFonte(noReferenciaVetor.getTrechoCodigoFonte());
            return construtorString.toString();
        }
        
        

        @Override
        public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA
        {
            StringBuilder construtorString = new StringBuilder();

            construtorString.append("Tipos incompatíveis! O retorno da função \"");
            construtorString.append(detalhes[0]);
            construtorString.append("\" é do tipo \"");            
            construtorString.append(tiposDado[0]);
            construtorString.append("\" mas foi retornada uma expressão do tipo \"");
            construtorString.append(tiposDado[1]);
            construtorString.append("\".");
            
            codigo +="25";
            
            setLinha(noRetorne.getTrechoCodigoFonte().getLinha());
            setColuna(noRetorne.getTrechoCodigoFonte().getColuna());

            setTrechoCodigoFonte(noRetorne.getTrechoCodigoFonte());
            return construtorString.toString();
        }
    }
}

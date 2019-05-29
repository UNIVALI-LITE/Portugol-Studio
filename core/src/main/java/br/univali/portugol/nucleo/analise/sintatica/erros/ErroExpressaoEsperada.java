package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.util.Stack;

/**
 * Erro gerado pelo analisador sintático quando uma construção espera uma expressão
 * mas esta expressão não é informada.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploErroExpressao()
 *      {
 *           se ()
 *           {
 *                escreva("A construção 'se' espera uma expressão entre o parêntesis...")
 *                escreva("...se não for informada uma expressão, o analisador sintático gerará este erro!")
 *           }
 * 
 *           enquanto ()
 *           {
 *                escreva("O mesmo se aplica à construção 'enquanto'!")
 *           }
 *      }
 * 
 * </pre></code>
 * 
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */
public final class ErroExpressaoEsperada extends ErroSintatico
{
    private String codigo = "ErroSintatico.ErroExpressaoEsperada.";
    /**
     * 
     * @param linha      a linha ond eo erro ocorreu.
     * @param coluna     a coluna onde o erro ocorreu.
     * @since 1.0
     */
    public ErroExpressaoEsperada(int linha, int coluna)
    {
        super(linha, coluna);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
//        if (estaNoContexto("listaListaExpressoes", pilhaContexto))
//        {
//            super.setCodigo(codigo += "1");
//            return "A linha da matriz não foi informada ou a expressão informada não é uma linha de matriz";
//            
//        }
//        else if (estaNoContexto("vetor", pilhaContexto))
//        {
//            super.setCodigo(codigo += "2");
//            return "O elemento do vetor não foi informado, insira um valor ou uma expressão para corrigir o problema";
//        }
//        else if (estaNoContexto("matriz", pilhaContexto))
//        {
//            super.setCodigo(codigo += "3");
//            return "O elemento não foi informado na linha da matriz, insira um valor ou uma expressão para corrigir o problema";
//        }
//        else if (estaEmUmComando(pilhaContexto))
//        {   
//            String comando = extrairComando(pilhaContexto);
//
//            switch (comando)
//            {
//                case "se": 
//                    super.setCodigo(codigo += "4");                  
//                    return "O comando \"se\" espera uma expressão do tipo lógico entre os parêntesis";
//                case "enquanto": 
//                    super.setCodigo(codigo += "5");
//                    return "O comando \"enquanto\" espera uma expressão do tipo lógico entre os parêntesis";
//                case "facaEnquanto": 
//                    super.setCodigo(codigo += "6");                
//                    return "O comando \"faca-enquanto\" espera uma expressão do tipo lógico entre os parêntesis";
//                case "escolha": 
//                    super.setCodigo(codigo += "7");
//                    return "O comando \"escolha\" espera um valor ou uma expressão";
//            }
//        }
        
        super.setCodigo(codigo += "8");
        return "Era esperada uma expressão";
    }
}

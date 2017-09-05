package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.simbolos.Funcao;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;

/**
 * Erro gerado pelo analisador semântico ao encontrar a declaração de um símbolo com um nome que já
 * está sendo utilizado por outro símbolo no mesmo escopo. Por símbolo entende-se, uma variável, vetor, matriz ou função. 
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploSimboloRedeclarado(inteiro param1)
 *      {
 *           inteiro param1 = 2                      // Gera erro, pois 'param1' já foi declarado como parâmetro da função
 *           inteiro valor1 = 5                      // Não gera erro, pois o nome 'valor1' ainda não foi utilizado por nenhum outro símbolo
 *           inteiro valor1 = 9                      // Gera erro, pois 'valor1' já foi declarado como uma variável
 *           inteiro exemploSimboloRedeclarado = 1   // Gera erro, pois 'exemploSimboloRedeclarado' já foi declarado como uma função
 *  
 *           enquanto (verdadeiro)
 *           {
 *                inteiro teste = 90                 // Não gera erro, pois o nome 'teste' ainda não foi utilizado por nenhum outro símbolo
 *           }
 * 
 *           inteiro teste = 50                      // Não gera erro, pois o símbolo 'teste' só existe no escopo do laço 'enquanto'
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 * @see ErroParametroRedeclarado
 */
public final class ErroSimboloRedeclarado extends ErroSemantico
{
    private Simbolo simboloExistente;
    private Simbolo simboloRedeclarado;
    private String codigo = "ErroSemantico.ErroSimboloRedeclarado";

    /**
     * 
     * @param simboloRedeclarado     o símbolo que foi redeclarado.
     * @param simboloExistente       o símbolo existente.
     * @since 1.0
     */
    public ErroSimboloRedeclarado(Simbolo simboloRedeclarado, Simbolo simboloExistente)
    {
        super(simboloRedeclarado.getTrechoCodigoFonteNome());

        this.simboloRedeclarado = simboloRedeclarado;
        this.simboloExistente = simboloExistente;
    }

    /**
     * Obtém o símbolo que foi redeclarado.
     * 
     * @return     o símbolo que foi redeclarado.
     * @since 1.0
     */
    public Simbolo getSimboloRedeclarado()
    {
        return simboloRedeclarado;
    }

    /**
     * Obtém o simbolo existente.
     * 
     * @return     o símbolo existente.
     * @since 1.0
     */
    public Simbolo getSimboloExistente()
    {
        return simboloExistente;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("O símbolo \"");
        construtorString.append(simboloRedeclarado.getNome());
        construtorString.append("\" já foi declarado como ");

        if (simboloExistente instanceof Vetor) {
            construtorString.append("um vetor");
            codigo +="1";
        }
        else
        if (simboloExistente instanceof Matriz){
            construtorString.append("uma matriz");            
            codigo +="2";
        }
        else
        if (simboloExistente instanceof Variavel){
            construtorString.append("uma variável");
            codigo +="3";
        }
        else
        if (simboloExistente instanceof Funcao){
            construtorString.append("uma função");
            codigo +="4";
        }

        if (simboloExistente.getTrechoCodigoFonteNome() != null) {
            construtorString.append(" na linha: ");
            construtorString.append(simboloExistente.getTrechoCodigoFonteNome().getLinha());
            construtorString.append(", coluna: ");
            construtorString.append(simboloExistente.getTrechoCodigoFonteNome().getColuna());
            construtorString.append(".");
        }
        super.setCodigo(codigo);
        return construtorString.toString();
    }
}

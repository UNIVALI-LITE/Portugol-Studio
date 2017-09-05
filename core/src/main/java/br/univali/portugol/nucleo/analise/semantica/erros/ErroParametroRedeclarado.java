package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.simbolos.Funcao;

/**
 * Erro gerado pelo analisador semântico quando uma função tenta declarar um parâmetro
 * com um nome que já foi utilizado.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *    funcao exemploRedeclaracaoParametro(inteiro valor1, cadeia valor2, real valor1)
 *    {
 *          escreva("Esta declaração de função gera um erro ao encontrar o terceiro parâmetro")
 *    }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 */

public final class ErroParametroRedeclarado extends ErroSemantico
{
    private Funcao funcao;
    private NoDeclaracaoParametro parametro;

    /**
     * 
     * @param parametro     a declaração de parâmetro que gerou o erro.
     * @param funcao        a função que gerou o erro.
     */
    public ErroParametroRedeclarado(NoDeclaracaoParametro parametro, Funcao funcao)
    {
        super(parametro.getTrechoCodigoFonteNome(),"ErroSemantico.ErroParametroRedeclarado");

        this.funcao = funcao;
        this.parametro = parametro;
    }

    /**
     * Obtém a função que gerou o erro.
     * 
     * @return     a função que gerou o erro.
     * @since 1.0
     */
    public Funcao getFuncao()
    {
        return funcao;
    }

    /**
     * Obtém a declaração de parâmetro que gerou o erro.
     * 
     * @return     a declaração de parâmetro que gerou o erro.
     * @since 1.0
     */
    public NoDeclaracaoParametro getParametro()
    {            
        return parametro;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorTexto = new StringBuilder();

        construtorTexto.append("O parâmetro \"");
        construtorTexto.append(parametro.getNome());
        construtorTexto.append("\" já foi declarado na função \"");
        construtorTexto.append(funcao.getNome());
        construtorTexto.append("\".");

        return construtorTexto.toString();
    }
}
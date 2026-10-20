package br.univali.portugol.nucleo.analise.semantica;

import br.univali.portugol.nucleo.asa.NoDeclaracaoBase;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoOperacao;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;

/**
 * Utilitários para detectar se o tamanho de um vetor ou matriz depende de
 * um valor que só pode ser conhecido em tempo de execução.
 * <p>
 * Um tamanho é considerado dinâmico quando a expressão que o define é uma
 * referência a uma variável (não constante) ou uma operação aritmética
 * envolvendo referências a variáveis (não constantes). Nesse caso o valor
 * final do tamanho só pode ser resolvido durante a execução do programa.
 * <p>
 * Expressões que são constantes ou operações sobre constantes são resolvidas
 * em tempo de compilação pelo {@link AnalisadorDeclaracaoTamanhoVetorMatriz}.
 *
 * @since 2.8
 */
public final class TamanhoDinamicoDetector
{
    private TamanhoDinamicoDetector()
    {
    }

    /**
     * Verifica se a expressão que define um tamanho de vetor ou matriz é
     * dinâmica, ou seja, se depende de valores que só são conhecidos em
     * tempo de execução.
     *
     * @param expressao a expressão que define o tamanho
     *
     * @return <code>true</code> se o tamanho só pode ser resolvido em tempo
     * de execução
     */
    public static boolean ehTamanhoDinamico(final NoExpressao expressao)
    {
        if (expressao == null)
        {
            return false;
        }

        if (expressao instanceof NoReferenciaVariavel)
        {
            return referenciaEhVariavelNaoConstante((NoReferenciaVariavel) expressao);
        }

        if (expressao instanceof NoOperacao)
        {
            return operacaoContemReferenciaDinamica(expressao);
        }

        return false;
    }

    /**
     * Verifica se a referência é a uma variável que não é constante, ou seja,
     * uma variável cujo valor pode ser alterado durante a execução.
     */
    private static boolean referenciaEhVariavelNaoConstante(final NoReferenciaVariavel referencia)
    {
        if (referencia.getEscopoBiblioteca() != null)
        {
            // constantes de biblioteca são resolvidas em tempo de compilação
            return false;
        }

        final NoDeclaracaoBase origem = referencia.getOrigemDaReferencia();

        if (origem == null)
        {
            return false;
        }

        return !origem.constante();
    }

    /**
     * Verifica se a operação aritmética envolve alguma referência a uma
     * variável que não é constante.
     */
    private static boolean operacaoContemReferenciaDinamica(final NoExpressao expressao)
    {
        if (expressao instanceof NoOperacao)
        {
            final NoOperacao operacao = (NoOperacao) expressao;

            return operacaoContemReferenciaDinamica(operacao.getOperandoEsquerdo())
                    || operacaoContemReferenciaDinamica(operacao.getOperandoDireito());
        }

        if (expressao instanceof NoReferenciaVariavel)
        {
            return referenciaEhVariavelNaoConstante((NoReferenciaVariavel) expressao);
        }

        return false;
    }

    /**
     * Verifica se a expressão que define o tamanho é um literal inteiro.
     */
    public static boolean ehTamanhoLiteral(final NoExpressao expressao)
    {
        return expressao instanceof NoInteiro;
    }

    /**
     * Verifica se a expressão que define o tamanho é uma variável simples
     * (não constante) declarada pelo usuário, utilizada como tamanho de vetor
     * ou matriz.
     */
    public static boolean ehVariavelSimplesNaoConstante(final NoExpressao expressao)
    {
        if (!(expressao instanceof NoReferenciaVariavel))
        {
            return false;
        }

        final NoReferenciaVariavel referencia = (NoReferenciaVariavel) expressao;

        if (!referenciaEhVariavelNaoConstante(referencia))
        {
            return false;
        }

        final NoDeclaracaoBase origem = referencia.getOrigemDaReferencia();

        return origem instanceof NoDeclaracaoVariavel;
    }
}

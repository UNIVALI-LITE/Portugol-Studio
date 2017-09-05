package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.OperacaoGrafica;

/**
 *
 * @author Luiz Fernando Noschang
 * @param <T> Uma operação gráfica otimizada
 */
public abstract class CacheOperacoesGraficas<T extends OperacaoGrafica>
{
    private final OperacaoGrafica[] OPERACOES;

    private final int QUANTIDADE_MAXIMA;
    private final int QUANTIDADE_ALOCACAO;

    private int indiceObtencao = 0;
    private int indiceDevolucao = 0;

    public CacheOperacoesGraficas(int quantidadeMaxima, int quantidadeAlocacao)
    {
        if (quantidadeAlocacao > quantidadeMaxima)
        {
            throw new IllegalStateException("A quantidade de alocação de operações não pode ser maior que a quantidade maxima");
        }

        QUANTIDADE_MAXIMA = quantidadeMaxima;
        QUANTIDADE_ALOCACAO = quantidadeAlocacao;

        OPERACOES = new OperacaoGrafica[quantidadeMaxima];

        alocarOperacoes();
    }

    private void alocarOperacoes()
    {
            int indice = indiceObtencao;
            int posicoesVerificadas = 0;
            int operacoesAlocadas = 0;

            do
            {
                if (OPERACOES[indice] == null)
                {
                    OPERACOES[indice] = criarInstancia();
                    operacoesAlocadas++;
                }

                posicoesVerificadas++;
                indice = (indice + 1) % QUANTIDADE_MAXIMA;
                indiceDevolucao = indice;
            }
            while (operacoesAlocadas < QUANTIDADE_ALOCACAO && posicoesVerificadas < QUANTIDADE_MAXIMA);
    }

    public T obter()
    {
        if (OPERACOES[indiceObtencao] == null)
        {
            alocarOperacoes();
        }

        T operacao = (T) OPERACOES[indiceObtencao];

        OPERACOES[indiceObtencao] = null;
        indiceObtencao = (indiceObtencao + 1) % QUANTIDADE_MAXIMA;

        return operacao;
    }

    public void devolver(T operacao)
    {
        int posicoesVerificadas = 0;

        do
        {
            if (OPERACOES[indiceDevolucao] == null)
            {
                OPERACOES[indiceDevolucao] = operacao;
                indiceDevolucao = (indiceDevolucao + 1) % QUANTIDADE_MAXIMA;
                operacao.liberarRecursos();
                return;
            }

            posicoesVerificadas++;
            indiceDevolucao = (indiceDevolucao + 1) % QUANTIDADE_MAXIMA;
        }
        while (posicoesVerificadas < QUANTIDADE_MAXIMA);
    }
    
    protected abstract T criarInstancia();
}

package br.univali.portugol.nucleo.programa;

/***
 * Classe usada apenas internamente para armazenar os dados dos vetores que
 * são inspecionados durante a execução
 */
public class Vetor
{
    private final Object[] dados;
    private int ultimaColunaAlterada = -1;
    public final int tamanho;

    protected Vetor(int tamanho)
    {
        dados = new Object[tamanho];
        this.tamanho = tamanho;
    }

    void reseta()
    {
        ultimaColunaAlterada = -1;
        for (int i = 0; i < tamanho; i++)
        {
            dados[i] = Programa.OBJETO_NULO;
        }
    }

    public void setValor(Object valor, int coluna)
    {
        if (coluna >= 0 && coluna < dados.length)
        {
            dados[coluna] = valor;
            ultimaColunaAlterada = coluna;
        }
    }

    public int getUltimaColunaAlterada()
    {
        return ultimaColunaAlterada;
    }

    public Object[] getDados() {
        return dados;
    }
}

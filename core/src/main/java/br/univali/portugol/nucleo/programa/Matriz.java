package br.univali.portugol.nucleo.programa;

public class Matriz {
    private final Vetor[] dados;
    private int ultimaLinhaAlterada = -1;
    public final int linhas;
    public final int colunas;

    Matriz(int totalLinhas, int totalColunas) {
        this.dados = new Vetor[totalLinhas];
        this.linhas = totalLinhas;
        this.colunas = totalColunas;
    }

    void reseta() {
        /*
         * @todo as classes Matriz e Vetor tem métodos comuns como o reseta.
         * Assim, poderia ser definida uma interface comum para as duas classes.
         */

        ultimaLinhaAlterada = -1;

        for (int i = 0; i < dados.length; i++) {
            if (dados[i] != null) {
                dados[i].reseta();
            }
        }
    }

    public void setValor(Object valor, int linha, int coluna) {
        if (linha >= 0 && linha < linhas) {
            if (dados[linha] == null) {
                dados[linha] = new Vetor(colunas);
            }
            dados[linha].setValor(valor, coluna);
            ultimaLinhaAlterada = linha;
        }
    }

    public int getUltimaColunaAlterada() {
        if (ultimaLinhaAlterada >= 0) {
            return dados[ultimaLinhaAlterada].getUltimaColunaAlterada();
        }
        return -1;
    }

    public int getUltimaLinhaAlterada() {
        return ultimaLinhaAlterada;
    }

    public Vetor[] getDados() {
        return dados;
    }
}

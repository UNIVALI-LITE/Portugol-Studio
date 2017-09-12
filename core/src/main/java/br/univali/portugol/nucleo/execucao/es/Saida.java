package br.univali.portugol.nucleo.execucao.es;

import br.univali.portugol.nucleo.asa.TipoDado;

/**
 * Interface para permitir a saída de dados nos programas através da
 * função <code>escreva</code>.
 * <p>
 * No Portugol, a saída de dados não é implementada diretamente no interpretador.
 * Ao invés disso é utilizada esta interface, que é chamada pelo interpretador cada vez que
 * a função <code>escreva</code> é usada no programa. Esta abstração permite tratar a saída 
 * de dados para obter diferentes resultados, por exemplo, escrever em um console em modo texto, 
 * escrever em um dispositivo em modo gráfico, salvar os dados em um arquivo no sistema de arquivos 
 * ou alimentar a entrada de dados de outro processo.
 * <p>
 * As aplicações que utilizam os serviços do Portugol devem obrigatoriamente fornecer uma
 * implementação para esta interface. O interpretador do Portugol aguarda o retorno deste método para
 * continuar seu processamento. Desta forma, as implementações devem garantir que o método não retorne 
 * até que o valor tenha sido escrito. Esta observação se aplica principalmente quando a escrita é feita 
 * de forma aasíncrona, por exemplo, quando se utiliza threads.
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public interface Saida
{
    /**
     * Limpa a saída de dados deixando-a como no início da execução do programa.
     * 
     * @throws InterruptedException     erro ocorrido durante a limpeza da saída.
     * @since 1.0
     */
    public void limpar() throws InterruptedException;

    /**
     * Escreve um dado do tipo {@link TipoDado#CADEIA} na saída de dados.
     * 
     * @param valor           o dado que será escrito na saída.
     * @throws InterruptedException      erro ocorrido durante a escrita do dado na saída.
     * @since 1.0
     */
    public void escrever(String valor) throws InterruptedException;

    /**
     * Escreve um dado do tipo {@link TipoDado#LOGICO} na saída de dados.
     * 
     * @param valor           o dado que será escrito na saída.
     * @throws InterruptedException      erro ocorrido durante a escrita do dado na saída.
     * @since 1.0
     */
    public void escrever(boolean valor) throws InterruptedException;

    /**
     * Escreve um dado do tipo {@link TipoDado#INTEIRO} na saída de dados.
     * 
     * @param valor           o dado que será escrito na saída.
     * @throws InterruptedException      erro ocorrido durante a escrita do dado na saída.
     * @since 1.0
     */
    public void escrever(int valor) throws InterruptedException;

    /**
     * Escreve um dado do tipo {@link TipoDado#REAL} na saída de dados.
     * 
     * @param valor           o dado que será escrito na saída.
     * @throws InterruptedException      erro ocorrido durante a escrita do dado na saída.
     * @since 1.0
     */
    public void escrever(double valor) throws InterruptedException;

    /**
     * Escreve um dado do tipo {@link TipoDado#CARACTER} na saída de dados.
     * 
     * @param valor           o dado que será escrito na saída.
     * @throws InterruptedException      erro ocorrido durante a escrita do dado na saída.
     * @since 1.0
     */
    public void escrever(char valor) throws InterruptedException;
}

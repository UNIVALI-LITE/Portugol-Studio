package br.univali.portugol.nucleo.execucao.es;

import br.univali.portugol.nucleo.asa.TipoDado;

/**
 * Interface para permitir a entrada de dados nos programas através da
 * função <code>leia</code>.
 * <p>
 * No Portugol, a entrada de dados não é implementada diretamente no interpretador.
 * Ao invés disso é utilizada esta interface, que é chamada pelo interpretador cada vez que
 * a função <code>leia</code> é usada no programa. Esta abstração permite alimentar os dados do programa 
 * de várias formas diferentes, por exemplo, através da interação com o usuário na interface da IDE ou 
 * através de um arquivo XML com entradas pré-definidas.
 * <p>
 * As aplicações que utilizam os serviços do Portugol devem obrigatoriamente fornecer uma
 * implementação para esta interface. O interpretador do Portugol aguarda o retorno deste método para
 * continuar seu processamento. Desta forma, as implementações devem garantir que o método não retorne 
 * até que o valor tenha sido lido. Esta observação se aplica principalmente quando a leitura é feita 
 * de forma aasíncrona, por exemplo, quando se utiliza threads.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public interface Entrada
{
    /**
     * Lê um dado da entrada de dados.
     * 
     * @param tipoDado       o tipo de dado que está sendo lido.
     * @throws Exception     a exceção que ocorreu durante a leitura do dado (quando ocorrer).
     */
    public void solicitaEntrada(TipoDado tipoDado, Armazenador armazenador) throws InterruptedException;
}

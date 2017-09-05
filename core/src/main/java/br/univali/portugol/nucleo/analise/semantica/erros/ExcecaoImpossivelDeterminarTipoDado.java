package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;

/**
 * Erro interno gerado pelo analisador semântico para interromper a avaliação de uma
 * expressão quando é impossível determinar o tipo de uma subexpressão. Este erro é utilizado
 * apenas para controle interno, e portanto, não é exposto à IDE.
 * <p>
 * Exemplo:
 * <code><pre>
 *
 *      funcao exemploImpossivelDeterminarTipo()
 *      {   
 *          /*
 *           * A expressão abaixo é uma operação de subtração composta por duas subexpressões.
 *           *
 *           * Para poder determinar o tipo de dado resultante desta operação de subtração, o analisador semântico 
 *           * precisa primeiro determinar o tipo de dado das subexpressões.
 *           * 
 *           * Para isso, ele irá começar avaliando o operando esquerdo, que por sua vez, é uma operação de soma.
 *           * Ao avaliar a expressão de soma, o analisador lançará esta exceção, pois a operação de soma entre
 *           * um lógico e um inteiro é inválida.
 *           *
 *           * Ao retornar desta avaliação, o analisador semântico irá capturar a exceção e registrar na lista de erros um
 *           * {@link ErroTiposIncompativeis}. Logo após, irá avaliar o operando direito.
 *           * 
 *           * Uma vez que não foi possível determinar o tipo de dado do operando esquerdo, também será impossível determinar 
 *           * o tipo de dado rsultante da subtração. Logo, o analisador semântico irá disparar novamente esta exceção.
 *           *
 *           *#47;
 *          inteiro valor = (falso + 9) - (5 * 6)
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 */
public final class ExcecaoImpossivelDeterminarTipoDado extends Exception
{

}

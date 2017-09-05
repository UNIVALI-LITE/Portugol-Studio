package br.univali.portugol.nucleo.asa;


/**
 * Representa uma operação de igualdade no código fonte.
 * <p>
 * A operação de <code>igualdade</code> serve para determinar se duas expressões são equivalentes (iguais).
 * No Portugol, a operação de <code>igualdade</code> é representada no código fonte pelo operador "==".
 * </p>
 * 
 * <p>
 * A operação de <code>igualdade</code> é uma operação lógica, e portanto, ao ser avaliada retorna um
 * valor lógico: <code>verdadeiro</code> se as expressões forem iguais e <code>falso</code> se 
 * as expressões forem diferentes. Pode ser utilizada para realizar o controle de laços de repetição 
 * e desvios condicionais.
 * </p>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public final class NoOperacaoLogicaIgualdade extends NoOperacaoLogica
{
    public NoOperacaoLogicaIgualdade(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
    {
        super(operandoEsquerdo, operandoDireito);
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }

    @Override
    public String toString()
    {
        return getOperandoEsquerdo().toString() + " == " + getOperandoDireito().toString();
    }
}

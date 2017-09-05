package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.asa.NoOperacao;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 * Erro gerado pelo analisador semântico quando uma operação {@link Operacao#ATRIBUICAO} tenta 
 * alterar o valor de uma expressão constante.
 * <p>
 * Uma expressão constante pode ser uma variável, vetor ou matriz
 * declarados com a palavra reservada const ou um tipo primitivo literal.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 * @see ErroOperacaoComExpressaoConstante
 */
public final class ErroOperandoEsquerdoAtribuicaoConstante extends ErroSemantico
{
    private NoOperacao atribuicao;

    /**
     * 
     * @param atribuicao      a operação de atribuição que gerou o erro.
     */
    public ErroOperandoEsquerdoAtribuicaoConstante(NoOperacao atribuicao)
    {
        super(atribuicao.getOperandoEsquerdo().getTrechoCodigoFonte(),"ErroSemantico.ErroOperandoEsquerdoAtribuicaoConstante");

        this.atribuicao = atribuicao;
    }

    /**
     * Obtém a operação de atribuição que gerou o erro.
     * 
     * @return     a operação de atribuição que gerou o erro.
     * @since 1.0
     */
    public NoOperacao getAtribuicao()
    {
        return atribuicao;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        return "O operando esquerdo da atribuição não pode ser uma expressão constante.";
    }
}
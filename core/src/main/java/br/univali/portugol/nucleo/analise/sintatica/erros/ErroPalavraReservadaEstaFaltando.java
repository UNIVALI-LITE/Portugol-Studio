package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 * Erro gerado pelo analisador sintático quando uma palavra reservada está faltando.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      programa
 *      {
 *           inteiro exemploPalavraReservada()
 *           {
 *                escreva("Esta declaração de função vai gerar um erro, pois falta a palavra reservada 'funcao' no início da declaração")
 *           }
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */
public final class ErroPalavraReservadaEstaFaltando extends ErroSintatico
{
    private final String palavraReservada;

    /**
     * 
     * @param linha                a linha onde o erro ocorreu.
     * @param coluna               a coluna onde o erro ocorreu.
     * @param palavraReservada     a palavra reservada que está faltando.
     */
    public ErroPalavraReservadaEstaFaltando(int linha, int coluna, String palavraReservada)
    {
        super(linha, coluna,"ErroSintatico.ErroPalavraReservadaEstaFaltando");
        this.palavraReservada = palavraReservada;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        return String.format("O algoritmo está incompleto, está faltando a palavra reservada '%s'", palavraReservada);
    }
}

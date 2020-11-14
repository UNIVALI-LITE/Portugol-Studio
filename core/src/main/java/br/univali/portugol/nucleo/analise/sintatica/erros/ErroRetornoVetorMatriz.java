package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSemiSintatico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroRetornoVetorMatriz extends ErroSemiSintatico
{
    
    
    public ErroRetornoVetorMatriz(int linha, int coluna, String codigofonte)
    {
        super(linha, coluna, codigofonte);
    }   

    @Override
    protected String construirMensagem()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Não é possível retornar vetores ou matrizes por funções.\n\n");
        
        return sb.toString();
    }
}

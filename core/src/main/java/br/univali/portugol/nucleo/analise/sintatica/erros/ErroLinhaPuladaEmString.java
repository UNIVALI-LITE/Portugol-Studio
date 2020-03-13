package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSemiSintatico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroLinhaPuladaEmString extends ErroSemiSintatico
{
    
    
    public ErroLinhaPuladaEmString(int linha, int coluna, String codigofonte)
    {
        super(linha, coluna, codigofonte);
    }   

    @Override
    protected String construirMensagem()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Você pulou uma linha ao escrever uma cadeia. Cadeias apenas podem ser escritas em na mesma linha");
        
        return sb.toString();
    }
}

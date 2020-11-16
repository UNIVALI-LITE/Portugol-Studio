package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSemiSintatico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroSenaoInesperado extends ErroSemiSintatico
{
    
    
    public ErroSenaoInesperado(int linha, int coluna, String codigofonte)
    {
        super(linha, coluna, codigofonte);
    }   

    @Override
    protected String construirMensagem()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("O token 'senao' não faz sentido neste local. O comando 'senao' deve ser utilizado com o comando 'se' da seguinte maneira:\n");
        sb.append("se(condicao){\n\n");
        sb.append("}senao{\n\n");
        sb.append("}\n\n");
        sb.append("ou então: \n\n");
        sb.append("se(condicao){\n\n");
        sb.append("}senao se(condicao2){\n\n");
        sb.append("}\n\n");
        
        return sb.toString();
    }
}

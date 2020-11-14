package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSemiSintatico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroParametrosNaoTipados extends ErroSemiSintatico
{
    
    
    public ErroParametrosNaoTipados(int linha, int coluna, String codigofonte)
    {
        super(linha, coluna, codigofonte);
    }   

    @Override
    protected String construirMensagem()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Os parametros da função, não estão tipados.\n");
        sb.append("Adicione tipos para cada um dos parametros utilizados ex: funcao nome(inteiro idade, cadeia sobrenome)");
        
        return sb.toString();
    }
}

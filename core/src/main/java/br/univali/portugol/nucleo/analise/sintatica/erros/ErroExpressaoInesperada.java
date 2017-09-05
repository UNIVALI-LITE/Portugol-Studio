package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSintatico;

public class ErroExpressaoInesperada extends ErroSintatico
{
    private String token;
    
    public ErroExpressaoInesperada(int linha, int coluna, String token)
    {
        super(linha, coluna,"ErroSintatico.ErroExpressaoInesperada");
        this.token = token;
    }
    
    @Override
    protected String construirMensagem()
    {
        return String.format("A expressão '%s' não era esperada neste local, remova a expressão para corrigir o problema", token);
    }    
}

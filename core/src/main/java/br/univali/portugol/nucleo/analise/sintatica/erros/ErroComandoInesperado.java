package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSintatico;

public class ErroComandoInesperado extends ErroSintatico
{
    private String token;
    
    public ErroComandoInesperado(int linha, int coluna, String token)
    {
        super(linha, coluna,"ErroSintatico.ErroExpressaoInesperada");
        this.token = token;
    }
    
    @Override
    protected String construirMensagem()
    {
        return String.format("O comando '%s' não era esperado neste local, remova-o para corrigir o problema", token);
    }    

    public String getToken() {
        return token;
    }
    
}

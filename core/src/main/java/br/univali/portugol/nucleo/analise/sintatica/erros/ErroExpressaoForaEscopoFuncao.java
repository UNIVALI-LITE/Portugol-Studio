package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSintatico;

public class ErroExpressaoForaEscopoFuncao extends ErroSintatico
{
    private String token;
    
    public ErroExpressaoForaEscopoFuncao(int linha, int coluna, String token)
    {
        super(linha, coluna,"ErroSintatico.ErroExpressaoForaEscopoFuncao");
        this.token = token;
    }
    
    @Override
    protected String construirMensagem()
    {
        return String.format("A expressão '%s' está fora de um escopo de função e nunca será chamada. Adicione ela a uma função ou remova-a.", token);
    }    

    public String getToken() {
        return token;
    }
    
}

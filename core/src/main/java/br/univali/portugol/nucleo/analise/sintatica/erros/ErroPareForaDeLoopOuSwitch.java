package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSintatico;

public class ErroPareForaDeLoopOuSwitch extends ErroSintatico
{
    private String token;
    
    public ErroPareForaDeLoopOuSwitch(int linha, int coluna, String token)
    {
        super(linha, coluna,"ErroSintatico.ErroExpressaoInesperada");
        this.token = token;
    }
    
    @Override
    protected String construirMensagem()
    {
        return String.format("O comando '%s' apenas pode ser utilizado dentro de Loops (como para, enquanto e faça enquanto) ou dentro de um escolha-caso", token);
    }    

    public String getToken() {
        return token;
    }
    
}

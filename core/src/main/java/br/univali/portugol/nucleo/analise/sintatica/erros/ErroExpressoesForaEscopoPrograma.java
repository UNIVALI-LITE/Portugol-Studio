package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroExpressoesForaEscopoPrograma extends ErroSintatico
{
    public static enum Local { ANTES, DEPOIS };
    
    private final int posicao;
    private final String expressoes;
    private final Local local;
    
    public ErroExpressoesForaEscopoPrograma(String expressoes, int posicao, String codigoFonte, Local local)
    {
        super(1, 1,"ErroSintatico.ErroExpressoesForaEscopoPrograma");
        this.posicao = posicao;
        this.expressoes = expressoes;
        this.local = local;
    }

    public Local getLocal() {
        return local;
    }

    public int getPosicao()
    {
        return posicao;
    }

    public String getExpressoes()
    {
        return expressoes;
    }
    
    @Override
    protected String construirMensagem()
    {
        String aux = "";
        
        if (local == Local.ANTES)
        {
            aux = "localizado antes da palavra reservada 'programa'";
        }
        else if (local == Local.DEPOIS)
        {
            aux = "localizado após o caracter '}'";
        }
        
        return String.format("Não são permitidas expressões fora do escopo do programa. Para corrigir o problema, remova o seguinte trecho de código '%s', que está %s", expressoes, aux);
    }
}

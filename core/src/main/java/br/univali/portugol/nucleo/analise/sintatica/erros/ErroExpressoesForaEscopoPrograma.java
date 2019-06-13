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
    private final Local local;
    private final String codigoForaDoPrograma;
    
    public ErroExpressoesForaEscopoPrograma(int posicao, String codigoFonte, Local local)
    {
        super(1, 1,"ErroSintatico.ErroExpressoesForaEscopoPrograma");
        this.posicao = posicao;
        this.local = local;
        this.codigoForaDoPrograma = getCodigoForaDoPrograma(codigoFonte, local);
    }

    public Local getLocal() {
        return local;
    }

    public int getPosicao()
    {
        return posicao;
    }

    public String getCodigoForaDoPrograma() {
        return codigoForaDoPrograma;
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
        
        return String.format("Não são permitidas expressões fora do escopo do programa. Para corrigir o problema, remova o seguinte trecho de código '%s', que está %s", codigoForaDoPrograma, aux);
    }
    
        
    private static String getCodigoForaDoPrograma(String codigo, Local local) {
        return local == Local.ANTES ? getCodigoAntesDoPrograma(codigo) : getCodigoDepoisDoPrograma(codigo);
    }
    
    private static String getCodigoAntesDoPrograma(String codigoFonte){
        
        int index = codigoFonte.indexOf("programa");
        
        if (index > 0) {
            return codigoFonte.substring(0, index-1);
        }
        
        return "";
    }
    
    private static String getCodigoDepoisDoPrograma(String codigoFonte){
        int index = codigoFonte.lastIndexOf("}");
        
        if (index > 0 && index < codigoFonte.length() - 1) {
            return codigoFonte.substring(index + 1, codigoFonte.length());
        }
        
        return ""; 
    }
    
}

package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroTokenFaltando extends ErroSintatico
{
    private final String token;
    private String codigo = "ErroSintatico.ErroTokenFaltando.";
    
    public ErroTokenFaltando(int linha, int coluna, String token)
    {
        super(linha, coluna, "ErroSintatico.ErroTokenFaltando");
        this.token = token;
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder sb = new StringBuilder();
        AnalisadorSintatico.TipoToken tipo = AnalisadorSintatico.getTipoToken(token);
        
        switch (tipo)
        {
            case OPERADOR:
                sb.append(String.format("A expressão está incompleta, está faltando o operador '%s'", token.toLowerCase()));
                codigo += "1";
                break;
                
            case TIPO_PRIMITIVO:
                sb.append(String.format("A expressão está incompleta, está faltando um dado do tipo '%s'", token.toLowerCase()));
                codigo += "2";
                break;
                
            default:
                sb.append(String.format("A expressão está incompleta, está faltando o token '%s'", token.toLowerCase()));
                codigo += "3";
                break;
        }
        
        super.setCodigo(codigo);
        return sb.toString();
    }
}

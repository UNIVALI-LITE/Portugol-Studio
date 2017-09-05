package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import static br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico.TipoToken.OPERADOR;
import static br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico.TipoToken.TIPO_PRIMITIVO;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroTokenFaltando extends ErroSintatico
{
    private String token;
    
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
                break;
                
            case TIPO_PRIMITIVO:
                sb.append(String.format("A expressão está incompleta, está faltando um dado do tipo '%s'", token.toLowerCase()));
                break;
                
            default:
                sb.append(String.format("A expressão está incompleta, está faltando o token '%s'", token.toLowerCase()));
                break;
        }
        
        return sb.toString();
    }
}

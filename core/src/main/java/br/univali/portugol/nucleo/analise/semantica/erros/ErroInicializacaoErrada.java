package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.simbolos.Simbolo;

public final class ErroInicializacaoErrada extends ErroSemantico
{
    private String codigo = "ErroSemantico.ErroInicializacaoErrada.";

    public ErroInicializacaoErrada(NoPara declaracao)
    {
       
        super(declaracao.getInicializacoes().get(0).getTrechoCodigoFonte());
    }

    public ErroInicializacaoErrada(Simbolo simbolo, NoPara declaracao)
    {
        this(declaracao);
    }
    
    @Override
    protected String construirMensagem()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Não é possível inicializar. ");
        builder.append("Utilize uma expressão de atribuição como: inteiro x = 0");
        super.setCodigo(codigo);
        return builder.toString();
    }
    
}

package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

public class ErroLeiaNecessitaReferencia extends ErroSemantico
{
    
    
    
    public ErroLeiaNecessitaReferencia(NoChamadaFuncao chamadaFuncao, NoExpressao expressao)
    {
        super(chamadaFuncao.getTrechoCodigoFonte(), "ErroSemantico.ErroLeiaNecessitaReferencia");
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("O parâmetro da função leia deve ser uma referência a uma variável, matriz ou vetor");
        
        return sb.toString();
    }
    
}

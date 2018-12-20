package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.mensagens.ErroAnalise;


/**
 *
 * @author fillipi
 */
public class ErroVariavelPodeNaoTerSidoInicializada extends ErroAnalise
{
    private String variavel = "";
    private String codigo = "ErroExecucao.ErroVariavelPodeNaoTerSidoInicializada";
    
    public ErroVariavelPodeNaoTerSidoInicializada(String mensagem)
    {
        variavel = mensagem.split("error: variable ")[1];
        variavel = variavel.split(" might not have")[0];
        super.setCodigo(codigo);
    }    
    
    @Override
    protected String construirMensagem()
    {
        return "Existem trechos de código onde a variável "+variavel+" pode não ter sido inicializada";
    }
    
}

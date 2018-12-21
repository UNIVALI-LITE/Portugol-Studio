package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroFuncaoInicialInvalida extends ErroExecucao
{
    private final String funcaoInicial;
    private String codigo = "ErroExecucao.ErroFuncaoInicialInvalida";

    public ErroFuncaoInicialInvalida(String funcaoInicial)
    {
        this.funcaoInicial = funcaoInicial;   
        super.setCodigo(codigo);
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("A função inicial \"%s\" não deve possuir parâmetros ou o parâmetro deve ser um vetor do tipo CADEIA.", funcaoInicial);
    }    
}

package br.univali.portugol.nucleo.execucao;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 * Esta classe contém informações referentes ao estado da execução do programa.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class ResultadoExecucao
{
    private Long tempoExecucao = 0L;
    private ModoEncerramento modoEncerramento = ModoEncerramento.NORMAL;
    private ErroExecucao erro;
    private Object retorno;

    /**
     * Obtém o tempo (em milissegundos) que o programa permaneceu rodando.
     * 
     * @return     o tempo (em milissegundos) que o programa permaneceu rodando.
     * @since 1.0
     */
    public Long getTempoExecucao()
    {
        return tempoExecucao;
    }
    
    /**
     * Define o tempo (em milissegundos) que o programa permaneceu rodando.
     * 
     * @param tempoExecucao     o tempo (em milissegundos) que o programa permaneceu rodando.
     * @since 1.0
     */
    public void setTempoExecucao(Long tempoExecucao)
    {
        this.tempoExecucao = tempoExecucao;
    }

    /**
     * Obtém o modo como a execução do programa foi encerrada.
     * 
     * @return     o modo como a execução do programa foi encerrada.
     * @since 1.0
     */
    public ModoEncerramento getModoEncerramento()
    {
        return modoEncerramento;
    }

    /**
     * Define o modo como a execução do programa foi encerrada.
     * 
     * @param modoEncerramento     o modo como a execução do programa foi encerrada.
     * @since 1.0
     */
    public void setModoEncerramento(ModoEncerramento modoEncerramento)
    {
        this.modoEncerramento = modoEncerramento;
    }

    /**
     * Obtém o erro que ocasionou o encerramento da execução.
     * 
     * @return      um erro de execução caso o encerramento da execução tenha sido ocasionado
     *              por um erro. Caso o encerramento tenha sido ocasionado por outros motivos,
     *              retorna null.
     * @since 1.0
     * @see         ModoEncerramento
     */
    public ErroExecucao getErro()
    {
        return erro;
    }

    /**
     * Define o erro que ocasionou o encerramento da execução.
     * 
     * @param erro     o erro que ocasionou o encerramento da execução.
     * @since 1.0
     */
    public void setErro(ErroExecucao erro)
    {
        this.erro = erro;
        this.modoEncerramento = ModoEncerramento.ERRO;
    }

    /**
     * Obtém o valor que foi retornado pela função principal. Caso a função não 
     * retorne nenhum valor, este método irá retornar null.
     * 
     * @return          o valor retornado pela função principal.
     */
    public Object getRetorno()
    {
        return retorno;
    }

    /**
     * Define o valor que foi retornado pela função principal.
     * 
     * @param retorno       o valor que foi retornado pela função principal.
     */
    public void setRetorno(Object retorno)
    {
        this.retorno = retorno;
    }    
}

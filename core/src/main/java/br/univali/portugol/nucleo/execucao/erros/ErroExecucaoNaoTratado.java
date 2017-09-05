package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Erro gerado pelo interpretador do Portugol quando ocorre um problema na execução dos
 * programas que ainda não foi detectado e tratado apropriadamente.
 * 
 * @author Luiz Fernando Noschang
 * @since 1.0
 */
public final class ErroExecucaoNaoTratado extends ErroExecucao
{
    private Exception causa;

    /**
     * 
     * @param causa    a exceção não tratada que originou este erro.
     * @since 1.0
     */
    public ErroExecucaoNaoTratado(Exception causa) 
    {
        this.causa = causa;
    }

    public Exception getCausa()
    {
        return causa;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String construirMensagem() 
    {
        StringBuilder construtorTexto = new StringBuilder();
        FluxoSaidaExcecao fluxoSaidaExcecao = new FluxoSaidaExcecao(construtorTexto);
        
        causa.printStackTrace(new PrintWriter(fluxoSaidaExcecao, true));
        
        return construtorTexto.toString();
    }
    
    /**
     * Classe utilitária usada para escrever os dados da pilha de execução
     * na mensagem de erro.
     * 
     * @author Luiz Fernando Noschang
     * @since 1.0
     * 
     * @version 1.0
     */
    private final class FluxoSaidaExcecao extends OutputStream
    {
        private StringBuilder construtorTexto;
        private StringBuilder saida;

        public FluxoSaidaExcecao(StringBuilder saida) 
        {
            construtorTexto = new StringBuilder(128);
            this.saida = saida;                    
        }
        
        @Override
        public void write(int b) throws IOException 
        {
            construtorTexto.append((char) b);
        }

        @Override
        public void flush() throws IOException 
        {
            saida.append(construtorTexto.toString());
            construtorTexto.setLength(0);            
        }
    }
}

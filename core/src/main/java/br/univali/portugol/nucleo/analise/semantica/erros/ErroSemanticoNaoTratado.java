package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Erro gerado pelo analisador semântico do Portugol quando ocorre um problema 
 * durante a análise dos algoritmos que ainda não foi detectado e tratado 
 * apropriadamente.
 * 
 * @author Luiz Fernando Noschang
 * @since 1.0
 */
public final class ErroSemanticoNaoTratado extends ErroSemantico
{
    private Exception causa;
    private String codigo = "ErroSemantico.ErroSemanticoNaoTratado";

    /**
     * 
     * @param causa    a exceção não tratada que originou este erro.
     * @since 1.0
     */
    public ErroSemanticoNaoTratado(Exception causa) 
    {
        super(new TrechoCodigoFonte(0, 0, 0));
        this.causa = causa;
        super.setCodigo(codigo);
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

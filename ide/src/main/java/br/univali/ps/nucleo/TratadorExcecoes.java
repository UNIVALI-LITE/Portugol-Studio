package br.univali.ps.nucleo;

import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.ui.telas.TelaCustomBorder;
import br.univali.ps.ui.telas.TelaExcecaoEncontrada;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import static br.univali.ps.nucleo.ExcecaoAplicacao.Tipo.ERRO_PROGRAMA;
import br.univali.ps.ui.Lancador;
import br.univali.ps.ui.swing.weblaf.jOptionPane.QuestionDialog;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 23/08/2011
 *
 */
public final class TratadorExcecoes implements Thread.UncaughtExceptionHandler
{
    private static final Logger LOGGER = Logger.getLogger(TratadorExcecoes.class.getName());

    private PrintWriter escritorExcecao;
    private FluxoSaidaExcecao fluxoSaida;
    private TelaExcecaoEncontrada telaExcecaoEncontrada;
    private TelaCustomBorder excecaoDialog;

    private boolean encerrarAplicacao = false;

    TratadorExcecoes()
    {

    }

    public void exibirExcecao(final Exception excecao)
    {
        final ExcecaoAplicacao excecaoAplicacao = transformarExcecao(excecao);
        final Level level = obterNivelLog(excecaoAplicacao);

        LOGGER.log(level, "Exceção capturada", excecao);

        if (SwingUtilities.isEventDispatchThread())
        {
            exibir(excecaoAplicacao);
        }
        else
        {
            try
            {
                SwingUtilities.invokeAndWait(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        exibir(excecaoAplicacao);
                    }
                });
            }
            catch (InterruptedException | InvocationTargetException ex)
            {

            }
        }
    }

    private void exibir(final ExcecaoAplicacao excecaoAplicacao)
    {
        PortugolStudio portugolStudio = PortugolStudio.getInstancia();

        int tipoDialogo = getTipoDialogo(excecaoAplicacao);

        if ((portugolStudio.isDepurando()) && (excecaoAplicacao.getTipo() == ExcecaoAplicacao.Tipo.ERRO_PROGRAMA))
        {
            exibirExcecaoDetalhada(excecaoAplicacao);
        }
        else
        {
            exibirExcecaoSimples(excecaoAplicacao, tipoDialogo);
        }

    }

    private void exibirExcecaoSimples(ExcecaoAplicacao excecaoAplicacao, int tipoDialogo)
    {
        QuestionDialog.getInstance().showMessage(excecaoAplicacao.getMessage(), tipoDialogo);

        if (encerrarAplicacao)
        {
            PortugolStudio.getInstancia().finalizar(-1);
        }
    }

    private void exibirExcecaoDetalhada(ExcecaoAplicacao excecaoAplicacao)
    {
        inicializarComponentesExcecaoDetalhada();                
        
        excecaoDialog.setVisible(true);

        if (encerrarAplicacao)
        {
            PortugolStudio.getInstancia().finalizar(-1);
        }
    }

    private void inicializarComponentesExcecaoDetalhada()
    {
        
        if (excecaoDialog == null)
        {
            excecaoDialog = new TelaCustomBorder("Erro Encontrado");
            telaExcecaoEncontrada = new TelaExcecaoEncontrada(excecaoDialog);
            excecaoDialog.setPanel(telaExcecaoEncontrada);
            excecaoDialog.setLocationRelativeTo(Lancador.getInstance().getJFrame());
            telaExcecaoEncontrada.getAreaTextoStackTrace().setText(null);
            telaExcecaoEncontrada.getAreaTextoStackTrace().append(fluxoSaida.getFullStack());
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable excecao)
    {
        final ExcecaoAplicacao excecaoAplicacao = new ExcecaoAplicacao(excecao, ExcecaoAplicacao.Tipo.ERRO_PROGRAMA);
        fluxoSaida = new FluxoSaidaExcecao();
        escritorExcecao = new PrintWriter(fluxoSaida, true);
        excecaoAplicacao.printStackTrace(escritorExcecao);
        
        if ((excecao instanceof ClassNotFoundException) || (excecao instanceof NoClassDefFoundError))
        {
            String mensagem;

            if (GerenciadorPlugins.getInstance().excecaoCausadaPorPlugin(excecao))
            {
                mensagem = "Ocoreu um erro ao executar um dos plugins do Portugol Studio.";
                encerrarAplicacao = false;
            }
            else
            {
                mensagem = "Uma das bibliotecas ou classes necessárias para o funcionamento do Portugol Studio não foi encontrada.\nO Portugol Studio será enecerrado.";
                encerrarAplicacao = true;
            }

            exibirExcecao(new ExcecaoAplicacao(mensagem, excecao, ExcecaoAplicacao.Tipo.ERRO_PROGRAMA));
        }
        else if (excecao instanceof IllegalArgumentException)
        {
            LOGGER.log(Level.WARNING, "Exceção não identificada", excecao);
        }
        else if (silenciador_de_excecoes())
        {
            //Shhhh, você não viu nada aqui, continue andando...
        }
        else
        {
            exibirExcecao(excecaoAplicacao);
        }
    }
    private boolean silenciador_de_excecoes(){
        String mensagem = fluxoSaida.getFullStack();
        //ignorar até JDK ser atualizada para versão 9
        String[] search = new String[2];
        search[0] = "component must be showing on the screen to determine its location";
        search[1] = "javax.swing.text.JTextComponent$InputMethodRequestsHandler.getTextLocation";        
        if(silenciador_de_excecoes_and(mensagem, search))
            return true;
        
        //https://github.com/UNIVALI-LITE/Portugol-Studio/issues/486
        search = new String[2];
        search[0] = "java.lang.NullPointerException: peer";
        search[1] = "Caused by: java.lang.NullPointerException: peer";
        if(silenciador_de_excecoes_and(mensagem, search))
            return true;
        
        //github.com/UNIVALI-LITE/Portugol-Studio/issues/488
        search = new String[2];
        search[0] = "Caused by: java.lang.ArithmeticException: / by zero";
        search[1] = "org.fife.ui.rsyntaxtextarea.SyntaxView.viewToModel";
        if(silenciador_de_excecoes_and(mensagem, search))
            return true;
        
        //issue #495
        search = new String[2];
        search[0] = "org.fife.ui.rsyntaxtextarea.ParserManager.noticeContainsPosition(ParserManager.java:531)";
        search[1] = "javax.swing.ToolTipManager$insideTimerAction.actionPerformed(ToolTipManager.java:663)";
        if(silenciador_de_excecoes_and(mensagem, search))
            return true;
        return false;
    }
    private boolean silenciador_de_excecoes_and(String mensagem, String[] search){
        for(int i = 0; i < search.length; i++){
            if(!mensagem.contains(search[i])){
                return false;
            }
        }
        return true;
    }

    private Level obterNivelLog(ExcecaoAplicacao excecaoAplicacao)
    {
        switch (excecaoAplicacao.getTipo())
        {
            case ERRO_USUARIO:
                return Level.WARNING;
            case ERRO_PROGRAMA:
                return Level.SEVERE;
            case AVISO:
                return Level.WARNING;
            case MENSAGEM:
                return Level.INFO;
        }

        return null;
    }

    private final class FluxoSaidaExcecao extends OutputStream
    {
        private final StringBuilder construtorTexto = new StringBuilder(128);

        public FluxoSaidaExcecao()
        {

        }

        public String getFullStack() {
            return construtorTexto.toString();
        }
        
        @Override
        public void write(int b) throws IOException
        {
            construtorTexto.append((char) b);
        }

        @Override
        public void flush() throws IOException
        {
            
        }
        
        
    }

    private int getTipoDialogo(ExcecaoAplicacao excecaoAplicacao)
    {
        switch (excecaoAplicacao.getTipo())
        {
            case ERRO_PROGRAMA:
                return JOptionPane.ERROR_MESSAGE;
            case MENSAGEM:
                return JOptionPane.INFORMATION_MESSAGE;
            case AVISO:
                return JOptionPane.WARNING_MESSAGE;
            default:
                return 0;
        }
    }

    private ExcecaoAplicacao transformarExcecao(Exception excecao)
    {
        if (!(excecao instanceof ExcecaoAplicacao))
        {
            excecao = new ExcecaoAplicacao(excecao, ExcecaoAplicacao.Tipo.ERRO_PROGRAMA);
        }

        return (ExcecaoAplicacao) excecao;
    }
}

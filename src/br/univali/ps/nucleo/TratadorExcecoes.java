package br.univali.ps.nucleo;

import static br.univali.ps.nucleo.ExcecaoAplicacao.Tipo.ERRO;

import br.univali.ps.plugins.base.GerenciadorPlugins;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 23/08/2011
 *
 */
public final class TratadorExcecoes implements Thread.UncaughtExceptionHandler
{
    private static final Logger LOGGER = Logger.getLogger(TratadorExcecoes.class.getName());

    private JPanel painel;
    private JLabel rotulo;
    private JTextArea caixaTexto;
    private PrintWriter escritorExcecao;
    private OutputStream fluxoSaida;
    private JScrollPane painelRolagem;
    private JPanel painelsul;
    private JButton botaoCopiarTexto;

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

        if ((portugolStudio.isDepurando()) && (excecaoAplicacao.getTipo() == ExcecaoAplicacao.Tipo.ERRO))
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
        JOptionPane.showMessageDialog(null, excecaoAplicacao.getMessage(), "Portugol Studio", tipoDialogo);

        if (encerrarAplicacao)
        {
            PortugolStudio.getInstancia().finalizar(-1);
        }
    }

    private void exibirExcecaoDetalhada(ExcecaoAplicacao excecaoAplicacao)
    {
        inicializarComponentesExcecaoDetalhada();

        caixaTexto.setText(null);
        rotulo.setText(String.format("<html><p>%s<br><br>Detalhes:</p></html>", excecaoAplicacao.getMessage()));

        excecaoAplicacao.printStackTrace(escritorExcecao);

        JOptionPane.showMessageDialog(null, painel, "Portugol Studio", JOptionPane.ERROR_MESSAGE);

        if (encerrarAplicacao)
        {
            PortugolStudio.getInstancia().finalizar(-1);
        }
    }

    private void inicializarComponentesExcecaoDetalhada()
    {
        if (caixaTexto == null)
        {
            rotulo = new JLabel();
            rotulo.setBorder(new EmptyBorder(8, 0, 8, 8));

            caixaTexto = new JTextArea();
            caixaTexto.setEditable(false);

            painelRolagem = new JScrollPane();
            painelRolagem.setViewportView(caixaTexto);
            painelRolagem.setPreferredSize(new Dimension(400, 250));

            botaoCopiarTexto = new JButton();

            botaoCopiarTexto.setAction(new AbstractAction("Copiar Texto")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Clipboard areaTransferencia = Toolkit.getDefaultToolkit().getSystemClipboard();
                    areaTransferencia.setContents(new StringSelection(caixaTexto.getText()), null);
                }
            });

            painelsul = new JPanel();
            painelsul.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            painelsul.setPreferredSize(new Dimension(20, 50));
            painelsul.add(botaoCopiarTexto);

            painel = new JPanel();
            painel.setLayout(new BorderLayout());
            painel.add(BorderLayout.NORTH, rotulo);
            painel.add(BorderLayout.CENTER, painelRolagem);
            painel.add(BorderLayout.SOUTH, painelsul);
            painel.setPreferredSize(new Dimension(550, 400));

            fluxoSaida = new FluxoSaidaExcecao();
            escritorExcecao = new PrintWriter(fluxoSaida, true);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable excecao)
    {
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

            exibirExcecao(new ExcecaoAplicacao(mensagem, excecao, ExcecaoAplicacao.Tipo.ERRO));
        }
        else if (excecao instanceof IllegalArgumentException)
        {
            LOGGER.log(Level.WARNING, "Exceção não identificada", excecao);
        }
        else
        {
            exibirExcecao(new ExcecaoAplicacao(excecao, ExcecaoAplicacao.Tipo.ERRO));
        }
    }

    private Level obterNivelLog(ExcecaoAplicacao excecaoAplicacao)
    {
        switch (excecaoAplicacao.getTipo())
        {
            case ERRO:
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

        @Override
        public void write(int b) throws IOException
        {
            construtorTexto.append((char) b);
        }

        @Override
        public void flush() throws IOException
        {
            caixaTexto.append(construtorTexto.toString());
            construtorTexto.setLength(0);
        }
    }

    private int getTipoDialogo(ExcecaoAplicacao excecaoAplicacao)
    {
        switch (excecaoAplicacao.getTipo())
        {
            case ERRO:
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
            excecao = new ExcecaoAplicacao(excecao, ExcecaoAplicacao.Tipo.ERRO);
        }

        return (ExcecaoAplicacao) excecao;
    }
}

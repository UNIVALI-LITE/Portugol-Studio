package br.univali.portugol.nucleo.bibliotecas.graficos;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class JanelaGraficaImpl extends JFrame implements JanelaGrafica
{
    private static final int LARGURA_PADRAO = 640;
    private static final int ALTURA_PADRAO = 480;
    private static final String TITULO_PADRAO = "Sem Título";

    private int largura = LARGURA_PADRAO;
    private int altura = ALTURA_PADRAO;
    
    private int largura_anterior = LARGURA_PADRAO;
    private int altura_anterior = ALTURA_PADRAO;

    private final Programa programa;
    private SuperficieDesenho superficieDesenho;
    
    private boolean tela_cheia = false;

    private JanelaGraficaImpl(Programa programa)
    {
        this.programa = programa;
        this.instalarObservadores();
    }

    public static JanelaGrafica criar(final Programa programa) throws ErroExecucaoBiblioteca
    {
        final RetornoJanela retornoJanela = new RetornoJanela();

        Swing.executarTarefa(new Runnable()
        {
            @Override
            public void run()
            {
                JanelaGraficaImpl janela = new JanelaGraficaImpl(programa);
                janela.setResizable(false);
                janela.criarSuperficieDesenho();
                
                retornoJanela.janela = janela;
            }
        });

        return retornoJanela.janela;
    }

    @Override
    public void instalarMouse(final MouseAdapter observadorMouse, final FocusListener observadorFoco) throws ErroExecucaoBiblioteca
    {
        Swing.executarTarefa(new Runnable()
        {
            @Override
            public void run()
            {
                superficieDesenho.instalarMouse(observadorMouse);
                addFocusListener(observadorFoco);
            }
        });
    }

    @Override
    public void instalarTeclado(final KeyListener observadorTeclado) throws ErroExecucaoBiblioteca
    {
        Swing.executarTarefa(new Runnable()
        {
            @Override
            public void run()
            {
                addKeyListener(observadorTeclado);
            }
        });
    }

    @Override
    public void definirCursor(final Cursor cursor) throws ErroExecucaoBiblioteca
    {
        Swing.executarTarefa(new Runnable()
        {
            @Override
            public void run()
            {
                setCursor(cursor);
            }
        });
    }

    @Override
    public boolean estaVisivel()
    {
        return isVisible();
    }

    @Override
    public void exibir(final boolean manterVisivel) throws ErroExecucaoBiblioteca
    {
        Swing.executarTarefa(new Runnable()
        {
            @Override
            public void run()
            {
                setAlwaysOnTop(manterVisivel);
                setIconImage(getIconePadrao());
                setTitle(TITULO_PADRAO);
                redimensionar(LARGURA_PADRAO, ALTURA_PADRAO);
                setVisible(true);
                requestFocusInWindow();
            }
        });
    }

    @Override
    public void ocultar() throws ErroExecucaoBiblioteca
    {
        do
        {
            try
            {
                SwingUtilities.invokeAndWait(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setExtendedState(JFrame.NORMAL);
                        setAlwaysOnTop(false);
                        dispose();
                        setUndecorated(false);
                    }
                });
            }
            catch (InterruptedException excecao)
            {
                /*
                 * Se o usuário interromper o programa fechando a janela ou de
                 * alguma outra forma, ocorrerá um interrupção. Neste caso, 
                 * simplesmente ignoramos e continuamos tentando fechar a janela.
                 */
            }
            catch (InvocationTargetException excecao)
            {
                throw new ErroExecucaoBiblioteca(excecao);
            }
        }
        while (isVisible());
    }

    @Override
    public void definirTitulo(final String titulo) throws ErroExecucaoBiblioteca
    {
        Swing.executarTarefa(new Runnable()
        {
            @Override
            public void run()
            {
                setTitle(titulo);
            }
        });
    }

    @Override
    public void definirIcone(final Image icone) throws ErroExecucaoBiblioteca
    {
        Swing.executarTarefa(new Runnable()
        {
            @Override
            public void run()
            {
                setIconImage(icone);
            }
        });
    }

    @Override
    public void definirDimensoes(final int largura, final int altura) throws ErroExecucaoBiblioteca
    {
        Swing.executarTarefa(new Runnable()
        {
            @Override
            public void run()
            {
                redimensionar(largura, altura);
            }
        });
    }

    private void redimensionar(int largura, int altura)
    {
        if (!tela_cheia)
        {
            this.largura = largura;
            this.altura = altura;            
            
            dispose();
            setVisible(true);
            
            getContentPane().setPreferredSize(new Dimension(largura, altura));
            pack();

            superficieDesenho.redimensionar(largura, altura);
        }
    }

    @Override
    public void minimizar() throws ErroExecucaoBiblioteca
    {
        Swing.executarTarefa(new Runnable()
        {
            @Override
            public void run()
            {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
    }

    @Override
    public void restaurar() throws ErroExecucaoBiblioteca
    {
        Swing.executarTarefa(new Runnable()
        {
            @Override
            public void run()
            {
                setExtendedState(JFrame.NORMAL);
            }
        });
    }

    @Override
    public void ocultarBorda() throws ErroExecucaoBiblioteca
    {
        ocultarBorda(true);
    }

    @Override
    public void exibirBorda() throws ErroExecucaoBiblioteca
    {
        ocultarBorda(false);
    }

    private void ocultarBorda(final boolean ocultar) throws ErroExecucaoBiblioteca
    {
        if (!tela_cheia)
        {
            Swing.executarTarefa(new Runnable()
            {
                @Override
                public void run()
                {
                    dispose();
                    setUndecorated(ocultar);
                    setVisible(true);
                    redimensionar(largura, altura);
                }
            });
        }
    }

    @Override
    public SuperficieDesenho getSuperficieDesenho()
    {
        return superficieDesenho;
    }

    private Image getIconePadrao()
    {
        Window janelaAtiva = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();

        if (janelaAtiva != null && !janelaAtiva.getIconImages().isEmpty())
        {
            return janelaAtiva.getIconImages().get(0);
        }

        return new ImageIcon(getClass().getResource("/br/univali/portugol/nucleo/bibliotecas/graficos/light-bulb.png")).getImage();
    }

    private void instalarObservadores()
    {
        getContentPane().addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                setLocationRelativeTo(null);
            }
        });

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                fechar();
            }
        });
    }

    @Override
    public void fechar()
    {
        programa.interromper();
    }

    private void criarSuperficieDesenho()
    {
        SuperficieDesenhoImpl superficie = new SuperficieDesenhoImpl();
        superficieDesenho = superficie;

        JPanel painelConteudo = (JPanel) getContentPane();

        painelConteudo.setFocusable(false);
        painelConteudo.setRequestFocusEnabled(false);
        painelConteudo.setLayout(null);
        painelConteudo.add(superficie);
    }

    private static final class RetornoJanela
    {
        public JanelaGrafica janela;
    }
    
    @Override
    public int getLargura() throws ErroExecucaoBiblioteca
    {
        return this.largura;
    }

    @Override
    public int getAltura() throws ErroExecucaoBiblioteca
    {
        return this.altura;
    }

    @Override
    public void entrarModoTelaCheia() throws ErroExecucaoBiblioteca
    {
        if (!tela_cheia)
        {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
            this.largura_anterior = this.largura;
            this.altura_anterior = this.altura;
        
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            ocultarBorda();
            definirDimensoes(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
            setLocationRelativeTo(null);
            gd.setFullScreenWindow(this);
            
            tela_cheia = true;
        }
    }

    @Override
    public void sairModoTelaCheia() throws ErroExecucaoBiblioteca
    {
        if (tela_cheia)
        {
            System.out.println("Exiting fullscreen!!");
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
            tela_cheia = false;
            
            gd.setFullScreenWindow(null);
            setExtendedState(JFrame.NORMAL);
            
            exibirBorda();
            definirDimensoes(this.largura_anterior, this.altura_anterior);
            setLocationRelativeTo(null);
            
        }
    }
}

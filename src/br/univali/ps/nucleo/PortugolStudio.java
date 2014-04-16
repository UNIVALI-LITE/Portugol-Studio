package br.univali.ps.nucleo;

import br.univali.ps.DetectorViolacoesThreadSwing;
import br.univali.ps.TelaPrincipal;
import br.univali.ps.TelaPrincipalApplet;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.ui.Configuracoes;
import br.univali.ps.ui.Splash;
import br.univali.ps.ui.TelaPrincipalDesktop;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.telas.TelaErrosPluginsBibliotecas;
import br.univali.ps.ui.telas.TelaInformacoesPlugin;
import br.univali.ps.ui.telas.TelaLicencas;
import br.univali.ps.ui.telas.TelaSobre;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 22/08/2011
 */
public final class PortugolStudio
{
    private static final Logger LOGGER = Logger.getLogger(PortugolStudio.class.getName());
    private static PortugolStudio instancia = null;

    private final List<File> arquivosIniciais = new ArrayList<>();
    private final List<File> diretoriosPluginsInformadosPorParametro = new ArrayList<>();

    private String versao = null;
    private boolean depurando = false;

    private TelaSobre telaSobre = null;
    private TelaPrincipal telaPrincipal = null;
    private TelaInformacoesPlugin telaInformacoesPlugin = null;
    private TelaErrosPluginsBibliotecas telaErrosPluginsBibliotecas = null;
    private TelaLicencas telaLicencas = null;

    private GerenciadorTemas gerenciadorTemas = null;
    private TratadorExcecoes tratadorExcecoes = null;

    private PortugolStudio()
    {

    }

    public static PortugolStudio getInstancia()
    {
        if (instancia == null)
        {
            instancia = new PortugolStudio();
        }

        return instancia;
    }

    public void iniciar()
    {
        iniciar(null);
    }

    public void iniciar(final String[] parametros)
    {
        if (versaoJavaCorreta())
        {
            Splash.exibir();

            inicializarMecanismoLog();
            Splash.definirProgresso(10, "step2.png");

            instalarDetectorExcecoesNaoTratadas();
            Splash.definirProgresso(20, "step3.png");

            processarParametrosLinhaComando(parametros);
            Splash.definirProgresso(30, "step4.png");

            instalarDetectorVialacoesNaThreadSwing();
            Splash.definirProgresso(40, "step4.png");

            definirLookAndFeel();
            Splash.definirProgresso(50, "step5.png");

            registrarFontes();
            Splash.definirProgresso(60, "step5.png");

            definirFontePadraoInterface();
            Splash.definirProgresso(70, "step6.png");

            /* 
             * Os plugins devem sempre ser carregados antes de inicializar o Pool de abas, 
             * caso contrário, os plugins não serão corretamente instalado nas abas ao criá-las
             */
            carregarPlugins();
            Splash.definirProgresso(80, "step7.png");

            carregarBibliotecas();
            Splash.definirProgresso(90, "step8.png");

            AbaCodigoFonte.inicializarPool();
            Splash.definirProgresso(100, "step9.png");

            try
            {
                exibirTelaPrincipal();
            }
            catch (ExcecaoAplicacao excecaoAplicacao)
            {
                getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
            }

            Splash.ocultar();
        }
    }

    private boolean versaoJavaCorreta()
    {
        try
        {
            String property = System.getProperty("java.specification.version");

            if (Double.valueOf(property) < 1.7)
            {
                JOptionPane.showMessageDialog(null, "Para executar o Portugol Studio é preciso utilizar o Java 1.7 ou superior.", "Portugol Studio", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        }
        catch (HeadlessException | NumberFormatException excecao)
        {
            JOptionPane.showMessageDialog(null, "Não foi possível determinar a versão do Java. O Portugol Studio será encerrado!", "Portugol Studio", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void inicializarMecanismoLog()
    {
        final InputStream inputStream = TelaPrincipalDesktop.class.getResourceAsStream("/logging.properties");

        try
        {
            LogManager.getLogManager().readConfiguration(inputStream);
        }
        catch (final IOException excecao)
        {
            Logger.getAnonymousLogger().severe("Não foi possível localizar o arquivo de configuração de log 'logging.properties'");
            Logger.getAnonymousLogger().log(Level.SEVERE, excecao.getMessage(), excecao);
        }
    }

    private void instalarDetectorExcecoesNaoTratadas()
    {
        Thread.setDefaultUncaughtExceptionHandler(getTratadorExcecoes());
    }

    private void processarParametrosLinhaComando(final String[] parametros)
    {
        if (parametros != null)
        {
            processarParametroModoDepuracao(parametros);
            processarParametroArquivosIniciais(parametros);
            processarParametrosDiretoriosPlugins(parametros);
        }
    }

    private void processarParametrosDiretoriosPlugins(final String[] parametros)
    {
        for (String parametro : parametros)
        {
            if (parametro.startsWith("-plugins="))
            {
                String descDiretorios = parametro.split("=")[1];
                String[] diretorios = descDiretorios.split(",");

                if (diretorios != null && diretorios.length > 0)
                {
                    for (String diretorio : diretorios)
                    {
                        diretoriosPluginsInformadosPorParametro.add(new File(diretorio));
                    }
                }
            }
        }
    }

    private void processarParametroModoDepuracao(final String[] parametros)
    {
        setDepurando(false);

        for (String parametro : parametros)
        {
            if (parametro.equals("-debug"))
            {
                setDepurando(true);
            }
        }
    }

    private void processarParametroArquivosIniciais(String[] argumentos)
    {
        if (!rodandoApplet())
        {
            if (argumentos != null && argumentos.length > 0)
            {
                for (String argumento : argumentos)
                {
                    File arquivo = new File(argumento);

                    if (arquivo.exists() && arquivo.isFile() && arquivo.canRead() && arquivo.getName().toLowerCase().endsWith(".por"))
                    {
                        arquivosIniciais.add(arquivo);
                    }
                }
            }
        }
    }

    private void instalarDetectorVialacoesNaThreadSwing()
    {
        RepaintManager.setCurrentManager(new DetectorViolacoesThreadSwing());
    }

    private void definirLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException excecao)
        {
            LOGGER.log(Level.INFO, "Não foi possível alterar o Look And Feel para o Look And Feel padrão do sistema operacional");
        }
    }

    private void registrarFontes()
    {
        final String path = "br/univali/ps/ui/fontes/";

        final String[] fontes =
        {
            "dejavu_sans_mono.ttf",
            "dejavu_sans_mono_bold.ttf",
            "dejavu_sans_mono_bold_oblique.ttf",
            "dejavu_sans_mono_oblique.ttf",
            "tahoma.ttf",
            "tahomabd.ttf"
        };

        for (String nome : fontes)
        {
            try
            {
                Font fonte = Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream(path + nome));
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fonte);
            }
            catch (FontFormatException | IOException excecao)
            {
                final String mensagem = String.format("Não foi possível registrar a fonte '%s' no ambiente", nome);

                LOGGER.log(Level.INFO, mensagem, excecao);
            }
        }
    }

    private void definirFontePadraoInterface()
    {
        try
        {
            SwingUtilities.invokeAndWait(new Runnable()
            {
                @Override
                public void run()
                {
                    Enumeration keys = UIManager.getDefaults().keys();

                    while (keys.hasMoreElements())
                    {
                        Object key = keys.nextElement();
                        Object value = UIManager.get(key);

                        if (value instanceof javax.swing.plaf.FontUIResource)
                        {
                            /*
                             * Não está funcionando. O swing altera a fonte padrão para a maioria dos componentes,
                             * mas não todos. Além disso, o tamanho da fonte da árvore estrutural para de funcionar
                             *
                             */
                            //UIManager.put(key, new Font("Tahoma", Font.PLAIN, 11));                            
                        }
                    }
                }
            });
        }
        catch (InterruptedException | InvocationTargetException excecao)
        {
            LOGGER.log(Level.INFO, "Não foi possível definir uma fonte padrão na interface do usuário", excecao);
        }
    }

    private void carregarPlugins()
    {
        if (!rodandoApplet())
        {
            GerenciadorPlugins gerenciadorPlugins = GerenciadorPlugins.getInstance();
            Configuracoes configuracoes = Configuracoes.getInstancia();

            if (configuracoes.getDiretorioPlugins() != null)
            {
                File diretorioPlugins = new File(configuracoes.getDiretorioPlugins());

                if (diretorioPlugins.exists())
                {
                    for (File pastaPlugin : listarPastasPlugins(diretorioPlugins))
                    {
                        gerenciadorPlugins.incluirDiretorioPlugin(pastaPlugin);
                    }
                }
            }

            for (File diretorio : diretoriosPluginsInformadosPorParametro)
            {
                gerenciadorPlugins.incluirDiretorioPlugin(diretorio);
            }

            gerenciadorPlugins.carregarPlugins();
        }
    }

    private void carregarBibliotecas()
    {
        // Implementar depois
    }

    private List<File> listarPastasPlugins(File diretorioPlugins)
    {
        File[] diretorios = diretorioPlugins.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return pathname.isDirectory();
            }
        });

        return Arrays.asList(diretorios);
    }

    private void exibirTelaPrincipal() throws ExcecaoAplicacao
    {
        try
        {
            SwingUtilities.invokeAndWait(new Runnable()
            {
                @Override
                public void run()
                {
                    getTelaPrincipal().exibir();
                }
            });
        }
        catch (InterruptedException | InvocationTargetException ex)
        {
            throw new ExcecaoAplicacao("Não foi possível iniciar o Portugol Studio", ex, ExcecaoAplicacao.Tipo.ERRO);
        }
    }

    public String getVersao()
    {
        if (versao == null)
        {
            versao = carregarVersao();
        }

        return versao;
    }

    private String carregarVersao()
    {
        try
        {
            Properties propriedades = new Properties();
            propriedades.load(getClass().getClassLoader().getResourceAsStream("versao.properties"));

            return propriedades.getProperty("portugol.studio.versao");
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.SEVERE, "Erro ao carregar o arquivo de versão", excecao);
        }

        return "Indefinida";
    }

    public TelaPrincipal getTelaPrincipal()
    {
        if (telaPrincipal == null)
        {
            if (rodandoApplet())
            {
                telaPrincipal = new TelaPrincipalApplet();
            }
            else
            {
                telaPrincipal = new TelaPrincipalDesktop();

                TelaPrincipalDesktop telaPrincipalDesktop = (TelaPrincipalDesktop) telaPrincipal;
                telaPrincipalDesktop.setArquivosIniciais(arquivosIniciais);
            }
        }

        return telaPrincipal;
    }

    public boolean isDepurando()
    {
        return depurando;
    }

    public void setDepurando(boolean depurando)
    {
        this.depurando = depurando;
    }

    public TratadorExcecoes getTratadorExcecoes()
    {
        if (tratadorExcecoes == null)
        {
            tratadorExcecoes = new TratadorExcecoes();
        }

        return tratadorExcecoes;
    }

    public GerenciadorTemas getGerenciadorTemas()
    {
        if (gerenciadorTemas == null)
        {
            gerenciadorTemas = new GerenciadorTemas();
        }

        return gerenciadorTemas;
    }

    public TelaSobre getTelaSobre()
    {
        if (telaSobre == null)
        {
            telaSobre = new TelaSobre();
        }

        telaSobre.setLocationRelativeTo(null);

        return telaSobre;
    }

    public TelaInformacoesPlugin getTelaInformacoesPlugin()
    {
        if (telaInformacoesPlugin == null)
        {
            telaInformacoesPlugin = new TelaInformacoesPlugin();
        }

        telaInformacoesPlugin.setLocationRelativeTo(null);

        return telaInformacoesPlugin;
    }

    public TelaErrosPluginsBibliotecas getTelaErrosPluginsBibliotecas()
    {
        if (telaErrosPluginsBibliotecas == null)
        {
            telaErrosPluginsBibliotecas = new TelaErrosPluginsBibliotecas();
        }

        telaErrosPluginsBibliotecas.setLocationRelativeTo(null);

        return telaErrosPluginsBibliotecas;
    }

    public TelaLicencas getTelaLicencas()
    {
        if (telaLicencas == null)
        {
            telaLicencas = new TelaLicencas();
        }
        
        telaLicencas.setLocationRelativeTo(null);
        
        return telaLicencas;
    }

    public boolean rodandoApplet()
    {
        return System.getSecurityManager() != null;
    }
}

package br.univali.ps.nucleo;

import br.univali.ps.DetectorViolacoesThreadSwing;
import br.univali.ps.atualizador.GerenciadorAtualizacoes;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.ui.Splash;
import br.univali.ps.ui.TelaPrincipal;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.telas.TelaErrosPluginsBibliotecas;
import br.univali.ps.ui.telas.TelaInformacoesPlugin;
import br.univali.ps.ui.telas.TelaLicencas;
import br.univali.ps.ui.telas.TelaSobre;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;
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

    private final Random random = new Random(System.nanoTime());
    private final List<String> dicas = new ArrayList<>();
    private final List<Integer> dicasExibidas = new ArrayList<>();

    private String versao = null;
    private boolean depurando = false;
    private String uriAtualizacao = "http://siaiacad17.univali.br/~alice/portugol/studio/";

    private TelaSobre telaSobre = null;
    private TelaPrincipal telaPrincipal = null;
    private TelaInformacoesPlugin telaInformacoesPlugin = null;
    private TelaErrosPluginsBibliotecas telaErrosPluginsBibliotecas = null;
    private TelaLicencas telaLicencas = null;

    private GerenciadorTemas gerenciadorTemas = null;
    private TratadorExcecoes tratadorExcecoes = null;
    private GerenciadorAtualizacoes gerenciadorAtualizacoes = null;

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

    public void iniciar(final String[] parametros)
    {
        try
        {
            exibirParametros(parametros);
            
            if (Mutex.existeUmaInstanciaExecutando())
            {
                try
                {
                    Mutex.InstanciaPortugolStudio studio = Mutex.conectarInstanciaPortugolStudio();
                    processarParametroArquivosIniciais(parametros);
                    
                    studio.abrirArquivos(arquivosIniciais);
                    studio.desconectar();
                    
                    finalizar(0);
                }
                catch (Mutex.ErroConexaoInstancia erro)
                {
                    // Se o arquivo de Mutex existe, mas não foi possível abrir a conexão para a instância,
                    // então provavelmente o aplicativo foi fechado de forma inesperada deixando o arquivo pra trás.
                    // Neste caso, apagamos o arquivo e iniciamos uma nova instãncia
                    iniciarNovaInstancia(parametros);
                }
            }
            else
            {
                iniciarNovaInstancia(parametros);
            }
        }
        catch (Mutex.ErroCriacaoMutex erro)
        {
            getTratadorExcecoes().exibirExcecao(erro);
        }
    }

    private void iniciarNovaInstancia(String[] parametros) throws Mutex.ErroCriacaoMutex
    {
        if (versaoJavaCorreta())
        {
            Mutex.criar();
            
            String dica = obterProximaDica();
            Splash.exibir(dica, 9);

            inicializarMecanismoLog();
            Splash.definirProgresso(18, "step2.png");

            instalarDetectorExcecoesNaoTratadas();
            Splash.definirProgresso(27, "step3.png");

            processarParametrosLinhaComando(parametros);
            Splash.definirProgresso(36, "step4.png");

            instalarDetectorVialacoesNaThreadSwing();
            Splash.definirProgresso(45, "step4.png");

            definirLookAndFeel();
            Splash.definirProgresso(54, "step5.png");

            registrarFontes();
            Splash.definirProgresso(63, "step5.png");

            definirFontePadraoInterface();
            Splash.definirProgresso(72, "step6.png");

            /* 
             * Os plugins devem sempre ser carregados antes de inicializar o Pool de abas, 
             * caso contrário, os plugins não serão corretamente instalado nas abas ao criá-las
             */
            carregarPlugins();
            Splash.definirProgresso(81, "step7.png");

            carregarBibliotecas();
            Splash.definirProgresso(90, "step8.png");

            //AbaCodigoFonte.inicializarPool();
            Splash.definirProgresso(100, "step9.png");

            try
            {
                exibirTelaPrincipal();
            }
            catch (ExcecaoAplicacao excecaoAplicacao)
            {
                getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
                finalizar(1);
            }

            Splash.ocultar();
        }
    }

    public void finalizar(int codigo)
    {
        Mutex.destruir();
        Configuracoes.getInstancia().salvar();        
        System.exit(codigo);
    }

    public String obterProximaDica()
    {
        if (dicas.isEmpty())
        {
            carregarDicas();
            carregarDicasExibidas();
        }

        if (dicasExibidas.size() == dicas.size())
        {
            dicasExibidas.clear();
        }

        if (!dicas.isEmpty())
        {
            int indice = random.nextInt(dicas.size());

            while (dicasExibidas.contains(indice))
            {
                indice = (indice + 1) % dicas.size();
            }

            dicasExibidas.add(indice);
            salvarDicasExibidas();

            return dicas.get(indice);
        }

        return null;
    }

    private void carregarDicas()
    {
        String linha;

        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("dicas.txt"), "UTF-8")))
        {
            while ((linha = leitor.readLine()) != null)
            {
                if (linha.trim().length() != 0 && !linha.startsWith("#"))
                {
                    dicas.add(linha);
                }
            }
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.SEVERE, "Erro ao carregar as dicas da Splash Screen", excecao);
        }
    }

    private void carregarDicasExibidas()
    {
        File arquivoDicas = Configuracoes.getInstancia().getCaminhoArquivoDicas();
        String linha;

        if (arquivoDicas.exists())
        {
            try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoDicas)))
            {
                while ((linha = leitor.readLine()) != null)
                {
                    if (linha.trim().length() != 0 && !linha.startsWith("#"))
                    {
                        dicasExibidas.add(Integer.parseInt(linha));
                    }
                }
            }
            catch (IOException excecao)
            {
                LOGGER.log(Level.SEVERE, "Erro ao carregar as dicas já exibidas", excecao);
            }
        }
    }

    private void salvarDicasExibidas()
    {
        if (!dicasExibidas.isEmpty())
        {
            File arquivoDicas = Configuracoes.getInstancia().getCaminhoArquivoDicas();

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoDicas)))
            {
                for (Integer indice : dicasExibidas)
                {
                    escritor.write(indice.toString());
                    escritor.newLine();
                }
            }
            catch (IOException excecao)
            {
                LOGGER.log(Level.SEVERE, "Erro ao salvar as dicas já exibidas", excecao);
            }
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
        final InputStream inputStream = TelaPrincipal.class.getResourceAsStream("/logging.properties");

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
            processarParametroDiretoriosPlugins(parametros);
            processarParametroUriAtualizacao(parametros);
        }
    }

    private void processarParametroUriAtualizacao(final String[] parametros)
    {
        if (parametroExiste("-atualizacao=*", parametros))
        {
            String parametro = obterParametro("-atualizacao=*", parametros);

            String uri = parametro.split("=")[1].trim();

            if (uri.length() > 0)
            {
                try
                {
                    uriAtualizacao = new URI(uri).toString();
                }
                catch (URISyntaxException excecao)
                {

                }
            }
        }
    }

    private void processarParametroDiretoriosPlugins(final String[] parametros)
    {
        if (parametroExiste("-plugins=*", parametros))
        {
            String parametro = obterParametro("-plugins=*", parametros);

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

    private void processarParametroModoDepuracao(final String[] parametros)
    {
        setDepurando(parametroExiste("-debug", parametros));
    }

    private boolean parametroExiste(String nome, String[] parametros)
    {
        for (String parametro : parametros)
        {
            if (nome.endsWith("*") && parametro.startsWith(nome.replace("*", "")))
            {
                return true;
            }
            else if (!nome.endsWith("*") && parametro.equals(nome))
            {
                return true;
            }
        }

        return false;
    }

    private void processarParametroArquivosIniciais(String[] argumentos)
    {
        if (argumentos != null && argumentos.length > 0)
        {
            for (String argumento : argumentos)
            {
                File arquivo = new File(argumento);

                if (arquivo.exists() && arquivo.isFile() && arquivo.canRead())
                {
                    arquivosIniciais.add(arquivo);
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
            "OpenSans-Bold.ttf",
            "OpenSans-Italic.ttf",
            "OpenSans-Regular.ttf",
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
            
            /* 
             * Se estiver rodando o projeto dentro do NetBeans com o JDK 7, é possível que JavaFX não seja encontrado
             * no classpath e um ClassNotFoundException seja gerado.
             *
             * Isto é um bug e ocorre quando o NetBeans é executado com o JDK 8. Para executar corretamente, deve-se
             * alterar o arquivo de configurações do NetBeans (netbeans.conf) para executar com o JDK 7.
             * 
             */
            
            javafx.scene.text.Font.loadFont(Thread.currentThread().getContextClassLoader().getResourceAsStream(path + nome), 12);
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
        GerenciadorPlugins gerenciadorPlugins = GerenciadorPlugins.getInstance();
        Configuracoes configuracoes = Configuracoes.getInstancia();

        if (configuracoes.getDiretorioPlugins() != null)
        {
            File diretorioPlugins = configuracoes.getDiretorioPlugins();

            LOGGER.log(Level.INFO, "Inicializando plugins em: {0}", diretorioPlugins.getAbsolutePath());
            
            if (diretorioPlugins.exists())
            {
                for (File pastaPlugin : listarPastasPlugins(diretorioPlugins))
                {
                    LOGGER.log(Level.INFO, "Inicializando plugin {0}", pastaPlugin.getAbsolutePath());
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
                    getTelaPrincipal().setVisible(true);
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
            telaPrincipal = new TelaPrincipal();
            telaPrincipal.setArquivosIniciais(arquivosIniciais);
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

    public GerenciadorAtualizacoes getGerenciadorAtualizacoes()
    {
        if (gerenciadorAtualizacoes == null)
        {
            gerenciadorAtualizacoes = new GerenciadorAtualizacoes();
        }

        return gerenciadorAtualizacoes;
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

    public String getUriAtualizacao()
    {
        if (uriAtualizacao.endsWith("/"))
        {
            uriAtualizacao = uriAtualizacao.substring(0, uriAtualizacao.length() - 1);
        }

        return uriAtualizacao;
    }

    private String obterParametro(String nome, String[] parametros)
    {
        for (String parametro : parametros)
        {
            if (nome.endsWith("*") && parametro.startsWith(nome.replace("*", "")))
            {
                return parametro;
            }
            else if (!nome.endsWith("*") && parametro.equals(nome))
            {
                return parametro;
            }
        }

        return null;
    }
    
    private void exibirParametros(String[] parametros)
    {
        for (String parametro : parametros)
        {
            LOGGER.log(Level.INFO, "Parametro: {0}", parametro);
        }
    }
}

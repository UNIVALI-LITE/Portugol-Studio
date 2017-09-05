package br.univali.ps.plugins.base;

import br.univali.portugol.util.jar.CarregadorJar;
import br.univali.portugol.util.jar.Classes;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Luiz Fernando
 */
public final class GerenciadorPlugins
{
    private static final Logger LOGGER = Logger.getLogger(GerenciadorPlugins.class.getName());
    private static GerenciadorPlugins instance;

    private final List<Class<? extends Plugin>> pluginsCarregados = new ArrayList<>();

    private final Map<UtilizadorPlugins, List<Plugin>> mapaUtilizadores = new HashMap<>();
    private final Map<Plugin, List<Action>> mapaAcoes = new HashMap<>();
    private final Map<String, CarregadorJar> carregadores = new TreeMap<>();

    private final MetaDadosPlugins metaDadosPlugins = new MetaDadosPlugins(true);
    ///private final CarregadorJar carregadorJar = new CarregadorJar();

    private final ResultadoCarregamento resultadoCarregamento = new ResultadoCarregamento();

    private boolean carregado = false;

    public static GerenciadorPlugins getInstance()
    {
        if (instance == null)
        {
            instance = new GerenciadorPlugins();
        }

        return instance;
    }

    /**
     * Inclui um diretório que será pesquisado pelo gerenciador em busca de
     * plugins. Se o caminho não existir ou apontar para um arquivo ao invés de
     * um diretório, então ele não será incluído.
     * <br/>
     * Só é possível incluir um caminho enquanto os plugins não tiverem sido
     * carregados. Qualquer chamada a este método após o carregamento dos
     * plugins será ignorada.
     *
     * @param caminho o diretório a ser incluído
     */
    public void incluirDiretorioPlugin(File caminho)
    {
        final String caminhoAbsoluto = Util.obterCaminhoAbsoluto(caminho);

        if (caminho.isDirectory() && !carregadores.containsKey(caminhoAbsoluto))
        {
            CarregadorJar carregadorJar = new CarregadorJar();
            carregadorJar.incluirCaminho(caminho);

            carregadores.put(caminhoAbsoluto, carregadorJar);
        }
    }

    /**
     * Carrega os plugins existentes nos diretórios de plugins. O carregamento
     * ocorre apenas uma vez. Múltiplas chamadas a este método serão ignoradas.
     * <br/>
     * Os plugins que contiverem erros, não serão carregados e os erros serão
     * registrados para serem tratados posteriormente.
     * <br/>
     * Os demais plugins, serão carregados normalmente e estarão disponíveis para
     * uso
     *
     * @return o resultado do carregamento dos plugins, a partir do qual é possível
     *         verificar se ocorreram erros e, caso afirmativo, obter uma lista destes erros
     *
     */
    public ResultadoCarregamento carregarPlugins()
    {
        if (!carregado)
        {
            for (CarregadorJar carregadorJar : carregadores.values())
            {
                carregadorJar.carregar();

                List<String> jarsInvalidos = listarArquivosJarInvalidos(carregadorJar);
                List<Class> classes = obterClassesValidas(carregadorJar, jarsInvalidos);

                for (String jarInvalido : jarsInvalidos)
                {
                    resultadoCarregamento.adicionarErro(new ErroCarregamentoPlugin(String.format("Erro ao carregar o plugin: o arquivo '%s' deve conter apenas uma classe de plugin", jarInvalido)));
                }

                for (Class classePlugin : classes)
                {
                    try
                    {
                        File arquivoJar = carregadorJar.obterJarClasse(classePlugin);

                        if (declaracaoValida(classePlugin))
                        {
                            if (construtorValido(classePlugin))
                            {
                                carregarPlugin(classePlugin, arquivoJar);
                            }
                            else
                            {
                                throw new ErroCarregamentoPlugin(montarMensagemConstrutorInvalido(classePlugin), arquivoJar, classePlugin);
                            }
                        }
                        else
                        {
                            throw new ErroCarregamentoPlugin(montarMensagemDeclaracaoInvalida(classePlugin), arquivoJar, classePlugin);
                        }
                    }
                    catch (ErroCarregamentoPlugin erro)
                    {
                        resultadoCarregamento.adicionarErro(erro);
                    }
                }
            }

            carregado = true;
        }

        return resultadoCarregamento;
    }

    private List<String> listarArquivosJarInvalidos(CarregadorJar carregadorJar)
    {
        List<String> jarsInvalidos = new ArrayList<>();

        for (File arquivoJar : carregadorJar.getJars())
        {
            Classes classes = carregadorJar.listarClasses().dosJars(arquivoJar).queEstendemOuImplementam(Plugin.class);

            if (classes.quantidade() > 1)
            {
                jarsInvalidos.add(Util.obterCaminhoAbsoluto(arquivoJar));
            }
        }

        return jarsInvalidos;
    }

    private List<Class> obterClassesValidas(CarregadorJar carregadorJar, List<String> jarsInvalidos)
    {
        List<Class> classesValidas = new ArrayList<>();
        Classes classesCarregadas = carregadorJar.listarClasses().queEstendemOuImplementam(Plugin.class);

        for (Class classe : classesCarregadas)
        {
            String jarClasse = Util.obterCaminhoAbsoluto(carregadorJar.obterJarClasse(classe));

            if (!jarsInvalidos.contains(jarClasse))
            {
                classesValidas.add(classe);
            }
        }

        return classesValidas;
    }

    /**
     * Obtém o resultado do carregamento dos plugins, a partir do qual é possível
     * verificar se ocorreram erros e, caso afirmativo, obter uma lista destes erros.
     * Somente será retornado um resultado se os plugins já tiverem sido carregados
     * através do método {@link GerenciadorPlugins#carregarPlugins()}.
     * Caso contrário, este método retorna null.
     *
     * @return o resultado do carregamento
     */
    public ResultadoCarregamento getResultadoCarregamento()
    {
        if (carregado)
        {
            return resultadoCarregamento;
        }

        return null;
    }

    private void carregarPlugin(Class<? extends Plugin> classePlugin, File arquivoJar) throws ErroCarregamentoPlugin
    {
        if (!pluginsCarregados.contains(classePlugin))
        {
            MetaDadosPlugin metaDadosPlugin = carregarMetaDados(arquivoJar, classePlugin);

            pluginsCarregados.add(classePlugin);
            metaDadosPlugins.incluir(metaDadosPlugin);
        }
        else
        {
            throw new ErroCarregamentoPlugin("A classe de plugin já foi carregada", arquivoJar, classePlugin);
        }
    }

    /**
     * Instala todos os plugins que foram carregados neste utilizador. Se os plugins
     * já tiverem sido instalados neste utilizador, eles não serão instalados novamente.
     *
     * @param utilizador o utilizador no qual os plugins serão instalados
     *
     * @throws ErroInstalacaoPlugin
     */
    public void instalarPlugins(UtilizadorPlugins utilizador) throws ErroInstalacaoPlugin
    {
        for (Class<? extends Plugin> classePlugin : pluginsCarregados)
        {
            try
            {
                if (!mapaUtilizadores.containsKey(utilizador))
                {
                    mapaUtilizadores.put(utilizador, new ArrayList<Plugin>());
                }

                if (!pluginJaInstalado(utilizador, classePlugin))
                {
                    Plugin plugin = classePlugin.newInstance();
                    plugin.setMetaDados(metaDadosPlugins.obter(classePlugin));
                    plugin.inicializar(utilizador);

                    utilizador.instalarPlugin(plugin);

                    mapaUtilizadores.get(utilizador).add(plugin);
                }
            }
            catch (IllegalAccessException | InstantiationException | RuntimeException excecao)
            {
                throw new ErroInstalacaoPlugin(String.format("Erro ao instalar o plugin '%s' no utilizador '%s'", classePlugin.getName(), utilizador.getClass().getName()), excecao);
            }
        }
    }

    private boolean pluginJaInstalado(UtilizadorPlugins utilizador, Class<? extends Plugin> classePlugin)
    {
        boolean pluginJaInstalado = false;

        List<Plugin> plugins = mapaUtilizadores.get(utilizador);

        for (Plugin plugin : plugins)
        {
            if (classePlugin.isInstance(plugin))
            {
                pluginJaInstalado = true;
                break;
            }
        }

        return pluginJaInstalado;
    }

    /**
     * Desinstala todos os plugins deste utilizador. Se os plugins não estiverem
     * instalados, o método não faz nada.
     *
     * @param utilizador o utilizador do qual os plugins serão desinstalados
     */
    public void desinstalarPlugins(UtilizadorPlugins utilizador)
    {
        if (mapaUtilizadores.containsKey(utilizador))
        {
            List<Plugin> plugins = mapaUtilizadores.get(utilizador);
            Iterator<Plugin> iterador = plugins.iterator();

            while (iterador.hasNext())
            {
                Plugin plugin = iterador.next();

                utilizador.desinstalarPlugin(plugin);
                desinstalarAcoesPlugin(plugin);

                plugin.finalizar(utilizador);
                iterador.remove();
            }
        }
    }

    private void desinstalarAcoesPlugin(Plugin plugin)
    {
        if (mapaAcoes.containsKey(plugin))
        {
            List<Action> acoes = new ArrayList<>(mapaAcoes.get(plugin));

            for (Action acao : acoes)
            {
                desinstalarAcaoPlugin(plugin, acao);
            }

            mapaAcoes.remove(plugin);
        }
    }

    /**
     * Instala uma ação do plugin em todos os utilizadores em que o plugin
     * estiver instalado.
     * <br/>
     *
     * @param plugin
     * @param acao
     */
    public void instalarAcaoPlugin(Plugin plugin, Action acao)
    {
        for (UtilizadorPlugins utilizador : mapaUtilizadores.keySet())
        {
            List<Plugin> plugins = mapaUtilizadores.get(utilizador);

            if (plugins.contains(plugin))
            {
                try
                {
                    if (!mapaAcoes.containsKey(plugin))
                    {
                        mapaAcoes.put(plugin, new ArrayList<Action>());
                    }

                    List<Action> acoes = mapaAcoes.get(plugin);

                    if (!acoes.contains(acao))
                    {
                        utilizador.instalarAcaoPlugin(plugin, acao);
                        acoes.add(acao);
                    }
                }
                catch (Exception excecao)
                {
                    LOGGER.log(Level.SEVERE, String.format("Erro ao registrar ação do o plugin '%s' no container '%s'", plugin.getClass().getName(), utilizador.getClass().getName()), excecao);
                }
            }
        }
    }

    /**
     * Desinstala uma ação do plugin de todos os utilizadores no qual este plugin
     * e esta ação tenham sido instalados.
     *
     * @param plugin
     * @param acao
     */
    public void desinstalarAcaoPlugin(Plugin plugin, Action acao)
    {
        for (UtilizadorPlugins utilizador : mapaUtilizadores.keySet())
        {
            List<Plugin> plugins = mapaUtilizadores.get(utilizador);

            if (plugins.contains(plugin))
            {
                if (mapaAcoes.containsKey(plugin))
                {
                    List<Action> acoes = mapaAcoes.get(plugin);

                    if (acoes.contains(acao))
                    {
                        utilizador.desinstalarAcaoPlugin(plugin, acao);
                        acoes.remove(acao);
                    }
                }
            }
        }
    }

    /**
     * Verifica se uma determinada exceção foi gerada a partir de uma classe de
     * Plugin.
     *
     * @param excecao
     *
     * @return
     */
    public boolean excecaoCausadaPorPlugin(Throwable excecao)
    {
        try
        {
            List<Class> classesPlugins = listarClassesPlugins();

            for (StackTraceElement elemento : excecao.getStackTrace())
            {
                Class classe = obterClasseErro(elemento.getClassName());

                if (classesPlugins.contains(classe))
                {
                    return true;
                }
            }
        }
        catch (ClassNotFoundException excecaoCNF)
        {
            LOGGER.log(Level.INFO, "Não foi possível determinar se a excecao foi causada por um plugin", excecao);
        }

        return false;
    }

    private Class obterClasseErro(String nomeClasses) throws ClassNotFoundException
    {
        ClassNotFoundException excecao = new ClassNotFoundException();

        for (CarregadorJar carregadorJar : carregadores.values())
        {
            try
            {
                Class classe = Class.forName(nomeClasses, true, carregadorJar.getCarregadorClasses());
                
                return classe;
            }
            catch (ClassNotFoundException excecaoCNF)
            {
                excecao = excecaoCNF;
            }
        }

        throw excecao;
    }

    private List<Class> listarClassesPlugins()
    {
        final List<Class> classesPlugins = new ArrayList<>();

        for (CarregadorJar carregadorJar : carregadores.values())
        {
            Classes classes = carregadorJar.listarClasses().queEstendemOuImplementam(Plugin.class, VisaoPlugin.class);

            for (Class classePlugin : classes)
            {
                classesPlugins.add(classePlugin);
            }
        }

        return classesPlugins;
    }

    public MetaDadosPlugins obterMetaDadosPlugins()
    {
        return metaDadosPlugins;
    }

    private MetaDadosPlugin carregarMetaDados(File arquivoJar, Class classePlugin) throws ErroCarregamentoPlugin
    {
        MetaDadosPlugin metaDadosPlugin = carregarMetaDadosXml(arquivoJar, classePlugin);

        if (metaDadosPlugin.getNomeClasse().equals(classePlugin.getName()))
        {
            metaDadosPlugin.setClasse(classePlugin);
            metaDadosPlugin.setArquivoJar(arquivoJar);

            carregarLicenca(arquivoJar, classePlugin, metaDadosPlugin);
            carregarIcone("icone_16x16.png", arquivoJar, classePlugin, metaDadosPlugin);
            carregarIcone("icone_32x32.png", arquivoJar, classePlugin, metaDadosPlugin);
        }
        else
        {
            throw new ErroCarregamentoPlugin("A classe informada no arquivo de metadados 'plugin.xml' não corresponde à classe de plugin existente no arquivo JAR", arquivoJar, classePlugin);
        }

        return metaDadosPlugin;
    }

    private void carregarLicenca(final File arquivoJar, final Class classePlugin, final MetaDadosPlugin metaDadosPlugin) throws ErroCarregamentoPlugin
    {
        final InputStream stream = classePlugin.getClassLoader().getResourceAsStream("licenca.txt");

        if (stream != null)
        {
            String linha;
            BufferedReader leitor = new BufferedReader(new InputStreamReader(stream));
            StringBuilder construtorString = new StringBuilder();

            try
            {
                while ((linha = leitor.readLine()) != null)
                {
                    construtorString.append(linha);
                    construtorString.append("\n");
                }

                String licenca = construtorString.toString();

                if (licenca.trim().length() > 0)
                {
                    metaDadosPlugin.setLicenca(licenca);
                }
                else
                {
                    throw new ErroCarregamentoPlugin("O arquivo de licença 'licenca.txt' está vazio", arquivoJar, classePlugin);
                }
            }
            catch (IOException execao)
            {
                throw new ErroCarregamentoPlugin("Ocorreu um erro ao carregar o arquivo de licença 'licenca.txt'", arquivoJar, classePlugin);
            }
            finally
            {
                try
                {
                    leitor.close();
                }
                catch (IOException ex)
                {
                }

                try
                {
                    stream.close();
                }
                catch (IOException ex)
                {
                }
            }
        }
        else
        {
            throw new ErroCarregamentoPlugin("O arquivo de licença 'licenca.txt' não foi encontrado", arquivoJar, classePlugin);
        }
    }

    private void carregarIcone(final String nomeArquivo, final File arquivoJar, final Class classePlugin, final MetaDadosPlugin metaDadosPlugin) throws ErroCarregamentoPlugin
    {
        final InputStream stream = classePlugin.getClassLoader().getResourceAsStream(nomeArquivo);

        if (stream != null)
        {
            try
            {
                BufferedImage icone = ImageIO.read(stream);
                int tamanho = obterTamanhoIcone(nomeArquivo);

                if (icone.getWidth() == tamanho && icone.getHeight() == tamanho)
                {
                    if (tamanho == 16)
                    {
                        metaDadosPlugin.setIcone16x16(icone);
                    }
                    else if (tamanho == 32)
                    {
                        metaDadosPlugin.setIcone32x32(icone);
                    }
                }
                else
                {
                    throw new ErroCarregamentoPlugin(String.format("O arquivo de ícone '%1$s' deve ter %2$dx%2$d pixels", nomeArquivo, tamanho), arquivoJar, classePlugin);
                }
            }
            catch (IOException execao)
            {
                throw new ErroCarregamentoPlugin(String.format("Ocorreu um erro ao carregar o arquivo de ícone '%s': %s", nomeArquivo, execao.getMessage()), arquivoJar, classePlugin);
            }
        }
        else
        {
            throw new ErroCarregamentoPlugin(String.format("O arquivo de ícone '%s' não foi encontrado", nomeArquivo), arquivoJar, classePlugin);
        }
    }

    private int obterTamanhoIcone(String nome)
    {
        Matcher avaliador = Pattern.compile("icone_(\\d{2})x(\\d{2}).png").matcher(nome);

        avaliador.find();

        return Integer.parseInt(avaliador.group(1));
    }

    private MetaDadosPlugin carregarMetaDadosXml(File arquivoJar, Class classePlugin) throws ErroCarregamentoPlugin
    {
        final InputStream stream = classePlugin.getClassLoader().getResourceAsStream("plugin.xml");

        if (stream != null)
        {
            try
            {
                JAXBContext contexto = JAXBContext.newInstance(MetaDadosPlugin.class);
                MetaDadosPlugin metaDadosPlugin = (MetaDadosPlugin) contexto.createUnmarshaller().unmarshal(stream);

                return metaDadosPlugin;
            }
            catch (JAXBException excecao)
            {
                throw new ErroCarregamentoPlugin(String.format("Ocorreu um erro ao carregar o arquivo de metadados 'plugin.xml': %s", excecao.getMessage()), arquivoJar, classePlugin);
            }
        }
        else
        {
            throw new ErroCarregamentoPlugin("O arquivo de metadados 'plugin.xml' não foi encontrado", arquivoJar, classePlugin);
        }
    }

    private boolean construtorValido(Class<? extends Plugin> classePlugin) throws ErroCarregamentoPlugin
    {
        Constructor[] construtores = classePlugin.getDeclaredConstructors();

        if (construtores.length == 1)
        {
            if (construtores[0].getParameterTypes().length == 0)
            {
                if (Modifier.isPublic(construtores[0].getModifiers()))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private String montarMensagemConstrutorInvalido(Class<? extends Plugin> classePlugin)
    {
        Constructor[] construtores = classePlugin.getDeclaredConstructors();

        if (construtores.length > 1)
        {
            return "a classe de plugin deve possuir apenas um construtor";
        }
        if (!Modifier.isPublic(construtores[0].getModifiers()))
        {
            return "o construtor da classe de plugin deve ser público";
        }
        if (construtores[0].getParameterTypes().length > 0)
        {
            return "o construtor da classe de plugin não deve possuir parâmetros";
        }

        return null;
    }

    private boolean declaracaoValida(Class<? extends Plugin> classePlugin)
    {
        boolean publica = Modifier.isPublic(classePlugin.getModifiers());
        boolean efinal = Modifier.isFinal(classePlugin.getModifiers());
        boolean estatica = Modifier.isStatic(classePlugin.getModifiers());
        boolean anonima = classePlugin.isAnonymousClass();
        boolean sintetica = classePlugin.isSynthetic();
        boolean membro = classePlugin.isMemberClass();
        boolean local = classePlugin.isLocalClass();

        return (publica && efinal && !estatica && !anonima && !sintetica && !membro && !local);
    }

    private String montarMensagemDeclaracaoInvalida(Class<? extends Plugin> classePlugin)
    {
        if (!Modifier.isPublic(classePlugin.getModifiers()))
        {
            return "a classe do plugin deve ser pública";
        }
        if (!Modifier.isFinal(classePlugin.getModifiers()))
        {
            return "a classe do plugin deve ser final";
        }
        if (Modifier.isStatic(classePlugin.getModifiers()))
        {
            return "a classe do plugin não pode ser estática";
        }
        if (classePlugin.isAnonymousClass())
        {
            return "a classe do plugin não pode ser uma classe anônima";
        }
        if (classePlugin.isSynthetic())
        {
            return "a classe do plugin não pode ser uma classe sintética";
        }
        if (classePlugin.isMemberClass())
        {
            return "a classe do plugin não pode ser uma classe membro";
        }
        if (classePlugin.isLocalClass())
        {
            return "a classe do plugin não pode ser uma classe local";
        }

        return null;
    }
}

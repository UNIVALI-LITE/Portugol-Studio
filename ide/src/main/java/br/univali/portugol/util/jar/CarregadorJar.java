package br.univali.portugol.util.jar;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Carrega todas as classes de um arquivo JAR na memória.
 *
 * @author Luiz Fernando Noschang
 */
public final class CarregadorJar
{
    private static final Logger LOGGER = Logger.getLogger(CarregadorJar.class.getName());

    private final FileFilter filtroArquivoJar = new FiltroArquivoJar();
    private final List<File> jars = new ArrayList<>();

    private final List<Class> classesCarregadas = new ArrayList<>();
    private final Map<String, List<Class>> mapaClasses = new HashMap<>();

    private ClassLoader carregadorClasses;
    private boolean carregado = false;

    public CarregadorJar()
    {

    }

    /**
     * Inclui um caminho que será pesquisado pelo carregador em busca de arquivos
     * JAR. Se o caminho apontar para um diretório, todos os subdiretórios serão
     * pesquisados recursivamente. Se o caminho apontar para um arquivo JAR, o
     * próprio arquivo JAR será carregado.
     * <br/>
     * Se o caminho informado não existir, ele será ignorado sem que nenhuma
     * exceção seja gerada.
     * <br/>
     * Só é possível incluir um caminho enquanto os JARs não tiverem sido
     * carregados. Qualquer chamada a este método após o carregamento dos JARs
     * será ignorada.
     *
     * @param caminho o caminho a ser incluído no carregamento
     */
    public void incluirCaminho(File caminho)
    {
        if (!carregado && caminho.exists())
        {
            if (caminho.isFile())
            {
                jars.add(caminho);
            }
            else
            {
                incluirDiretorio(caminho);
            }
        }
    }

    private void incluirDiretorio(File diretorio)
    {
        File[] entradas = diretorio.listFiles(filtroArquivoJar);

        if (entradas != null)
        {
            for (File entrada : entradas)
            {
                incluirCaminho(entrada);
            }
        }
    }

    /**
     * Obtém a lista dos JARs que serão carregados.
     *
     * @return a lista dos JARs que serão carregados
     */
    public List<File> getJars()
    {
        return new ArrayList<>(jars);
    }

    /**
     * Carrega os JARs encontrados nos caminhos incluídos. O carregamento ocorre
     * apenas uma vez. Múltiplas chamadas a este método serão ignoradas.
     */
    public void carregar()
    {
        if (!carregado)
        {
            URL[] urls = obterURLs();
            carregadorClasses = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

            for (File jar : jars)
            {
                String caminhoJar = Util.obterCaminhoArquivo(jar);
                
                List<String> nomesClasses = listarNomesClasses(jar);
                List<Class> classesJar = carregarClasses(carregadorClasses, nomesClasses);

                classesCarregadas.addAll(classesJar);
                mapaClasses.put(caminhoJar, classesJar);
            }

            carregado = true;
        }
    }

    public Classes listarClasses()
    {
        return new Classes(classesCarregadas, mapaClasses);
    }

    private URL[] obterURLs()
    {
        URL[] urls = new URL[jars.size()];

        for (int i = 0; i < urls.length; i++)
        {
            try
            {
                urls[i] = jars.get(i).toURI().toURL();
            }
            catch (MalformedURLException excecao)
            {
                LOGGER.log(Level.SEVERE, String.format("Erro ao converter o caminho '%s' para URL", jars.get(i).getAbsolutePath()), excecao);
            }
        }

        return urls;
    }

    private List<Class> carregarClasses(ClassLoader carregador, List<String> nomesClasses)
    {
        List<Class> classes = new ArrayList<>();

        for (String nomeClasse : nomesClasses)
        {
            try
            {
                classes.add(carregador.loadClass(nomeClasse));
            }
            catch (ClassNotFoundException | NoClassDefFoundError excecao)
            {
                /* 
                 *  Em teoria, é seguro assumir que esta exceção nunca ocorrerá, pois 
                 *  os nomes das classes a serem carregadas são obtidos a partir dos 
                 *  próprios JARs.
                 * 
                 * Neste caso, vamos engolir esta exeção e apenas informá-la no log, 
                 * caso ocorra.
                 */

                LOGGER.log(Level.SEVERE, "Esta exceção não deveria ocorrer ao carregar os JARs", excecao);
            }
        }

        return classes;
    }

    private List<String> listarNomesClasses(File arquivoJar)
    {
        List<String> nomes = new ArrayList<>();

        try
        {
            JarInputStream jar = new JarInputStream(new FileInputStream(arquivoJar));
            JarEntry entradaJar;

            while ((entradaJar = jar.getNextJarEntry()) != null)
            {
                if (entradaJar.getName().toLowerCase().endsWith(".class"))
                {
                    String nome = entradaJar.getName();

                    nome = nome.substring(0, nome.toLowerCase().indexOf(".class"));
                    nome = nome.replace("/", ".");

                    nomes.add(nome);
                }
            }
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.SEVERE, String.format("Erro ao listar as classes do JAR '%s'", arquivoJar.getAbsolutePath()), excecao);
        }

        return nomes;
    }

    public File obterJarClasse(Class classe)
    {
        CodeSource codeSource = classe.getProtectionDomain().getCodeSource();
        File jarFile;

        if (codeSource.getLocation() != null)
        {
            String l;

            try
            {
                l = URLDecoder.decode(codeSource.getLocation().toString(), "UTF-8");
            }
            catch (UnsupportedEncodingException ex)
            {
                l = codeSource.getLocation().toString();
            }

            l = l.replace("file:/", "");

            jarFile = new File(l);
        }
        else
        {
            String path = classe.getResource(classe.getSimpleName().replace(".", "/").concat(".class")).getPath();
            String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));

            jarFile = new File(jarFilePath);
        }

        return jarFile;
    }

    public ClassLoader getCarregadorClasses()
    {
        return carregadorClasses;
    }

    private final class FiltroArquivoJar implements FileFilter
    {
        @Override
        public boolean accept(File pathname)
        {
            return pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(".jar");
        }
    }
}

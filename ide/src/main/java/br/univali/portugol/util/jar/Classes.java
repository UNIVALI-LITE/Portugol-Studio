package br.univali.portugol.util.jar;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Representa uma coleção de classes que pode ser filtradas. Os filtros são
 * cumulativos e podem ser chamados em qualquer ordem.
 *
 * @author Luiz Fernando Noschang
 */
public final class Classes implements Iterable<Class>
{
    private final List<Class> classes;
    private final Map<String, List<Class>> mapaClasses;

    private final List<String> filtrosArquivo = new ArrayList<>();
    private final List<String> filtrosPacote = new ArrayList<>();
    private final List<Class> filtrosHeranca = new ArrayList<>();

    private List<Class> classesFiltradas;

    Classes(List<Class> classes, Map<String, List<Class>> mapaClasses)
    {
        this.classes = classes;
        this.classesFiltradas = classes;
        this.mapaClasses = mapaClasses;
    }

    /**
     * Lista somente as classes que tiverem sido carregadas a partir dos JARs
     * informados.
     *
     * @param jars
     *
     * @return as classes encontradas
     */
    public Classes dosJars(File... jars)
    {
        for (File arquivo : jars)
        {
            String caminho = Util.obterCaminhoArquivo(arquivo);

            if (!filtrosArquivo.contains(caminho))
            {
                filtrosArquivo.add(caminho);
            }
        }

        filtrarClasses();

        return this;
    }

    /**
     * Lista somente as classes que estiverem dentro dos pacotes informados. Os
     * nomes de pacotes podem ser absolutos ou podem utilizar o caracter curinga
     * "*".
     * <br/>
     * Considere a seguinte organização:
     * <pre>
     * - com
     *   - example
     *     - foo
     *       - ClassA.class
     *       - ClassB.class
     *     ClassC.class
     *     ClassD.class
     * </pre>
     * <br/>
     * Se o nome do pacote for absoluto, serão carregadas apenas as classes que
     * estiverem contidas diretamente neste pacote. Por exemplo, ao filtrar
     * pelo pacote "com.example", somente as classes "ClassC" e "ClassD" serão
     * retornadas.
     * <br/>
     * Se o nome do pacote utilizar o caracter curinga, serão carregadas todas
     * as classes que estiverem dentro do pacote e seus sub-pacotes. Por exemplo,
     * ao filtrar pelo pacote "com.example.*", serão carregadas as classes
     * "ClassA", "ClassB", "ClassC" e "ClassD".
     * <br/>
     * O caracter curinga somente irá funcionar quando estiver no final do nome
     * do pacote, após separado de nome qualificado ".". Exemplos de nomes de
     * pacote válidos são: "com.*", "com.example.*", "com.example.foo.*".
     * Exemplos de nomes de pacotes inválidos são: "com*", "com.ex*",
     * "com.exampl*", "com.ex*.foo"
     *
     * @param pacotes
     *
     * @return as classes encontradas
     */
    public Classes nosPacotes(String... pacotes)
    {
        for (String pacote : pacotes)
        {
            if (!filtrosPacote.contains(pacote))
            {
                filtrosPacote.add(pacote);
            }
        }

        filtrarClasses();

        return this;
    }

    /**
     * Lista somente as classes que estendem ou implementam <b>qualquer uma</b>
     * das classes informadas.
     *
     * @param classes
     *
     * @return as classes encontradas
     */
    public Classes queEstendemOuImplementam(Class... classes)
    {
        for (Class classe : classes)
        {
            if (!filtrosHeranca.contains(classe))
            {
                filtrosHeranca.add(classe);
            }
        }

        filtrarClasses();

        return this;
    }

    @Override
    public Iterator<Class> iterator()
    {
        return new ArrayList<>(classesFiltradas).iterator();
    }

    private void filtrarArquivos(List<Class> listaClasses)
    {
        if (!filtrosArquivo.isEmpty())
        {
            for (String arquivo : filtrosArquivo)
            {
                if (mapaClasses.containsKey(arquivo))
                {
                    listaClasses.retainAll(mapaClasses.get(arquivo));
                }
            }
        }
    }

    private void filtrarPacotes(List<Class> listaClasses)
    {
        if (!filtrosPacote.isEmpty())
        {
            List<Class> classesEncontradas = new ArrayList<>();

            for (String pacote : filtrosPacote)
            {
                for (Class classe : listaClasses)
                {
                    if (pacote.endsWith(".*"))
                    {
                        String prefixoPacote = pacote.substring(0, pacote.lastIndexOf(".*"));

                        if (classe.getPackage().getName().startsWith(prefixoPacote) && !classesEncontradas.contains(classe))
                        {
                            classesEncontradas.add(classe);
                        }
                    }
                    else if (classe.getPackage().getName().equals(pacote) && !classesEncontradas.contains(classe))
                    {
                        classesEncontradas.add(classe);
                    }
                }
            }

            listaClasses.retainAll(classesEncontradas);
        }
    }

    private void filtrarHeranca(List<Class> listaClasses)
    {
        if (!filtrosHeranca.isEmpty())
        {
            List<Class> classesEncontradas = new ArrayList<>();

            for (Class classePai : filtrosHeranca)
            {
                for (Class classe : listaClasses)
                {
                    if (classePai.isAssignableFrom(classe) && !classe.equals(classePai))
                    {
                        classesEncontradas.add(classe);
                    }
                }
            }

            listaClasses.retainAll(classesEncontradas);
        }
    }

    public int quantidade()
    {
        return classesFiltradas.size();
    }

    public boolean contem(Class classe)
    {
        return classesFiltradas.contains(classe);
    }

    private void filtrarClasses()
    {
        classesFiltradas = new ArrayList<>(classes);

        filtrarArquivos(classesFiltradas);
        filtrarPacotes(classesFiltradas);
        filtrarHeranca(classesFiltradas);
    }
}

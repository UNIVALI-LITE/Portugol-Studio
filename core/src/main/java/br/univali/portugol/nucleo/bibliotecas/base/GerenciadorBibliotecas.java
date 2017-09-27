package br.univali.portugol.nucleo.bibliotecas.base;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ModoAcesso;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoConstante;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.NaoExportar;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Esta classe é responsável por carregar as bibliotecas em memória e gerenciar
 * seu ciclo de vida. É responsável também por criar os metadados das
 * bibliotecas e validar se as mesmas foram implementadas de acordo com as
 * regras definidas na classe base ({@link Biblioteca}).
 *
 * @author Luiz Fernando Noschang
 */
public final class GerenciadorBibliotecas
{
    private static GerenciadorBibliotecas instance = null;

    private List<String> bibliotecasDisponiveis;

    private final MetaDadosBibliotecas metaDadosBibliotecas;
    private final Map<String, Class<? extends Biblioteca>> bibliotecasCarregadas;

    private final Map<String, Biblioteca> bibliotecasCompartilhadas;
    private final Map<Programa, Map<String, Biblioteca>> bibliotecasReservadas;

    public static GerenciadorBibliotecas getInstance()
    {
        if (instance == null)
        {
            instance = new GerenciadorBibliotecas();
        }

        return instance;
    }

    private GerenciadorBibliotecas()
    {
        bibliotecasCarregadas = new TreeMap<>();
        metaDadosBibliotecas = new MetaDadosBibliotecas();

        bibliotecasCompartilhadas = new TreeMap<>();
        bibliotecasReservadas = new TreeMap<>(new ComparadorPrograma());
    }

    private class ComparadorPrograma implements Comparator<Programa>
    {
        @Override
        public int compare(Programa o1, Programa o2)
        {
            Integer h1 = System.identityHashCode(o1);
            Integer h2 = System.identityHashCode(o2);

            return h1.compareTo(h2);
        }
    }

    public List<String> listarBibliotecasDisponiveis()
    {
        if (bibliotecasDisponiveis == null)
        {
            bibliotecasDisponiveis = new ArrayList<>();
            bibliotecasDisponiveis.add("Util");
            bibliotecasDisponiveis.add("Graficos");
            bibliotecasDisponiveis.add("Matematica");
            bibliotecasDisponiveis.add("Teclado");
            bibliotecasDisponiveis.add("Texto");
            bibliotecasDisponiveis.add("Tipos");
            bibliotecasDisponiveis.add("Mouse");
            bibliotecasDisponiveis.add("Arquivos");
            bibliotecasDisponiveis.add("Sons");

            Collections.sort(bibliotecasDisponiveis);
        }

        return new ArrayList<>(bibliotecasDisponiveis);
    }

    public void registrarBibliotecaExterna(Class<? extends Biblioteca> biblioteca) throws ErroCarregamentoBiblioteca
    {
        final String nome = biblioteca.getSimpleName();

        if (!bibliotecasCarregadas.containsKey(nome))
        {
            listarBibliotecasDisponiveis();
            bibliotecasDisponiveis.add(nome);
            bibliotecasCarregadas.put(nome, biblioteca);
            MetaDadosBiblioteca metaDadosBiblioteca = obterMetaDadosBiblioteca(nome, biblioteca);
            metaDadosBibliotecas.incluir(metaDadosBiblioteca);
        }
        else
        {
            throw new ErroCarregamentoBiblioteca(nome, "Uma biblioteca já foi registrada com este nome");
        }
    }

    /**
     * Obtém os metadados da biblioteca especificada. Os metadados contém
     * informações importantes sobre a biblioteca, como a documentação e os
     * metadados das funções e constantes.
     *
     * <p>
     * A chamada a este métodos fará com que o {@link GerenciadorBibliotecas}
     * tente carregar a biblioteca caso ela ainda não esteja em memória.
     * </p>
     *
     *
     * @param nome o nome da biblioteca para a qual se deseja obter os metadados
     * @return os metadados da biblioteca em questão
     *
     * @throws ErroCarregamentoBiblioteca esta exceção é jogada caso o
     * {@link GerenciadorBibliotecas} não consiga carregar a biblioteca
     * especificada
     */
    public MetaDadosBiblioteca obterMetaDadosBiblioteca(String nome) throws ErroCarregamentoBiblioteca
    {
        if (!metaDadosBibliotecas.contem(nome))
        {
            Class classeBiblioteca = carregarBiblioteca(nome);
            MetaDadosBiblioteca metaDadosBiblioteca = obterMetaDadosBiblioteca(nome, classeBiblioteca);

            metaDadosBibliotecas.incluir(metaDadosBiblioteca);
        }

        return metaDadosBibliotecas.obter(nome);
    }

    private Class<? extends Biblioteca> carregarBiblioteca(String nome) throws ErroCarregamentoBiblioteca
    {
        if (!bibliotecasCarregadas.containsKey(nome))
        {
            try
            {
                if (!listarBibliotecasDisponiveis().contains(nome))
                {
                    throw new ClassNotFoundException();
                }

                Class classeBiblioteca = Class.forName("br.univali.portugol.nucleo.bibliotecas.".concat(nome)).asSubclass(Biblioteca.class);
                bibliotecasCarregadas.put(nome, classeBiblioteca);

                return classeBiblioteca;
            }
            catch (ClassNotFoundException | NoClassDefFoundError excecao)
            {
                throw new ErroCarregamentoBiblioteca(nome, "a biblioteca não foi encontrada");
            }
            catch (ClassCastException excecao)
            {
                throw new ErroCarregamentoBiblioteca(nome, "a biblioteca não estende a classe base");
            }
        }

        return bibliotecasCarregadas.get(nome);
    }

    private MetaDadosBiblioteca obterMetaDadosBiblioteca(String nomeBiblioteca, Class<? extends Biblioteca> classeBiblioteca) throws ErroCarregamentoBiblioteca
    {
        if (declaracaoValida(classeBiblioteca))
        {
            PropriedadesBiblioteca propriedadesBiblioteca = obterAnotacaoClasse(nomeBiblioteca, classeBiblioteca, PropriedadesBiblioteca.class);
            DocumentacaoBiblioteca documentacaoBiblioteca = obterAnotacaoClasse(nomeBiblioteca, classeBiblioteca, DocumentacaoBiblioteca.class);

            MetaDadosBiblioteca metaDadosBiblioteca = new MetaDadosBiblioteca();

            metaDadosBiblioteca.setNome(nomeBiblioteca);
            metaDadosBiblioteca.setTipo(propriedadesBiblioteca.tipo());
            metaDadosBiblioteca.setDocumentacao(documentacaoBiblioteca);
            metaDadosBiblioteca.setPacoteBiblioteca(classeBiblioteca.getPackage().getName());

            MetaDadosConstantes metaDadosConstantes = obterMetaDadosConstantes(nomeBiblioteca, classeBiblioteca);
            MetaDadosFuncoes metaDadosFuncoes = obterMetaDadosFuncoes(nomeBiblioteca, classeBiblioteca);

            if (!metaDadosFuncoes.vazio() || !metaDadosConstantes.vazio())
            {
                metaDadosBiblioteca.setMetaDadosFuncoes(metaDadosFuncoes);
                metaDadosBiblioteca.setMetaDadosConstantes(metaDadosConstantes);
            }

            else
            {
                throw new ErroCarregamentoBiblioteca(nomeBiblioteca, "a biblioteca não está exportando nenhuma constante ou função");
            }

            return metaDadosBiblioteca;
        }

        else
        {
            throw new ErroCarregamentoBiblioteca(nomeBiblioteca, montarMensagemDeclaracaoInvalida(classeBiblioteca));
        }
    }

    private MetaDadosFuncoes obterMetaDadosFuncoes(String nomeBiblioteca, Class<? extends Biblioteca> classeBiblioteca) throws ErroCarregamentoBiblioteca
    {
        MetaDadosFuncoes metaDadosFuncoes = new MetaDadosFuncoes();

        for (Method metodo : classeBiblioteca.getDeclaredMethods())
        {
            if (Modifier.isPublic(metodo.getModifiers()) && metodo.getAnnotation(NaoExportar.class) == null && !metodo.getName().equals("inicializar") && !metodo.getName().equals("finalizar"))
            {
                MetaDadosFuncao metaDadosFuncao = obterMetaDadosFuncao(nomeBiblioteca, metodo);

                if (!metaDadosFuncoes.contem(metaDadosFuncao.getNome()))
                {
                    metaDadosFuncoes.incluir(metaDadosFuncao);
                }

                else
                {
                    throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o método '%s' possui sobrecargas", metodo.getName()));
                }
            }
        }

        return metaDadosFuncoes;
    }

    private MetaDadosFuncao obterMetaDadosFuncao(String nomeBiblioteca, Method metodo) throws ErroCarregamentoBiblioteca
    {
        if (!Modifier.isStatic(metodo.getModifiers()))
        {
            if (jogaExcecao(metodo, ErroExecucaoBiblioteca.class))
            {
                if (jogaExcecao(metodo, InterruptedException.class))
                {
                    if (!metodo.getReturnType().isArray())
                    {
                        DocumentacaoFuncao documentacaoFuncao = obterAnotacaoMetodo(nomeBiblioteca, metodo, DocumentacaoFuncao.class);

                        MetaDadosFuncao metaDadosFuncao = new MetaDadosFuncao();

                        metaDadosFuncao.setNome(metodo.getName());
                        metaDadosFuncao.setDocumentacao(documentacaoFuncao);
                        metaDadosFuncao.setQuantificador(Quantificador.VALOR);
                        metaDadosFuncao.setTipoDado(obterTipoDadoMetodo(nomeBiblioteca, metodo));
                        metaDadosFuncao.setMetaDadosParametros(obterMetaDadosParametros(nomeBiblioteca, metodo, documentacaoFuncao));

                        return metaDadosFuncao;
                    }
                    else
                    {
                        throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o retorno do método '%s' não pode ser um vetor nem uma matriz para ser exportado como uma função", metodo.getName()));
                    }
                }
                else
                {
                    throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o método '%s' deve jogar uma exceção do tipo '%s' para ser exportado como uma função", metodo.getName(), InterruptedException.class.getName()));
                }
            }

            else
            {
                throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o método '%s' deve jogar uma exceção do tipo '%s' para ser exportado como uma função", metodo.getName(), ErroExecucaoBiblioteca.class.getName()));
            }
        }

        else
        {
            throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o método '%s' não pode ser estático para ser exportado como uma função", metodo.getName()));
        }
    }

    private boolean jogaExcecao(Method metodo, Class<? extends Exception> classeExcecao)
    {
        for (Class<?> tipoExcecao : metodo.getExceptionTypes())
        {
            if (tipoExcecao == classeExcecao)
            {
                return true;
            }
        }

        return false;
    }

    private MetaDadosParametros obterMetaDadosParametros(String nomeBiblioteca, Method metodo, DocumentacaoFuncao documentacaoFuncao) throws ErroCarregamentoBiblioteca
    {
        MetaDadosParametros metaDadosParametros = new MetaDadosParametros();

        Class[] tiposParametros = metodo.getParameterTypes();
        Annotation[] anotacoesParametros = documentacaoFuncao.parametros();

        for (int indice = 0; indice < tiposParametros.length; indice++)
        {
            if (indice < anotacoesParametros.length)
            {
                DocumentacaoParametro documentacaoParametro = (DocumentacaoParametro) anotacoesParametros[indice];
                MetaDadosParametro metaDadosParametro = new MetaDadosParametro();

                metaDadosParametro.setNome(documentacaoParametro.nome());
                metaDadosParametro.setDocumentacaoParametro(documentacaoParametro);
                metaDadosParametro.setTipoDado(obterTipoDadoParametro(nomeBiblioteca, metodo, indice, documentacaoParametro.nome()));
                metaDadosParametro.setIndice(indice);
                metaDadosParametro.setModoAcesso(obterModoAcessoParametro(metodo, indice));
                metaDadosParametro.setQuantificador(obterQuantificadorParametro(metodo, indice));

                if (!metaDadosParametros.contem(metaDadosParametro.getNome()))
                {
                    metaDadosParametros.incluir(metaDadosParametro);
                }

                else
                {
                    throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o método '%s' está documentando diferentes parâmetros com o mesmo nome: '%s'", metodo.getName(), documentacaoParametro.nome()));
                }
            }

            else
            {
                throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o %dº parâmetro da função '%s' não foi documentado com a anotação '%s'", indice + 1, metodo.getName(), DocumentacaoParametro.class.getSimpleName()));
            }
        }

        if (anotacoesParametros.length > tiposParametros.length)
        {
            throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("a função '%s' está documentando um parâmetro inexistente: '%s'", metodo.getName(), ((DocumentacaoParametro) anotacoesParametros[anotacoesParametros.length - 1]).nome()));
        }

        return metaDadosParametros;
    }

    private Quantificador obterQuantificadorParametro(Method metodo, int indice)
    {
        Parameter parametro = metodo.getParameters()[indice];
        Class tipo = parametro.getType();

        if (tipo.isArray() && (tipo.getComponentType() != null && tipo.getComponentType().isArray()))
        {
            return Quantificador.MATRIZ;
        }
        else if (tipo.isArray())
        {
            return Quantificador.VETOR;
        }

        return Quantificador.VALOR;
    }

    private ModoAcesso obterModoAcessoParametro(Method metodo, int indice)
    {
        Parameter parametro = metodo.getParameters()[indice];
        Class tipo = parametro.getType();

        if (tipo.isArray() || (tipo.getComponentType() != null && tipo.getComponentType().isArray()))
        {
            return ModoAcesso.POR_REFERENCIA;
        }

        return ModoAcesso.POR_VALOR;
    }

    private MetaDadosConstantes obterMetaDadosConstantes(String nomeBiblioteca, Class<? extends Biblioteca> classeBiblioteca) throws ErroCarregamentoBiblioteca
    {
        MetaDadosConstantes metaDadosConstantes = new MetaDadosConstantes();

        for (Field atributo : classeBiblioteca.getDeclaredFields())
        {
            if (Modifier.isPublic(atributo.getModifiers()))
            {
                if (Modifier.isStatic(atributo.getModifiers()))
                {
                    if (Modifier.isFinal(atributo.getModifiers()))
                    {
                        if (maiusculo(atributo.getName()))
                        {
                            if (!atributo.getType().isArray())
                            {
                                DocumentacaoConstante documentacaoConstante = obterAnotacaoAtributo(nomeBiblioteca, atributo, DocumentacaoConstante.class);
                                MetaDadosConstante metaDadosConstante = new MetaDadosConstante();

                                metaDadosConstante.setNome(atributo.getName());
                                metaDadosConstante.setDocumentacao(documentacaoConstante);
                                metaDadosConstante.setQuantificador(Quantificador.VALOR);
                                metaDadosConstante.setTipoDado(obterTipoDadoConstante(nomeBiblioteca, atributo));

                                try
                                {
                                    metaDadosConstante.setValor(atributo.get(null));
                                }
                                catch (IllegalArgumentException | IllegalAccessException excecao)
                                {
                                    metaDadosConstante.setValor("indefinido");
                                }

                                if (!metaDadosConstantes.contem(metaDadosConstante.getNome()))
                                {
                                    metaDadosConstantes.incluir(metaDadosConstante);
                                }
                            }

                            else
                            {
                                throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o atributo '%s' não pode ser um vetor nem uma matriz para ser exportado como uma constante", atributo.getName()));
                            }
                        }

                        else
                        {
                            throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o atributo '%s' deve ter o nome todo em letras maiúsuclas para ser exportado como uma constante", atributo.getName()));
                        }
                    }

                    else
                    {
                        throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o atributo '%s' deve ser final para ser exportado como uma constante", atributo.getName()));
                    }
                }

                else
                {
                    throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o atributo '%s' deve ser estático para ser exportado como uma constante", atributo.getName()));
                }
            }
        }

        return metaDadosConstantes;
    }

    private TipoDado obterTipoDadoConstante(String nomeBiblioteca, Field atributo) throws ErroCarregamentoBiblioteca
    {
        Class classeTipo = atributo.getType();

        if ((classeTipo == Void.class || classeTipo == Void.TYPE))
        {
            throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o atributo '%s' não pode ser do tipo primitivo '%s' nem do tipo '%s'", atributo.getName(), Void.TYPE.getName(), classeTipo.getName()));
        }

        TipoDado tipoDado;

        if ((tipoDado = TipoDado.obterTipoDadoPeloTipoJava(classeTipo)) != null)
        {
            return tipoDado;
        }

        throw new ErroCarregamentoBiblioteca(
                nomeBiblioteca, String.format("o tipo do atributo '%s' deve ser um dos tipos a seguir: '%s', '%s', '%s', '%s', ou '%s'", atributo.getName(),
                        TipoDado.CADEIA.getTipoJava().getName(),
                        TipoDado.CARACTER.getTipoJava().getName(),
                        TipoDado.INTEIRO.getTipoJava().getName(),
                        TipoDado.LOGICO.getTipoJava().getName(),
                        TipoDado.REAL.getTipoJava().getName()
                ));
    }

    private boolean maiusculo(String texto)
    {
        return texto.equals(texto.toUpperCase());
    }

    private <T extends Annotation> T obterAnotacaoClasse(String nomeBiblioteca, Class<? extends Biblioteca> classeBiblioteca, Class<T> classeAnotacao) throws ErroCarregamentoBiblioteca
    {
        T anotacao;

        if ((anotacao = classeBiblioteca.getAnnotation(classeAnotacao)) != null)
        {
            return anotacao;
        }

        throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("a biblioteca não foi anotada com a anotação '%s'", classeAnotacao.getSimpleName()));
    }

    private <T extends Annotation> T obterAnotacaoAtributo(String nomeBiblioteca, Field atributo, Class<T> classeAnotacao) throws ErroCarregamentoBiblioteca
    {
        T anotacao;

        if ((anotacao = atributo.getAnnotation(classeAnotacao)) != null)
        {
            return anotacao;
        }

        throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o atributo '%s' não foi anotado com a anotação '%s'", atributo.getName(), classeAnotacao.getSimpleName()));
    }

    private TipoDado obterTipoDadoMetodo(String nomeBiblioteca, Method metodo) throws ErroCarregamentoBiblioteca
    {
        Class classeTipo = metodo.getReturnType();

        if (classeTipo == Void.class)
        {
            throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o retorno do método '%s' deve ser do tipo primitivo '%s' ao invés do tipo '%s'", metodo.getName(), Void.TYPE.getName(), classeTipo.getName()));
        }

        TipoDado tipoDado;

        if ((tipoDado = TipoDado.obterTipoDadoPeloTipoJava(classeTipo)) != null)
        {
            if (tipoDado != TipoDado.TODOS)
            {
                return tipoDado;
            }
        }

        throw new ErroCarregamentoBiblioteca(
                nomeBiblioteca, String.format("o retorno do método '%s' deve ser um dos tipos a seguir: '%s', '%s', '%s', '%s', '%s' ou '%s'", metodo.getName(),
                        TipoDado.CADEIA.getTipoJava().getName(),
                        TipoDado.CARACTER.getTipoJava().getName(),
                        TipoDado.INTEIRO.getTipoJava().getName(),
                        TipoDado.LOGICO.getTipoJava().getName(),
                        TipoDado.REAL.getTipoJava().getName(),
                        TipoDado.VAZIO.getTipoJava().getName()
                ));
    }

    private TipoDado obterTipoDadoParametro(String nomeBiblioteca, Method metodo, int indice, String nomeParametro) throws ErroCarregamentoBiblioteca
    {
        Class classeTipo = metodo.getParameterTypes()[indice];

        if (classeTipo.isArray())
        {
            classeTipo = classeTipo.getComponentType();

            if (classeTipo == null)
            {
                throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o tipo do parâmetro '%s' do método '%s' não foi especificado", nomeParametro, metodo.getName()));
            }
        }

        if (classeTipo.isArray())
        {
            classeTipo = classeTipo.getComponentType();
        }

        if (classeTipo == java.lang.Object.class)
        {
            return TipoDado.TODOS;
        }

        if ((classeTipo == Void.class || classeTipo == Void.TYPE))
        {
            throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("o parâmetro '%s' do método '%s' não pode ser do tipo primitivo '%s' nem do tipo '%s'", nomeParametro, metodo.getName(), Void.TYPE.getName(), classeTipo.getName()));
        }

        TipoDado tipoDado;

        if ((tipoDado = TipoDado.obterTipoDadoPeloTipoJava(classeTipo)) != null)
        {
            return tipoDado;
        }

        throw new ErroCarregamentoBiblioteca(
                nomeBiblioteca, String.format("o tipo do parâmetro '%s' do método '%s' deve ser um dos tipos a seguir: '%s', '%s', '%s', '%s', '%s' ou '%s'", nomeParametro, metodo.getName(),
                        TipoDado.CADEIA.getTipoJava().getName(),
                        TipoDado.CARACTER.getTipoJava().getName(),
                        TipoDado.INTEIRO.getTipoJava().getName(),
                        TipoDado.LOGICO.getTipoJava().getName(),
                        TipoDado.REAL.getTipoJava().getName(),
                        Object.class.getName()
                ));
    }

    private Class obterTipoReferencia(Type tipo)
    {
        if (tipo instanceof ParameterizedType)
        {
            Type[] generics = ((ParameterizedType) tipo).getActualTypeArguments();

            if (generics != null && generics.length > 0)
            {
                return (Class) generics[0];
            }
        }

        return null;
    }

    private <T extends Annotation> T obterAnotacaoMetodo(String nomeBiblioteca, Method metodo, Class<T> classeAnotacao) throws ErroCarregamentoBiblioteca
    {
        T anotacao;

        if ((anotacao = metodo.getAnnotation(classeAnotacao)) != null)
        {
            return anotacao;
        }

        throw new ErroCarregamentoBiblioteca(nomeBiblioteca, String.format("a função '%s' não foi anotada com a anotação '%s'", metodo.getName(), classeAnotacao.getSimpleName()));
    }

    private boolean declaracaoValida(Class<? extends Biblioteca> classeBiblioteca) throws ErroCarregamentoBiblioteca
    {
        boolean publica = Modifier.isPublic(classeBiblioteca.getModifiers());
        boolean efinal = Modifier.isFinal(classeBiblioteca.getModifiers());
        boolean estatica = Modifier.isStatic(classeBiblioteca.getModifiers());
        boolean anonima = classeBiblioteca.isAnonymousClass();
        boolean sintetica = classeBiblioteca.isSynthetic();
        boolean membro = classeBiblioteca.isMemberClass();
        boolean local = classeBiblioteca.isLocalClass();

        return (publica && efinal && !estatica && !anonima && !sintetica && !membro && !local);
    }

    private String montarMensagemDeclaracaoInvalida(Class<? extends Biblioteca> classeBiblioteca)
    {
        if (!Modifier.isPublic(classeBiblioteca.getModifiers()))
        {
            return "a biblioteca deve ser pública";
        }
        if (!Modifier.isFinal(classeBiblioteca.getModifiers()))
        {
            return "a biblioteca deve ser final";
        }
        if (Modifier.isStatic(classeBiblioteca.getModifiers()))
        {
            return "a biblioteca não pode ser estática";
        }
        if (classeBiblioteca.isAnonymousClass())
        {
            return "a biblioteca não pode ser uma classe anônima";
        }
        if (classeBiblioteca.isSynthetic())
        {
            return "a biblioteca não pode ser uma classe sintética";
        }
        if (classeBiblioteca.isMemberClass())
        {
            return "a biblioteca não pode ser uma classe membro";
        }
        if (classeBiblioteca.isLocalClass())
        {
            return "a biblioteca não pode ser uma classe local";
        }

        return null;
    }
}

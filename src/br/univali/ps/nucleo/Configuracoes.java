package br.univali.ps.nucleo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Configuracoes
{
    private static final Logger LOGGER = Logger.getLogger(Configuracoes.class.getName());
    private static final Configuracoes instancia = new Configuracoes();

    private static File arquivoConfiguracoes;
    private static String diretorioExemplos;
    private static String diretorioPlugins;

    public static final String TAMANHO_FONTE_CONSOLE = "tamanhoFonteConsole";
    public static final String TAMANHO_FONTE_EDITOR = "tamanhoFonteEditor";
    public static final String EXIBIR_OPCOES_EXECUCAO = "exibirOpcoesExecucao";
    public static final String DIRETORIO_EXEMPLOS = "diretorioExemplos";
    public static final String TEMA_EDITOR = "temaEditor";
    public static final String TAMANHO_FONTE_ARVORE = "tamanhoFonteArvore";
    public static final String CENTRALIZAR_CODIGO_FONTE = "centralizarCodigoFonte";
    //public static final String URL_DOS_PACOTES = "http://localhost/portugol-pacotes/";
    public static final String URL_DOS_PACOTES = "http://siaiacad17.univali.br/~alice/portugolStudio/";
    public static final String EXIBIR_AVISO_VIDEO_AULAS = "exibirAvisoVideoAulas";

    
    private final PropertyChangeSupport suporteMudancaPropriedade = new PropertyChangeSupport(this);
    private final Properties configuracoes = new Properties();

    private boolean exibirOpcoesExecucao = false;    
    private float tamanhoFonteConsole = 12.0f;
    private float tamanhoFonteEditor = 12.0f;
    private float tamanhoFonteArvore = 12.0f;
    private String temaEditor = "Padrão";
    private boolean centralizarCodigoFonte = false;
    private boolean exibirAvisoVideoAulas = true;

    private Configuracoes()
    {
        carregar();
    }

    public static Configuracoes getInstancia()
    {
        return instancia;
    }

    public static String getUrlDosPacotes()
    {
        return URL_DOS_PACOTES;
    }

    private void carregar()
    {
        if (!PortugolStudio.getInstancia().rodandoApplet())
        {
            try
            {
                arquivoConfiguracoes = obterCaminhoArquivoConfiguracoes();
                configuracoes.load(new FileReader(arquivoConfiguracoes));

                diretorioExemplos = obterDiretorioPortugol().getAbsolutePath() + "/exemplos";
                diretorioPlugins = obterDiretorioPortugol().getAbsolutePath() + "/plugins";
                exibirOpcoesExecucao = Boolean.parseBoolean(configuracoes.getProperty(EXIBIR_OPCOES_EXECUCAO, "false"));
                tamanhoFonteConsole = Float.parseFloat(configuracoes.getProperty(TAMANHO_FONTE_CONSOLE, "12.0"));
                tamanhoFonteEditor = Float.parseFloat(configuracoes.getProperty(TAMANHO_FONTE_EDITOR, "12.0"));
                temaEditor = configuracoes.getProperty(TEMA_EDITOR, "Padrão");
                tamanhoFonteArvore = Float.parseFloat(configuracoes.getProperty(TAMANHO_FONTE_ARVORE, "12.0"));
                centralizarCodigoFonte = Boolean.parseBoolean(configuracoes.getProperty(CENTRALIZAR_CODIGO_FONTE, "false"));
                exibirAvisoVideoAulas = Boolean.parseBoolean(configuracoes.getProperty(EXIBIR_AVISO_VIDEO_AULAS, "true"));

            }
            catch (IOException excecao)
            {
                LOGGER.log(Level.INFO, "Não foi possível carregar as configurações do Portugol Studio. As configurações padrão serão utilizadas", excecao);
            }
        }
    }

    public void salvar()
    {
        if (!PortugolStudio.getInstancia().rodandoApplet())
        {
            try
            {
                configuracoes.store(new FileWriter(arquivoConfiguracoes), "");
            }
            catch (IOException excecao)
            {
                LOGGER.log(Level.INFO, "Não foi possível salvar as configurações do Portugol Studio. As configurações padrão serão utilizadas na próxima inicialização", excecao);
            }
        }
    }

    public float getTamanhoFonteConsole()
    {
        return tamanhoFonteConsole;
    }

    public void setTemaEditor(String theme)
    {
        String oldTheme = this.temaEditor;

        this.configuracoes.setProperty(TEMA_EDITOR, theme);
        this.temaEditor = theme;

        suporteMudancaPropriedade.firePropertyChange(TEMA_EDITOR, oldTheme, theme);
    }

    public String getTemaEditor()
    {
        return this.temaEditor;
    }

    public void setTamanhoFonteConsole(float tamanhoFonteConsole)
    {
        float valorAntigo = this.tamanhoFonteConsole;

        this.configuracoes.setProperty(TAMANHO_FONTE_CONSOLE, Float.toString(tamanhoFonteConsole));
        this.tamanhoFonteConsole = tamanhoFonteConsole;

        suporteMudancaPropriedade.firePropertyChange(TAMANHO_FONTE_CONSOLE, valorAntigo, tamanhoFonteConsole);
    }

    public float getTamanhoFonteEditor()
    {
        return tamanhoFonteEditor;
    }

    public void setTamanhoFonteEditor(float tamanhoFonteEditor)
    {
        float valorAntigo = this.tamanhoFonteEditor;

        this.configuracoes.setProperty(TAMANHO_FONTE_EDITOR, Float.toString(tamanhoFonteEditor));
        this.tamanhoFonteEditor = tamanhoFonteEditor;

        suporteMudancaPropriedade.firePropertyChange(TAMANHO_FONTE_EDITOR, valorAntigo, tamanhoFonteEditor);
    }

    public void setExibirOpcoesExecucao(boolean exibirOpcoesExecucao)
    {
        boolean valorAntigo = this.exibirOpcoesExecucao;

        this.configuracoes.setProperty(EXIBIR_OPCOES_EXECUCAO, Boolean.toString(exibirOpcoesExecucao));
        this.exibirOpcoesExecucao = exibirOpcoesExecucao;

        suporteMudancaPropriedade.firePropertyChange(EXIBIR_OPCOES_EXECUCAO, valorAntigo, exibirOpcoesExecucao);
    }

    public boolean isExibirAvisoVideoAulas()
    {
        return exibirAvisoVideoAulas;
    }

    public void setExibirAvisoVideoAulas(boolean exibirAvisoVideoAulas)
    {

        boolean valorAntigo = this.exibirAvisoVideoAulas;

        this.configuracoes.setProperty(EXIBIR_AVISO_VIDEO_AULAS, Boolean.toString(exibirAvisoVideoAulas));
        this.exibirAvisoVideoAulas = exibirAvisoVideoAulas;

        suporteMudancaPropriedade.firePropertyChange(EXIBIR_AVISO_VIDEO_AULAS, valorAntigo, exibirAvisoVideoAulas);
    }

    public String getDiretorioExemplos()
    {
        return diretorioExemplos;
    }

    public String getDiretorioPlugins()
    {
        return diretorioPlugins;
    }

    public boolean isExibirOpcoesExecucao()
    {
        return exibirOpcoesExecucao;
    }

    public void setTamanhoFonteArvore(float tamanhoFonteArvore)
    {
        float valorAntigo = this.tamanhoFonteArvore;

        this.configuracoes.setProperty(TAMANHO_FONTE_ARVORE, Float.toString(tamanhoFonteArvore));
        this.tamanhoFonteArvore = tamanhoFonteArvore;

        suporteMudancaPropriedade.firePropertyChange(TAMANHO_FONTE_ARVORE, valorAntigo, tamanhoFonteArvore);
    }

    public float getTamanhoFonteArvore()
    {
        return tamanhoFonteArvore;
    }

    public void setCentralizarCodigoFonte(boolean centralizarCodigoFonte)
    {
        boolean valorAntigo = this.centralizarCodigoFonte;

        this.configuracoes.setProperty(CENTRALIZAR_CODIGO_FONTE, Boolean.toString(centralizarCodigoFonte));
        this.centralizarCodigoFonte = centralizarCodigoFonte;

        suporteMudancaPropriedade.firePropertyChange(CENTRALIZAR_CODIGO_FONTE, valorAntigo, centralizarCodigoFonte);
    }

    public boolean isCentralizarCodigoFonte()
    {
        return centralizarCodigoFonte;
    }

    public void adicionarObservadorConfiguracoes(PropertyChangeListener observador)
    {
        suporteMudancaPropriedade.addPropertyChangeListener(observador);
    }

    public void adicionarObservadorConfiguracao(PropertyChangeListener observador, String configuracao)
    {
        suporteMudancaPropriedade.addPropertyChangeListener(configuracao, observador);
    }

    public void removerObservadorConfiguracoes(PropertyChangeListener observador)
    {
        suporteMudancaPropriedade.removePropertyChangeListener(observador);
    }

    public void removerObservadorConfiguracao(PropertyChangeListener observador, String configuracao)
    {
        suporteMudancaPropriedade.removePropertyChangeListener(configuracao, observador);
    }

    public static File obterDiretorioPortugol()
    {
        File diretorioUsuario = new File(".");

        try
        {
            diretorioUsuario = new File(System.getProperty("user.home"));

            if (!diretorioUsuario.exists())
            {
                throw new Exception();
            }
        }
        catch (Exception ex)
        {
        }

        File caminho = new File(diretorioUsuario, ".portugol");

        if (!caminho.exists())
        {
            caminho.mkdir();
        }
        return caminho;
    }

    private File obterCaminhoArquivoConfiguracoes()
    {
        String nomeArquivo = "configuracoes.properties";
        File caminho = obterDiretorioPortugol();

        return new File(caminho, nomeArquivo);
    }
}

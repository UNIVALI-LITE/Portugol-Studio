package br.univali.ps.nucleo;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.weblaf.jOptionPane.QuestionDialog;
import br.univali.ps.ui.telas.TelaPrincipal;
import br.univali.ps.ui.utils.FileHandle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Configuracoes
{
    private static final Logger LOGGER = Logger.getLogger(Configuracoes.class.getName());
    private static Configuracoes instancia = null;

    public static final String TAMANHO_FONTE_CONSOLE = "tamanhoFonteConsole";
    public static final String TAMANHO_FONTE_EDITOR = "tamanhoFonteEditor";
    public static final String EXIBIR_OPCOES_EXECUCAO = "exibirOpcoesExecucao";
    public static final String TEMA_EDITOR = "temaEditor";
    public static final String TEMA_PORTUGOL = "temaPortugol";
    public static final String TAMANHO_FONTE_ARVORE = "tamanhoFonteArvore";
    public static final String CENTRALIZAR_CODIGO_FONTE = "centralizarCodigoFonte";
    public static final String ENVIAR_DADOS = "enviar_dados";
    public static final String EXIBIR_AVISO_VIDEO_AULAS = "exibirAvisoVideoAulas";
    public static final String EXIBIR_AVISO_RENOMEAR = "exibirAvisoRenomear";
    public static final String EXIBIR_TUTORIAL_USO = "exibirTutorialUso";
    public static final String EXIBIR_DICAS_INTERFACE = "exibirDicasInterface";
    public static final String URI_ATUALIZACAO = "uriAtualizacao";
    public static final String CAMINHO_ULTIMO_DIRETORIO = "caminhoUltimoDiretorio";
    public static final String ID_USUARIO_ANALYTICS = "idAnalytics";
    public static final String MAC_USUARIO = "macAnalytics";

    private final PropertyChangeSupport suporteMudancaPropriedade = new PropertyChangeSupport(this);
    private final Properties configuracoes = new Properties();

    private final File diretorioConfiguracoes = resolverDiretorioConfiguracoes();
    private final File caminhoArquivoConfiguracoes = new File(diretorioConfiguracoes, "configuracoes.properties");
    private final File caminhoArquivoDicas = new File(diretorioConfiguracoes, "dicas_exibidas.txt");
    private final File caminhoArquivosRecentes = new File(diretorioConfiguracoes, "arquivos_recentes.txt");
    private final File caminhoArquivoTemas = new File(diretorioConfiguracoes, "temas.json");
    
    private final File diretorioInstalacao = Caminhos.obterDiretorioInstalacao();
    private final File diretorioAjuda = resolverDiretorioAjuda();
    private final File diretorioExemplos = resolverDiretorioExemplos();
    private final File diretorioTemporario = new File(diretorioConfiguracoes, "temp");
    private final File diretorioCompilacao = new File(diretorioTemporario, "compilacao");
    private final File diretorioPlugins = new File(diretorioInstalacao, "plugins");
    private final File diretorioBibliotecas = new File(diretorioInstalacao, "bibliotecas");
    private final File diretorioAplicacao = new File(diretorioInstalacao, "aplicacao");
    private final File caminhoLogAtualizacoes = new File(diretorioInstalacao, "atualizacao.log");
    private final File caminhoInicializadorPortugolStudio = new File(diretorioInstalacao, "inicializador-ps.jar");
    private final File caminhoArquivosRecuperadosOriginais = new File(diretorioTemporario, "arquivos_originais.txt");
    
    private boolean exibirOpcoesExecucao = false;
    private float tamanhoFonteConsole = 12.0f;
    private float tamanhoFonteEditor = 12.0f;
    private float tamanhoFonteArvore = 12.0f;
    private String temaEditor = "Dark";
    private String temaPortugol = "Dark";
    private String icones = "Escuros";
    private boolean envio_de_dados = true;
    private boolean centralizarCodigoFonte = false;
    private boolean exibirAvisoVideoAulas = true;
    private boolean exibirAvisoRenomear = true;
    private boolean exibirTutorialUso = true;
    private boolean exibirDicasInterface = true;
    private String userMac = "0-0-0-0";
    private String userAnalyticsID = "bafta";
    private String uriAtualizacao = "https://api.github.com/repos/UNIVALI-LITE/Portugol-Studio/releases/latest";
    private String caminhoUltimoDiretorio = getDiretorioUsuario().toString();
    private JSONObject arquivo_temas = null;

    private Configuracoes()
    {
        carregar();
        this.arquivo_temas = carregar_temas();
        setTemaPortugol(arquivo_temas.getString("tema_selecionado"));
        
    }

    public static Configuracoes getInstancia()
    {
        if (instancia == null)
        {
            instancia = new Configuracoes();
        }

        return instancia;
    }

    private void carregar()
    {
        try
        {
            configuracoes.load(new FileReader(caminhoArquivoConfiguracoes));
            userMac = configuracoes.getProperty(MAC_USUARIO, "nao");
            userAnalyticsID = configuracoes.getProperty(ID_USUARIO_ANALYTICS, "nao");
            exibirOpcoesExecucao = Boolean.parseBoolean(configuracoes.getProperty(EXIBIR_OPCOES_EXECUCAO, "false"));
            tamanhoFonteConsole = Float.parseFloat(configuracoes.getProperty(TAMANHO_FONTE_CONSOLE, "12.0"));
            tamanhoFonteEditor = Float.parseFloat(configuracoes.getProperty(TAMANHO_FONTE_EDITOR, "12.0"));
            envio_de_dados = Boolean.parseBoolean(configuracoes.getProperty(ENVIAR_DADOS, "true"));
            tamanhoFonteArvore = Float.parseFloat(configuracoes.getProperty(TAMANHO_FONTE_ARVORE, "12.0"));
            centralizarCodigoFonte = Boolean.parseBoolean(configuracoes.getProperty(CENTRALIZAR_CODIGO_FONTE, "false"));
            exibirAvisoVideoAulas = Boolean.parseBoolean(configuracoes.getProperty(EXIBIR_AVISO_VIDEO_AULAS, "true"));
            exibirAvisoRenomear = Boolean.parseBoolean(configuracoes.getProperty(EXIBIR_AVISO_RENOMEAR, "true"));
            exibirTutorialUso = Boolean.parseBoolean(configuracoes.getProperty(EXIBIR_TUTORIAL_USO, "true"));
            exibirDicasInterface = Boolean.parseBoolean(configuracoes.getProperty(EXIBIR_DICAS_INTERFACE, "true"));
            uriAtualizacao = configuracoes.getProperty(URI_ATUALIZACAO, "https://api.github.com/repos/UNIVALI-LITE/Portugol-Studio/releases/latest");
            caminhoUltimoDiretorio = configuracoes.getProperty(CAMINHO_ULTIMO_DIRETORIO, getCaminhoUltimoDiretorio());
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.INFO, "Não foi possível carregar as configurações do Portugol Studio. As configurações padrão serão utilizadas");
        }
    }

    public void salvar()
    {        
        try
        {
            configuracoes.store(new FileWriter(caminhoArquivoConfiguracoes), "");
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.INFO, "Não foi possível salvar as configurações do Portugol Studio. As configurações padrão serão utilizadas na próxima inicialização", excecao);
        }
    }
    
    public JSONObject carregar_temas()
    {
        File f = getCaminhoArquivoTemas();
        
        try 
        {            
            String jsonText = FileHandle.read(new FileInputStream(f));
            JSONObject json = new JSONObject(jsonText);            
            return json;
            
        } 
        catch (Exception ex) 
        {
            return ColorController.getTemaPadrao();
        }
    }
    
    public void salvarTema(String tema)
    {        
        File arquivosTemasFile = getCaminhoArquivoTemas();        
        arquivo_temas.put("tema_selecionado", tema);
        
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivosTemasFile)))
        {
            escritor.write(arquivo_temas.toString());
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.SEVERE, "Erro ao criar ou editar tema", excecao);
        }
    }

    public boolean isExibirDicasInterface()
    {
        return exibirDicasInterface;
    }

    public void setExibirDicasInterface(boolean exibirDicasInterface)
    {
        boolean oldValue = this.exibirDicasInterface;
        this.configuracoes.setProperty(EXIBIR_DICAS_INTERFACE, Boolean.toString(exibirDicasInterface));
        this.exibirDicasInterface = exibirDicasInterface;
        suporteMudancaPropriedade.firePropertyChange(EXIBIR_DICAS_INTERFACE, oldValue, exibirDicasInterface);
    }

    public float getTamanhoFonteConsole()
    {
        return tamanhoFonteConsole;
    }

    public String getUserMac() {      
        return userMac;
    }

    public void setUserMac(String userMac) {
        this.configuracoes.setProperty(MAC_USUARIO, userMac);
        this.userMac = userMac;  
    }

    public String getUserAnalyticsID() {
        return userAnalyticsID;
    }

    public void setUserAnalyticsID(String userAnalyticsID) {
        this.configuracoes.setProperty(ID_USUARIO_ANALYTICS, userAnalyticsID);
        this.userAnalyticsID = userAnalyticsID;
    }
    
    public void setTemaPortugol(String theme)
    {
        String oldTheme = this.temaPortugol;
        this.temaPortugol = theme;
        salvarTema(theme);
        
        suporteMudancaPropriedade.firePropertyChange(TEMA_PORTUGOL, oldTheme, theme);
    }

    public String getTemaPortugol()
    {
        return this.temaPortugol;
    }

    public String getIconesCores() {
        return icones;
    }

    public void setIconesCores(String icones) {
        this.icones = icones;
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

    public boolean isExibirAvisoRenomear()
    {
        return exibirAvisoRenomear;
    }

    public void setExibirAvisoRenomear(boolean exibirAvisoRenomear)
    {
        boolean valorAntigo = this.exibirAvisoRenomear;

        this.configuracoes.setProperty(EXIBIR_AVISO_RENOMEAR, Boolean.toString(exibirAvisoRenomear));
        this.exibirAvisoRenomear = exibirAvisoRenomear;

        suporteMudancaPropriedade.firePropertyChange(EXIBIR_AVISO_VIDEO_AULAS, valorAntigo, exibirAvisoRenomear);
    }

    public void setExibirTutorialUso(boolean exibirTutorialUso)
    {
        boolean valorAntigo = this.exibirAvisoVideoAulas;

        this.configuracoes.setProperty(EXIBIR_TUTORIAL_USO, Boolean.toString(exibirTutorialUso));
        this.exibirTutorialUso = exibirTutorialUso;

        suporteMudancaPropriedade.firePropertyChange(EXIBIR_TUTORIAL_USO, valorAntigo, exibirTutorialUso);
    }

    public boolean isExibirTutorialUso()
    {
        return exibirTutorialUso;
    }

    public boolean isExibirOpcoesExecucao()
    {
        return exibirOpcoesExecucao;
    }

    public boolean isEnvio_de_dados() {
        return envio_de_dados;
    }

    public void setEnvio_de_dados(boolean envio_de_dados) {
        this.envio_de_dados = envio_de_dados;
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

    public void alterarCentralizarCondigoFonte()
    {
        setCentralizarCodigoFonte(!centralizarCodigoFonte);
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

    public void TrocarTema(String Tema) 
    {
        if(confirmouReinicializacao())
        {            
            setTemaPortugol(Tema);
            restartApplication();
        }
    }
    
    public boolean isTemaDark()
    {
        return icones.equals("Claros");
    }
    
    public void restartApplication()
    {
        TelaPrincipal telaPrincipal = PortugolStudio.getInstancia().getTelaPrincipal();
        
            if(telaPrincipal.fecharAplicativoParaReiniciar())
            {
                if(Caminhos.rodandoEmDesenvolvimento())
                {
                    PortugolStudio.getInstancia().finalizar(0);
                }
                try{
                    final String javaBin = Caminhos.obterCaminhoExecutavelJava();
                    final File currentJar = new File(this.getDiretorioAplicacao() + File.separator + "portugol-studio.jar");

                    /* is it a jar file? */
                    if(!currentJar.getName().endsWith(".jar"))
                    {
                        System.out.println("nao deu");
                        return;
                    }
                    RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
                    List<String> arguments = runtimeMxBean.getInputArguments();

                    /* Build command: java -jar application.jar */
                    final ArrayList<String> command = new ArrayList<>();
                    command.add(javaBin);
                    command.add("-jar");
                    command.addAll(arguments);
                    command.add(currentJar.getCanonicalPath());
                    final ProcessBuilder builder = new ProcessBuilder(command);
                    builder.directory(this.getDiretorioInstalacao());            

                    builder.start();
                    PortugolStudio.getInstancia().finalizar(0);
                }catch(Exception e)
                {
                    System.out.println("Alguma coisa deu errada no reiniciar");
                }
            }
            else
            {
                QuestionDialog.getInstance().showMessage("Você deve fechar todas as abas de código antes de reiniciar");
            }
        
    }
    
    private boolean confirmouReinicializacao()
    {
        int resp = QuestionDialog.getInstance().showConfirmMessage("Para trocar de tema o Portugol Studio precisa reinicializar! Confirma?");
        if (resp == JOptionPane.YES_OPTION)
        {
            return true;
        }
        else if (resp == JOptionPane.CANCEL_OPTION || resp == JOptionPane.CLOSED_OPTION)
        {
            return false;
        }
        else
        {
            return false;
        }
    }
    
    public String getUriAtualizacao()
    {
        if (uriAtualizacao.endsWith("/"))
        {
            uriAtualizacao = uriAtualizacao.substring(0, uriAtualizacao.length() - 1);
        }
        return uriAtualizacao;
    }

    public void setUriAtualizacao(String uriAtualizacao)
    {
        String valorAntigo = this.uriAtualizacao;
        
        this.configuracoes.setProperty(URI_ATUALIZACAO, uriAtualizacao);
        this.uriAtualizacao = uriAtualizacao;
        
        suporteMudancaPropriedade.firePropertyChange(URI_ATUALIZACAO, valorAntigo, uriAtualizacao);        
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

    public File getDiretorioInstalacao()
    {
        return diretorioInstalacao;
    }

    public File getDiretorioTemporario()
    {
        return diretorioTemporario;
    }

    public File getDiretorioCompilacao()
    {
        return diretorioCompilacao;
    }
    
    public File getDiretorioAjuda()
    {
        return diretorioAjuda;
    }

    public File getDiretorioBibliotecas()
    {
        return diretorioBibliotecas;
    }

    public File getDiretorioExemplos()
    {
        return diretorioExemplos;
    }

    public File getDiretorioPlugins()
    {
        return diretorioPlugins;
    }

    public File getDiretorioAplicacao()
    {
        return diretorioAplicacao;
    }

    public File getCaminhoArquivoDicas()
    {
        return caminhoArquivoDicas;
    }

    public File getCaminhoArquivosRecentes() {
        return caminhoArquivosRecentes;
    }

    public File getCaminhoArquivoTemas() {
        return caminhoArquivoTemas;
    }   

    public File getCaminhoArquivosRecuperadosOriginais() {
        return caminhoArquivosRecuperadosOriginais;
    }

    public JSONObject getArquivo_temas() {
        return arquivo_temas;
    }

    public void setArquivo_temas(JSONObject arquivo_temas) {
        this.arquivo_temas = arquivo_temas;
    }   
    
    
    private File resolverDiretorioConfiguracoes()
    {
        File diretorioUsuario = getDiretorioUsuario();
        File caminho = new File(diretorioUsuario, ".portugol");

        if (!caminho.exists())
        {
            caminho.mkdir();
        }

        return caminho;
    }

    public File getDiretorioUsuario()
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

        return diretorioUsuario;
    }
    
    public void setCaminhoUltimoDiretorio(File ultimoDiretorio) {
        this.configuracoes.setProperty(CAMINHO_ULTIMO_DIRETORIO, ultimoDiretorio.toString());
        this.caminhoUltimoDiretorio = ultimoDiretorio.toString();
     }
     
    public String getCaminhoUltimoDiretorio() {
        return caminhoUltimoDiretorio;
    }

    public File getCaminhoLogAtualizacoes()
    {
        return caminhoLogAtualizacoes;
    }

    public File getCaminhoInicializadorPortugolStudio()
    {
        return caminhoInicializadorPortugolStudio;
    }

    private File resolverDiretorioAjuda()
    {
        if (rodandoEmDesenvolvimento())
        {
            return new File("src/main/assets/ajuda");
        }

        return new File(diretorioInstalacao, "ajuda");
    }

    private File resolverDiretorioExemplos()
    {
        if (rodandoEmDesenvolvimento())
        {
            return new File("src/main/assets/exemplos");
        }

        return new File(diretorioInstalacao, "exemplos");
    }

    public static boolean rodandoEmDesenvolvimento()
    {
        return Caminhos.rodandoEmDesenvolvimento();
    }

    public static boolean rodandoNoWindows()
    {
        String os = System.getProperty("os.name");

        return (os != null && os.toLowerCase().contains("win"));
    }

    private File extrairCaminho(File arquivo)
    {
        try
        {
            return arquivo.getCanonicalFile();
        }
        catch (IOException ex)
        {
            return arquivo.getAbsoluteFile();
        }
    }
}

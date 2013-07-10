package br.univali.ps.nucleo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Configuracoes
{    
    private static final String arquivoConfiguracoes = "configuracoes.properties";
    
    public static final String TAMANHO_FONTE_CONSOLE = "tamanhoFonteConsole";
    public static final String EXIBIR_OPCOES_EXECUCAO = "exibirOpcoesExecucao";
    public static final String DIRETORIO_EXEMPLOS = "diretorioExemplos";
    
    private PropertyChangeSupport suporteMudancaPropriedade = new PropertyChangeSupport(this);
    private Properties configuracoes = new Properties();

    private boolean exibirOpcoesExecucao;
    private String diretorioExemplos;
    private float tamanhoFonteConsole;
    
    Configuracoes()
    {
        
    }
    
    public void carregar() throws ExcecaoAplicacao
    {
        try
        {
            configuracoes.load(new FileReader("./" + arquivoConfiguracoes));
            
            exibirOpcoesExecucao = Boolean.parseBoolean(configuracoes.getProperty(EXIBIR_OPCOES_EXECUCAO, "true"));
            diretorioExemplos = configuracoes.getProperty(DIRETORIO_EXEMPLOS, "./Exemplos");
            tamanhoFonteConsole = Float.parseFloat(configuracoes.getProperty(TAMANHO_FONTE_CONSOLE, "12.0"));
        }
        catch (IOException excecao)
        {
            throw new ExcecaoAplicacao("Não foi possível carregar as configurações do PortugolStudio. As configurações padrão serão utilizadas", excecao, ExcecaoAplicacao.Tipo.AVISO);
        }
    }
    
    public void salvar() throws ExcecaoAplicacao
    {
        try
        {
            configuracoes.store(new FileWriter("./" + arquivoConfiguracoes), "");
        }
        catch (IOException excecao)
        {
            throw new ExcecaoAplicacao("Não foi possível salvar as configurações do PortugolStudio. As configurações padrão serão utilizadas na próxima inicialização", excecao, ExcecaoAplicacao.Tipo.AVISO);
        }
    }

    public float getTamanhoFonteConsole()
    {
        return tamanhoFonteConsole;
    }

    public void setTamanhoFonteConsole(float tamanhoFonteConsole)
    {
        float valorAntigo = this.tamanhoFonteConsole;
        
        this.configuracoes.setProperty(TAMANHO_FONTE_CONSOLE, Float.toString(tamanhoFonteConsole));
        this.tamanhoFonteConsole = tamanhoFonteConsole;
        
        suporteMudancaPropriedade.firePropertyChange(TAMANHO_FONTE_CONSOLE, valorAntigo, tamanhoFonteConsole);        
    }
    
    public void setExibirOpcoesExecucao(boolean exibirOpcoesExecucao)
    {
        boolean valorAntigo = this.exibirOpcoesExecucao;
        
        this.configuracoes.setProperty(EXIBIR_OPCOES_EXECUCAO, Boolean.toString(exibirOpcoesExecucao));
        this.exibirOpcoesExecucao = exibirOpcoesExecucao;
        
        suporteMudancaPropriedade.firePropertyChange(EXIBIR_OPCOES_EXECUCAO, valorAntigo, exibirOpcoesExecucao);
    }

    public String getDiretorioExemplos()
    {
        return diretorioExemplos;
    }

    public void setDiretorioExemplos(String diretorioExemplos)
    {
        String valorAntigo = this.diretorioExemplos;
        
        this.configuracoes.setProperty(DIRETORIO_EXEMPLOS, diretorioExemplos);
        this.diretorioExemplos = diretorioExemplos;
        
        suporteMudancaPropriedade.firePropertyChange(DIRETORIO_EXEMPLOS, valorAntigo, diretorioExemplos);
    }

    public boolean isExibirOpcoesExecucao()
    {
        return exibirOpcoesExecucao;
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
}

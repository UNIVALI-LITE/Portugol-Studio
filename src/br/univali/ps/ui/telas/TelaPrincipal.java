/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas;

import br.univali.ps.atualizador.GerenciadorAtualizacoes;
import br.univali.ps.atualizador.ObservadorAtualizacao;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.ui.Lancador;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.abas.AbaInicial;
import br.univali.ps.ui.paineis.PainelTabuladoPrincipal;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.utils.FileHandle;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author lite
 */
public class TelaPrincipal extends javax.swing.JPanel
{
    private boolean abrindo = true;
    private List<File> arquivosIniciais;
    private static final Logger LOGGER = Logger.getLogger(TelaPrincipal.class.getName());
    /**
    /**
     * Creates new form TelaInicial
     */
    public TelaPrincipal()
    {
        initComponents();
        criaAbas();
        configurarCores();
        instalarObservadores();
    }
    private void criaAbas()
    {
        painelTabuladoPrincipal.setAbaInicial(new AbaInicial(this));
    }
    
    private void configurarCores(){
//        mainPanel.setBackground(ColorController.COR_DESTAQUE);
        painelTabuladoPrincipal.setBackground(ColorController.COR_PRINCIPAL);
    }
    
    
    private void instalarObservadores()
    {
        instalarObservadorJanela();
    }

    private void instalarObservadorJanela()
    {
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                fecharAplicativo();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                Configuracoes configuracoes = Configuracoes.getInstancia();
                if(configuracoes.isExibirDicasInterface()){
                    SwingUtilities.invokeLater(() -> {
                        PortugolStudio.getInstancia().getTelaDicas().setVisible(true);
                    });
                }
                
                LOGGER.log(Level.INFO, "Janela principal aberta!");
            }
            
        });

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                if (abrindo)
                {
                    abrindo = false;

                    // Por enquanto o André pediu para desativar esta verificação, ela só estará disponível 
                    // no final do ano
                    //verificarAtualizacaoCritica();
                    if (Configuracoes.getInstancia().isExibirTutorialUso())
                    {
                        //TODO: criar e executar tutorial de uso antes de iniciar o Portugol
                        dispararProcessosAbertura();
                    }
                    else
                    {
                        dispararProcessosAbertura();
                    }
                }
            }
        });
    }

    private void verificarAtualizacaoCritica()
    {
        File diretorioJava = new File(Configuracoes.getInstancia().getDiretorioInstalacao(), "java");

        if (!diretorioJava.exists() && Configuracoes.rodandoNoWindows() && !Configuracoes.rodandoNoNetbeans())
        {
            String mensagem = "Caro usuário, foi lançada uma atualização crítica para o Portugol Studio que não pode ser\n"
                    + "instalada através do sistema de atualização automática.\n\n"
                    + "Você pode continuar utilizando o Portugol Studio normalmente, mas é altamente recomendável que\n"
                    + "instale esta atualização.\n\n"
                    + "Para instalar a atualização, você deve baixar e instalar a versão mais recente do Portugol Studio,\n"
                    + "disponível no endereço: http://sourceforge.net/projects/portugolstudio.\n\n"
                    + "Deseja ir agora para o site e baixar a nova versão?";

            int opcao = JOptionPane.showConfirmDialog(TelaPrincipal.this, mensagem, "Portugol Studio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcao == JOptionPane.YES_OPTION)
            {
                try
                {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://sourceforge.net/projects/portugolstudio"));
                }
                catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(TelaPrincipal.this, "Não foi possível abrir o seu navegador de Internet!\nPara baixar a versão mais recente do Portugol Studio, acesse manualmente o endereço:\n\nhttp://sourceforge.net/projects/portugolstudio", "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void dispararProcessosAbertura()
    {
        abrirArquivosCodigoFonte(arquivosIniciais);

//        exibirErrosPluginsBibliotecas();
        exibirLogAtualizacoes();

        //baixarNovasAtualizacoes();
    }

    private void exibirErrosPluginsBibliotecas()
    {
        boolean errosPlugins = GerenciadorPlugins.getInstance().getResultadoCarregamento().contemErros();
        boolean errosBibliotecas = false;

        if (errosPlugins || errosBibliotecas)
        {
            TelaErrosPluginsBibliotecas telaErrosPluginsBibliotecas = PortugolStudio.getInstancia().getTelaErrosPluginsBibliotecas();
            telaErrosPluginsBibliotecas.setVisible(true);
        }
    }

    private void exibirLogAtualizacoes()
    {
        File logAtualizacoes = Configuracoes.getInstancia().getCaminhoLogAtualizacoes();

        if (logAtualizacoes.exists())
        {
            try
            {
                String atualizacoes = FileHandle.open(logAtualizacoes);
                TelaLogAtualizacoes telaLogAtualizacoes = new TelaLogAtualizacoes();

                telaLogAtualizacoes.setAtualizacoes(atualizacoes);
                telaLogAtualizacoes.setVisible(true);

                FileUtils.deleteQuietly(logAtualizacoes);
            }
            catch (Exception excecao)
            {
                LOGGER.log(Level.SEVERE, "Erro ao carregar o log de atualizações", excecao);
            }
        }
    }

    private void baixarNovasAtualizacoes()
    {
        GerenciadorAtualizacoes gerenciadorAtualizacoes = PortugolStudio.getInstancia().getGerenciadorAtualizacoes();
        gerenciadorAtualizacoes.setObservadorAtualizacao(new ObservadorAtualizacao()
        {
            @Override
            public void atualizacaoConcluida()
            {
                //BotoesControleAba cabecalho = (BotoesControleAba) getPainelTabulado().getAbaInicial().getCabecalho();
                FabricaDicasInterface.mostrarNotificacao("Foram encontradas novas atualizações. Elas serão instaladas na próxima vez em que o Portugol Studio for iniciado");
            }
        });

        gerenciadorAtualizacoes.baixarAtualizacoes();
    }

    public void criarNovoCodigoFonte()
    {
        final AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
        painelTabuladoPrincipal.add(abaCodigoFonte);
        revalidate();
    }

    public void abrirArquivosCodigoFonte(final List<File> arquivos)
    {
        if (arquivos != null && !arquivos.isEmpty())
        {
            Lancador.getInstance().focarJanela();

            SwingUtilities.invokeLater(() ->
            {
                TelaPrincipal.this.setEnabled(false);
                
                for (File arquivo : arquivos)
                {
                    if (arquivoJaEstaAberto(arquivo))
                    {
                        AbaCodigoFonte aba = obterAbaArquivo(arquivo);
                        aba.selecionar();
                    }
                    else
                    {
                        try
                        {
                            final String conteudo = FileHandle.open(arquivo);
                            final AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
                            
                            abaCodigoFonte.setCodigoFonte(conteudo, arquivo, true);
                            
                            getPainelTabulado().add(abaCodigoFonte);
                        }
                        catch (Exception excecao)
                        {
                            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecao);
                        }
                    }
                }
                
                TelaPrincipal.this.setEnabled(true);
            });
        }
    }
    public void setArquivosIniciais(List<File> arquivos)
    {
        this.arquivosIniciais = arquivos;
    }
    
    

    private boolean arquivoJaEstaAberto(File arquivo)
    {
        AbaCodigoFonte aba = obterAbaArquivo(arquivo);

        return aba != null;
    }

    public AbaCodigoFonte obterAbaArquivo(File arquivo)
    {
        for (Aba aba : getPainelTabulado().getAbas(AbaCodigoFonte.class))
        {
            AbaCodigoFonte abaCodigoFonte = (AbaCodigoFonte) aba;
            PortugolDocumento documento = abaCodigoFonte.getPortugolDocumento();

            if (documento.getFile() != null)
            {
                try
                {
                    Path caminhoArquivoAba = documento.getFile().toPath();
                    Path caminhoArquivoAbrir = arquivo.toPath();

                    if (Files.isSameFile(caminhoArquivoAba, caminhoArquivoAbrir))
                    {
                        return abaCodigoFonte;
                    }
                }
                catch (IOException excecao)
                {
                    LOGGER.log(Level.SEVERE, String.format("Erro ao verificar se o arquivo '%s' já estava aberto em alguma aba", arquivo.getAbsolutePath()), excecao);
                }
            }
        }

        return null;
    }

    private void fecharAplicativo()
    {
        painelTabuladoPrincipal.fecharTodasAbas(AbaCodigoFonte.class);

        if (!painelTabuladoPrincipal.temAbaAberta(AbaCodigoFonte.class))
        {
            PortugolStudio.getInstancia().finalizar(0);
        }
    }

    public PainelTabuladoPrincipal getPainelTabulado()
    {
        return painelTabuladoPrincipal;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painelTabuladoPrincipal = new br.univali.ps.ui.paineis.PainelTabuladoPrincipal();

        setLayout(new java.awt.BorderLayout());

        painelTabuladoPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(painelTabuladoPrincipal, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.univali.ps.ui.paineis.PainelTabuladoPrincipal painelTabuladoPrincipal;
    // End of variables declaration//GEN-END:variables
}

package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.atualizador.GerenciadorAtualizacoes;
import br.univali.ps.atualizador.ObservadorAtualizacao;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.ui.abas.AbaInicial;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.ui.ColorController;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.paineis.PainelTabuladoPrincipal;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.utils.FileHandle;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.weblaf.WeblafUtils;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.*;
import org.apache.commons.io.FileUtils;

public final class TelaPrincipal extends JFrame
{

    private static final Logger LOGGER = Logger.getLogger(TelaPrincipal.class.getName());

    private boolean abrindo = true;
    private List<File> arquivosIniciais;

    public static void main(final String argumentos[])
    {
        inicializarMecanismoLog(); //o log é a primeira coisa a ser iniciada, assim você consegue logar os detalhes de inicialização
        
        LOGGER.log(Level.INFO, "Iniciando main...");
        
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Portugol Studio");

        try
        {
            SwingUtilities.invokeAndWait(() ->
            {
                Thread.currentThread().setName("Portugol-Studio (Swing)");
            });

        }
        catch (InterruptedException | InvocationTargetException ex)
        {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        LOGGER.log(Level.INFO, "Iniciando PS com {0} argumentos", argumentos.length);
        PortugolStudio.getInstancia().iniciar(argumentos);
    }

    private static void inicializarMecanismoLog()
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

    
    public TelaPrincipal()
    {
        initComponents();
        configurarJanela();
        criaAbas();
        instalarObservadores();
        configurarCores();
        configurarBotoes();
    }
    
    private void configurarBotoes(){
        if(WeblafUtils.weblafEstaInstalado()){
            WeblafUtils.configurarBotao(closeButton1,ColorController.FUNDO_CLARO,ColorController.COR_PRINCIPAL, ColorController.PROGRESS_BAR, ColorController.COR_LETRA, 5);
            WeblafUtils.configurarBotao(iconifyButton,ColorController.FUNDO_CLARO,ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, 5);
        }
    }
    
    private void configurarCores(){
//        mainPanel.setBackground(ColorController.COR_DESTAQUE);
        getContentPane().setBackground(ColorController.FUNDO_CLARO);
        painelTabuladoPrincipal.setBackground(ColorController.COR_PRINCIPAL);
    }

    public void setArquivosIniciais(List<File> arquivos)
    {
        this.arquivosIniciais = arquivos;
    }

    private void criaAbas()
    {
        painelTabuladoPrincipal.setAbaInicial(new AbaInicial(this));
    }

    private void configurarJanela()
    {
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setIconImage(IconFactory.getDefaultWindowIcon());
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
            focarJanela();

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

    public void focarJanela()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                if (janelaMinimizada())
                {
                    restaurarJanela();
                }

                TelaPrincipal.this.toFront();
                TelaPrincipal.this.requestFocusInWindow();
            }
        });
    }

    private boolean janelaMinimizada()
    {
        return (getExtendedState() & JFrame.ICONIFIED) == JFrame.ICONIFIED;
    }

    private void restaurarJanela()
    {
        setExtendedState(getExtendedState() & (~JFrame.ICONIFIED));
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        webButton1 = new com.alee.laf.button.WebButton();
        maximizeButton = new com.alee.laf.button.WebButton();
        jPanel1 = new javax.swing.JPanel();
        iconifyButton = new com.alee.laf.button.WebButton();
        closeButton1 = new com.alee.laf.button.WebButton();
        painelTabuladoPrincipal = new br.univali.ps.ui.paineis.PainelTabuladoPrincipal();

        webButton1.setText("webButton1");

        maximizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/white_min.png"))); // NOI18N
        maximizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maximizeButtonActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Portugol Studio");
        setBackground(new java.awt.Color(0, 0, 0));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(700, 520));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 0));
        jPanel1.setMaximumSize(new java.awt.Dimension(200, 60));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        iconifyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/white_min.png"))); // NOI18N
        iconifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iconifyButtonActionPerformed(evt);
            }
        });
        jPanel1.add(iconifyButton);

        closeButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/white_close.png"))); // NOI18N
        closeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(closeButton1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        getContentPane().add(jPanel1, gridBagConstraints);

        painelTabuladoPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(painelTabuladoPrincipal, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iconifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iconifyButtonActionPerformed
        PortugolStudio.getInstancia().getTelaPrincipal().setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_iconifyButtonActionPerformed

    private void closeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButton1ActionPerformed
        fecharAplicativo();
    }//GEN-LAST:event_closeButton1ActionPerformed

    private void maximizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maximizeButtonActionPerformed
        int state = PortugolStudio.getInstancia().getTelaPrincipal().getExtendedState();
        if(state==JFrame.MAXIMIZED_BOTH){
            PortugolStudio.getInstancia().getTelaPrincipal().setExtendedState(JFrame.NORMAL);
        }else{
            PortugolStudio.getInstancia().getTelaPrincipal().setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }//GEN-LAST:event_maximizeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton closeButton1;
    private com.alee.laf.button.WebButton iconifyButton;
    private javax.swing.JPanel jPanel1;
    private com.alee.laf.button.WebButton maximizeButton;
    private br.univali.ps.ui.paineis.PainelTabuladoPrincipal painelTabuladoPrincipal;
    private com.alee.laf.button.WebButton webButton1;
    // End of variables declaration//GEN-END:variables
}

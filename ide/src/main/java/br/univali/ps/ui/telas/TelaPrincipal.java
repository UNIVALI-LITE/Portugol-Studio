/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas;

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
import br.univali.ps.ui.utils.FileHandle;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.json.JSONObject;

/**
 *
 * @author lite
 */
public class TelaPrincipal extends javax.swing.JPanel
{
    private boolean abrindo = true;
    private List<File> arquivosIniciais;
    int pX, pY;
    
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
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                // Get x,y and store them
                pX = me.getX();
                pY = me.getY();

            }

             public void mouseDragged(MouseEvent me) {

                Lancador.getJFrame().setLocation(Lancador.getJFrame().getLocation().x + me.getX() - pX,Lancador.getJFrame().getLocation().y + me.getY() - pY);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {

                Lancador.getJFrame().setLocation(Lancador.getJFrame().getLocation().x + me.getX() - pX,Lancador.getJFrame().getLocation().y + me.getY() - pY);
            }
        });
    }
    
    private void criaAbas()
    {
        painelTabuladoPrincipal.setAbaInicial(new AbaInicial(this));
    }
    
    private void configurarCores(){
//        mainPanel.setBackground(ColorController.COR_DESTAQUE);
        setBackground(ColorController.FUNDO_ESCURO);
        painelTabuladoPrincipal.setBackground(ColorController.COR_PRINCIPAL);
    }
    
    
    private void instalarObservadores()
    {
        instalarObservadorJanela();
        
    }

    private void instalarObservadorJanela()
    {
        Lancador.getJFrame().addWindowListener(new WindowAdapter()
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

        Lancador.getJFrame().addComponentListener(new ComponentAdapter()
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

    private void dispararProcessosAbertura()
    {
        abrirArquivosCodigoFonte(arquivosIniciais);

//        exibirErrosPluginsBibliotecas();
        Thread thread = new Thread(){
            public void run(){
                JSONObject ultimaVersao = procurarAtualizacoes();
                if(ultimaVersao!=null)
                {
                    String versao = ultimaVersao.getString("tag_name");
                    String texto = ultimaVersao.getString("body");
                    if(versao.equals("v"+PortugolStudio.getInstancia().getVersao()))
                    {
                        exibirTelaAtualizacoes(texto);
                    }
                }
            }
        };

        thread.start();
        

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
    
    private JSONObject procurarAtualizacoes()
    {
        String requestURL = Configuracoes.getInstancia().getUriAtualizacao();
        try {
            InputStream is = new URL(requestURL).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = org.apache.commons.io.IOUtils.toString(rd);
            JSONObject json = new JSONObject(jsonText);
            is.close();
            return json;
        } catch (MalformedURLException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    private void exibirTelaAtualizacoes(String body)
    {
        SwingUtilities.invokeLater(() -> {
            TelaCustomBorder main = new TelaCustomBorder("Atualização Encontrada");
            TelaAtualizacoes ta = new TelaAtualizacoes(body);
            main.setMinimumSize(new Dimension(250, 400));
            main.setPanel(ta, false);
            main.setLocationRelativeTo(null);
            main.setVisible(true);
            main.pack();
        });
        
    }

    public void criarNovoCodigoFonte()
    {
        final AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
        painelTabuladoPrincipal.add(abaCodigoFonte);
        abaCodigoFonte.carregarAlgoritmoPadrao();
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
                            
                            
                            if(PortugolStudio.getInstancia().isArquivoRecuperado(arquivo))
                            {
                                abaCodigoFonte.setCodigoFonte(conteudo, null, true);
                            }
                            else{
                                PortugolStudio.getInstancia().salvarComoRecente(arquivo);
                                abaCodigoFonte.setCodigoFonte(conteudo, arquivo, true);
                            }                            
                            
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
            if (documento.getFile() != null && arquivo.exists())
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

    public boolean fecharAplicativo()
    {
        painelTabuladoPrincipal.fecharTodasAbas(AbaCodigoFonte.class);
        if (!painelTabuladoPrincipal.temAbaAberta(AbaCodigoFonte.class))
        {
            
            PortugolStudio.getInstancia().finalizar(0);
            return true;
        }
        
        return false;
    }
    public boolean fecharAplicativoParaReiniciar()
    {
        painelTabuladoPrincipal.fecharTodasAbas(AbaCodigoFonte.class);
        if (!painelTabuladoPrincipal.temAbaAberta(AbaCodigoFonte.class))
        {
            return true;
        }
        
        return false;
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
    private void initComponents() {

        painelTabuladoPrincipal = new br.univali.ps.ui.paineis.PainelTabuladoPrincipal();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setLayout(new java.awt.BorderLayout());
        add(painelTabuladoPrincipal, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.univali.ps.ui.paineis.PainelTabuladoPrincipal painelTabuladoPrincipal;
    // End of variables declaration//GEN-END:variables
}

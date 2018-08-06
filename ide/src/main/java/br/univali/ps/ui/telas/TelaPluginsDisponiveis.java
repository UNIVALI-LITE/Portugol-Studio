/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.Caminhos;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.ui.Lancador;
import br.univali.ps.ui.paineis.PainelPluginItem;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.PSOutTabbedPaneUI;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.swing.weblaf.jOptionPane.QuestionDialog;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.markdown4j.Markdown4jProcessor;

/**
 *
 * @author Adson Esteves
 */
public class TelaPluginsDisponiveis extends javax.swing.JPanel implements Themeable{

    /**
     * Creates new form TelaPluginsDisponiveis
     */
    
    String URIList[] = {"https://api.github.com/repos/UNIVALI-LITE/Plugin-Portugol-GoGoBoard/releases/latest"};
    private List<PainelPluginItem> listaPlugins = new ArrayList<>();
    private static JDialog indicadorProgresso;
    private String tuto = "";
    
    public TelaPluginsDisponiveis() {
        initComponents();
        configurarCores();
        configurarBotoes();
        configuraLoader();
        carregarPluginsDisponiveis();
    }
    
    private void configuraLoader() {
        boolean usandoTemaDark = Configuracoes.getInstancia().isTemaDark();
        String caminhoIcone = String.format("/br/univali/ps/ui/icones/%s/grande/load.gif", usandoTemaDark ? "Dark" : "Portugol");
        Icon icone = new ImageIcon(getClass().getResource(caminhoIcone));
        indicadorProgresso = new JDialog();
        indicadorProgresso.setUndecorated(true);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(ColorController.FUNDO_CLARO);
        painel.add(new JLabel(icone, JLabel.CENTER));
        JLabel instalando = new JLabel("Baixando...", JLabel.CENTER);
        instalando.setForeground(ColorController.COR_LETRA);
        instalando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painel.add(instalando);

        indicadorProgresso.add(painel);        
        indicadorProgresso.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        indicadorProgresso.pack();
        indicadorProgresso.setLocationRelativeTo(Lancador.getJFrame());
    }
    
    private void carregarPluginsDisponiveis()
    {
        for (String string : URIList) {
            JSONObject pluginReleased = procurarPlugins(string);
            if(pluginReleased!=null)
            {
                JSONArray arquivoPlugin = pluginReleased.getJSONArray("assets");
                
                String nome = pluginReleased.getString("name");
                String downloadURL = arquivoPlugin.getJSONObject(0).getString("browser_download_url");
                String descricao = pluginReleased.getString("body");
                
                PainelPluginItem item = new PainelPluginItem();
                item.setLinkDownload(downloadURL);
                item.getLabelPluginInstalado().setText(nome);
                if(descricao.contains("Tutorial"))
                {
                    item.setDescricao(descricao);                    
                }                
                listaPlugins.add(item);
                painelPluginsDisponiveis.add(item);
            }
        }
    }
    
    private JSONObject procurarPlugins(String requestURL)
    {
        try {
            InputStream is = new URL(requestURL).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = org.apache.commons.io.IOUtils.toString(rd);
            JSONObject json = new JSONObject(jsonText);
            is.close();
            return json;
        } catch (MalformedURLException ex) {
            
        } catch (IOException ex) {
            
        }
        
        return null;
    }
    
    private void configurarBotoes() {
        botaoSelecionarTodos.setAction(new AbstractAction("selecionar todos") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (PainelPluginItem component : listaPlugins) {
                    component.getSeletorPlugin().setSelected(true);
                }
            }
        });
        botaoInstalarPlugins.setAction(new AbstractAction("instalar plugins") {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<File> listaArquivos = new ArrayList<>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (PainelPluginItem component : listaPlugins) {
                            if(component.getSeletorPlugin().isSelected())
                            {                                
                                listaArquivos.add(baixarPlugin(component.getLinkDownload()));
                            }
                        }
                        indicadorProgresso.setVisible(false);
                        GerenciadorPlugins.getInstance().instalarPlugins(listaArquivos);
                        SwingUtilities.invokeLater(() -> {
                            try {
                                showTutoTab();
                            } catch (IOException ex) {
                                Logger.getLogger(TelaPluginsDisponiveis.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if (confirmouReinicializacao()) {
                                Configuracoes.getInstancia().restartApplication();
                            }
                        });                        
                    }
                }).start();
                indicadorProgresso.setVisible(true);
            }
        });
    }
    
    private void showTutoTab() throws IOException{
        JTabbedPane pspt = new JTabbedPane();
        
        pspt.setUI(new PSOutTabbedPaneUI());
        pspt.setForeground(ColorController.COR_LETRA);
        
        for (PainelPluginItem Plugin : listaPlugins) {
            if(Plugin.getDescricao() != null)
            {
                JPanel painel = new JPanel(new BorderLayout());
                JEditorPane editorPane = new JEditorPane();
                editorStyleSet(editorPane);
                editorPane.setBackground(ColorController.FUNDO_CLARO);
                editorPane.setForeground(ColorController.COR_LETRA);
                
                String html = new Markdown4jProcessor().process(Plugin.getDescricao());
                editorPane.setText("<html><body><div>"+html.replaceAll("</p>", "</p><br>")+"</div></body></html>");
                editorPane.setCaretPosition(0);                
                painel.add(editorPane);
                
                pspt.add(Plugin.getLabelPluginInstalado().getText(), painel);
            }
        }
        if(pspt.getTabCount() > 0)
        {
            TelaCustomBorder tcb = new TelaCustomBorder("Tutorial de Instalação de Dependências");
            tcb.setPanel(new JPanel());
            tcb.getPanel().add(pspt);
            tcb.getPanel().setBackground(ColorController.FUNDO_MEDIO);
            tcb.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            tcb.setLocationRelativeTo(Lancador.getJFrame());
            tcb.pack();
            tcb.setVisible(true);
        }
    }
    
    private void editorStyleSet(JEditorPane jEditorPane1)
    {
        jEditorPane1.setEditable(false);
        
        HTMLEditorKit editorKit = new HTMLEditorKit();
        jEditorPane1.setEditorKit(editorKit);
        
        File imageBase = Configuracoes.getInstancia().getDiretorioAjuda();
        
        if (Caminhos.rodandoEmDesenvolvimento())
        {        
            imageBase = new File(".");            
        }
        
        String imgPath = "";
        
        try
        {
            imgPath = imageBase.getCanonicalPath();            
        }
        catch (Exception e)
        {

        }
        
        imgPath = imgPath + "/src/main/assets/ajuda";
        imgPath = imgPath.replaceAll("\\\\", "/");
    
        String rule = String.format("ul {list-style-image: url('file:///%s'); }", imgPath + "/recursos/imagens/light_pix.png" );
        String rgb = ColorController.COR_LETRA.getRed() + "," + ColorController.COR_LETRA.getGreen() + "," + ColorController.COR_LETRA.getBlue();
                
        StyleSheet ss = editorKit.getStyleSheet();
        ss.addRule("body {color: rgb(" + rgb  + ");}");
        ss.addRule("div{ font-family:Arial; font-size: 10px; }");
        ss.addRule(rule);
        ss.addRule("a, a:HOVER, a:VISITED, a:ACTIVE { color: rgb(" + rgb  + "); text-decoration:none; cursor: default;}");        
        
        jEditorPane1.setDocument(editorKit.createDefaultDocument());
    }
    
    
    private boolean confirmouReinicializacao() {
        int resp = QuestionDialog.getInstance().showConfirmMessage("Para finalizar a instalação do plugin, o Portugol Studio precisa reinicializar! Confirma?");
        if (resp == JOptionPane.YES_OPTION) {
            return true;
        } else if (resp == JOptionPane.CANCEL_OPTION || resp == JOptionPane.CLOSED_OPTION) {
            return false;
        } else {
            return false;
        }
    }
    
    private File baixarPlugin(String link)
    {
        File pluginFile = new File(Configuracoes.getInstancia().getDiretorioTemporario(), link.substring(link.lastIndexOf("/")+1, link.length()));
        try{            
            URL website = new URL(link);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(pluginFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
        return pluginFile;
    }

    @Override
    public void configurarCores() {
        painelSelecionadorPlugins.setBackground(ColorController.FUNDO_MEDIO);
        painelPluginsDisponiveis.setBackground(ColorController.FUNDO_CLARO);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configuraWebLaf(scrollPlugins);
            WeblafUtils.configurarBotao(botaoInstalarPlugins, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 2, true);            
            WeblafUtils.configurarBotao(botaoSelecionarTodos, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 2, true);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPlugins = new javax.swing.JScrollPane();
        painelPluginsDisponiveis = new javax.swing.JPanel();
        painelSelecionadorPlugins = new javax.swing.JPanel();
        botaoSelecionarTodos = new com.alee.laf.button.WebButton();
        botaoInstalarPlugins = new com.alee.laf.button.WebButton();

        setLayout(new java.awt.BorderLayout());

        painelPluginsDisponiveis.setLayout(new javax.swing.BoxLayout(painelPluginsDisponiveis, javax.swing.BoxLayout.Y_AXIS));
        scrollPlugins.setViewportView(painelPluginsDisponiveis);

        add(scrollPlugins, java.awt.BorderLayout.CENTER);

        painelSelecionadorPlugins.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        botaoSelecionarTodos.setText("selecionar todos");
        painelSelecionadorPlugins.add(botaoSelecionarTodos);

        botaoInstalarPlugins.setText("instalar plugins");
        painelSelecionadorPlugins.add(botaoInstalarPlugins);

        add(painelSelecionadorPlugins, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoInstalarPlugins;
    private com.alee.laf.button.WebButton botaoSelecionarTodos;
    private javax.swing.JPanel painelPluginsDisponiveis;
    private javax.swing.JPanel painelSelecionadorPlugins;
    private javax.swing.JScrollPane scrollPlugins;
    // End of variables declaration//GEN-END:variables
}

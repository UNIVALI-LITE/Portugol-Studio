/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.Caminhos;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.WebConnectionUtils;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.markdown4j.Markdown4jProcessor;

/**
 *
 * @author Adson Esteves
 */
public class TelaAtualizacoes extends javax.swing.JPanel implements Themeable{

    /**
     * Creates new form TelaAtualizacoes
     */
    public TelaAtualizacoes(String body) {
        initComponents();
        configurarCores();
        
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
        
        
        botaoBaixar.setAction(new AbstractAction("Baixar Atualização")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                WebConnectionUtils.abrirSite("http://lite.acad.univali.br/portugol/");
            }
        });
        
        try {
            setAtualizacoes(body);
        } catch (IOException ex) {
            Logger.getLogger(TelaAtualizacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setAtualizacoes(String atualizacoes) throws IOException
    {
        String html = new Markdown4jProcessor().process(atualizacoes);
        jEditorPane1.setText("<html><body><div>"+html.replaceAll("</p>", "</p><br>")+"</div></body></html>");
        System.out.println(html);
    }

    @Override
    public void configurarCores() {
        setBackground(ColorController.FUNDO_MEDIO);
        painelTitulo.setBackground(ColorController.FUNDO_MEDIO);
        rotulo.setBackground(ColorController.FUNDO_MEDIO);
        rotulo.setForeground(ColorController.COR_LETRA);
        jEditorPane1.setBackground(ColorController.FUNDO_CLARO);
        jEditorPane1.setForeground(ColorController.COR_LETRA);
        painelBotoes.setBackground(ColorController.FUNDO_ESCURO);
        if(WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configurarBotao(botaoBaixar, ColorController.AMARELO, ColorController.FUNDO_ESCURO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 5, true);            
            WeblafUtils.configuraWebLaf(jScrollPane1);
        }
    }  
        
    public static void main(String argumentos[]) 
    {
//        WeblafUtils.instalaWeblaf();
//        TelaCustomBorder main = new TelaCustomBorder("Erro Encontrado");
//        TelaAtualizacoes ta = new TelaAtualizacoes();
//        main.setPanel(ta);
//        try {
//            ta.setAtualizacoes("- Erros de interface encontrados agora apresentam tela para reporte de erro  - [Issue #277](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/277)\r\n    \r\n- Erros de compilação tratados:\r\n    - Unreachable Statement - [Issue #271](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/271)\r\n\r\n    - Variable might not be intialized. [Issue #299](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/299)\r\n\r\n    - Cannot find symbol inicio() - [Issue #283](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/283)\r\n\r\n    - Comando escolha-caso não funcionava com variáveis - [Issue #295](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/294)\r\n\r\n    - Pontos de parada dentro de escolhas caso [Issue #294](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/294)\r\n");
//        } catch (IOException ex) {
//            Logger.getLogger(TelaAtualizacoes.class.getName()).log(Level.SEVERE, null, ex);
//        }        
//        main.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelTitulo = new javax.swing.JPanel();
        rotulo = new javax.swing.JLabel();
        painelBotoes = new javax.swing.JPanel();
        botaoBaixar = new com.alee.laf.button.WebButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setLayout(new java.awt.BorderLayout());

        painelTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelTitulo.setLayout(new java.awt.BorderLayout());

        rotulo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        rotulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotulo.setText("<html><body><div style=\"text-align:center\">\nHá uma nova versão do Portugol Studio disponível para você baixar!\n</div></body></html>");
        rotulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotulo.setMinimumSize(new java.awt.Dimension(10, 14));
        rotulo.setPreferredSize(new java.awt.Dimension(250, 45));
        painelTitulo.add(rotulo, java.awt.BorderLayout.CENTER);

        add(painelTitulo, java.awt.BorderLayout.NORTH);

        painelBotoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));
        painelBotoes.setLayout(new java.awt.GridBagLayout());

        botaoBaixar.setText("Baixar Atualização");
        botaoBaixar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        painelBotoes.add(botaoBaixar, new java.awt.GridBagConstraints());

        add(painelBotoes, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setViewportView(jEditorPane1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoBaixar;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JLabel rotulo;
    // End of variables declaration//GEN-END:variables
}

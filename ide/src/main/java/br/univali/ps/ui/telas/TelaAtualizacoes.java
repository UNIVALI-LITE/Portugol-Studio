/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.Caminhos;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
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
    public TelaAtualizacoes(String body, String versao) {
        initComponents();
        mostrarVersoes(versao);
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
        jEditorPane1.setCaretPosition(0);
    }
    
    public void mostrarVersoes(String versao){
        rotuloVAtual.setText("Versão atual: "+PortugolStudio.getInstancia().getVersao());
        rotuloNovaV.setText("Nova Versão: "+ versao.substring(1));
    }

    @Override
    public void configurarCores() {
        setBackground(ColorController.FUNDO_MEDIO);
        if(Configuracoes.getInstancia().isTemaDark()){
            rotulo.setForeground(ColorController.AMARELO);
        }else{
            rotulo.setForeground(ColorController.FUNDO_ESCURO);
        }
        
        rotuloVAtual.setForeground(ColorController.COR_LETRA);
        rotuloNovaV.setForeground(ColorController.COR_LETRA);
        painelAba.setBackground(ColorController.FUNDO_CLARO);        
        labelAba.setForeground(ColorController.COR_LETRA);
        labelAba.setBackground(ColorController.FUNDO_CLARO);
        jEditorPane1.setBackground(ColorController.FUNDO_CLARO);
        jEditorPane1.setForeground(ColorController.COR_LETRA);
        
        if(WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configurarBotao(botaoBaixar, ColorController.AMARELO, ColorController.FUNDO_ESCURO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);            
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

        painelSuperior = new javax.swing.JPanel();
        painelIcone = new javax.swing.JPanel();
        iconeAT = new javax.swing.JLabel();
        painelTitulo = new javax.swing.JPanel();
        rotulo = new javax.swing.JLabel();
        painelVersoes = new javax.swing.JPanel();
        rotuloVAtual = new javax.swing.JLabel();
        rotuloNovaV = new javax.swing.JLabel();
        painelAba = new javax.swing.JPanel();
        labelAba = new javax.swing.JLabel();
        finalAba = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        painelBotoes = new javax.swing.JPanel();
        botaoBaixar = new com.alee.laf.button.WebButton();

        setMinimumSize(new java.awt.Dimension(106, 600));
        setPreferredSize(new java.awt.Dimension(400, 200));
        setLayout(new java.awt.BorderLayout());

        painelSuperior.setOpaque(false);
        painelSuperior.setPreferredSize(new java.awt.Dimension(400, 110));
        painelSuperior.setLayout(new java.awt.BorderLayout());

        painelIcone.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 20, 10, 10));
        painelIcone.setOpaque(false);
        painelIcone.setLayout(new java.awt.BorderLayout());

        iconeAT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/atualizacao.png"))); // NOI18N
        painelIcone.add(iconeAT, java.awt.BorderLayout.CENTER);

        painelSuperior.add(painelIcone, java.awt.BorderLayout.WEST);

        painelTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 15, 1, 1));
        painelTitulo.setOpaque(false);
        painelTitulo.setLayout(new java.awt.BorderLayout());

        rotulo.setFont(new java.awt.Font("Arial", 0, 26)); // NOI18N
        rotulo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rotulo.setText("<html><body><div class=\"titulo\" style=\"text-align:center\">Atualização Encontrada</div></body></html>");
        rotulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotulo.setMinimumSize(new java.awt.Dimension(10, 14));
        rotulo.setPreferredSize(new java.awt.Dimension(250, 45));
        painelTitulo.add(rotulo, java.awt.BorderLayout.NORTH);

        painelVersoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));
        painelVersoes.setOpaque(false);
        painelVersoes.setLayout(new java.awt.BorderLayout());

        rotuloVAtual.setText("Versão atual:");
        painelVersoes.add(rotuloVAtual, java.awt.BorderLayout.LINE_START);

        rotuloNovaV.setText("Nova versão:");
        painelVersoes.add(rotuloNovaV, java.awt.BorderLayout.PAGE_END);

        painelTitulo.add(painelVersoes, java.awt.BorderLayout.SOUTH);

        painelSuperior.add(painelTitulo, java.awt.BorderLayout.CENTER);

        painelAba.setOpaque(false);
        painelAba.setLayout(new java.awt.BorderLayout());

        labelAba.setText("Atualizações");
        labelAba.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4));
        labelAba.setOpaque(true);
        painelAba.add(labelAba, java.awt.BorderLayout.WEST);

        finalAba.setOpaque(false);

        javax.swing.GroupLayout finalAbaLayout = new javax.swing.GroupLayout(finalAba);
        finalAba.setLayout(finalAbaLayout);
        finalAbaLayout.setHorizontalGroup(
            finalAbaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        finalAbaLayout.setVerticalGroup(
            finalAbaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        painelAba.add(finalAba, java.awt.BorderLayout.CENTER);

        painelSuperior.add(painelAba, java.awt.BorderLayout.PAGE_END);

        add(painelSuperior, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(23, 230));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(108, 200));

        jEditorPane1.setPreferredSize(new java.awt.Dimension(106, 200));
        jScrollPane1.setViewportView(jEditorPane1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        painelBotoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 8));
        painelBotoes.setOpaque(false);
        painelBotoes.setLayout(new java.awt.BorderLayout());

        botaoBaixar.setText("Baixar Atualização");
        botaoBaixar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        painelBotoes.add(botaoBaixar, java.awt.BorderLayout.LINE_END);

        add(painelBotoes, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoBaixar;
    private javax.swing.JPanel finalAba;
    private javax.swing.JLabel iconeAT;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAba;
    private javax.swing.JPanel painelAba;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelIcone;
    private javax.swing.JPanel painelSuperior;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JPanel painelVersoes;
    private javax.swing.JLabel rotulo;
    private javax.swing.JLabel rotuloNovaV;
    private javax.swing.JLabel rotuloVAtual;
    // End of variables declaration//GEN-END:variables
}

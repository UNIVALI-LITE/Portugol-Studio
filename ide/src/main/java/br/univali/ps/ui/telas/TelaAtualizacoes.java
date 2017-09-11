/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.WebConnectionUtils;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
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
    public TelaAtualizacoes() {
        initComponents();
        configurarCores();
        
        areaTexto.setEditable(false);
        
        HTMLEditorKit editorKit = new HTMLEditorKit();
        areaTexto.setEditorKit(editorKit);
        
        StyleSheet ss = editorKit.getStyleSheet();
        ss.addRule("div{ font-family:Arial; font-size: 10px; }");
        areaTexto.setDocument(editorKit.createDefaultDocument());
        
        botaoBaixar.setAction(new AbstractAction("Baixar Atualização")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                WebConnectionUtils.abrirSite("http://lite.acad.univali.br/portugol/");
            }
        });
        
        try {
            setAtualizacoes("- Erros de interface encontrados agora apresentam tela para reporte de erro  - [Issue #277](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/277)\r\n    \r\n- Erros de compilação tratados:\r\n    - Unreachable Statement - [Issue #271](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/271)\r\n\r\n    - Variable might not be intialized. [Issue #299](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/299)\r\n\r\n    - Cannot find symbol inicio() - [Issue #283](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/283)\r\n\r\n    - Comando escolha-caso não funcionava com variáveis - [Issue #295](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/294)\r\n\r\n    - Pontos de parada dentro de escolhas caso [Issue #294](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/294)\r\n");
        } catch (IOException ex) {
            Logger.getLogger(TelaAtualizacoes.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void setAtualizacoes(String atualizacoes) throws IOException
    {
        String html = new Markdown4jProcessor().process(atualizacoes);
        areaTexto.setText("<html><body><div>"+html+"</div></body></html>");
    }

    @Override
    public void configurarCores() {
        setBackground(ColorController.FUNDO_MEDIO);
        painelTitulo.setBackground(ColorController.FUNDO_MEDIO);
        rotulo.setBackground(ColorController.FUNDO_MEDIO);
        rotulo.setForeground(ColorController.COR_LETRA);
        areaTexto.setBackground(ColorController.FUNDO_CLARO);
        areaTexto.setForeground(ColorController.COR_LETRA);
        painelBotoes.setBackground(ColorController.FUNDO_ESCURO);
        if(WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configurarBotao(botaoBaixar, ColorController.AMARELO, ColorController.FUNDO_ESCURO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 5, true);            
            WeblafUtils.configuraWebLaf(painelRolagem);
        }
    }  
        
    public static void main(String argumentos[]) 
    {
        WeblafUtils.instalaWeblaf();
        TelaCustomBorder main = new TelaCustomBorder("Erro Encontrado");
        TelaAtualizacoes ta = new TelaAtualizacoes();
        main.setPanel(ta);
        try {
            ta.setAtualizacoes("- Erros de interface encontrados agora apresentam tela para reporte de erro  - [Issue #277](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/277)\r\n    \r\n- Erros de compilação tratados:\r\n    - Unreachable Statement - [Issue #271](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/271)\r\n\r\n    - Variable might not be intialized. [Issue #299](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/299)\r\n\r\n    - Cannot find symbol inicio() - [Issue #283](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/283)\r\n\r\n    - Comando escolha-caso não funcionava com variáveis - [Issue #295](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/294)\r\n\r\n    - Pontos de parada dentro de escolhas caso [Issue #294](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/294)\r\n");
        } catch (IOException ex) {
            Logger.getLogger(TelaAtualizacoes.class.getName()).log(Level.SEVERE, null, ex);
        }        
        main.setVisible(true);
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
        painelRolagem = new javax.swing.JScrollPane();
        areaTexto = new javax.swing.JEditorPane();
        painelBotoes = new javax.swing.JPanel();
        botaoBaixar = new com.alee.laf.button.WebButton();

        setLayout(new java.awt.BorderLayout());

        painelTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        rotulo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        rotulo.setText("<html><body><div style=\"text-align:center\">\nHá uma nova versão do Portugol Studio disponível para você baixar!\n</div></body></html>");
        rotulo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        rotulo.setMinimumSize(new java.awt.Dimension(10, 14));
        rotulo.setPreferredSize(new java.awt.Dimension(250, 45));
        rotulo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout painelTituloLayout = new javax.swing.GroupLayout(painelTitulo);
        painelTitulo.setLayout(painelTituloLayout);
        painelTituloLayout.setHorizontalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(rotulo, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        painelTituloLayout.setVerticalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(rotulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(painelTitulo, java.awt.BorderLayout.NORTH);

        areaTexto.setEditable(false);
        areaTexto.setContentType("text/html"); // NOI18N
        painelRolagem.setViewportView(areaTexto);

        add(painelRolagem, java.awt.BorderLayout.CENTER);

        painelBotoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));

        botaoBaixar.setText("Baixar Atualização");

        javax.swing.GroupLayout painelBotoesLayout = new javax.swing.GroupLayout(painelBotoes);
        painelBotoes.setLayout(painelBotoesLayout);
        painelBotoesLayout.setHorizontalGroup(
            painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBotoesLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(botaoBaixar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        painelBotoesLayout.setVerticalGroup(
            painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(botaoBaixar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        add(painelBotoes, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane areaTexto;
    private com.alee.laf.button.WebButton botaoBaixar;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JScrollPane painelRolagem;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JLabel rotulo;
    // End of variables declaration//GEN-END:variables
}

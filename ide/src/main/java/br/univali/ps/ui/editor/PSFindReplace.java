/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.editor;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.swing.weblaf.jOptionPane.QuestionDialog;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.fife.rsta.ui.search.SearchEvent;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;

/**
 *
 * @author Adson Esteves
 */
public class PSFindReplace extends javax.swing.JPanel implements Themeable{

    /**
     * Creates new form PSFindReplace
     */
    
    private SearchContext searchContext;
    private RSyntaxTextArea rSyntaxTextArea;
    
    private enum tipo {
        BUSCAR, SUBSTITUIR, SUBSTITUIR_TODAS
    }
    
    public PSFindReplace(RSyntaxTextArea textArea) {
        initComponents();        
        rSyntaxTextArea = textArea;
        searchContext = new SearchContext();
        avancarRadioButton.setSelected(true);
        marcarCheckBox.setSelected(true);
        configurarAcoes();
        configurarCores();
    }
    
    private void configurarAcoes()
    {
        procurarTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procurarButton.doClick(0);
            }
        });
        
        procurarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarPesquisa(tipo.BUSCAR);
            }
        });
        
        substituirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarPesquisa(tipo.SUBSTITUIR);
            }
        });
        
        substituirTudoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarPesquisa(tipo.SUBSTITUIR_TODAS);
            }
        });
        
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        avancarRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarRadioButton.setSelected(false);
            }
        });
        
        voltarRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avancarRadioButton.setSelected(false);
            }
        });
    }

    private void realizarPesquisa(tipo tipo)
    {
        String searchText = procurarTextField.getText();
        if(searchText.length()==0)
        {
            return;
        }
        
        searchContext.setSearchFor(searchText);
        searchContext.setMatchCase(diferenciarCheckBox.isSelected());
        searchContext.setRegularExpression(expressaoCheckBox.isSelected());
        searchContext.setSearchForward(avancarRadioButton.isSelected());
        searchContext.setWholeWord(palavraInteiraCheckBox.isSelected());
        searchContext.setMarkAll(marcarCheckBox.isSelected());
        searchContext.setReplaceWith(substituirTextField.getText());        
        SearchResult result;
        
        switch(tipo)
        {
            case BUSCAR:
                result = SearchEngine.find(rSyntaxTextArea, searchContext);
                if (!result.wasFound())
                {
                    reiniciar(searchContext, rSyntaxTextArea, tipo);
                }
                break;
            case SUBSTITUIR:
                result = SearchEngine.replace(rSyntaxTextArea, searchContext);
                if (!result.wasFound())
                {
                    reiniciar(searchContext, rSyntaxTextArea, tipo);
                }
                break;
            case SUBSTITUIR_TODAS:
                result = SearchEngine.replaceAll(rSyntaxTextArea, searchContext);
                QuestionDialog.getInstance().showMessage(result.getCount()+ " ocorrências substituídas.");
                break;
        }        
    }
    
    private void reiniciar(SearchContext context, RSyntaxTextArea textArea, tipo tipo)
    {
        UIManager.getLookAndFeel().provideErrorFeedback(textArea);

        String s = "A pesquisa chegou no início do arquivo, deseja recomeçar do final?";

        if (context.getSearchForward())
        {
            s = "A pesquisa chegou no final do arquivo, deseja recomeçar do início?";
        }

        if (QuestionDialog.getInstance().showConfirmMessage(s, JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION)
        {
            if (context.getSearchForward())
            {
                textArea.setCaretPosition(0);
            }
            else
            {
                textArea.setCaretPosition(textArea.getText().length() - 1);
            }

            realizarPesquisa(tipo);
        }
    }
    
    @Override
    public void configurarCores() {
        setBackground(ColorController.FUNDO_CLARO);
        substituirLabel.setForeground(ColorController.COR_LETRA);
        procurarLabel.setForeground(ColorController.COR_LETRA);
        avancarRadioButton.setForeground(ColorController.COR_LETRA);
        voltarRadioButton.setForeground(ColorController.COR_LETRA);
        if(WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configuraWebLaf(marcarCheckBox);
            WeblafUtils.configuraWebLaf(diferenciarCheckBox);
            WeblafUtils.configuraWebLaf(palavraInteiraCheckBox);
            WeblafUtils.configuraWebLaf(expressaoCheckBox);
            WeblafUtils.configuraWebLaf(procurarTextField);
            WeblafUtils.configuraWebLaf(substituirTextField);
            WeblafUtils.configuraWebLaf(avancarRadioButton);
            WeblafUtils.configuraWebLaf(voltarRadioButton);
            WeblafUtils.configurarBotao(procurarButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 2, true);
            WeblafUtils.configurarBotao(substituirButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 2, true);
            WeblafUtils.configurarBotao(substituirTudoButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 2, true);
            WeblafUtils.configurarBotao(cancelarButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 2, true);
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

        searchPane = new javax.swing.JPanel();
        findReplaceFieldsPane = new javax.swing.JPanel();
        replacePane = new javax.swing.JPanel();
        substituirLabel = new javax.swing.JLabel();
        substituirTextField = new javax.swing.JTextField();
        findPane = new javax.swing.JPanel();
        procurarLabel = new javax.swing.JLabel();
        procurarTextField = new javax.swing.JTextField();
        optionsPane = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        diferenciarCheckBox = new javax.swing.JCheckBox();
        marcarCheckBox = new javax.swing.JCheckBox();
        palavraInteiraCheckBox = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        expressaoCheckBox = new javax.swing.JCheckBox();
        avancarRadioButton = new javax.swing.JRadioButton();
        voltarRadioButton = new javax.swing.JRadioButton();
        buttonsPane = new javax.swing.JPanel();
        procurarButton = new com.alee.laf.button.WebButton();
        substituirButton = new com.alee.laf.button.WebButton();
        substituirTudoButton = new com.alee.laf.button.WebButton();
        cancelarButton = new com.alee.laf.button.WebButton();

        setPreferredSize(new java.awt.Dimension(530, 170));
        setLayout(new java.awt.BorderLayout());

        searchPane.setOpaque(false);
        searchPane.setPreferredSize(new java.awt.Dimension(400, 233));
        searchPane.setLayout(new java.awt.BorderLayout());

        findReplaceFieldsPane.setOpaque(false);
        findReplaceFieldsPane.setPreferredSize(new java.awt.Dimension(153, 50));
        findReplaceFieldsPane.setLayout(new java.awt.BorderLayout());

        replacePane.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        replacePane.setOpaque(false);
        replacePane.setPreferredSize(new java.awt.Dimension(153, 50));
        replacePane.setLayout(new java.awt.BorderLayout());

        substituirLabel.setText("Substituir ");
        replacePane.add(substituirLabel, java.awt.BorderLayout.WEST);

        substituirTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                substituirTextFieldActionPerformed(evt);
            }
        });
        replacePane.add(substituirTextField, java.awt.BorderLayout.CENTER);

        findReplaceFieldsPane.add(replacePane, java.awt.BorderLayout.SOUTH);

        findPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        findPane.setOpaque(false);
        findPane.setLayout(new java.awt.BorderLayout());

        procurarLabel.setText(" Procurar ");
        findPane.add(procurarLabel, java.awt.BorderLayout.WEST);

        procurarTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procurarTextFieldActionPerformed(evt);
            }
        });
        findPane.add(procurarTextField, java.awt.BorderLayout.CENTER);

        findReplaceFieldsPane.add(findPane, java.awt.BorderLayout.NORTH);

        searchPane.add(findReplaceFieldsPane, java.awt.BorderLayout.CENTER);

        optionsPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 65, 1, 1));
        optionsPane.setOpaque(false);
        optionsPane.setPreferredSize(new java.awt.Dimension(100, 80));
        optionsPane.setLayout(new javax.swing.BoxLayout(optionsPane, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        diferenciarCheckBox.setText("Diferenciar maiúsculas/minúsculas");
        diferenciarCheckBox.setBorder(null);
        jPanel1.add(diferenciarCheckBox);

        marcarCheckBox.setText("Marcar Todas");
        marcarCheckBox.setBorder(null);
        marcarCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marcarCheckBoxActionPerformed(evt);
            }
        });
        jPanel1.add(marcarCheckBox);

        palavraInteiraCheckBox.setText("Palavra Inteira");
        palavraInteiraCheckBox.setBorder(null);
        jPanel1.add(palavraInteiraCheckBox);

        optionsPane.add(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        expressaoCheckBox.setText("Expressão Regular");
        expressaoCheckBox.setBorder(null);
        expressaoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expressaoCheckBoxActionPerformed(evt);
            }
        });
        jPanel2.add(expressaoCheckBox);

        avancarRadioButton.setText("Avançar");
        avancarRadioButton.setBorder(null);
        jPanel2.add(avancarRadioButton);

        voltarRadioButton.setText("Voltar");
        voltarRadioButton.setBorder(null);
        jPanel2.add(voltarRadioButton);

        optionsPane.add(jPanel2);

        searchPane.add(optionsPane, java.awt.BorderLayout.PAGE_END);

        add(searchPane, java.awt.BorderLayout.CENTER);

        buttonsPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonsPane.setOpaque(false);
        buttonsPane.setPreferredSize(new java.awt.Dimension(110, 233));

        procurarButton.setText("Procurar");
        procurarButton.setPreferredSize(new java.awt.Dimension(93, 24));
        buttonsPane.add(procurarButton);

        substituirButton.setText("Substituir");
        substituirButton.setPreferredSize(new java.awt.Dimension(93, 24));
        buttonsPane.add(substituirButton);

        substituirTudoButton.setText("Substituir todas");
        substituirTudoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                substituirTudoButtonActionPerformed(evt);
            }
        });
        buttonsPane.add(substituirTudoButton);

        cancelarButton.setText("Cancelar");
        cancelarButton.setPreferredSize(new java.awt.Dimension(93, 24));
        buttonsPane.add(cancelarButton);

        add(buttonsPane, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void substituirTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_substituirTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_substituirTextFieldActionPerformed

    private void procurarTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procurarTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_procurarTextFieldActionPerformed

    private void marcarCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marcarCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_marcarCheckBoxActionPerformed

    private void expressaoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expressaoCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expressaoCheckBoxActionPerformed

    private void substituirTudoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_substituirTudoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_substituirTudoButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton avancarRadioButton;
    private javax.swing.JPanel buttonsPane;
    private com.alee.laf.button.WebButton cancelarButton;
    private javax.swing.JCheckBox diferenciarCheckBox;
    private javax.swing.JCheckBox expressaoCheckBox;
    private javax.swing.JPanel findPane;
    private javax.swing.JPanel findReplaceFieldsPane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JCheckBox marcarCheckBox;
    private javax.swing.JPanel optionsPane;
    private javax.swing.JCheckBox palavraInteiraCheckBox;
    private com.alee.laf.button.WebButton procurarButton;
    private javax.swing.JLabel procurarLabel;
    private javax.swing.JTextField procurarTextField;
    private javax.swing.JPanel replacePane;
    private javax.swing.JPanel searchPane;
    private com.alee.laf.button.WebButton substituirButton;
    private javax.swing.JLabel substituirLabel;
    private javax.swing.JTextField substituirTextField;
    private com.alee.laf.button.WebButton substituirTudoButton;
    private javax.swing.JRadioButton voltarRadioButton;
    // End of variables declaration//GEN-END:variables
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import com.alee.laf.button.WebButton;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import org.json.JSONObject;

/**
 *
 * @author Adson Esteves
 */
public class TelaEditarTemas extends javax.swing.JPanel implements Themeable{

    /**
     * Creates new form TelaEditarTemas
     */
    
    TelaCustomBorder dialog;
    
    public TelaEditarTemas(TelaCustomBorder dialog) {
        initComponents();
        this.dialog = dialog;
        configurarCores();
        carregarTemas();
    }
    
    private void carregarTemas()
    {
        String[] temas = ColorController.listarTemas();
        MeuModel model = new MeuModel();
        
        for (String tema : temas) 
        {
            model.add(tema);
        }
        listaTemas.setCellRenderer(new Render());
        listaTemas.setModel(model);
        carregarCoresTema(temas[0]);
    }
    
    private void carregarCoresTema(String tema)
    {
        JSONObject temas = ColorController.getTemas();
        JSONObject coresTema = temas.getJSONObject(tema);
        
        for (String name : JSONObject.getNames(coresTema)) 
        {
            if(!name.equals("Editor"))
            {
                JPanel estilo = new JPanel();
                estilo.setOpaque(false);
                estilo.setAlignmentY(LEFT_ALIGNMENT);
                JLabel nomeVariavel = new JLabel("<html><body width='110px'><div>"+name.replace("_", " ")+"</div></body></html>");                
                nomeVariavel.setForeground(ColorController.COR_LETRA);
                WebButton botaoColorPicker = new WebButton("+");
                WeblafUtils.configurarBotao(botaoColorPicker, 1);
                estilo.add(botaoColorPicker);
                estilo.add(nomeVariavel);
                estilo.revalidate();
                estilo.repaint();
                painelVariaveisPSInterior.add(estilo);
                
            }
        }
        painelVariaveisPSInterior.setSize(new Dimension(100, 60));
        JSONObject coresEditor = coresTema.getJSONObject("Editor");
        
        for (String name : JSONObject.getNames(coresEditor)) 
        {
            JPanel estilo = new JPanel();
            estilo.setOpaque(false);
            estilo.setAlignmentY(LEFT_ALIGNMENT);
            JLabel nomeVariavel = new JLabel("<html><body width='110px'><div>"+name.replace("_", " ")+"</div></body></html>");
            nomeVariavel.setForeground(ColorController.COR_LETRA);
            WebButton botaoColorPicker = new WebButton("+");
            WeblafUtils.configurarBotao(botaoColorPicker, 1);
            estilo.add(botaoColorPicker);
            estilo.add(nomeVariavel);
            estilo.revalidate();
            estilo.repaint();
            painelVariaveisEditorInterior.add(estilo);
        }
    }

    @Override
    public void configurarCores() {
        setBackground(ColorController.FUNDO_MEDIO);
        setForeground(ColorController.COR_LETRA);
        labelTemas.setForeground(ColorController.COR_LETRA);
        labelEditor.setForeground(ColorController.COR_LETRA);
        labelPS.setForeground(ColorController.COR_LETRA);
        if(WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configuraWebLaf(variavelScrollPane);
            WeblafUtils.configurarBotao(botaoNovoTema, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);
            WeblafUtils.configurarBotao(botaoAplicarTema, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);
            WeblafUtils.configurarBotao(botaoCancelar, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);
        }
    }
    
    public class Render extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            final JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            renderer.setForeground(ColorController.COR_LETRA);
            renderer.setOpaque(false);

            return renderer;
        }
    }
    
    private class MeuModel implements ListModel<String> {
        
        List<String> temas = new ArrayList<>();

        @Override
        public int getSize() {
            return temas.size();
        }

        @Override
        public String getElementAt(int index) {
            return temas.get(index);
        }

        @Override
        public void addListDataListener(ListDataListener l) {
            
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
            
        }
        
        public void add(String dado) {
            temas.add(dado);
        }

        public void clear() {
            temas.clear();
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

        jPanel1 = new javax.swing.JPanel();
        variavelScrollPane = new javax.swing.JScrollPane();
        painelVariaveis = new javax.swing.JPanel();
        painelVariaveisEditor = new javax.swing.JPanel();
        labelEditor = new javax.swing.JLabel();
        painelVariaveisEditorInterior = new javax.swing.JPanel();
        painelVariaveisIDE = new javax.swing.JPanel();
        labelPS = new javax.swing.JLabel();
        painelVariaveisPSInterior = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaTemas = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        labelTemas = new javax.swing.JLabel();
        botaoNovoTema = new com.alee.laf.button.WebButton();
        botaoAplicarTema = new com.alee.laf.button.WebButton();
        botaoCancelar = new com.alee.laf.button.WebButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);

        variavelScrollPane.setMaximumSize(new java.awt.Dimension(760, 580));
        variavelScrollPane.setOpaque(false);

        painelVariaveis.setMaximumSize(new java.awt.Dimension(760, 580));
        painelVariaveis.setOpaque(false);
        painelVariaveis.setLayout(new java.awt.BorderLayout());

        painelVariaveisEditor.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelVariaveisEditor.setOpaque(false);
        painelVariaveisEditor.setLayout(new java.awt.BorderLayout());

        labelEditor.setText("Editor");
        labelEditor.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelVariaveisEditor.add(labelEditor, java.awt.BorderLayout.PAGE_START);

        painelVariaveisEditorInterior.setOpaque(false);
        painelVariaveisEditorInterior.setLayout(new java.awt.GridLayout(10, 3));
        painelVariaveisEditor.add(painelVariaveisEditorInterior, java.awt.BorderLayout.CENTER);

        painelVariaveis.add(painelVariaveisEditor, java.awt.BorderLayout.CENTER);

        painelVariaveisIDE.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 20, 5));
        painelVariaveisIDE.setOpaque(false);
        painelVariaveisIDE.setLayout(new java.awt.BorderLayout());

        labelPS.setText("Portugol Studio");
        labelPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelVariaveisIDE.add(labelPS, java.awt.BorderLayout.PAGE_START);

        painelVariaveisPSInterior.setOpaque(false);
        painelVariaveisPSInterior.setLayout(new java.awt.GridLayout(5, 3));
        painelVariaveisIDE.add(painelVariaveisPSInterior, java.awt.BorderLayout.CENTER);

        painelVariaveis.add(painelVariaveisIDE, java.awt.BorderLayout.NORTH);

        variavelScrollPane.setViewportView(painelVariaveis);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(variavelScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 493, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(variavelScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setOpaque(false);

        listaTemas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaTemas);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);

        labelTemas.setText("Temas");
        jPanel3.add(labelTemas);

        botaoNovoTema.setText("+");
        botaoNovoTema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNovoTemaActionPerformed(evt);
            }
        });
        jPanel3.add(botaoNovoTema);

        botaoAplicarTema.setText("aplicar tema");
        jPanel3.add(botaoAplicarTema);

        botaoCancelar.setText("cancelar");
        jPanel3.add(botaoCancelar);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        add(jPanel2, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoNovoTemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoTemaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoNovoTemaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoAplicarTema;
    private com.alee.laf.button.WebButton botaoCancelar;
    private com.alee.laf.button.WebButton botaoNovoTema;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelEditor;
    private javax.swing.JLabel labelPS;
    private javax.swing.JLabel labelTemas;
    private javax.swing.JList<String> listaTemas;
    private javax.swing.JPanel painelVariaveis;
    private javax.swing.JPanel painelVariaveisEditor;
    private javax.swing.JPanel painelVariaveisEditorInterior;
    private javax.swing.JPanel painelVariaveisIDE;
    private javax.swing.JPanel painelVariaveisPSInterior;
    private javax.swing.JScrollPane variavelScrollPane;
    // End of variables declaration//GEN-END:variables
}

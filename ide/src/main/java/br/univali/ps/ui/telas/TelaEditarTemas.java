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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
    MeuModel model;
    
    public TelaEditarTemas(TelaCustomBorder dialog) {
        initComponents();
        this.dialog = dialog;
        configurarCores();
        carregarTemas();
    }
    
    private void carregarTemas()
    {
        String[] temas = ColorController.listarTemas();
        model = new MeuModel();
        
        for (String tema : temas) 
        {
            model.add(tema);
        }
        listaTemas.setCellRenderer(new Render());
        listaTemas.setModel(model);
        listaTemas.setSelectedIndex(0);
        carregarCoresTema(temas[0]);
    }
    
    private void carregarCoresTema(String tema)
    {
        painelVariaveisPSInterior.removeAll();
        painelVariaveisEditorInterior.removeAll();
        JSONObject temas = ColorController.getTemas();
        JSONObject coresTema = temas.getJSONObject(tema);
        
        for (String name : JSONObject.getNames(coresTema)) 
        {
            if(!name.equals("Editor") && !name.equals("icones"))
            {
                JPanel estilo = new JPanel();
                estilo.setOpaque(false);
                estilo.setAlignmentY(LEFT_ALIGNMENT);
                JLabel nomeVariavel = new JLabel("<html><body width='110px'><div>"+name.replace("_", " ")+"</div></body></html>");                
                nomeVariavel.setForeground(ColorController.COR_LETRA);
                WebButton botaoColorPicker = new WebButton("+");
                Color botaoColor = new Color(Integer.parseInt(coresTema.getString(name), 16));
                WeblafUtils.configurarBotao(botaoColorPicker, botaoColor, ColorController.COR_LETRA_TITULO, botaoColor.brighter(), ColorController.COR_LETRA_TITULO, 1, true);
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
            Color botaoColor = new Color(Integer.parseInt(coresEditor.getString(name), 16));
            WeblafUtils.configurarBotao(botaoColorPicker, botaoColor, ColorController.COR_LETRA_TITULO, botaoColor.brighter(), ColorController.COR_LETRA_TITULO, 1, true);
            estilo.add(botaoColorPicker);
            estilo.add(nomeVariavel);
            painelVariaveisEditorInterior.add(estilo);
        }
        painelVariaveisPSInterior.revalidate();
        painelVariaveisPSInterior.repaint();
        painelVariaveisEditorInterior.revalidate();
        painelVariaveisEditorInterior.repaint();
    }

    @Override
    public void configurarCores() {
        setBackground(ColorController.FUNDO_MEDIO);
        setForeground(ColorController.COR_LETRA);
        //labelTemas.setForeground(ColorController.COR_LETRA);
        labelEditor.setForeground(ColorController.COR_LETRA);
        labelPS.setForeground(ColorController.COR_LETRA);
        listaTemas.setBackground(ColorController.FUNDO_ESCURO);
        listaTemas.setSelectionBackground(ColorController.FUNDO_CLARO);
        
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
            renderer.setBackground(ColorController.FUNDO_ESCURO);
            if(isSelected)
            {
              renderer.setBackground(ColorController.AMARELO);
              renderer.setForeground(ColorController.FUNDO_ESCURO);
            }
            
            renderer.setBorder(new EmptyBorder(5, 10, 5, 0));
            

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

        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaTemas = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        variavelScrollPane = new javax.swing.JScrollPane();
        painelVariaveis = new javax.swing.JPanel();
        painelVariaveisEditor = new javax.swing.JPanel();
        labelEditor = new javax.swing.JLabel();
        painelVariaveisEditorInterior = new javax.swing.JPanel();
        painelVariaveisIDE = new javax.swing.JPanel();
        labelPS = new javax.swing.JLabel();
        painelVariaveisPSInterior = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        botaoNovoTema = new com.alee.laf.button.WebButton();
        botaoAplicarTema = new com.alee.laf.button.WebButton();
        botaoCancelar = new com.alee.laf.button.WebButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new java.awt.BorderLayout());

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        listaTemas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaTemas.setPreferredSize(new java.awt.Dimension(100, 80));
        listaTemas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaTemasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaTemas);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        variavelScrollPane.setBorder(null);
        variavelScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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

        painelVariaveisIDE.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 20, 5));
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

        jPanel1.add(variavelScrollPane, java.awt.BorderLayout.CENTER);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        botaoNovoTema.setText("adicionar tema");
        botaoNovoTema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNovoTemaActionPerformed(evt);
            }
        });
        jPanel4.add(botaoNovoTema);

        botaoAplicarTema.setText("aplicar tema");
        jPanel4.add(botaoAplicarTema);

        botaoCancelar.setText("cancelar");
        jPanel4.add(botaoCancelar);

        jPanel1.add(jPanel4, java.awt.BorderLayout.SOUTH);

        jPanel5.add(jPanel1, java.awt.BorderLayout.CENTER);

        add(jPanel5, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoNovoTemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoTemaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoNovoTemaActionPerformed

    private void listaTemasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaTemasMouseClicked
        final int index = ((JList) evt.getSource()).getSelectedIndex();
        String tema = model.getElementAt(index);
        carregarCoresTema(tema);
    }//GEN-LAST:event_listaTemasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoAplicarTema;
    private com.alee.laf.button.WebButton botaoCancelar;
    private com.alee.laf.button.WebButton botaoNovoTema;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelEditor;
    private javax.swing.JLabel labelPS;
    private javax.swing.JList<String> listaTemas;
    private javax.swing.JPanel painelVariaveis;
    private javax.swing.JPanel painelVariaveisEditor;
    private javax.swing.JPanel painelVariaveisEditorInterior;
    private javax.swing.JPanel painelVariaveisIDE;
    private javax.swing.JPanel painelVariaveisPSInterior;
    private javax.swing.JScrollPane variavelScrollPane;
    // End of variables declaration//GEN-END:variables
}

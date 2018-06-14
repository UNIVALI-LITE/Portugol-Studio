/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.rstautil.tree;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Adson Esteves
 */
public class SearchTextPanel extends javax.swing.JPanel implements Themeable{
    
    private int searchDelay = 500;
    private Action searchAction;
    
    private Timer searchTimer;
    /**
     * Creates new form SearchTextPanel
     */
    public SearchTextPanel() {
        initComponents();
        limpar.setVisible(false);
        campoBusca.setText("");

        campoBusca.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                campoBusca.repaint();
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                campoBusca.repaint();
            }
        });

        limpar.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusca.setText("");
            }
        });

        campoBusca.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                if(!campoBusca.getText().equals(""))
                {
                    limpar.setVisible(true);
                }
                else{
                    limpar.setVisible(false);
                }
                doSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                if(!campoBusca.getText().equals(""))
                {
                    limpar.setVisible(true);
                }
                else{
                    limpar.setVisible(false);
                }
                doSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                if(!campoBusca.getText().equals(""))
                {
                    limpar.setVisible(true);
                }
                else{
                    limpar.setVisible(false);
                }
                doSearch();
            }
            
            private void doSearch()
            {
                if (searchTimer == null)
                {
                    searchTimer = new Timer(searchDelay, (ActionEvent e) ->
                    {
                        if (getSearchAction() != null)
                        {
                            getSearchAction().actionPerformed(e);
                        }
                    });
                    
                    searchTimer.setInitialDelay(searchDelay);
                    searchTimer.setRepeats(false);
                    searchTimer.start();
                }
                else
                {
                    searchTimer.restart();
                }
            }
        });
        configurarCores();        
    }
    
    public String getPlaceholder()
    {
        return campoBusca.getPlaceholder();
    }

    public void setPlaceholder(String placeholder)
    {
        campoBusca.setPlaceholder(placeholder);
    }
    
     public Action getSearchAction()
    {
        return searchAction;
    }

    public void setSearchAction(Action searchAction)
    {
        this.searchAction = searchAction;
    }
    
    public int getSearchDelay() {
        return searchDelay;
    }

    public void setSearchDelay(int search_delay) {
        this.searchDelay = search_delay;
    }

    public SearchTextField getCampoBusca() {
        return campoBusca;
    }

    public void setCampoBusca(SearchTextField campoBusca) {
        this.campoBusca = campoBusca;
    }
    
    @Override
    public void configurarCores() {
        painelbotoes.setBackground(ColorController.COR_DESTAQUE);
        setBackground(ColorController.COR_DESTAQUE);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configurarBotao(buscar, ColorController.TRANSPARENTE, ColorController.TRANSPARENTE, ColorController.TRANSPARENTE, ColorController.TRANSPARENTE, 1, true);
            WeblafUtils.configurarBotao(limpar, ColorController.TRANSPARENTE, ColorController.TRANSPARENTE, ColorController.TRANSPARENTE, ColorController.TRANSPARENTE, 1, true);
        }
        limpar.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "remove.png"));
        buscar.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "find.png"));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        campoBusca = new br.univali.ps.ui.rstautil.tree.SearchTextField();
        painelbotoes = new javax.swing.JPanel();
        limpar = new com.alee.laf.button.WebButton();
        buscar = new com.alee.laf.button.WebButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new java.awt.BorderLayout());
        add(campoBusca, java.awt.BorderLayout.CENTER);

        painelbotoes.setLayout(new javax.swing.BoxLayout(painelbotoes, javax.swing.BoxLayout.LINE_AXIS));

        limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/remove.png"))); // NOI18N
        painelbotoes.add(limpar);

        buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/find.png"))); // NOI18N
        painelbotoes.add(buscar);

        add(painelbotoes, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton buscar;
    private br.univali.ps.ui.rstautil.tree.SearchTextField campoBusca;
    private com.alee.laf.button.WebButton limpar;
    private javax.swing.JPanel painelbotoes;
    // End of variables declaration//GEN-END:variables

    
}

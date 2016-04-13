package br.univali.ps.ui.rstautil.tree.filters.view;

import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.rstautil.tree.filters.SymbolTypeFilter;
import br.univali.ps.ui.rstautil.tree.filters.SymbolTypeFilterListener;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

/**
 *
 * @author Luiz Fernando Noschang
 */
public class SymbolTypeFilterView extends javax.swing.JPanel
{
    private final SymbolTypeFilterListener listener = new FilterListener();

    private SymbolTypeFilter filter;

    public SymbolTypeFilterView()
    {
        initComponents();
        configureButtons();
    }

    private void configureButtons()
    {
        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

        buttonFunctions.setCursor(cursor);
        buttonVariables.setCursor(cursor);
        buttonMatrixes.setCursor(cursor);
        buttonArrays.setCursor(cursor);
        buttonShowAll.setCursor(cursor);
    }

    public void setFilter(SymbolTypeFilter filter)
    {
        if (this.filter != null)
        {
            this.filter.removeListener(listener);
        }

        this.filter = filter;
        this.filter.addListener(listener);
        this.updateStatus();
    }

    private void updateStatus()
    {
        if (filter != null)
        {
            buttonFunctions.setEnabled(true);
            buttonVariables.setEnabled(true);
            buttonMatrixes.setEnabled(true);
            buttonArrays.setEnabled(true);
            buttonShowAll.setEnabled(true);

            buttonFunctions.setSelected(filter.isAccepting(SymbolTypeFilter.SymbolType.FUNCAO));
            buttonVariables.setSelected(filter.isAccepting(SymbolTypeFilter.SymbolType.VARIAVEL));
            buttonMatrixes.setSelected(filter.isAccepting(SymbolTypeFilter.SymbolType.MATRIZ));
            buttonArrays.setSelected(filter.isAccepting(SymbolTypeFilter.SymbolType.VETOR));            
            buttonShowAll.setSelected(filter.isAcceptingAll());
        }
        else
        {
            buttonFunctions.setEnabled(false);
            buttonVariables.setEnabled(false);
            buttonMatrixes.setEnabled(false);
            buttonArrays.setEnabled(false);
            buttonShowAll.setEnabled(false);
        }
    }

    private void updateFilter()
    {
        if (filter != null)
        {
            toggleSymbolType(filter, SymbolTypeFilter.SymbolType.FUNCAO, buttonFunctions);
            toggleSymbolType(filter, SymbolTypeFilter.SymbolType.VARIAVEL, buttonVariables);
            toggleSymbolType(filter, SymbolTypeFilter.SymbolType.MATRIZ, buttonMatrixes);
            toggleSymbolType(filter, SymbolTypeFilter.SymbolType.VETOR, buttonArrays);
        }
    }

    private void showAll()
    {
        if (filter != null)
        {
            if (buttonShowAll.isSelected())
            {
                filter.acceptAll();
            }
            else
            {
                filter.rejectAll();
            }
        }
    }

    private void toggleSymbolType(SymbolTypeFilter filter, SymbolTypeFilter.SymbolType symbolType, JToggleButton button)
    {
        if (button.isSelected())
        {
            filter.accept(symbolType);
        }
        else
        {
            filter.reject(symbolType);
        }
    }
    
    public void registerActions(AbaCodigoFonte abaCodigoFonte)
    {
        registerToggleArraysAction(abaCodigoFonte);
        registerToggleFunctionsAction(abaCodigoFonte);
        registerToggleVariablesAction(abaCodigoFonte);
        registerToggleMatrixesAction(abaCodigoFonte);
        registerShowAllAction(abaCodigoFonte);
    }
    
    private void registerToggleVariablesAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "SymbolTypeFilter_variáveis";
        Action action = new AbstractAction("Variáveis (Ctrl+Shift+A)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonVariables.doClick();
            }
        };
        
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl shift A");
        
        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }
    
    private void registerToggleArraysAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "SymbolTypeFilter_vetores";
        Action action = new AbstractAction("Vetores (Ctrl+Shift+E)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonArrays.doClick();
            }
        };
        
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl shift E");
        
        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }
    
    private void registerToggleMatrixesAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "SymbolTypeFilter_matrizes";
        Action action = new AbstractAction("Matrizes (Ctrl+Shift+M)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonMatrixes.doClick();
            }
        };
        
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl shift M");
        
        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }
    
    private void registerToggleFunctionsAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "SymbolTypeFilter_funcoes";
        Action action = new AbstractAction("Funções (Ctrl+Shift+F)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonFunctions.doClick();
            }
        };
        
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl shift F");
        
        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }
    
    private void registerShowAllAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "SymbolTypeFilter_all";
        Action action = new AbstractAction("Todos (Ctrl+Shift+T)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonShowAll.doClick();
            }
        };
        
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl shift T");
        
        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }

    private final class FilterListener implements SymbolTypeFilterListener
    {
        @Override
        public void symbolTypeAccepted(SymbolTypeFilter.SymbolType symbolType)
        {
            updateStatus();
        }

        @Override
        public void symbolTypeRejected(SymbolTypeFilter.SymbolType symbolType)
        {
            updateStatus();
        }

        @Override
        public void constantsAccepted()
        {
            updateStatus();
        }

        @Override
        public void constantsRejected()
        {
            updateStatus();
        }

        @Override
        public void variablesAccepted()
        {
            updateStatus();
        }

        @Override
        public void variablesRejected()
        {
            updateStatus();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        titleLabel = new javax.swing.JLabel();
        alignmentPanel = new javax.swing.JPanel();
        buttonVariables = new javax.swing.JToggleButton();
        buttonArrays = new javax.swing.JToggleButton();
        buttonMatrixes = new javax.swing.JToggleButton();
        buttonFunctions = new javax.swing.JToggleButton();
        buttonShowAll = new javax.swing.JToggleButton();

        setMaximumSize(new java.awt.Dimension(250, 150));
        setMinimumSize(new java.awt.Dimension(250, 150));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(250, 150));
        setLayout(new java.awt.BorderLayout());

        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Filtrar árvore estrutural por tipo de símbolo");
        titleLabel.setPreferredSize(new java.awt.Dimension(206, 20));
        add(titleLabel, java.awt.BorderLayout.NORTH);

        alignmentPanel.setMaximumSize(new java.awt.Dimension(250, 130));
        alignmentPanel.setMinimumSize(new java.awt.Dimension(250, 130));
        alignmentPanel.setOpaque(false);
        alignmentPanel.setPreferredSize(new java.awt.Dimension(250, 130));
        alignmentPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        buttonVariables.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonVariables.setSelected(true);
        buttonVariables.setText("Variáveis");
        buttonVariables.setPreferredSize(new java.awt.Dimension(110, 30));
        buttonVariables.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonVariables.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonVariablesActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonVariables);

        buttonArrays.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonArrays.setSelected(true);
        buttonArrays.setText("Vetores");
        buttonArrays.setPreferredSize(new java.awt.Dimension(110, 30));
        buttonArrays.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonArrays.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonArraysActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonArrays);

        buttonMatrixes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonMatrixes.setSelected(true);
        buttonMatrixes.setText("Matrizes");
        buttonMatrixes.setPreferredSize(new java.awt.Dimension(110, 30));
        buttonMatrixes.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonMatrixes.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonMatrixesActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonMatrixes);

        buttonFunctions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonFunctions.setSelected(true);
        buttonFunctions.setText("Funções");
        buttonFunctions.setPreferredSize(new java.awt.Dimension(110, 30));
        buttonFunctions.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonFunctions.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonFunctionsActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonFunctions);

        buttonShowAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonShowAll.setSelected(true);
        buttonShowAll.setText("Todos");
        buttonShowAll.setPreferredSize(new java.awt.Dimension(110, 30));
        buttonShowAll.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonShowAll.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonShowAllActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonShowAll);

        add(alignmentPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonVariablesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonVariablesActionPerformed
    {//GEN-HEADEREND:event_buttonVariablesActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonVariablesActionPerformed

    private void buttonArraysActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonArraysActionPerformed
    {//GEN-HEADEREND:event_buttonArraysActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonArraysActionPerformed

    private void buttonMatrixesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonMatrixesActionPerformed
    {//GEN-HEADEREND:event_buttonMatrixesActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonMatrixesActionPerformed

    private void buttonFunctionsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonFunctionsActionPerformed
    {//GEN-HEADEREND:event_buttonFunctionsActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonFunctionsActionPerformed

    private void buttonShowAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonShowAllActionPerformed
    {//GEN-HEADEREND:event_buttonShowAllActionPerformed
        showAll();
    }//GEN-LAST:event_buttonShowAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel alignmentPanel;
    private javax.swing.JToggleButton buttonArrays;
    private javax.swing.JToggleButton buttonFunctions;
    private javax.swing.JToggleButton buttonMatrixes;
    private javax.swing.JToggleButton buttonShowAll;
    private javax.swing.JToggleButton buttonVariables;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}

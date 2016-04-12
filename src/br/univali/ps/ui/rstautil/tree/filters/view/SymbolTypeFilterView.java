package br.univali.ps.ui.rstautil.tree.filters.view;

import br.univali.ps.ui.rstautil.tree.filters.SymbolTypeFilter;
import br.univali.ps.ui.rstautil.tree.filters.SymbolTypeFilterListener;
import java.awt.Cursor;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

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
        buttonShortcuts.setCursor(cursor);
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
            buttonShortcuts.setEnabled(true);
            
            
            buttonFunctions.setSelected(filter.isAccepting(SymbolTypeFilter.SymbolType.FUNCAO));
            buttonVariables.setSelected(filter.isAccepting(SymbolTypeFilter.SymbolType.VARIAVEL));
            buttonMatrixes.setSelected(filter.isAccepting(SymbolTypeFilter.SymbolType.MATRIZ));
            buttonArrays.setSelected(filter.isAccepting(SymbolTypeFilter.SymbolType.VETOR));
        }
        else
        {
            buttonFunctions.setEnabled(false);
            buttonVariables.setEnabled(false);
            buttonMatrixes.setEnabled(false);
            buttonArrays.setEnabled(false);
            buttonShowAll.setEnabled(false);
            buttonShortcuts.setEnabled(false);
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
            filter.accept(SymbolTypeFilter.SymbolType.VETOR);
            filter.accept(SymbolTypeFilter.SymbolType.FUNCAO);
            filter.accept(SymbolTypeFilter.SymbolType.MATRIZ);
            filter.accept(SymbolTypeFilter.SymbolType.VARIAVEL);
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
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        buttonVariables = new javax.swing.JToggleButton();
        buttonArrays = new javax.swing.JToggleButton();
        buttonMatrixes = new javax.swing.JToggleButton();
        buttonFunctions = new javax.swing.JToggleButton();
        buttonShowAll = new javax.swing.JButton();
        buttonShortcuts = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(332, 174));
        setMinimumSize(new java.awt.Dimension(332, 174));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(332, 174));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Filtrar árvore estrutural por tipo de símbolo");

        buttonVariables.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonVariables.setText("variáveis");
        buttonVariables.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonVariablesActionPerformed(evt);
            }
        });

        buttonArrays.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonArrays.setText("vetores");
        buttonArrays.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonArraysActionPerformed(evt);
            }
        });

        buttonMatrixes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonMatrixes.setText("matrizes");
        buttonMatrixes.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonMatrixesActionPerformed(evt);
            }
        });

        buttonFunctions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonFunctions.setText("funções");
        buttonFunctions.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonFunctionsActionPerformed(evt);
            }
        });

        buttonShowAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonShowAll.setText("Exibir Todos");
        buttonShowAll.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonShowAllActionPerformed(evt);
            }
        });

        buttonShortcuts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonShortcuts.setText("Atalhos do Teclado");
        buttonShortcuts.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonShortcutsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buttonVariables, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonArrays, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buttonMatrixes, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonFunctions, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonShortcuts)
                    .addComponent(buttonShowAll, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonVariables)
                    .addComponent(buttonArrays))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonMatrixes)
                    .addComponent(buttonFunctions))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(buttonShowAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonShortcuts)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonVariablesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonVariablesActionPerformed
    {//GEN-HEADEREND:event_buttonVariablesActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonVariablesActionPerformed

    private void buttonShowAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonShowAllActionPerformed
    {//GEN-HEADEREND:event_buttonShowAllActionPerformed
        showAll();
    }//GEN-LAST:event_buttonShowAllActionPerformed

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

    private void buttonShortcutsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonShortcutsActionPerformed
    {//GEN-HEADEREND:event_buttonShortcutsActionPerformed
        JOptionPane.showMessageDialog(SymbolTypeFilterView.this, "Será implementado!", "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_buttonShortcutsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton buttonArrays;
    private javax.swing.JToggleButton buttonFunctions;
    private javax.swing.JToggleButton buttonMatrixes;
    private javax.swing.JButton buttonShortcuts;
    private javax.swing.JButton buttonShowAll;
    private javax.swing.JToggleButton buttonVariables;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}

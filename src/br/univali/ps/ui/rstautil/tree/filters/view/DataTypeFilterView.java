package br.univali.ps.ui.rstautil.tree.filters.view;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.ps.ui.rstautil.tree.filters.DataTypeFilter;
import br.univali.ps.ui.rstautil.tree.filters.DataTypeFilterListener;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

/**
 *
 * @author Luiz Fernando Noschang
 */
public class DataTypeFilterView extends javax.swing.JPanel
{
    private final DataTypeFilterListener listener = new FilterListener();
    
    private DataTypeFilter filter; 

    public DataTypeFilterView()
    {
        initComponents();
    }

    public void setFilter(DataTypeFilter filter)
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
            buttonCadeia.setEnabled(true);
            buttonCaracter.setEnabled(true);
            buttonInteiro.setEnabled(true);
            buttonLogico.setEnabled(true);
            buttonReal.setEnabled(true);
            buttonShowAll.setEnabled(true);
            buttonShortcuts.setEnabled(true);
            
            
            buttonCadeia.setSelected(filter.isAccepting(TipoDado.CADEIA));
            buttonCaracter.setSelected(filter.isAccepting(TipoDado.CARACTER));
            buttonInteiro.setSelected(filter.isAccepting(TipoDado.INTEIRO));
            buttonLogico.setSelected(filter.isAccepting(TipoDado.LOGICO));
            buttonReal.setSelected(filter.isAccepting(TipoDado.REAL));
        }
        else
        {
            buttonCadeia.setEnabled(false);
            buttonCaracter.setEnabled(false);
            buttonInteiro.setEnabled(false);
            buttonLogico.setEnabled(false);
            buttonReal.setEnabled(false);
            buttonShowAll.setEnabled(false);
            buttonShortcuts.setEnabled(false);
        }
    }

    private void updateFilter()
    {
        if (filter != null)
        {
            toggleDataType(filter, TipoDado.CADEIA, buttonCadeia);
            toggleDataType(filter, TipoDado.CARACTER, buttonCaracter);
            toggleDataType(filter, TipoDado.INTEIRO, buttonInteiro);
            toggleDataType(filter, TipoDado.LOGICO, buttonLogico);
            toggleDataType(filter, TipoDado.REAL, buttonReal);
        }
    }
    
    private void showAll()
    {
        if (filter != null)
        {
            filter.accept(TipoDado.CADEIA);
            filter.accept(TipoDado.CARACTER);
            filter.accept(TipoDado.INTEIRO);
            filter.accept(TipoDado.LOGICO);
            filter.accept(TipoDado.REAL);
        }
    }
    
    private void toggleDataType(DataTypeFilter filter, TipoDado dataType, JToggleButton button)
    {
        if (button.isSelected())
        {
            filter.accept(dataType);
        }
        else
        {
            filter.reject(dataType);
        }
    }
    
    private final class FilterListener implements DataTypeFilterListener
    {
        @Override
        public void dataTypeAccepted(TipoDado dataType)
        {
            updateStatus();
        }

        @Override
        public void dataTypeRejected(TipoDado dataType)
        {
            updateStatus();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        buttonInteiro = new javax.swing.JToggleButton();
        buttonReal = new javax.swing.JToggleButton();
        buttonLogico = new javax.swing.JToggleButton();
        buttonCaracter = new javax.swing.JToggleButton();
        buttonCadeia = new javax.swing.JToggleButton();
        buttonShowAll = new javax.swing.JButton();
        buttonShortcuts = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(336, 197));
        setOpaque(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Filtrar símbolos por tipo de dado");

        buttonInteiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/inteiro.png"))); // NOI18N
        buttonInteiro.setText("inteiro");
        buttonInteiro.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonInteiroActionPerformed(evt);
            }
        });

        buttonReal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/real.png"))); // NOI18N
        buttonReal.setText("real");
        buttonReal.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonRealActionPerformed(evt);
            }
        });

        buttonLogico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/logico.png"))); // NOI18N
        buttonLogico.setText("lógico");
        buttonLogico.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonLogicoActionPerformed(evt);
            }
        });

        buttonCaracter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/caracter.png"))); // NOI18N
        buttonCaracter.setText("caracter");
        buttonCaracter.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonCaracterActionPerformed(evt);
            }
        });

        buttonCadeia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/cadeia.png"))); // NOI18N
        buttonCadeia.setText("cadeia");
        buttonCadeia.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonCadeiaActionPerformed(evt);
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
                        .addComponent(buttonInteiro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonReal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonLogico, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonShowAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonCaracter, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonCadeia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buttonShortcuts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonInteiro)
                    .addComponent(buttonReal)
                    .addComponent(buttonLogico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCaracter)
                    .addComponent(buttonCadeia))
                .addGap(18, 18, 18)
                .addComponent(buttonShowAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonShortcuts)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonInteiroActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonInteiroActionPerformed
    {//GEN-HEADEREND:event_buttonInteiroActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonInteiroActionPerformed

    private void buttonShowAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonShowAllActionPerformed
    {//GEN-HEADEREND:event_buttonShowAllActionPerformed
        showAll();
    }//GEN-LAST:event_buttonShowAllActionPerformed

    private void buttonRealActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonRealActionPerformed
    {//GEN-HEADEREND:event_buttonRealActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonRealActionPerformed

    private void buttonLogicoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonLogicoActionPerformed
    {//GEN-HEADEREND:event_buttonLogicoActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonLogicoActionPerformed

    private void buttonCaracterActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonCaracterActionPerformed
    {//GEN-HEADEREND:event_buttonCaracterActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonCaracterActionPerformed

    private void buttonCadeiaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonCadeiaActionPerformed
    {//GEN-HEADEREND:event_buttonCadeiaActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonCadeiaActionPerformed

    private void buttonShortcutsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonShortcutsActionPerformed
    {//GEN-HEADEREND:event_buttonShortcutsActionPerformed
        JOptionPane.showMessageDialog(DataTypeFilterView.this, "Será implementado!", "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_buttonShortcutsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton buttonCadeia;
    private javax.swing.JToggleButton buttonCaracter;
    private javax.swing.JToggleButton buttonInteiro;
    private javax.swing.JToggleButton buttonLogico;
    private javax.swing.JToggleButton buttonReal;
    private javax.swing.JButton buttonShortcuts;
    private javax.swing.JButton buttonShowAll;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}

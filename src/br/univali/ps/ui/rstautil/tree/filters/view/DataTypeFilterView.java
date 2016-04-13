package br.univali.ps.ui.rstautil.tree.filters.view;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.rstautil.tree.filters.DataTypeFilter;
import br.univali.ps.ui.rstautil.tree.filters.DataTypeFilterListener;
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
public class DataTypeFilterView extends javax.swing.JPanel
{
    private final DataTypeFilterListener listener = new FilterListener();

    private DataTypeFilter filter;

    public DataTypeFilterView()
    {
        initComponents();
        configureButtons();
    }

    private void configureButtons()
    {
        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

        buttonCadeia.setCursor(cursor);
        buttonCaracter.setCursor(cursor);
        buttonInteiro.setCursor(cursor);
        buttonLogico.setCursor(cursor);
        buttonReal.setCursor(cursor);
        buttonShowAll.setCursor(cursor);
    }

    public void registerActions(AbaCodigoFonte abaCodigoFonte)
    {
        registerToggleCadeiaAction(abaCodigoFonte);
        registerToggleCaracterAction(abaCodigoFonte);
        registerToggleInteiroAction(abaCodigoFonte);
        registerToggleLogicoAction(abaCodigoFonte);
        registerToggleRealAction(abaCodigoFonte);
        registerToggleVazioAction(abaCodigoFonte);
        registerShowAllAction(abaCodigoFonte);
    }

    private void registerToggleInteiroAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "DataTypeFilter_inteiro";
        Action action = new AbstractAction("Inteiro (Alt+Shift+I)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonInteiro.doClick();
            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke("alt shift I");

        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }

    private void registerToggleCadeiaAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "DataTypeFilter_cadeia";
        Action action = new AbstractAction("Cadeia (Alt+Shift+C)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonCadeia.doClick();
            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke("alt shift C");

        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }

    private void registerToggleRealAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "DataTypeFilter_real";
        Action action = new AbstractAction("Real (Alt+Shift+R)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonReal.doClick();
            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke("alt shift R");

        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }

    private void registerToggleCaracterAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "DataTypeFilter_caracter";
        Action action = new AbstractAction("Caracter (Alt+Shift+A)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonCaracter.doClick();
            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke("alt shift A");

        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }

    private void registerToggleLogicoAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "DataTypeFilter_logico";
        Action action = new AbstractAction("Lógico (Alt+Shift+L)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonLogico.doClick();
            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke("alt shift L");

        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }
    
    private void registerToggleVazioAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "DataTypeFilter_vazio";
        Action action = new AbstractAction("Vazio (Alt+Shift+V)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonVazio.doClick();
            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke("alt shift V");

        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
    }

    private void registerShowAllAction(AbaCodigoFonte abaCodigoFonte)
    {
        String name = "DataTypeFilter_all";
        Action action = new AbstractAction("Todos (Alt+Shift+T)")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonShowAll.doClick();
            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke("alt shift T");

        abaCodigoFonte.getActionMap().put(name, action);
        abaCodigoFonte.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
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
            buttonVazio.setEnabled(true);
            buttonShowAll.setEnabled(true);

            buttonCadeia.setSelected(filter.isAccepting(TipoDado.CADEIA));
            buttonCaracter.setSelected(filter.isAccepting(TipoDado.CARACTER));
            buttonInteiro.setSelected(filter.isAccepting(TipoDado.INTEIRO));
            buttonLogico.setSelected(filter.isAccepting(TipoDado.LOGICO));
            buttonReal.setSelected(filter.isAccepting(TipoDado.REAL));
            buttonVazio.setSelected(filter.isAccepting(TipoDado.VAZIO));
            buttonShowAll.setSelected(filter.isAcceptingAll());
        }
        else
        {
            buttonCadeia.setEnabled(false);
            buttonCaracter.setEnabled(false);
            buttonInteiro.setEnabled(false);
            buttonLogico.setEnabled(false);
            buttonReal.setEnabled(false);
            buttonVazio.setEnabled(false);
            buttonShowAll.setEnabled(false);
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
            toggleDataType(filter, TipoDado.VAZIO, buttonVazio);
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

        titleLabel = new javax.swing.JLabel();
        alignmentPanel = new javax.swing.JPanel();
        buttonInteiro = new javax.swing.JToggleButton();
        buttonReal = new javax.swing.JToggleButton();
        buttonCaracter = new javax.swing.JToggleButton();
        buttonLogico = new javax.swing.JToggleButton();
        buttonCadeia = new javax.swing.JToggleButton();
        buttonVazio = new javax.swing.JToggleButton();
        buttonShowAll = new javax.swing.JToggleButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(175, 30), new java.awt.Dimension(175, 30), new java.awt.Dimension(175, 30));

        setMaximumSize(new java.awt.Dimension(385, 190));
        setMinimumSize(new java.awt.Dimension(385, 190));
        setName(""); // NOI18N
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(385, 190));
        setLayout(new java.awt.BorderLayout());

        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Filtrar árvore estrutural por tipo de dado");
        titleLabel.setPreferredSize(new java.awt.Dimension(195, 20));
        add(titleLabel, java.awt.BorderLayout.NORTH);

        alignmentPanel.setMaximumSize(new java.awt.Dimension(385, 175));
        alignmentPanel.setMinimumSize(new java.awt.Dimension(385, 175));
        alignmentPanel.setOpaque(false);
        alignmentPanel.setPreferredSize(new java.awt.Dimension(385, 175));
        alignmentPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        buttonInteiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonInteiro.setSelected(true);
        buttonInteiro.setText("Inteiro (Alt+Shift+I)");
        buttonInteiro.setPreferredSize(new java.awt.Dimension(175, 30));
        buttonInteiro.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/inteiro.png"))); // NOI18N
        buttonInteiro.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonInteiroActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonInteiro);

        buttonReal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonReal.setSelected(true);
        buttonReal.setText("Real (Alt+Shift+R)");
        buttonReal.setPreferredSize(new java.awt.Dimension(175, 30));
        buttonReal.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/real.png"))); // NOI18N
        buttonReal.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonRealActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonReal);

        buttonCaracter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonCaracter.setSelected(true);
        buttonCaracter.setText("Caracter (Alt+Shift+A)");
        buttonCaracter.setPreferredSize(new java.awt.Dimension(175, 30));
        buttonCaracter.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/caracter.png"))); // NOI18N
        buttonCaracter.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonCaracterActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonCaracter);

        buttonLogico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonLogico.setSelected(true);
        buttonLogico.setText("Lógico (Alt+Shift+L)");
        buttonLogico.setPreferredSize(new java.awt.Dimension(175, 30));
        buttonLogico.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/logico.png"))); // NOI18N
        buttonLogico.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonLogicoActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonLogico);

        buttonCadeia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonCadeia.setSelected(true);
        buttonCadeia.setText("Cadeia (Alt+Shift+C)");
        buttonCadeia.setPreferredSize(new java.awt.Dimension(175, 30));
        buttonCadeia.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/cadeia.png"))); // NOI18N
        buttonCadeia.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonCadeiaActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonCadeia);

        buttonVazio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonVazio.setSelected(true);
        buttonVazio.setText("Vazio (Alt+Shift+V)");
        buttonVazio.setPreferredSize(new java.awt.Dimension(175, 30));
        buttonVazio.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        buttonVazio.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonVazioActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonVazio);

        buttonShowAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
        buttonShowAll.setSelected(true);
        buttonShowAll.setText("Todos (Alt+Shift+T)");
        buttonShowAll.setPreferredSize(new java.awt.Dimension(175, 30));
        buttonShowAll.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        buttonShowAll.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonShowAllActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonShowAll);
        alignmentPanel.add(filler1);

        add(alignmentPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonInteiroActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonInteiroActionPerformed
    {//GEN-HEADEREND:event_buttonInteiroActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonInteiroActionPerformed

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

    private void buttonShowAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonShowAllActionPerformed
    {//GEN-HEADEREND:event_buttonShowAllActionPerformed
        showAll();
    }//GEN-LAST:event_buttonShowAllActionPerformed

    private void buttonVazioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonVazioActionPerformed
    {//GEN-HEADEREND:event_buttonVazioActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonVazioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel alignmentPanel;
    private javax.swing.JToggleButton buttonCadeia;
    private javax.swing.JToggleButton buttonCaracter;
    private javax.swing.JToggleButton buttonInteiro;
    private javax.swing.JToggleButton buttonLogico;
    private javax.swing.JToggleButton buttonReal;
    private javax.swing.JToggleButton buttonShowAll;
    private javax.swing.JToggleButton buttonVazio;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}

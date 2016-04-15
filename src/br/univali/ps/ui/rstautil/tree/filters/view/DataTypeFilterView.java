package br.univali.ps.ui.rstautil.tree.filters.view;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.ps.ui.ColorController;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.rstautil.tree.filters.DataTypeFilter;
import br.univali.ps.ui.rstautil.tree.filters.DataTypeFilterListener;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.weblaf.WeblafUtils;
import java.awt.Color;
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
        WeblafUtils.configurarToogleBotao(buttonCadeia, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA);
        WeblafUtils.configurarToogleBotao(buttonCaracter,ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA);
        WeblafUtils.configurarToogleBotao(buttonInteiro, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA);
        WeblafUtils.configurarToogleBotao(buttonLogico, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA);
        WeblafUtils.configurarToogleBotao(buttonReal, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA);
        WeblafUtils.configurarToogleBotao(buttonVazio, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA);
        WeblafUtils.configurarToogleBotao(buttonShowAll, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 2);
        
        String texto = "Exibir/Ocultar símbolos deste tipo (Alt+Shift+";
        
        FabricaDicasInterface.criarTooltip(buttonCadeia, texto+"C)");
        FabricaDicasInterface.criarTooltip(buttonCaracter, texto+"A)");
        FabricaDicasInterface.criarTooltip(buttonInteiro, texto+"I)");
        FabricaDicasInterface.criarTooltip(buttonLogico, texto+"L)");
        FabricaDicasInterface.criarTooltip(buttonReal, texto+"R)");
        FabricaDicasInterface.criarTooltip(buttonVazio, texto+"V)");
        FabricaDicasInterface.criarTooltip(buttonShowAll, "Exibir/Ocultar todos os tipos de dados: Alt+Shift+T");
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
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        buttonShowAll = new com.alee.laf.button.WebToggleButton();
        alignmentPanel = new javax.swing.JPanel();
        buttonInteiro = new com.alee.laf.button.WebToggleButton();
        buttonReal = new com.alee.laf.button.WebToggleButton();
        buttonCaracter = new com.alee.laf.button.WebToggleButton();
        buttonLogico = new com.alee.laf.button.WebToggleButton();
        buttonCadeia = new com.alee.laf.button.WebToggleButton();
        buttonVazio = new com.alee.laf.button.WebToggleButton();

        setName(""); // NOI18N
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        titleLabel.setText("Tipo de Dado");
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        titleLabel.setFocusable(false);
        titleLabel.setPreferredSize(new java.awt.Dimension(195, 20));
        jPanel1.add(titleLabel, java.awt.BorderLayout.CENTER);

        buttonShowAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/all_types.png"))); // NOI18N
        buttonShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonShowAllActionPerformed(evt);
            }
        });
        jPanel1.add(buttonShowAll, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        alignmentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 10, 10));
        alignmentPanel.setOpaque(false);
        alignmentPanel.setLayout(new java.awt.GridLayout(3, 2, 10, 10));

        buttonInteiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/inteiro.png"))); // NOI18N
        buttonInteiro.setText("Inteiro");
        buttonInteiro.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonInteiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInteiroActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonInteiro);

        buttonReal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/real.png"))); // NOI18N
        buttonReal.setText("Real");
        buttonReal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonReal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRealActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonReal);

        buttonCaracter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/caracter.png"))); // NOI18N
        buttonCaracter.setText("Caracter");
        buttonCaracter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonCaracter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCaracterActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonCaracter);

        buttonLogico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/logico.png"))); // NOI18N
        buttonLogico.setText("Lógico");
        buttonLogico.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonLogico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogicoActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonLogico);

        buttonCadeia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/cadeia.png"))); // NOI18N
        buttonCadeia.setText("Cadeia");
        buttonCadeia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonCadeia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCadeiaActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonCadeia);

        buttonVazio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/vazio.png"))); // NOI18N
        buttonVazio.setText("Vazio");
        buttonVazio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonVazio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVazioActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonVazio);

        add(alignmentPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonInteiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInteiroActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonInteiroActionPerformed

    private void buttonRealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRealActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonRealActionPerformed

    private void buttonCaracterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCaracterActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonCaracterActionPerformed

    private void buttonLogicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogicoActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonLogicoActionPerformed

    private void buttonCadeiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadeiaActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonCadeiaActionPerformed

    private void buttonVazioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVazioActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonVazioActionPerformed

    private void buttonShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonShowAllActionPerformed
        showAll();
    }//GEN-LAST:event_buttonShowAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel alignmentPanel;
    private com.alee.laf.button.WebToggleButton buttonCadeia;
    private com.alee.laf.button.WebToggleButton buttonCaracter;
    private com.alee.laf.button.WebToggleButton buttonInteiro;
    private com.alee.laf.button.WebToggleButton buttonLogico;
    private com.alee.laf.button.WebToggleButton buttonReal;
    private com.alee.laf.button.WebToggleButton buttonShowAll;
    private com.alee.laf.button.WebToggleButton buttonVazio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}

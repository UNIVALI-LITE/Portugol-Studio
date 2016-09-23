package br.univali.ps.ui.rstautil.tree.filters.view;

import br.univali.ps.ui.ColorController;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.rstautil.tree.filters.SymbolTypeFilter;
import br.univali.ps.ui.rstautil.tree.filters.SymbolTypeFilterListener;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.weblaf.WeblafUtils;
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
        WeblafUtils.configurarToogleBotao(buttonArrays, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA);
        WeblafUtils.configurarToogleBotao(buttonFunctions, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA);
        WeblafUtils.configurarToogleBotao(buttonMatrixes, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA);
        WeblafUtils.configurarToogleBotao(buttonVariables, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA);
        WeblafUtils.configurarToogleBotao(buttonShowAll, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 2);
        
        String texto = "Exibir/Ocultar (Ctrl+Shift+";
        
        FabricaDicasInterface.criarTooltip(buttonVariables, texto+"A)");
        FabricaDicasInterface.criarTooltip(buttonFunctions, texto+"F)");
        FabricaDicasInterface.criarTooltip(buttonMatrixes, texto+"M)");
        FabricaDicasInterface.criarTooltip(buttonArrays, texto+"E)");
        FabricaDicasInterface.criarTooltip(buttonShowAll, "Exibir/Ocultar todos símbolos: Ctrl+Shift+T");
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
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonShowAll = new com.alee.laf.button.WebToggleButton();
        titleLabel = new javax.swing.JLabel();
        alignmentPanel = new javax.swing.JPanel();
        buttonVariables = new com.alee.laf.button.WebToggleButton();
        buttonArrays = new com.alee.laf.button.WebToggleButton();
        buttonMatrixes = new com.alee.laf.button.WebToggleButton();
        buttonFunctions = new com.alee.laf.button.WebToggleButton();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        buttonShowAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/puzzleicon.png"))); // NOI18N
        buttonShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonShowAllActionPerformed(evt);
            }
        });
        jPanel1.add(buttonShowAll, java.awt.BorderLayout.EAST);

        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        titleLabel.setText("Tipo de Símbolo");
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        titleLabel.setPreferredSize(new java.awt.Dimension(206, 20));
        jPanel1.add(titleLabel, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        alignmentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 10, 10));
        alignmentPanel.setOpaque(false);
        alignmentPanel.setLayout(new java.awt.GridLayout(2, 2, 10, 10));

        buttonVariables.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/cadeia.png"))); // NOI18N
        buttonVariables.setText("Variáveis");
        buttonVariables.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonVariables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVariablesActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonVariables);

        buttonArrays.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/vetor_caracter.png"))); // NOI18N
        buttonArrays.setText("Vetores");
        buttonArrays.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonArrays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonArraysActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonArrays);

        buttonMatrixes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/matriz_real.png"))); // NOI18N
        buttonMatrixes.setText("Matrizes");
        buttonMatrixes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonMatrixes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMatrixesActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonMatrixes);

        buttonFunctions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/funcaoDoUsuario.png"))); // NOI18N
        buttonFunctions.setText("Funções");
        buttonFunctions.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonFunctions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFunctionsActionPerformed(evt);
            }
        });
        alignmentPanel.add(buttonFunctions);

        add(alignmentPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonShowAllActionPerformed
        showAll();
    }//GEN-LAST:event_buttonShowAllActionPerformed

    private void buttonFunctionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFunctionsActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonFunctionsActionPerformed

    private void buttonMatrixesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMatrixesActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonMatrixesActionPerformed

    private void buttonArraysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonArraysActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonArraysActionPerformed

    private void buttonVariablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVariablesActionPerformed
        updateFilter();
    }//GEN-LAST:event_buttonVariablesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel alignmentPanel;
    private com.alee.laf.button.WebToggleButton buttonArrays;
    private com.alee.laf.button.WebToggleButton buttonFunctions;
    private com.alee.laf.button.WebToggleButton buttonMatrixes;
    private com.alee.laf.button.WebToggleButton buttonShowAll;
    private com.alee.laf.button.WebToggleButton buttonVariables;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}

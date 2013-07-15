package br.univali.ps.ui;

import br.univali.portugol.nucleo.Programa;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.IconFactory;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class TelaOpcoesExecucao extends JDialog implements PropertyChangeListener, ChangeListener
{
    private Programa programa;
    private String ultimaFuncaoSelecionada;
    private boolean cancelado;
            
    
    public TelaOpcoesExecucao()
    {
        initComponents();
        getRootPane().setDefaultButton(botaoExecutar);
        
        instalarObservadores();
        carregarConfiguracoes();
        
    }
    
    private void instalarObservadores()
    {
        Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
        
        configuracoes.adicionarObservadorConfiguracao(this, Configuracoes.EXIBIR_OPCOES_EXECUCAO);
        campoExibirTela.addChangeListener(TelaOpcoesExecucao.this);        
    }
    
    private void carregarConfiguracoes()
    {
        Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
        campoExibirTela.setSelected(configuracoes.isExibirOpcoesExecucao());
    }

    @Override
    public void setVisible(boolean visivel)
    {
        if (visivel)
        {
            setLocationRelativeTo(null);
            cancelado = true;
        }
        
        super.setVisible(visivel);
    }
    
    public void inicializar(Programa programa, boolean depurar)
    {
        this.programa = programa;
        
        DefaultComboBoxModel modelo = (DefaultComboBoxModel) campoFuncaoInicial.getModel();
        modelo.removeAllElements();
        
        List<String> funcoes = programa.getFuncoes();
        
        Collections.sort(funcoes);
        
        for (String funcao : funcoes)
        {
            modelo.addElement(funcao);
        }
        
        if (!funcoes.isEmpty())
        {
            if (funcaoExiste(ultimaFuncaoSelecionada, programa))
            {
                campoFuncaoInicial.setSelectedItem(ultimaFuncaoSelecionada);                
            }
            else
            if (funcaoExiste(programa.getFuncaoInicial(), programa))
            {
                campoFuncaoInicial.setSelectedItem(programa.getFuncaoInicial());
            }
        }
        
        if (depurar)
        {
            botaoExecutar.setText("Depurar");
            botaoExecutar.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png"));
            campoDepuracaoDetalhada.setVisible(true);
        }
        else
        {
            botaoExecutar.setText("Executar");
            botaoExecutar.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "resultset_next.png"));
            campoDepuracaoDetalhada.setVisible(false);
        }
        
        if (funcoes.isEmpty())
        {
            rotuloAviso.setVisible(true);
        }
        else
        {
            rotuloAviso.setVisible(false);
        }
    }

    public boolean isCancelado()
    {
        return cancelado;
    }
    
    private boolean funcaoExiste(String funcao, Programa programa)
    {
        for (String func : programa.getFuncoes())
        {
            if (func.equals(funcao))
            {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isExibirTela()
    {
        return campoExibirTela.isSelected();
    }
    
    protected String[] getParametros()
    {
        if (campoParametros.getText() != null)
        {
            String textoParametros = campoParametros.getText();

            if (textoParametros.length() > 0)
            {
                return textoParametros.split(";");
            }
        }
        
        return null;
            
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals(Configuracoes.EXIBIR_OPCOES_EXECUCAO))
        {
            boolean exibir = (Boolean) evt.getNewValue();
            
            if (campoExibirTela.isSelected() != exibir)
            {
                campoExibirTela.setSelected(exibir);
            }
        }
    }
    
    public boolean isDepuracaoDetalhada(){
        return campoDepuracaoDetalhada.isSelected();
    }
    
    @Override
    public void stateChanged(ChangeEvent e)
    {
        Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
        configuracoes.setExibirOpcoesExecucao(campoExibirTela.isSelected());
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        rotuloFuncaoInicial = new javax.swing.JLabel();
        campoFuncaoInicial = new javax.swing.JComboBox();
        rotuloParametros = new javax.swing.JLabel();
        painelRolagemParametros = new javax.swing.JScrollPane();
        campoParametros = new javax.swing.JTextArea();
        campoDepuracaoDetalhada = new javax.swing.JCheckBox();
        campoExibirTela = new javax.swing.JCheckBox();
        botaoLimpar = new javax.swing.JButton();
        separador = new javax.swing.JSeparator();
        rotuloAviso = new javax.swing.JLabel();
        botaoExecutar = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setTitle("Opções de Execução");
        setModal(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        rotuloFuncaoInicial.setLabelFor(campoFuncaoInicial);
        rotuloFuncaoInicial.setText("Função inicial:");

        campoFuncaoInicial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rotuloParametros.setLabelFor(campoParametros);
        rotuloParametros.setText("Parâmetros (um em cada linha):");

        campoParametros.setColumns(20);
        campoParametros.setRows(5);
        painelRolagemParametros.setViewportView(campoParametros);

        campoDepuracaoDetalhada.setText("Realizar depuração detalhada");

        campoExibirTela.setSelected(true);
        campoExibirTela.setText("Exibir esta tela antes de cada execução");
        campoExibirTela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        campoExibirTela.setFocusPainted(false);
        campoExibirTela.setFocusable(false);

        botaoLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/edit_clear.png"))); // NOI18N
        botaoLimpar.setText("Limpar");
        botaoLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoLimpar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                botaoLimparActionPerformed(evt);
            }
        });

        rotuloAviso.setForeground(new java.awt.Color(255, 0, 0));
        rotuloAviso.setText("Aviso: não existem funções no programa");

        botaoExecutar.setText("Executar");
        botaoExecutar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoExecutar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                botaoExecutarActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, separador)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(campoFuncaoInicial, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(rotuloParametros, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(rotuloFuncaoInicial, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(painelRolagemParametros)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(rotuloAviso, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(10, 10, 10)
                        .add(botaoExecutar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(campoExibirTela, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(campoDepuracaoDetalhada)
                                .add(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(botaoLimpar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(rotuloFuncaoInicial)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(campoFuncaoInicial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(11, 11, 11)
                .add(rotuloParametros)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(painelRolagemParametros, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 163, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(campoDepuracaoDetalhada)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botaoLimpar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(campoExibirTela))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(separador, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botaoExecutar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(rotuloAviso, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoExecutarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoExecutarActionPerformed
    {//GEN-HEADEREND:event_botaoExecutarActionPerformed
        if (!programa.getFuncoes().isEmpty())
        {
            String selecionada = (String) campoFuncaoInicial.getSelectedItem();
            
            ultimaFuncaoSelecionada = selecionada;                    
            programa.setFuncaoInicial(selecionada);
        }
        
        cancelado = false;
        setVisible(false);
    }//GEN-LAST:event_botaoExecutarActionPerformed

    private void botaoLimparActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoLimparActionPerformed
    {//GEN-HEADEREND:event_botaoLimparActionPerformed
        campoParametros.setText(null);
        campoParametros.requestFocus();
    }//GEN-LAST:event_botaoLimparActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoExecutar;
    private javax.swing.JButton botaoLimpar;
    private javax.swing.JCheckBox campoDepuracaoDetalhada;
    private javax.swing.JCheckBox campoExibirTela;
    private javax.swing.JComboBox campoFuncaoInicial;
    private javax.swing.JTextArea campoParametros;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JScrollPane painelRolagemParametros;
    private javax.swing.JLabel rotuloAviso;
    private javax.swing.JLabel rotuloFuncaoInicial;
    private javax.swing.JLabel rotuloParametros;
    private javax.swing.JSeparator separador;
    // End of variables declaration//GEN-END:variables
}

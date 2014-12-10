package br.univali.ps.ui.ajuda;

import br.univali.ps.ui.editor.Editor;
import br.univali.ps.ui.PainelTabulado;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class EditorAjuda extends JPanel
{
    private Editor editor;
    private Action acaoTenteVoceMesmo;
    private PainelTabulado painelTabulado;
    
    public EditorAjuda()
    {
        initComponents();
        configurarEditor();
        configurarAcoes();
        
        botaoTentar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        
    }
    
    private void configurarEditor()
    {
        editor = new Editor();
        editor.getScrollPane().setWheelScrollingEnabled(false);
        painelEditor.add(editor, BorderLayout.CENTER);
    }
    
    private void configurarAcoes()
    {
        configurarAcaoTenteVoceMesmo();
    }
    
    public void setPainelTabulado(PainelTabulado painelTabulado){
        this.painelTabulado = painelTabulado;
    }
    
    private void configurarAcaoTenteVoceMesmo()
    {
        acaoTenteVoceMesmo = new AbstractAction("Tente você mesmo", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "programa.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                AbaCodigoFonte aba = AbaCodigoFonte.novaAba();
                aba.getEditor().setCodigoFonte(editor.getTextArea().getText());
                
                if(painelTabulado == null){
                    throw new IllegalStateException("O painel tabulado do EditorAjuda não está setado!");
                }
                painelTabulado.add(aba);
            }
        };
        
        botaoTentar.setAction(acaoTenteVoceMesmo);                
    }
    
    public void setCodigo(String codigo)
    {
        editor.setCodigo(codigo);
    }
    
    public void setEditavel(String editavel)
    {
        editor.setEditavel(editavel);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painelEditor = new javax.swing.JPanel();
        painelBotao = new javax.swing.JPanel();
        botaoTentar = new javax.swing.JButton();

        setBackground(new java.awt.Color(250, 250, 250));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        setFocusable(false);
        setLayout(new java.awt.BorderLayout());

        painelEditor.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210))));
        painelEditor.setOpaque(false);
        painelEditor.setLayout(new java.awt.BorderLayout());
        add(painelEditor, java.awt.BorderLayout.CENTER);

        painelBotao.setBackground(new java.awt.Color(250, 250, 250));
        painelBotao.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 0, 8));
        painelBotao.setPreferredSize(new java.awt.Dimension(400, 48));
        painelBotao.setLayout(new java.awt.BorderLayout());

        botaoTentar.setText("Tente você mesmo");
        botaoTentar.setIconTextGap(8);
        botaoTentar.setPreferredSize(new java.awt.Dimension(165, 23));
        painelBotao.add(botaoTentar, java.awt.BorderLayout.WEST);

        add(painelBotao, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoTentar;
    private javax.swing.JPanel painelBotao;
    private javax.swing.JPanel painelEditor;
    // End of variables declaration//GEN-END:variables
}

package br.univali.ps.ui.abas;

import br.univali.ps.ui.utils.IconFactory;
import javax.swing.Icon;

public final class AbaEnunciado extends Aba
{
    private static final Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "question.png");

    public AbaEnunciado()
    {
        super("Enunciado", icone, false);

        initComponents();
    }

    public void setEnunciado(String enunciado)
    {
        campoTextoEnunciado.setText(enunciado);
    }

    public String getEnunciado()
    {
        return campoTextoEnunciado.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painelRolagemEnunciado = new javax.swing.JScrollPane();
        campoTextoEnunciado = new javax.swing.JEditorPane();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        painelRolagemEnunciado.setBackground(new java.awt.Color(250, 250, 250));
        painelRolagemEnunciado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        painelRolagemEnunciado.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));

        campoTextoEnunciado.setEditable(false);
        campoTextoEnunciado.setBackground(new java.awt.Color(250, 250, 250));
        campoTextoEnunciado.setBorder(null);
        campoTextoEnunciado.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        painelRolagemEnunciado.setViewportView(campoTextoEnunciado);

        add(painelRolagemEnunciado, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane campoTextoEnunciado;
    private javax.swing.JScrollPane painelRolagemEnunciado;
    // End of variables declaration//GEN-END:variables
}

package br.univali.ps.ui;

import br.univali.ps.ui.util.IconFactory;
import javax.swing.JTabbedPane;


public class AbaEnunciado extends Aba {

    /** Creates new form EnunciadoAba */
    public AbaEnunciado(JTabbedPane painelTabulado) {
        super(painelTabulado);
        cabecalho.setIcone(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "question.png"));
        cabecalho.setBotaoFecharVisivel(false);
        cabecalho.setTitulo("Enunciado");
        initComponents();
    }
    
    public void setEninciado(String enunciado){
        jEnunciado.setText(enunciado);
    }
    
    public String getEnunciado(){
        return jEnunciado.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEnunciado = new javax.swing.JEditorPane();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(250, 250, 250));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));

        jEnunciado.setEditable(false);
        jEnunciado.setBackground(new java.awt.Color(250, 250, 250));
        jEnunciado.setBorder(null);
        jEnunciado.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jScrollPane1.setViewportView(jEnunciado);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEnunciado;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

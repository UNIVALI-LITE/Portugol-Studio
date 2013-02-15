package br.univali.ps.ui;

import javax.swing.JTabbedPane;


public class AbaEnunciado extends Aba {

    /** Creates new form EnunciadoAba */
    public AbaEnunciado(JTabbedPane painelTabulado) {
        super(painelTabulado);
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
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEnunciado = new javax.swing.JEditorPane();

        setLayout(new java.awt.BorderLayout());

        jEnunciado.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jScrollPane1.setViewportView(jEnunciado);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEnunciado;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

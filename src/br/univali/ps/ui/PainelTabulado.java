package br.univali.ps.ui;

import br.univali.ps.dominio.PortugolDocumento;


public class PainelTabulado extends javax.swing.JTabbedPane implements CabecalhoListener{

    /** Creates new form PainelTabulado */
    public PainelTabulado() {
        initComponents();
    }

    public void novaAba(){
        Aba aba = new Aba();
        adicionarAba(aba);
    }

    public void novaAba(PortugolDocumento documento){
        Aba aba = new Aba(documento);
        adicionarAba(aba);
    }
    
    private void adicionarAba(Aba aba){
        this.add( aba.getConteudo());
        ((CabecalhoAba)aba.getCabecalho()).adicionaListener(this);
        setTabComponentAt(indexOfComponent(aba.getConteudo()), aba.getCabecalho());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void fecharAba(CabecalhoAba cabecalhoAba) {
        int indice = indexOfTabComponent(cabecalhoAba);
        if (indice > -1) {
            Conteudo conteudo = (Conteudo) getComponent(indice);
            if (!conteudo.getEditor().getPortugolDocumento().isChanged())
                remove(indice);
        }
    }
}

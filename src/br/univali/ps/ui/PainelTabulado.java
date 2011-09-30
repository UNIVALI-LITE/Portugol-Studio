package br.univali.ps.ui;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import br.univali.ps.ui.acoes.AcaoAbrirArquivo;
import br.univali.ps.ui.acoes.AcaoNovoArquivo;

public class PainelTabulado extends javax.swing.JTabbedPane implements ComponentListener{

    private BotoesControleAba cabecalhoAbaInicial;
    private List<PainelTabuladoListener> painelTabuladoListeners;
    
    public PainelTabulado() {
        initComponents();
        painelTabuladoListeners = new ArrayList<PainelTabuladoListener>();
        Aba aba = new AbaInicial(this);
        cabecalhoAbaInicial = (BotoesControleAba) aba.cabecalho;
    }

    @Override
    public Component add(Component cmpnt) {
        cmpnt.addComponentListener(this);
        return super.add(cmpnt);
    }
    
    
    
    public void init(AcaoAbrirArquivo acaoAbrir, AcaoNovoArquivo acaoNovo){
        cabecalhoAbaInicial.setAcaoAbrirAction(acaoAbrir);
        cabecalhoAbaInicial.setAcaoNovoArquivo(acaoNovo);
    }

    public Aba getAbaSelecionada(){
        if (getSelectedComponent() instanceof Aba)
            return (Aba) getSelectedComponent(); 
        else
            return null;
    }
    
    public void fecharTodasAbas(Class<? extends Aba> classe)
    {
        Component[] components = getComponents();
        for (int i = 0; i < components.length; i++){
            
            if (components[i].getClass() == classe){
                ((Aba)components[i]).fechar();
            }   
        }
    }
    
    
    public boolean temAbaAberta(Class<? extends Aba> classe)
    {
    	Component[] components = getComponents();
    	
        for (int i = 0; i < components.length; i++){

            if ((components[i].getClass() == classe)){
                return true;
            }   
        }
        
        return false;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void componentResized(ComponentEvent ce) {
    
    }

    public void componentMoved(ComponentEvent ce) {
       
    }

    public void componentShown(ComponentEvent ce) {
        Aba aba =  (Aba) ce.getComponent();   
        disparaAbaSelecionada(aba);
    }

    public void componentHidden(ComponentEvent ce) {
        
    }
    
    private void disparaAbaSelecionada(Aba aba){
        for (PainelTabuladoListener painelTabuladoListener : painelTabuladoListeners) {
            painelTabuladoListener.abaSelecionada(aba);
        }
    }
    
    public void adicionaPainelTabuladoListener(PainelTabuladoListener listener) {
        if (!painelTabuladoListeners.contains(listener))
            painelTabuladoListeners.add(listener);
    }
    
    public void removePainelTabuladoListener(PainelTabuladoListener listener) {
        painelTabuladoListeners.remove(listener);
    }
    
}

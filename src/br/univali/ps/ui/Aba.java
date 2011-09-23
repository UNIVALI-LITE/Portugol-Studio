package br.univali.ps.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public abstract class Aba extends JPanel{
    
    protected CabecalhoAba cabecalho;
    private JTabbedPane painelTabulado;
    private List<AbaListener> listeners;
    
    public Aba(JTabbedPane painelTabulado) {
        listeners = new ArrayList<AbaListener>();
        this.painelTabulado = painelTabulado;
        cabecalho = new CabecalhoAba(this);
        cabecalho.setTitulo("Sem título");
        cabecalho.setBotaoFecharVisivel(true);
        this.painelTabulado.add(this);
        this.painelTabulado.setTabComponentAt(painelTabulado.indexOfComponent(this), cabecalho);
        this.painelTabulado.setSelectedComponent(this);
    }

    public void setRemovivel(boolean removivel) {
        cabecalho.setBotaoFecharVisivel(removivel);
    }

    public boolean isRemovivel() {
        return cabecalho.isBotaoFecharVisivel();
    }
    
    public boolean fechar() {
        
        boolean podeFechar = true;
        
        for (AbaListener listener : listeners){
            if (!listener.fechandoAba(this))
                podeFechar = false;
        }
        
        if (podeFechar)
            painelTabulado.remove(this);
        
        return podeFechar;
    }
    
    public void adicionarAbaListener(AbaListener listener){
        if (!listeners.contains(listener))
            listeners.add(listener);
    }
    
    public void removerAbaListener (AbaListener listener){
        listeners.remove(listener);
    }
}

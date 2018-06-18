package br.univali.ps.ui.paineis.utils;

import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.paineis.PSPainelTabulado;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fillipi Domingos Pelz
 */
public class PainelTabulado extends PSPainelTabulado{

    private List<PainelTabuladoListener> painelTabuladoListeners;

    public PainelTabulado() {
        painelTabuladoListeners = new ArrayList<>();        
    }

    public void adicionaPainelTabuladoListener(PainelTabuladoListener listener) {
        if (!painelTabuladoListeners.contains(listener)) {
            painelTabuladoListeners.add(listener);
        }
    }
    
    @Override
    public void trocouAba(Aba aba) {
        disparaAbaSelecionada(aba);
    }

    public void fecharTodasAbas(Class<? extends Aba> classe) {
        Component[] components = getAbaContainer().getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i].getClass() == classe) {
                ((Aba) components[i]).fechar();
            }
        }
    }    

    public List<Aba> getAbas(Class<? extends Aba> classe) {
        List<Aba> abas = new ArrayList<>();

        for (Component componente : getAbaContainer().getComponents()) {
            if (componente.getClass() == classe) {
                abas.add((Aba) componente);
            }
        }
        return abas;
    }
    
    @Override
    public void remove(Component component) {
        if (component instanceof Aba) {
            selecionarAbaAnterior();
        }
        super.remove(component);
    }

    public void removePainelTabuladoListener(PainelTabuladoListener listener) {
        painelTabuladoListeners.remove(listener);
    }

    public void selecionarAbaAnterior() {
        Component[] components = getAbaContainer().getComponents();
        for (int i = 0; i < components.length; i++) {
            if(components[i] instanceof Aba)
            {                
                if (components[i].isVisible()) {
                    mudarParaAba(((Aba) components[Math.max(0, i - 1)]));
                    return;
                }
            }
        }
    }

    public void selecionarProximaAba() {
        Component[] components = getAbaContainer().getComponents();
        for (int i = 0; i < components.length; i++) {
            if(components[i] instanceof Aba)
            {                
                if (components[i].isVisible()) {
                    mudarParaAba((Aba) components[Math.min(i + 1, components.length - 1)]);
                    return;
                }
            }
        }
    }

    public boolean temAbaAberta(Class<? extends Aba> classe) {
        Component[] components = getAbaContainer().getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i].getClass() == classe) {
                return true;
            }
        }

        return false;
    }

    private void disparaAbaSelecionada(Aba aba) {
        for (PainelTabuladoListener painelTabuladoListener : painelTabuladoListeners) {
            painelTabuladoListener.abaSelecionada(aba);
        }
    }
    
}

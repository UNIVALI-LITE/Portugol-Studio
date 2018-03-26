package br.univali.ps.ui.paineis.utils;

import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.paineis.NewPainelTabulado;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fillipi Domingos Pelz
 */
public class PainelTabulado extends NewPainelTabulado implements ComponentListener {

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
    public void componentShown(ComponentEvent ce) {
        Aba aba = (Aba) ce.getComponent();
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

//    @Override
//    public void remove(Component component) {
//        if (component instanceof Aba) {
//            component.removeComponentListener(this);
//        }
//        super.remove(component);
//    }

    public void removePainelTabuladoListener(PainelTabuladoListener listener) {
        painelTabuladoListeners.remove(listener);
    }

    public void selecionarAbaAnterior() {
        Component[] components = getAbaContainer().getComponents();
        for (int i = 0; i < components.length; i++) {
            if(components[i] instanceof Aba)
            {                
                if (components[i].isVisible()) {
                    ((Aba) components[i]).setVisible(false);
                    ((Aba) components[Math.max(0, i - 1)]).setVisible(true);
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
                    ((Aba) components[i]).setVisible(false);
                    ((Aba) components[Math.min(i + 1, components.length - 1)]).setVisible(true);
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

    @Override
    public void componentHidden(ComponentEvent ce) {
    }

    @Override
    public void componentMoved(ComponentEvent ce) {
    }

    @Override
    public void componentResized(ComponentEvent ce) {
    }
}

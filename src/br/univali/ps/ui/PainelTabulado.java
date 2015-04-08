package br.univali.ps.ui;

import br.univali.ps.ui.abas.Aba;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Fillipi Domingos Pelz
 */
public class PainelTabulado extends JTabbedPane implements ComponentListener
{
    private List<PainelTabuladoListener> painelTabuladoListeners;

    public PainelTabulado()
    {
        setTabLayoutPolicy(javax.swing.JTabbedPane.WRAP_TAB_LAYOUT);
        painelTabuladoListeners = new ArrayList<>();
        
    }    

  
    @Override
    public Component add(Component componente)
    {
        if (componente instanceof Aba)
        {
            Aba aba = (Aba)componente;
           try {
            aba.addComponentListener(this);
            aba.setPainelTabulado(this);
            super.add(aba);
            setTabComponentAt(indexOfComponent(aba), aba.getCabecalho());
            setSelectedComponent(aba);
           }
           catch (Exception ex)
           {
               ex.printStackTrace(System.err);
           }
            return aba;
        }
        return super.add(componente);
    }

    public void adicionaPainelTabuladoListener(PainelTabuladoListener listener)
    {
        if (!painelTabuladoListeners.contains(listener))
        {
            painelTabuladoListeners.add(listener);
        }
    }

    @Override
    public void componentShown(ComponentEvent ce)
    {
        Aba aba = (Aba) ce.getComponent();
        disparaAbaSelecionada(aba);
    }

    public void fecharTodasAbas(Class<? extends Aba> classe)
    {
        Component[] components = getComponents();
        
        for (int i = 0; i < components.length; i++)
        {
            if (components[i].getClass() == classe)
            {
                ((Aba) components[i]).fechar();
            }
        }
    }

    public Aba getAbaSelecionada()
    {
        if (getSelectedComponent() instanceof Aba)
        {
            return (Aba) getSelectedComponent();
        }
        else
        {
            return null;
        }
    }

    public List<Aba> getAbas(Class<? extends Aba> classe)
    {
        List<Aba> abas = new ArrayList<>();
        
        for (Component componente : getComponents())
        {
            if (componente.getClass() == classe)
            {
                abas.add((Aba) componente);
            }
        }
        return abas;
    }

    @Override
    public void remove(Component component)
    {
        if (component instanceof Aba)
        {
            component.removeComponentListener(this);
        }
        super.remove(component);
    }

    public void removePainelTabuladoListener(PainelTabuladoListener listener)
    {
        painelTabuladoListeners.remove(listener);
    }

    public void selecionarAbaAnterior()
    {
        setSelectedIndex(Math.max(0, getSelectedIndex() - 1));
    }

    public void selecionarProximaAba()
    {
        setSelectedIndex(Math.min(getSelectedIndex() + 1, getTabCount() - 1));
    }

    public boolean temAbaAberta(Class<? extends Aba> classe)
    {
        Component[] components = getComponents();
        
        for (int i = 0; i < components.length; i++)
        {
            if (components[i].getClass() == classe)
            {
                return true;
            }
        }
        
        return false;
    }

    private void disparaAbaSelecionada(Aba aba)
    {
        for (PainelTabuladoListener painelTabuladoListener : painelTabuladoListeners)
        {
            painelTabuladoListener.abaSelecionada(aba);
        }
    }
    
    
    @Override public void componentHidden(ComponentEvent ce) { }

    @Override public void componentMoved(ComponentEvent ce) { }

    @Override public void componentResized(ComponentEvent ce) { }
}

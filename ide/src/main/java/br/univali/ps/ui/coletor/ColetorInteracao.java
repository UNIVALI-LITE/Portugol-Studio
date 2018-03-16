package br.univali.ps.ui.coletor;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class ColetorInteracao
{

    private static final Logger LOGGER = Logger.getLogger(ColetorInteracao.class.getName());

    private static final ColetorInteracao instancia = new ColetorInteracao();

    private final ListenerMouse listenerMouse = new ListenerMouse();

    private final List<Interacao> interacoes = new ArrayList<>();

    private final Set<Component> componentesRegistrados = new HashSet<>();

    public static ColetorInteracao getInstancia()
    {
        return instancia;
    }

    private ColetorInteracao()
    {
        //
    }

    public void inspeciona(JFrame frame)
    {
        adicionarListeners(frame);
    }

    
    private void adicionarListeners(Component componente)
    {
    
        // evita adicionar o listener mais de uma vez no mesmo componente
        //System.out.println(componente.getName() + " - " + componentesRegistrados.contains(componente)+ " -|- " + (componente instanceof Container));
        if (!componentesRegistrados.contains(componente)) {
            componente.addMouseListener(listenerMouse);
            LOGGER.log(Level.INFO, "Adicionou listener em {0}", componente.getName());
            //System.out.println("Adicionou listner em "+componente.getName());
            componentesRegistrados.add(componente);
            
            if (componente instanceof Container) {

                Container container = (Container) componente;
                int filhos = container.getComponentCount();
                for (int i = 0; i < filhos; i++) {
                    //System.out.println("Filho de "+container.getName() + " - " + container.getComponent(i).getName());
                    adicionarListeners(container.getComponent(i));
                }

                container.addContainerListener(new ContainerAdapter()
                {
                    @Override
                    public void componentAdded(ContainerEvent e)
                    {
                        //System.out.println("Component " + e.getChild().getName() + " added to " + e.getContainer().getName());
                        adicionarListeners(e.getChild());
                    }

                });
            }
        }

        
    }

    private void adicionaInteracao(MouseEvent evento)
    {
        Component componente = (Component) evento.getSource();
        Interacao interacao = new Interacao(componente, evento.getPoint());
        interacoes.add(interacao);
        System.out.println(interacao.toString());
    }

    private class ListenerMouse extends MouseAdapter
    {

        @Override
        public void mouseClicked(MouseEvent e)
        {
            adicionaInteracao(e);
        }
    }

}

package br.univali.ps.ui;

import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.weblaf.PSWebTabbedPaneUI;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPaneUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.plaf.TabbedPaneUI;

/**
 *
 * @author Fillipi Domingos Pelz
 */
public class PainelTabulado extends JTabbedPane implements ComponentListener {

    private List<PainelTabuladoListener> painelTabuladoListeners;

    public PainelTabulado() {
        setTabLayoutPolicy(javax.swing.JTabbedPane.WRAP_TAB_LAYOUT);
        painelTabuladoListeners = new ArrayList<>();
        if (WeblafUtils.weblafEstaInstalado()) {
          setUI(criaUi());
        }
    }

    protected TabbedPaneUI criaUi() {
        //acabei usando a classe webTabbedPaneUI que eu baixei do githubda weblaf, essa classe tem métodos
        //que a classe que está no jar não possui (setTabBorderColor e setContentBorderColor)
        PSWebTabbedPaneUI ui = new PSWebTabbedPaneUI();
        ui.setTabRunIndent(5);
        ui.setTabbedPaneStyle(TabbedPaneStyle.attached);
        ui.setTabBorderColor(WeblafUtils.COR_DAS_BORDAS);
        //ui.setContentBorderColor(WeblafUtils.COR_DAS_BORDAS_II);
        
        return ui;
    }

    private class UiDosPaineisTabulados extends WebTabbedPaneUI {

        public UiDosPaineisTabulados() {
            super();
             
        }

        
        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected); //To change body of generated methods, choose Tools | Templates.
            GeneralPath path = createTabShape(tabPlacement, x, y, w, h, isSelected);
            ((Graphics2D) g).setColor(WeblafUtils.COR_DAS_BORDAS_DOS_PAINEIS_TABULADOS);
            ((Graphics2D) g).draw(path);
            
        }

        //código copiado do fonte do weblaf e modificado 
        private GeneralPath createTabShape(final int tabPlacement, int x, final int y, int w, final int h,
                final boolean isSelected) {
            // Fix for basic layouting of selected left-sided tab x coordinate
            final Insets insets = tabPane.getInsets();
            if (isSelected) {
                // todo fix for other tabPlacement values aswell
                if (tabPlacement == TOP && x == insets.left) {
                    x = x - 1;
                    w = w + 1;
                }
            }

            final int actualRound = getRound();
            final GeneralPath bgShape = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

            if (!isSelected) {
                return bgShape;
            }

            if (tabPlacement == JTabbedPane.TOP) {
                bgShape.moveTo(x, y + h + (-1));//getChange(tabShapeType));
                bgShape.lineTo(x, y + actualRound);
                bgShape.quadTo(x, y, x + actualRound, y);
                bgShape.lineTo(x + w - actualRound, y);
                bgShape.quadTo(x + w, y, x + w, y + actualRound);
                bgShape.lineTo(x + w, y + h + (-1));//getChange(tabShapeType));
            }
            return bgShape;
        }
    }

    @Override
    public Component add(Component componente) {
        if (componente instanceof Aba) {
            Aba aba = (Aba) componente;
            try {
                aba.addComponentListener(this);
                aba.setPainelTabulado(this);
                super.add(aba);
                setTabComponentAt(indexOfComponent(aba), aba.getCabecalho());
                setSelectedComponent(aba);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
            return aba;
        }
        return super.add(componente);
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
        Component[] components = getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i].getClass() == classe) {
                ((Aba) components[i]).fechar();
            }
        }
    }

    public Aba getAbaSelecionada() {
        if (getSelectedComponent() instanceof Aba) {
            return (Aba) getSelectedComponent();
        } else {
            return null;
        }
    }

    public List<Aba> getAbas(Class<? extends Aba> classe) {
        List<Aba> abas = new ArrayList<>();

        for (Component componente : getComponents()) {
            if (componente.getClass() == classe) {
                abas.add((Aba) componente);
            }
        }
        return abas;
    }

    @Override
    public void remove(Component component) {
        if (component instanceof Aba) {
            component.removeComponentListener(this);
        }
        super.remove(component);
    }

    public void removePainelTabuladoListener(PainelTabuladoListener listener) {
        painelTabuladoListeners.remove(listener);
    }

    public void selecionarAbaAnterior() {
        setSelectedIndex(Math.max(0, getSelectedIndex() - 1));
    }

    public void selecionarProximaAba() {
        setSelectedIndex(Math.min(getSelectedIndex() + 1, getTabCount() - 1));
    }

    public boolean temAbaAberta(Class<? extends Aba> classe) {
        Component[] components = getComponents();

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

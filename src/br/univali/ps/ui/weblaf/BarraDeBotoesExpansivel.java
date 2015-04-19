package br.univali.ps.ui.weblaf;

import br.univali.ps.ui.util.IconFactory;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.combobox.WebComboBoxStyle;
import com.alee.laf.menu.WebMenuBarUI;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author elieser
 */
public class BarraDeBotoesExpansivel extends JMenuBar {

    private final JMenu menu = new JMenu();

    public BarraDeBotoesExpansivel() {
        
        menu.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "gear_in.png"));
        add(menu);
        if (WebLookAndFeel.isInstalled()) {
            ((WebMenuBarUI) getUI()).setUndecorated(true);
        }
    }

    public void adicionaAcao(Acao acao) {
        JMenuItem item = new JMenuItem(acao.getAction());
        item.setIcon(acao.getIcone());
        menu.add(item);
    }
    
    public void adicionaMenu(JMenu submenu){
        this.menu.add(submenu);
    }

    public static Acao criaAcao(AbstractAction action, Icon icone) {
        return new Acao(icone, action);
    }

    public static class Acao {

        private Icon icone;
        private AbstractAction action;

        public Acao(Icon icone, AbstractAction action) {
            this.icone = icone;
            this.action = action;
        }

        public AbstractAction getAction() {
            return action;
        }

        Icon getIcone() {
            return icone;
        }

    }

}

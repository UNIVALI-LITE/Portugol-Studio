package br.univali.ps.ui.weblaf;

import br.univali.ps.ui.util.IconFactory;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.menu.WebMenuBarUI;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author elieser
 */
public class BarraDeBotoesExpansivel extends JMenuBar {

    private final JMenu menu = new JMenu();

    public BarraDeBotoesExpansivel() {

        menu.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "gear_in.png"));
        add(menu);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (WeblafUtils.weblafEstaInstalado()) {
            ((WebMenuBarUI) getUI()).setUndecorated(true);
        }
        
    }

    public void adicionaItemParaTamanhoDeFonte(String texto, Icon icone, Action acaoAumentar, Action acaoDiminuir){
        menu.add(new ItemDeMenuParaAlterarTamanho(texto, icone, acaoAumentar, acaoDiminuir));
    }
    
    private class ItemDeMenuParaAlterarTamanho extends JPanel {

        private final JLabel label;
        private final JButton botaoMais;
        private final JButton botaoMenos;

        public ItemDeMenuParaAlterarTamanho(String texto, Icon icone, Action acaoAumentar, Action acaoDiminuir) {
            super();
            setLayout(new FlowLayout(FlowLayout.LEFT, 7, 0));
            label = new JLabel(texto, SwingConstants.LEFT);
            label.setIcon(icone);
            botaoMais = new JButton(acaoAumentar);
            botaoMenos = new JButton(acaoDiminuir);
            add(label);

            
            if (WeblafUtils.weblafEstaInstalado()) {
                WebButtonGroup textGroup = new WebButtonGroup(true, botaoMais, botaoMenos);
                textGroup.setButtonsDrawFocus(false);
                add(textGroup);
            } else {
                add(botaoMais);
                add(botaoMenos);
            }
            //add(new JLabel(""), new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        }

    }

    public JMenu getMenu() {
        return menu;
    }

    public void adicionaAcao(Acao acao) {
        JMenuItem item = new JMenuItem(acao.getAction());
        item.setIcon(acao.getIcone());
        menu.add(item);
    }

    public void adicionaMenu(JMenu submenu) {
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

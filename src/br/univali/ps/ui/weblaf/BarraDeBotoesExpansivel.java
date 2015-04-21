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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

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

    public void adicionaGrupoDeItems(String texto, Icon icone, Action acoes[]) {
        menu.add(new ItemDeMenuParaGrupoDeAcoes(texto, icone, acoes));
    }

    private class ItemDeMenuParaGrupoDeAcoes extends JPanel {

        private final JLabel label;

        public ItemDeMenuParaGrupoDeAcoes(String texto, Icon icone, Action acoes[]) {
            super();
            setLayout(new FlowLayout(FlowLayout.LEFT, 7, 0));
            label = new JLabel(texto, SwingConstants.LEFT);
            label.setIcon(icone);
            add(label);

            JButton botoes[] = new JButton[acoes.length];
            for (int i = 0; i < acoes.length; i++) {
                botoes[i] = new JButton(acoes[i]);
            }
            WebButtonGroup textGroup = new WebButtonGroup(true, botoes);
            textGroup.setButtonsDrawFocus(false);
            add(textGroup);
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
        for (int i = 0; i < submenu.getItemCount(); i++) {
            submenu.getItem(i).addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent me) {
                    JMenuItem item =  (JMenuItem)me.getSource();
                    item.doClick();//dispara a acão do botão apenas passando o mouse em cima do item do menu
                }
                
            });
        }

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

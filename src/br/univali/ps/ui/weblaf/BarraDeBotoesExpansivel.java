package br.univali.ps.ui.weblaf;

import br.univali.ps.ui.util.IconFactory;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.button.WebButtonUI;
import com.alee.laf.menu.WebPopupMenuUI;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

/**
 *
 * @author elieser
 */
public class BarraDeBotoesExpansivel extends JButton {

    private final JPopupMenu menu;// = new JPopupMenu();

    public BarraDeBotoesExpansivel() {
        super();
        menu = new JPopupMenu();
        menu.setFont(getFont());
        //menu.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "gear_in.png"));
        //add(menu);
        setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "gear_in.png"));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (WeblafUtils.weblafEstaInstalado()) {
            ((WebButtonUI) getUI()).setUndecorated(false);
            ((WebButtonUI) getUI()).setLeftRightSpacing(0);
            ((WebButtonUI) getUI()).setRolloverDecoratedOnly(true);
            
            ((WebPopupMenuUI)menu.getUI()).setTransparency(1);
        }

        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                menu.show(BarraDeBotoesExpansivel.this, 0, getHeight());
            }
        });
    }

    public void adicionaGrupoDeItems(String texto, Icon icone, Action acoes[]) {
        adicionaGrupoDeItems(texto, icone, acoes, false);
    }

    public void adicionaGrupoDeItems(String texto, Icon icone, Action acoes[], boolean usarToggleButtons) {
        ItemDeMenuParaGrupoDeAcoes item = new ItemDeMenuParaGrupoDeAcoes(texto, icone, acoes, usarToggleButtons);
        menu.add(item);
        item.setFont(getFont());
    }

    public void adicionaSeparador() {
        menu.addSeparator();
    }

    public JPopupMenu getPopupMenu() {
        return menu;
    }

    private class ItemDeMenuParaGrupoDeAcoes extends JPanel {

        private final JLabel label;

        public ItemDeMenuParaGrupoDeAcoes(String texto, Icon icone, Action acoes[], boolean usarToggleButtons) {
            super(new FlowLayout(FlowLayout.LEFT, 7, 0));
            setOpaque(false);
            label = new JLabel(texto, SwingConstants.LEFT);
            label.setIcon(icone);
            label.setFont(getFont());
            add(label);
            if (acoes.length <= 3) {
                criaUmaLinhaDeBotoes(acoes);
            } else {
                criaColunasDeBotoes(acoes, usarToggleButtons);
            }
        }

        
        private void criaColunasDeBotoes(Action acoes[], boolean usarTogleButtons) {
            int colunas = acoes.length / 2;
            int indiceDaAcao = 0;
            WebButtonGroup gruposDasColunas[] = new WebButtonGroup[colunas];
            for (int c = 0; c < colunas; c++) {
                gruposDasColunas[c] = new WebButtonGroup(WebButtonGroup.VERTICAL, true, new JComponent[0]);//array vazio inicialmente
                gruposDasColunas[c].setButtonsDrawFocus(false);
                for (int i = 0; i < 2; i++) {//2 botões no máximo em cada coluna
                    if (indiceDaAcao < acoes.length) {
                        gruposDasColunas[c].add((usarTogleButtons) ? new JToggleButton(acoes[indiceDaAcao]) : new JButton(acoes[indiceDaAcao]));
                        indiceDaAcao++;
                    }
                }
            }
            WebButtonGroup grupo = new WebButtonGroup(WebButtonGroup.HORIZONTAL, true, gruposDasColunas);
            grupo.setButtonsDrawFocus(false);
            add(grupo);
        }

        private void criaUmaLinhaDeBotoes(Action acoes[]) {
            JButton botoes[] = new JButton[acoes.length];
            for (int i = 0; i < acoes.length; i++) {
                botoes[i] = new JButton(acoes[i]);
            }
            WebButtonGroup textGroup = new WebButtonGroup(true, botoes);

            textGroup.setButtonsDrawFocus(false);
            add(textGroup);
        }

    }

    public JComponent getCompomemtParaAdicionarDica() {
        //return menu;
        return this;
    }

    public void adicionaAcao(Action acao) {
        JMenuItem item = new JMenuItem(acao);
        item.setIcon((Icon) acao.getValue(Action.SMALL_ICON));
        item.setFont(getFont());
        menu.add(item);
    }

    public void adicionaMenu(JMenu submenu) {
        adicionaMenu(submenu, false);
    }

    public void adicionaMenu(JMenu submenu, boolean usaToggleButtons) {
        Action acoes[] = new Action[submenu.getItemCount()];
        for (int i = 0; i < submenu.getItemCount(); i++) {
            acoes[i] = submenu.getItem(i).getAction();
        }
        adicionaGrupoDeItems(submenu.getText(), submenu.getIcon(), acoes, usaToggleButtons);

//        this.menu.add(submenu);
//        for (int i = 0; i < submenu.getItemCount(); i++) {
//            submenu.getItem(i).addMouseListener(new MouseAdapter() {
//
//                @Override
//                public void mouseEntered(MouseEvent me) {
//                    JMenuItem item =  (JMenuItem)me.getSource();
//                    item.doClick();//dispara a acão do botão apenas passando o mouse em cima do item do menu
//                }
//                
//            });
//        }
    }

//    public static Acao criaAcao(AbstractAction action, Icon icone) {
//        return new Acao(icone, action);
//    }
//
//    public static class Acao {
//
//        private Icon icone;
//        private AbstractAction action;
//
//        public Acao(Icon icone, AbstractAction action) {
//            this.icone = icone;
//            this.action = action;
//        }
//
//        public AbstractAction getAction() {
//            return action;
//        }
//
//        Icon getIcone() {
//            return icone;
//        }
//
//    }
}

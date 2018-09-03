package br.univali.ps.ui.swing.weblaf;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.utils.IconFactory;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author elieser
 */
public final class BarraDeBotoesExpansivel extends WebButton
{
    private final WebPopupMenu menu;// = new JPopupMenu();

    public BarraDeBotoesExpansivel()
    {
        super();
        menu = new WebPopupMenu();
        menu.setFont(getFont());
        menu.setBorderColor(ColorController.FUNDO_ESCURO);
        menu.setCornerWidth(0);
        menu.setMargin(5);
        menu.setRound(0);
        menu.setShadeWidth(0);
        menu.setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
        menu.setForeground(ColorController.COR_LETRA);
        //menu.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "gear_in.png"));
        //add(menu);
        setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "gear_in.png"));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addActionListener((ActionEvent ae) -> 
        {
            if (!menu.isVisible())
            {
                
                menu.show(BarraDeBotoesExpansivel.this, 0, getHeight());
            }
            else
            {
                menu.setVisible(false);
            }
        });
    }

    public void adicionaGrupoDeItems(String texto, Icon icone, Action acoes[])
    {
        adicionaGrupoDeItems(texto, icone, acoes, false);
    }

    public void adicionaGrupoDeItems(String texto, Icon icone, Action acoes[], boolean usarToggleButtons)
    {
        ItemDeMenuParaGrupoDeAcoes item = new ItemDeMenuParaGrupoDeAcoes(texto, icone, acoes, usarToggleButtons);
        menu.add(item);
        item.setFont(getFont());
    }

    public void adicionaSeparador()
    {
        JPanel jp = new JPanel();
        jp.setBackground(ColorController.COR_DESTAQUE);
        jp.setPreferredSize(new Dimension(200, 2));
        menu.add(jp);
    }

    public JPopupMenu getPopupMenu()
    {
        return menu;
    }

    public void adicionarComponente(JComponent component)
    {
        menu.add(component);
    }

    private final class ItemDeMenuParaGrupoDeAcoes extends JPanel
    {
        private final JLabel label;

        public ItemDeMenuParaGrupoDeAcoes(String texto, Icon icone, Action acoes[], boolean usarToggleButtons)
        {
            super(new FlowLayout(FlowLayout.LEFT, 7, 0));
            setOpaque(false);

            label = new JLabel(texto, SwingConstants.LEFT);
            label.setName(texto);
            label.setIcon(icone);
            label.setFont(getFont());
            setBorder(new EmptyBorder(5,0,5,0));
            label.setForeground(ColorController.COR_LETRA);

            add(label);

            if (acoes.length <= 3)
            {
                criaUmaLinhaDeBotoes(acoes);
            }
            else
            {
                criaColunasDeBotoes(acoes, usarToggleButtons);
            }
        }

        private void criaColunasDeBotoes(Action acoes[], boolean usarTogleButtons)
        {
            int colunas = 2;
            int indiceDaAcao = 0;

            WebButtonGroup gruposDasColunas[] = new WebButtonGroup[colunas];

            for (int c = 0; c < 2; c++)
            {
                gruposDasColunas[c] = new WebButtonGroup(WebButtonGroup.VERTICAL, true, new JComponent[0]);//array vazio inicialmente
                gruposDasColunas[c].setButtonsDrawFocus(false);

                int linhas = acoes.length%2==0 ? (acoes.length / 2) : (acoes.length / 2)+1;
                for (int i = 0; i < linhas; i++)
                {
                    if (indiceDaAcao < acoes.length)
                    {
                        WebToggleButton botao = new WebToggleButton(acoes[indiceDaAcao]);
                        WeblafUtils.configurarToogleBotao(botao, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5);
                        gruposDasColunas[c].add(botao);
                        indiceDaAcao++;
                    }
                }
            }

            WebButtonGroup grupo = new WebButtonGroup(WebButtonGroup.HORIZONTAL, true, gruposDasColunas);
            grupo.setName(label.getName());
            grupo.setButtonsDrawFocus(false);

            add(grupo);
        }

        private void criaUmaLinhaDeBotoes(Action acoes[])
        {
            JButton botoes[] = new WebButton[acoes.length];

            for (int i = 0; i < acoes.length; i++)
            {
                botoes[i] = new WebButton(acoes[i]);
                WeblafUtils.configurarBotao((WebButton) botoes[i], ColorController.COR_DESTAQUE, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5);
            }

            WebButtonGroup textGroup = new WebButtonGroup(true, botoes);
            textGroup.setButtonsDrawFocus(false);
            add(textGroup);
        }
    }

    public JComponent getCompomemtParaAdicionarDica()
    {
        return this;
    }

    public void adicionaAcao(Action acao)
    {
        WebMenuItem item = new WebMenuItem(acao);
        acao.putValue("MenuItem", item);

        item.setIcon((Icon) acao.getValue(Action.SMALL_ICON));
        item.setFont(getFont());
        item.setForeground(ColorController.COR_LETRA);
        item.setAcceleratorFg(ColorController.COR_DESTAQUE);
        item.setAcceleratorDisabledFg(ColorController.COR_DESTAQUE);
        item.setAcceleratorBg(ColorController.FUNDO_ESCURO);
        item.setSelectedTopBg(ColorController.COR_DESTAQUE);
        item.setSelectedBottomBg(ColorController.COR_DESTAQUE);
        menu.add(item);
    }

    public void adicionaMenu(JMenu submenu)
    {
        adicionaMenu(submenu, false);
    }

    public void adicionaMenu(JMenu submenu, boolean usaToggleButtons)
    {
        Action acoes[] = new Action[submenu.getItemCount()];

        for (int i = 0; i < submenu.getItemCount(); i++)
        {
            acoes[i] = submenu.getItem(i).getAction();
        }

        adicionaGrupoDeItems(submenu.getText(), submenu.getIcon(), acoes, usaToggleButtons);
    }
}

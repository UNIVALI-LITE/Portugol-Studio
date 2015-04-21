package br.univali.ps.ui.abas;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.GerenciadorTemas;
import static br.univali.ps.ui.abas.AbaCodigoFonte.VALOR_INCREMENTO_FONTE;
import br.univali.ps.ui.editor.Editor;
import br.univali.ps.ui.util.IconFactory;
import br.univali.ps.ui.weblaf.BarraDeBotoesExpansivel;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;

/**
 *
 * @author elieser
 */
public class FabricaDeAcoesDoEditor {

    public static BarraDeBotoesExpansivel.Acao criaAcaoExpandirEditor(final JSplitPane splitPaneEditorArvore, final JSplitPane splitPaneEditorConsole) {
        AbstractAction acaoExpandir = new AbstractAction("Expandir/Restaurar") {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean estaContraido = splitPaneEditorArvore.getDividerLocation() < splitPaneEditorArvore.getMaximumDividerLocation();
                if( estaContraido ){
                    splitPaneEditorArvore.setDividerLocation(1.0);
                    splitPaneEditorConsole.setDividerLocation(1.0);
                }
                else{
                    splitPaneEditorArvore.setDividerLocation(-1);
                    splitPaneEditorConsole.setDividerLocation(-1);
                }
            }
        };
        return BarraDeBotoesExpansivel.criaAcao(acaoExpandir, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "expandir_componente.png"));
    }
    
  
    public static BarraDeBotoesExpansivel.Acao criaAcaoPesquisarSubstituir(final FindDialog dialogoPesquisar, final ReplaceDialog dialogoSubstituir, final ActionMap actionMap, final InputMap inputMap) {

        String nome = "Pesquisar e substituir";

        AbstractAction acao = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialogoPesquisar.isVisible()) {
                    dialogoPesquisar.setVisible(false);
                }

                dialogoSubstituir.setVisible(true);
            }
        };
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK);
        acao.putValue(Action.ACCELERATOR_KEY, atalho);
        actionMap.put(nome, acao);
        inputMap.put(atalho, nome);
        return BarraDeBotoesExpansivel.criaAcao(acao, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "find.png"));
    }

    public static BarraDeBotoesExpansivel.Acao criaAcaoCentralizarCodigoFonte(final ActionMap actionMap, final InputMap inputMap) {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_PAUSE, InputEvent.SHIFT_DOWN_MASK);
        String nome = "Centralizar código fonte";

        AbstractAction acaoCentralizarCodigoFonte = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Configuracoes configuracoes = Configuracoes.getInstancia();
                configuracoes.alterarCentralizarCondigoFonte();
            }
        };

        acaoCentralizarCodigoFonte.putValue(Action.ACCELERATOR_KEY, atalho);
        

        actionMap.put(nome, acaoCentralizarCodigoFonte);
        inputMap.put(atalho, nome);
        return BarraDeBotoesExpansivel.criaAcao(acaoCentralizarCodigoFonte, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "centralizar_codigo.png"));
    }

    public static JMenu criaMenuDosTemas(GerenciadorTemas gerenciadorTemas, final Editor editor) {

        final JMenu menu = new JMenu("Listar Temas");
        menu.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "temas.png"));

        AbstractAction acaoAplicarTema = new AbstractAction("Aplicar tema", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "temas.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem itemSelecionado = (JCheckBoxMenuItem) e.getSource();
                String tema = itemSelecionado.getText();
                editor.aplicarTema(tema);
            }
        };

        for (String tema : gerenciadorTemas.listarTemas()) {
            JCheckBoxMenuItem itemMenu = new JCheckBoxMenuItem();
            itemMenu.setAction(acaoAplicarTema);
            itemMenu.setText(tema);
            itemMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            menu.add(itemMenu);
        }
        return menu;
    }
}

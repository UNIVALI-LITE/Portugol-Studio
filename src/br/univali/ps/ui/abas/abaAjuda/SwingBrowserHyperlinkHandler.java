/**
 * SwingBrowserHyperlinkHandler.java (c) Peter Bielik and Radek Burget,
 * 2011-2012
 *
 * SwingBox is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * SwingBox is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SwingBox. If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on 17.7.2012, 13:53:56 by burgetr
 */
package br.univali.ps.ui.abas.abaAjuda;

import br.univali.portugol.ajuda.Ajuda;
import br.univali.portugol.ajuda.ErroCaminhoTopicoInvalido;
import br.univali.portugol.ajuda.ErroTopicoNaoEncontrado;
import br.univali.portugol.ajuda.Topico;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.util.FileHandle;
import java.awt.Cursor;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.fit.cssbox.swingbox.demo.SwingBrowser;

import org.fit.cssbox.swingbox.util.DefaultHyperlinkHandler;

/**
 * This hyperlink handler implements the demo browser behaviour when a link is
 * clicked.
 *
 * @author burgetr
 */
public class SwingBrowserHyperlinkHandler extends DefaultHyperlinkHandler {

    private final Ajuda ajuda;
    private final JTree arvore;

    public SwingBrowserHyperlinkHandler(Ajuda ajuda, JTree arvore)
    {
        this.ajuda = ajuda;
        this.arvore = arvore;
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent evt)
    {
        if (evt.getEventType() ==  HyperlinkEvent.EventType.ACTIVATED)
        {
            try
            {
                final JComponent componente = (JComponent) evt.getSource();
                
                SwingUtilities.invokeLater(() -> { componente.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); });
                
                String url = evt.getURL().toString();
                
                url = url.replace(evt.getURL().getProtocol(), "");
                url = url.replace("\\", "/");
                
                if (url.charAt(0) == ':')
                {
                    url = url.substring(1);
                }
                
                if (url.charAt(0) == '/')
                {
                    url = url.substring(1);
                }
                
                File arquivo = new File(url);
                String codigoFonte = FileHandle.open(arquivo);
                AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
                
                abaCodigoFonte.setCodigoFonte(codigoFonte, null, true);
                abaCodigoFonte.getEditor().getPortugolDocumento().setChanged(true);
                
                PortugolStudio.getInstancia().getTelaPrincipal().getPainelTabulado().add(abaCodigoFonte);
            }
            catch (Exception ex)
            {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
            }
        }
    }

    @Override
    protected void loadPage(JEditorPane pane, HyperlinkEvent evt)
    {
        Pattern padraoJavaScript = Pattern.compile("^javascript:exibirConteudo\\('(.+)'\\)$");

        try
        {
            String diretorioTopico = evt.getDescription();
            Matcher padraoJavaScriptMatcher = padraoJavaScript.matcher(diretorioTopico);

            if (padraoJavaScriptMatcher.find())
            {
                diretorioTopico = padraoJavaScriptMatcher.group(1);
            }

            Topico topico = ajuda.obterTopicoPeloDiretorio(diretorioTopico);

            TreePath caminhoTopicoNaArvore = localizarTopicoNaArvore((DefaultMutableTreeNode) arvore.getModel().getRoot(), topico);

            if (caminhoTopicoNaArvore != null)
            {
                arvore.setExpandsSelectedPaths(true);
                arvore.setSelectionPath(caminhoTopicoNaArvore);
            }
        } catch (ErroCaminhoTopicoInvalido | ErroTopicoNaoEncontrado ex)
        {
            Logger.getLogger(AbaAjuda.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private TreePath localizarTopicoNaArvore(DefaultMutableTreeNode raiz, Topico topico)
    {
        Enumeration<DefaultMutableTreeNode> e = raiz.depthFirstEnumeration();
        while (e.hasMoreElements())
        {
            DefaultMutableTreeNode no = e.nextElement();
            if (no.getUserObject().equals(topico))
            {
                return new TreePath(no.getPath());
            }
        }
        return null;
    }

}

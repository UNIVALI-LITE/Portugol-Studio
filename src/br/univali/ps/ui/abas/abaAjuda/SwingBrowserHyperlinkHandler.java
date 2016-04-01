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
import br.univali.ps.ui.utils.FileHandle;
import java.awt.Cursor;
import java.io.File;
import java.net.URISyntaxException;
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
public class SwingBrowserHyperlinkHandler extends DefaultHyperlinkHandler
{

    private final Ajuda ajuda;
    private final JTree arvore;

    private enum Tipo
    {

        PORLOCAL, HTMLLOCAL
    }

    public SwingBrowserHyperlinkHandler(Ajuda ajuda, JTree arvore)
    {
        this.ajuda = ajuda;
        this.arvore = arvore;
    }

    private Tipo decodificarTipo(HyperlinkEvent evt) throws TipoUrlInvalidoException
    {
        String protocol = evt.getURL().getProtocol();
        switch (protocol)
        {
            case "file":
                String tipoArquivo = evt.getURL().getPath();
                tipoArquivo = tipoArquivo.substring(tipoArquivo.lastIndexOf(".") + 1);
                switch (tipoArquivo)
                {
                    case "por":
                        return Tipo.PORLOCAL;
                    case "html":
                        return Tipo.HTMLLOCAL;
                    default:
                        throw new TipoUrlInvalidoException(tipoArquivo);
                }
            default:
                throw new TipoUrlInvalidoException(protocol);
        }
    }

    private void tratarPorLocal(HyperlinkEvent evt) throws Exception
    {
        final JComponent componente = (JComponent) evt.getSource();

        File arquivo = new File(evt.getURL().toURI());
        String codigoFonte = FileHandle.open(arquivo);
        AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();

        abaCodigoFonte.setCodigoFonte(codigoFonte, null, true);
        abaCodigoFonte.getEditor().getPortugolDocumento().setChanged(true);

        PortugolStudio.getInstancia().getTelaPrincipal().getPainelTabulado().add(abaCodigoFonte);
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent evt)
    {
        if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
        {
            //Age caso o usuário clique no link.
            String url = evt.getURL().getPath();
            try
            {
                switch (decodificarTipo(evt))
                {
                    case PORLOCAL:
                        tratarPorLocal(evt);
                    case HTMLLOCAL:
                        loadPage((JEditorPane) evt.getSource(), evt);
                }

            } catch (TipoUrlInvalidoException ex)
            {
                Logger.getLogger(SwingBrowserHyperlinkHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex)
            {
                Logger.getLogger(SwingBrowserHyperlinkHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (evt.getEventType() == HyperlinkEvent.EventType.ENTERED)
        {
            //Se o usuário só ficar em cima do link, muda a forma do mouse
            SwingUtilities.invokeLater(() ->
            {
                ((JComponent) evt.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            });
        } else if (evt.getEventType() == HyperlinkEvent.EventType.EXITED)
        {
            //Se o usuário sair de cima do link, volta o mouse ao normal
            SwingUtilities.invokeLater(() ->
            {
                ((JComponent) evt.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            });
        }
    }

    @Override
    protected void loadPage(JEditorPane pane, HyperlinkEvent evt)
    {
        try
        {
            String diretorioTopico = evt.getURL().getPath();

            Topico topico = ajuda.obterTopicoPeloDiretorio(diretorioTopico.substring(diretorioTopico.lastIndexOf("topicos")));

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

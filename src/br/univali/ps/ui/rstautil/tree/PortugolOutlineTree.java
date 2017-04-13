package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.ps.ui.abas.AbaCodigoFonte;

import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.tree.filters.PortugolASTFilter;
import br.univali.ps.ui.rstautil.tree.filters.PortugolASTFilterListener;
import br.univali.ps.ui.swing.weblaf.PSTreeUI;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import com.alee.laf.tree.WebTreeUI;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class PortugolOutlineTree extends JTree
{

    private static final Logger LOGGER = Logger.getLogger(PortugolOutlineTree.class.getName());

    private final PortugolASTFilter filter = new PortugolASTFilter();

    private DefaultTreeModel model;
    private Programa programa = null;
    
    private final FilterListener listenerFiltro = new FilterListener();

    private final Timer timerCriacaoDaArvore;

    private static final int TAMANHO_ARVORE_GRANDE = 250; // árvores com mais de 250 nós serão consideradas grandes demais para serem totalmente expandidas

    public PortugolOutlineTree()
    {
        configurarAparencia();
        configurarDragAndDrop();
        configurarModel();

        timerCriacaoDaArvore = new Timer(50, (ActionEvent e) ->
        {
            doBuildTree(); // a árvore só é criada se nenhuma outra ocorrência do timer acontecer antes de 50 ms. Assim evita-se que várias chamadas sucessivas para buildTree() causando lentidão na montagem da árvore.
        });
        timerCriacaoDaArvore.setRepeats(false);
    }

    private void configurarAparencia()
    {
        setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        setRowHeight(0);
        setRootVisible(false);
        setCellRenderer(new AstTreeCellRenderer());

        if (WeblafUtils.weblafEstaInstalado())
        {
            ((WebTreeUI) getUI()).setRightChildIndent(3);
        }

        setUI(new PSTreeUI());
    }

    private void configurarDragAndDrop()
    {
        setDragEnabled(true);
        setTransferHandler(new DragAndDropDeSimbolos());
    }

    private void configurarModel()
    {
        model = new DefaultTreeModel(new DefaultMutableTreeNode("Nothing"));
        setModel(model);
    }

    public void observar(RSyntaxTextArea textArea)
    {
        if (textArea != null)
        {
            PortugolParser.getParser(textArea).addPropertyChangeListener(PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO, new ObservadorCompilacaoPrograma());
        }
    }

    private int getQuantidadeDeNos(TreeModel model)
    {
        return getQuantidadeDeNos(model, model.getRoot());
    }

    private int getQuantidadeDeNos(TreeModel model, Object node)
    {
        int count = 1;
        int nChildren = model.getChildCount(node);
        for (int i = 0; i < nChildren; i++)
        {
            count += getQuantidadeDeNos(model, model.getChild(node, i));
        }
        return count;
    }

    private void refresh()
    {
        if (model.getRoot() instanceof SourceTreeNode)
        {
            model.reload();
            if (arvoreEhGrande())
            {
                expandeSomentePrimeiroNivel();
            } else
            {
                expandeTodosOsNiveis();
            }
        }
    }

    private boolean arvoreEhGrande()
    {
        return getQuantidadeDeNos(model) >= TAMANHO_ARVORE_GRANDE;
    }

    private void expandeTodosOsNiveis()
    {
        RstaTreeUtils.expandAll(PortugolOutlineTree.this, true);
    }

    private void expandeSomentePrimeiroNivel()
    {
        DefaultMutableTreeNode noAtual = ((SourceTreeNode) model.getRoot()).getNextNode();
        do
        {
            if (noAtual.getLevel() == 1)
            {
                expandPath(new TreePath(noAtual.getPath()));
            }
            noAtual = noAtual.getNextNode();
        } while (noAtual != null);
    }

    public PortugolASTFilter getFilter()
    {
        return filter;
    }

    private final class ObservadorCompilacaoPrograma implements PropertyChangeListener
    {

        @Override
        public void propertyChange(PropertyChangeEvent evento)
        {
            String name = evento.getPropertyName();

            programa = (Programa) evento.getNewValue();

            if (RSyntaxTextArea.SYNTAX_STYLE_PROPERTY.equals(name) || PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO.equals(name))
            {
                filter.addListener(listenerFiltro);
                buildTree();
            }
        }
    }

    public void desinstalaListenersDosFiltros() 
    {
        filter.removeListener(listenerFiltro);
    }
    
    private void doBuildTree()
    {
        if (programa != null)
        {
            final AstOutlineTreeFactory factory = new AstOutlineTreeFactory();
            final SourceTreeNode root;

            if (filter != null)
            {
                root = factory.createFilteredTree(programa.getArvoreSintaticaAbstrata(), filter);
            } else
            {
                root = factory.createTree(programa.getArvoreSintaticaAbstrata());
            }

            SwingUtilities.invokeLater(() ->
            {
                model.setRoot(root);
                refresh();
            });
        }
    }

    private void buildTree()
    {
        timerCriacaoDaArvore.restart(); // reinicia o timer toda vez que algum código tenta recriar a árvore.
    }

    private final class DragAndDropDeSimbolos extends TransferHandler
    {

        @Override
        public boolean canImport(TransferHandler.TransferSupport ts)
        {
            return false;
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport ts)
        {
            // A árvore não aceita drop            
            return false;
        }

        @Override
        public Point getDragImageOffset()
        {
            Point p = super.getDragImageOffset();

            // Faz com que a imagem fique ao lado direito do ícone de arrastar e soltar
            p.translate(-20, 0);

            return p;
        }

        @Override
        public Image getDragImage()
        {
            if (getSelectionPaths() == null)
            {
                return null;
            }

            List<String> nomesDosSimbolos = new ArrayList<>();
            FontMetrics metrics = getFontMetrics(getFont());
            int larguraDaImagem = 0;

            for (TreePath path : getSelectionPaths())
            {
                try
                {
                    DefaultMutableTreeNode selecionado = (DefaultMutableTreeNode) path.getLastPathComponent();

                    if (selecionado.isLeaf())
                    {
                        NoDeclaracao noDeclaracao = (NoDeclaracao) selecionado.getUserObject();
                        String nomeDoSimbolo = noDeclaracao.getNome();
                        int larguraDoNomeDoSimbolo = metrics.stringWidth(nomeDoSimbolo);

                        nomesDosSimbolos.add(nomeDoSimbolo);

                        if (larguraDoNomeDoSimbolo > larguraDaImagem)
                        {
                            larguraDaImagem = larguraDoNomeDoSimbolo;
                        }
                    }
                } catch (Exception e)
                {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }

            if (!nomesDosSimbolos.isEmpty())
            {
                final int MARGEM = 5;
                int alturaDaImagem = metrics.getHeight() * nomesDosSimbolos.size();

                // Evita que variáveis com apenas uma letra no nome sejam desenhadas muito pequenas
                larguraDaImagem = MARGEM + Math.max(larguraDaImagem, 50) + MARGEM;

                BufferedImage imagem = new BufferedImage(larguraDaImagem, alturaDaImagem, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) imagem.getGraphics();

                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, larguraDaImagem, alturaDaImagem);
                g.setFont(getFont());

                for (int i = 0; i < nomesDosSimbolos.size(); ++i)
                {
                    int larguraDoNome = metrics.stringWidth(nomesDosSimbolos.get(i));
                    int xDaString = (nomesDosSimbolos.size() == 1) ? (larguraDaImagem / 2 - larguraDoNome / 2) : MARGEM;//centraliza caso tenha apenas um símbolo
                    int yDaString = metrics.getHeight() * (i + 1) - metrics.getDescent();

                    g.setColor(Color.DARK_GRAY);
                    g.drawString(nomesDosSimbolos.get(i), xDaString, yDaString);
                }

                return imagem;
            }

            return null;
        }

        @Override
        protected Transferable createTransferable(JComponent jc)
        {
            if (getSelectionPaths() != null)
            {
                List<NoDeclaracao> nosSelecionados = new ArrayList<>();
                TreePath paths[] = getSelectionPaths();

                for (TreePath caminhoSelecionado : paths)
                {
                    Object componentSelectionado = caminhoSelecionado.getLastPathComponent();

                    if (componentSelectionado != null && componentSelectionado instanceof DefaultMutableTreeNode)
                    {
                        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) caminhoSelecionado.getLastPathComponent();

                        if (treeNode.isLeaf())
                        {
                            try
                            {
                                NoDeclaracao noDeclaracao = (NoDeclaracao) treeNode.getUserObject();
                                nosSelecionados.add(noDeclaracao);
                            } catch (Exception e)
                            {
                                // Caso o nó não seja um NoDeclaracao
                                LOGGER.log(Level.WARNING, e.getMessage(), e);
                            }
                        }
                    }
                }

                return new AbaCodigoFonte.NoTransferable(nosSelecionados);
            }

            return null;
        }

        @Override
        public int getSourceActions(JComponent jc)
        {
            return DnDConstants.ACTION_COPY;
        }
    }

    private final class FilterListener implements PortugolASTFilterListener
    {

        @Override
        public void filterChanged()
        {
            buildTree();
        }
    }
}

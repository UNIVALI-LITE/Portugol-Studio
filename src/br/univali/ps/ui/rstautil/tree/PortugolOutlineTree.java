/*
 * 03/21/2010
 *
 * Copyright (C) 2010 Robert Futrell
 * robert_futrell at users.sourceforge.net
 * http://fifesoft.com/rsyntaxtextarea
 *
 * This library is distributed under a modified BSD license.  See the included
 * RSTALanguageSupport.License.txt file for details.
 */
package br.univali.ps.ui.rstautil.tree;

import br.univali.ps.ui.rstautil.ComparadorNos;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.simbolos.Funcao;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Ponteiro;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;
import br.univali.ps.ui.abas.AbaCodigoFonte;

import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.weblaf.PSTreeUI;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.laf.tree.WebTreeUI;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.text.Element;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * A tree view showing the outline of Java source, similar to the "Outline" view
 * in the Eclipse JDT. It also uses Eclipse's icons, just like the rest of this
 * code completion library.<p>
 *
 * You can get this tree automatically updating in response to edits in an
 * <code>RSyntaxTextArea</code> with {@link JavaLanguageSupport} installed by
 * calling {@link #observar(RSyntaxTextArea)}. Note that an instance of this
 * class can only listen to a single editor at a time, so if your application
 * contains multiple instances of RSyntaxTextArea, you'll either need a separate
 * <code>JavaOutlineTree</code> for each one, or call <code>uninstall()</code>
 * and <code>listenTo(RSyntaxTextArea)</code> each time a new RSTA receives
 * focus.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class PortugolOutlineTree extends AbstractTree implements ObservadorExecucao
{

    private DefaultTreeModel model;
    private PortugolParser parser;
    private Listener listener;
    private boolean atualizacaoHabilitada = false;

    //usei este map para guardar os últimos valores dos nós da JTree. Quando
    //um programa é executado o valor de um nó pode mudar muitas vezes durante
    //a execução. Este map guarda apenas o valor da última modificação. Este valor
    //é utilizado para atualizar o nó em dois momentos: quando a execução termina
    //e todos os valores da árvore são atualizados e quando a execução fica pausada 
    //em um ponto de parada, pois nesse caso também é importante ver o estado atual das
    //variáveis.
    private Map<PortugolTreeNode, Object> valoresDosNos = new HashMap<>();

    /**
     * Constructor. The tree created will not have its elements sorted
     * alphabetically.
     */
    public PortugolOutlineTree()
    {
        this(false);

    }

    public PortugolOutlineTree(int a)
    {
        this(false);
    }

    public void setStatusDaAtualizacaoDosNos(boolean atualizaoAtivada)
    {
        //this.atualizacaoHabilitada = atualizaoAtivada;
        //a atualização dos valores da árvore está desabilitada, a árvore está mostrando
        //apenas a estrutura
    }

    /**
     * Constructor.
     *
     * @param sorted Whether the tree should sort its elements alphabetically.
     * Note that outline trees will likely group nodes by type before sorting
     * (i.e. methods will be sorted in one group, fields in another group,
     * etc.).
     */
    public PortugolOutlineTree(boolean sorted)
    {
//        setSorted(sorted);
        setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        setRowHeight(0);
        setRootVisible(false);
        setCellRenderer(new AstTreeCellRenderer());
        model = new DefaultTreeModel(new DefaultMutableTreeNode("Nothing"));
        setModel(model);
        listener = new Listener();
        setDragEnabled(true);
        setTransferHandler(new TreeTransferHandler());
        
        if (WeblafUtils.weblafEstaInstalado())
        {
            ((WebTreeUI) getUI()).setRightChildIndent(3);
        }
        setUI(new PSTreeUI());
    }
    

    private class TreeTransferHandler extends TransferHandler
    {

        @Override
        public boolean canImport(TransferHandler.TransferSupport ts)
        {
            return false;
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport ts)
        {
            return false;//a árvore não aceita drop
        }

        @Override
        public Point getDragImageOffset()
        {
            Point p = super.getDragImageOffset(); //To change body of generated methods, choose Tools | Templates.
            p.translate(-20, 0);//faz com que a imagem fique ao lado direito do ícone de arrastar e soltar
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
                        nomesDosSimbolos.add(nomeDoSimbolo);
                        int larguraDoNomeDoSimbolo = metrics.stringWidth(nomeDoSimbolo);
                        if (larguraDoNomeDoSimbolo > larguraDaImagem)
                        {
                            larguraDaImagem = larguraDoNomeDoSimbolo;
                        }
                    }
                }
                catch (Exception e)
                {
                    //
                }
            }
            if (!nomesDosSimbolos.isEmpty())
            {
                final int MARGEM = 5;
                int alturaDaImagem = metrics.getHeight() * nomesDosSimbolos.size();
                larguraDaImagem = MARGEM + Math.max(larguraDaImagem, 50) + MARGEM;//evita que variáveis com apenas uma letra no nome sejam desenhadas muito pequenas
                BufferedImage imagem = new BufferedImage(larguraDaImagem, alturaDaImagem, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) imagem.getGraphics();
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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
                            }
                            catch (Exception e)
                            {
                                //caso o nó não seja um NoDeclaracao
                                Logger.getLogger(AbaCodigoFonte.class.getName()).log(Level.WARNING, e.getMessage(), e);
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

    AstOutlineTreeFactory astFactory = new AstOutlineTreeFactory();

    /**
     * Refreshes this tree.
     *
     * @param ast The parsed compilation unit. If this is <code>null</code> then
     * the tree is cleared.
     */
    private void update(Programa programa)
    {
        if (programa == null)
        {
            return;
        }

        final SourceTreeNode root = astFactory.createTree(programa.getArvoreSintaticaAbstrata());
        SwingUtilities.invokeLater(() ->
        {
            model.setRoot(root);
            refresh();

            model.reload();
            RstaTreeUtils.expandAll(PortugolOutlineTree.this, true);
        });

    }

    private void gotoElementAtPath(TreePath path)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        Object obj = node.getUserObject();
        TrechoCodigoFonte trechoCodigoFonte = null;

        if (obj instanceof NoDeclaracao)
        {
            trechoCodigoFonte = ((NoDeclaracao) obj).getTrechoCodigoFonteNome();
        }

        if (trechoCodigoFonte != null)
        {
            int linha = trechoCodigoFonte.getLinha() - 1;
            Element elem = textArea.getDocument().getDefaultRootElement().getElement(linha);
            int offs = elem.getStartOffset() + trechoCodigoFonte.getColuna();
            int end = offs + trechoCodigoFonte.getTamanhoTexto();

            System.out.println("Linha: " + linha);
            
            textArea.getFoldManager().ensureOffsetNotInClosedFold(offs);
            textArea.select(offs, end);
            textArea.requestFocusInWindow();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public boolean gotoSelectedElement()
    {
        TreePath path = getLeadSelectionPath();//e.getNewLeadSelectionPath();
        if (path != null)
        {
            gotoElementAtPath(path);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param textArea
     */
    @Override
    public void observar(RSyntaxTextArea textArea)
    {
        if (this.textArea != null)
        {
            uninstall();
        }

        // Nothing new to listen to
        if (textArea == null)
        {
            return;
        }

        // Listen for future language changes in the text editor
        this.textArea = textArea;
        PortugolParser.getParser(textArea).addPropertyChangeListener(PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uninstall()
    {
        if (parser != null)
        {
            parser.removePropertyChangeListener(PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO, listener);
            parser = null;
        }

        if (textArea != null)
        {
            textArea.removePropertyChangeListener(RSyntaxTextArea.SYNTAX_STYLE_PROPERTY, listener);
            textArea = null;
        }
    }

    ComparadorNos comparador = new ComparadorNos();

    @Override
    public void execucaoIniciada(Programa programa)
    {

    }

    @Override
    public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao)
    {
        valoresDosNos.clear();
    }

    @Override
    public void highlightLinha(int linha)
    {

    }

    @Override
    public void highlightDetalhadoAtual(int linha, int coluna, int tamanho)
    {
    }

    @Override
    public void simbolosAlterados(final List<Simbolo> simbolos)
    {
        if (!atualizacaoHabilitada)
        {
            return;
        }
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                SourceTreeNode root = (SourceTreeNode) model.getRoot();
                limparModificado(root);
                for (Simbolo simbolo : simbolos)
                {
                    if (!(simbolo instanceof Funcao))
                    {
                        PortugolTreeNode node = getPortugolTreeNode(root, simbolo);
                        valoresDosNos.put(node, getValorDoSimbolo(simbolo));
                        if (node != null)
                        {
                            modificar(simbolo, node);
                            model.nodeChanged(node);
                        }
                    }
                }
            }
        });

    }

    @Override
    public void simboloRemovido(final Simbolo simbolo)
    {
        if (!atualizacaoHabilitada)
        {
            return;
        }
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                SourceTreeNode root = (SourceTreeNode) model.getRoot();
                limparModificado(root);
                if (!(simbolo instanceof Funcao))
                {
                    PortugolTreeNode node = getPortugolTreeNode(root, simbolo);
                    valoresDosNos.put(node, getValorDoSimbolo(simbolo));
                    if (node != null)
                    {
                        remover(node, simbolo);
                        node.setDeclarado(false);
                        model.nodeChanged(node);
                    }
                }
            }
        });

    }

    @Override
    public void simboloDeclarado(final Simbolo simbolo)
    {
        if (!atualizacaoHabilitada)
        {
            return;
        }
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                SourceTreeNode root = (SourceTreeNode) model.getRoot();
                limparModificado(root);
                if (!(simbolo instanceof Funcao))
                {
                    PortugolTreeNode node = getPortugolTreeNode(root, simbolo);
                    valoresDosNos.put(node, getValorDoSimbolo(simbolo));
                    if (node != null)
                    {
                        inicializar(node, simbolo);
                        node.setDeclarado(true);
                        model.nodeChanged(node);
                    }
                }
            }
        });

    }

    public void atualizaValoresDosNos()
    {
        Enumeration en = ((SourceTreeNode) model.getRoot()).depthFirstEnumeration();
        while (en.hasMoreElements())
        {
            SourceTreeNode s = (SourceTreeNode) en.nextElement();
            if (s instanceof PortugolTreeNode)
            {
                Object valorDoNo = valoresDosNos.get((PortugolTreeNode) s);
                ((PortugolTreeNode) s).setValor(valorDoNo);
                model.nodeChanged(s);
            }
        }
    }

    private void inicializar(PortugolTreeNode node, Simbolo simbolo)
    {
        if (simbolo instanceof Variavel)
        {
            inicializarVariavel(simbolo, node);
        }
        else if (simbolo instanceof Vetor)
        {
            if (node.getChildCount() == 0)
            {
                inicializarVetor(simbolo, node);
            }
        }
        else if (simbolo instanceof Matriz)
        {
            if (node.getChildCount() == 0)
            {
                inicializarMatriz(simbolo, node);
            }
        }
        else if (simbolo instanceof Ponteiro)
        {
            inicializar(node, ((Ponteiro) simbolo).getSimboloApontado());
        }
    }

    private void inicializarVariavel(Simbolo simbolo, PortugolTreeNode node)
    {
        final Object valor = ((Variavel) simbolo).getValor();
        node.setValor(valor);
        model.nodeChanged(node);
    }

    private void inicializarVetor(Simbolo simbolo, PortugolTreeNode node)
    {
        List<Object> valores = ((Vetor) simbolo).obterValores();

        for (int i = 0; i < valores.size(); i++)
        {
            ValorTreeNode vtn = new ValorTreeNode(i, valores.get(i), simbolo.getTipoDado());
            model.insertNodeInto(vtn, node, node.getChildCount());
        }
    }

    private void inicializarMatriz(Simbolo simbolo, PortugolTreeNode node)
    {
        List<List<Object>> obterValores = ((Matriz) simbolo).obterValores();

        for (int i = 0; i < obterValores.size(); i++)
        {
            List<Object> list = obterValores.get(i);
            ValorTreeNode valorTreeNode = new ValorTreeNode(i, null, simbolo.getTipoDado());
            valorTreeNode.setColuna(true);
            for (int j = 0; j < list.size(); j++)
            {
                ValorTreeNode vtn = new ValorTreeNode(j, list.get(j), simbolo.getTipoDado());
                valorTreeNode.add(vtn);
            }
            model.insertNodeInto(valorTreeNode, node, node.getChildCount());
        }
    }

    private Object getValorDoSimbolo(Simbolo simbolo)
    {
        if (simbolo instanceof Variavel)
        {
            return ((Variavel) simbolo).getValor();
        }
        else if (simbolo instanceof Vetor)
        {
            final Vetor vetor = (Vetor) simbolo;
            List valores = vetor.obterValores();
            int indice = vetor.getUltimoIndiceModificado();
            if (!valores.isEmpty() && indice >= 0 && indice < valores.size())
            {
                return valores.get(indice);
            }
            return null;
        }
        else if (simbolo instanceof Matriz)
        {
            Matriz matriz = ((Matriz) simbolo);
            int indiceDaLinha = matriz.getUltimaLinhaModificada();
            int indiceDaColuna = matriz.getUltimaColunaModificada();
            List linhas = matriz.obterValores();
            if (!linhas.isEmpty())
            {
                if (indiceDaLinha >= 0 && indiceDaLinha < linhas.size())
                {
                    List linha = (List) linhas.get(indiceDaLinha);
                    if (!linha.isEmpty() && indiceDaColuna >= 0 && indiceDaColuna < linha.size())
                    {
                        return linha.get(indiceDaColuna);
                    }
                }
            }
            return null;
        }
        else if (simbolo instanceof Ponteiro)
        {
            return getValorDoSimbolo(((Ponteiro) simbolo).getSimboloApontado());
        }

        return null;
    }

    private void modificar(Simbolo simbolo, PortugolTreeNode noAlteraldo)
    {
        if (simbolo instanceof Variavel)
        {
            noAlteraldo.setValor(getValorDoSimbolo(simbolo));
            noAlteraldo.setModificado(true);
            model.nodeChanged(noAlteraldo);
        }
        else if (simbolo instanceof Vetor)
        {
            final Vetor vetor = (Vetor) simbolo;
            if (noAlteraldo.getChildCount() > vetor.getUltimoIndiceModificado())
            {
                ValorTreeNode valorTreenode = (ValorTreeNode) noAlteraldo.getChildAt(vetor.getUltimoIndiceModificado());
                valorTreenode.setModificado(true);
                noAlteraldo.setModificado(true);
                valorTreenode.setValor(getValorDoSimbolo(simbolo));
                model.nodeChanged(valorTreenode);
                model.nodeChanged(noAlteraldo);
            }
        }
        else if (simbolo instanceof Matriz)
        {
            Matriz matriz = ((Matriz) simbolo);
            if (noAlteraldo.getChildCount() > 0)
            {
                ValorTreeNode noDaLinha = (ValorTreeNode) noAlteraldo.getChildAt(matriz.getUltimaLinhaModificada());
                noDaLinha.setModificado(true);
                noAlteraldo.setModificado(true);
                if (noDaLinha.getChildCount() > 0)
                {
                    ValorTreeNode NoDaColuna = (ValorTreeNode) noDaLinha.getChildAt(matriz.getUltimaColunaModificada());
                    NoDaColuna.setModificado(true);
                    NoDaColuna.setValor(getValorDoSimbolo(simbolo));
                    model.nodeChanged(noAlteraldo);
                    model.nodeChanged(noDaLinha);
                    model.nodeChanged(NoDaColuna);
                }
            }
        }
        else if (simbolo instanceof Ponteiro)
        {
            modificar(((Ponteiro) simbolo).getSimboloApontado(), noAlteraldo);
        }

    }

    private void limparModificado(SourceTreeNode root)
    {
        Enumeration en = root.depthFirstEnumeration();
        while (en.hasMoreElements())
        {
            SourceTreeNode s = (SourceTreeNode) en.nextElement();
            s.setModificado(false);
        }
    }

    private PortugolTreeNode getPortugolTreeNode(SourceTreeNode root, Simbolo simbolo)
    {
        NoDeclaracao noDeclaracao = simbolo.getOrigemDoSimbolo();
        PortugolTreeNode node = null; //tentei usar um hash map para associar o simbolo com o nó da JTree, mas ficou mais lento ainda. why??? Usei um TreeMap e deu na mesma, why???
        Enumeration en = root.depthFirstEnumeration();
        while (en.hasMoreElements())
        {
            SourceTreeNode s = (SourceTreeNode) en.nextElement();
            if (s instanceof PortugolTreeNode)
            {
                node = (PortugolTreeNode) s;
                if (node.getASTNode() != null && noDeclaracao != null
                        && comparador.compare(node.getASTNode(), noDeclaracao) > 0)
                {
                    break;
                }
            }
        }
        return node;
    }

    private void remover(PortugolTreeNode node, Simbolo simbolo)
    {
        if (simbolo instanceof Variavel)
        {
            removerVariavel(node);
        }
        else if (simbolo instanceof Vetor)
        {
            removerVetor(node);
        }
        else if (simbolo instanceof Matriz)
        {
            removerMatriz(node);
        }
        else if (simbolo instanceof Ponteiro)
        {
            remover(node, ((Ponteiro) simbolo).getSimboloApontado());
        }
    }

    private void removerVariavel(PortugolTreeNode node)
    {
        node.setValor(null);
    }

    private void removerVetor(PortugolTreeNode node)
    {
        removerFilhos(node);
    }

    private void removerMatriz(final PortugolTreeNode node)
    {
        removerFilhos(node);

    }

    private void removerFilhos(final PortugolTreeNode node)
    {
        for (int i = model.getChildCount(node) - 1; i >= 0; i--)
        {
            model.removeNodeFromParent((MutableTreeNode) model.getChild(node, i));

        }
    }

    /**
     * Listens for events this tree is interested in (events in the associated
     * editor, for example), as well as events in this tree.
     */
    private class Listener implements PropertyChangeListener
    {

        /**
         * Called whenever the text area's syntax style changes, as well as when
         * it is re-parsed.
         */
        @Override
        public void propertyChange(PropertyChangeEvent e)
        {
            String name = e.getPropertyName();

            Programa programa = (Programa) e.getNewValue();

            if (RSyntaxTextArea.SYNTAX_STYLE_PROPERTY.equals(name) || PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO.equals(name))
            {
                update(programa);
            }
        }
    }
}

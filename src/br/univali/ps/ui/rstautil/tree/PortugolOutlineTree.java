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

import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.asa.No;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.depuracao.DepuradorListener;
import br.univali.portugol.nucleo.depuracao.InterfaceDepurador;
import br.univali.portugol.nucleo.simbolos.Funcao;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Ponteiro;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.completion.PortugolLanguageSuport;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Element;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * A tree view showing the outline of Java source, similar to the "Outline" view
 * in the Eclipse JDT. It also uses Eclipse's icons, just like the rest of this
 * code completion library.<p>
 *
 * You can get this tree automatically updating in response to edits in an
 * <code>RSyntaxTextArea</code> with {@link JavaLanguageSupport} installed by
 * calling {@link #listenTo(RSyntaxTextArea)}. Note that an instance of this
 * class can only listen to a single editor at a time, so if your application
 * contains multiple instances of RSyntaxTextArea, you'll either need a separate
 * <code>JavaOutlineTree</code> for each one, or call
 * <code>uninstall()</code> and
 * <code>listenTo(RSyntaxTextArea)</code> each time a new RSTA receives focus.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class PortugolOutlineTree extends AbstractTree implements DepuradorListener
{
    private DefaultTreeModel model;
    private PortugolParser parser;
    private Listener listener;

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
        addTreeSelectionListener(listener);
    }
    
    AstOutlineTreeFactory astFactory = new AstOutlineTreeFactory();

    /**
     * Refreshes this tree.
     *
     * @param ast The parsed compilation unit. If this is <code>null</code> then
     * the tree is cleared.
     */
    private void update(ResultadoAnalise resultadoAnalise)
    {

        if (resultadoAnalise == null || resultadoAnalise.getNumeroErrosSintaticos() > 0)
        {
            return;
        }

        SourceTreeNode root = astFactory.createTree(resultadoAnalise.getAsa());
        model.setRoot(root);
        refresh();

        model.reload();
        TreeUtils.expandAll(this, true);
    }

    /**
     * Refreshes listeners on the text area when its syntax style changes.
     */
    private void checkForPortugolParsing()
    {


        PortugolLanguageSuport jls = new PortugolLanguageSuport();

        // Listen for re-parsing of the editor, and update the tree accordingly
        parser = jls.getParser(textArea);
        if (parser != null)
        { // Should always be true
            parser.addPropertyChangeListener(PortugolParser.PROPERTY_RESULTADO_ANALISE, listener);
            // Populate with any already-existing CompilationUnit

            if (parser.getResultadoAnalise() != null)
            {
                update(parser.getResultadoAnalise());
            }
        }
        else
        {
            update((ResultadoAnalise) null); // Clear the tree
        }

    }

   
    private void gotoElementAtPath(TreePath path)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.
                getLastPathComponent();
        Object obj = node.getUserObject();
        TrechoCodigoFonte trechoCodigoFonte = null;
        
        if (obj instanceof NoDeclaracao)
        {
            trechoCodigoFonte = ((NoDeclaracao) obj).getTrechoCodigoFonteNome();
        }
        else if (obj instanceof NoDeclaracaoParametro)
        {
            trechoCodigoFonte = ((NoDeclaracaoParametro) obj).getTrechoCodigoFonteNome();
        }


        if (trechoCodigoFonte != null)
        {
            int linha = trechoCodigoFonte.getLinha() - 1;
            Element elem = textArea.getDocument().getDefaultRootElement().getElement(linha);
            int offs = elem.getStartOffset() + trechoCodigoFonte.getColuna();
            int end = offs + trechoCodigoFonte.getTamanhoTexto();

            textArea.select(offs, end);
            textArea.requestFocus();
        }
    }
   
    /**
     * {@inheritDoc}
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
     */
    @Override
    public void listenTo(RSyntaxTextArea textArea)
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
        textArea.addPropertyChangeListener(PortugolParser.PROPERTY_RESULTADO_ANALISE, listener);

        // Check whether we're currently editing Java
        checkForPortugolParsing();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uninstall()
    {
        if (parser != null)
        {
            parser.removePropertyChangeListener(PortugolParser.PROPERTY_RESULTADO_ANALISE, listener);
            parser = null;
        }

        if (textArea != null)
        {
            textArea.removePropertyChangeListener(RSyntaxTextArea.SYNTAX_STYLE_PROPERTY, listener);
            textArea = null;
        }
    }

    /**
     * Overridden to also update the UI of the child cell renderer.
     */
    @Override
    public void updateUI()
    {
        super.updateUI();
        // DefaultTreeCellRenderer caches colors, so we can't just call
        // ((JComponent)getCellRenderer()).updateUI()...
        //setCellRenderer(new AstTreeCellRenderer());
    }

    ComparadorNos comparador = new ComparadorNos();
    
    @Override
    public void depuracaoInicializada(InterfaceDepurador depurador)
    {}

    @Override
    public void highlightLinha(int linha)
    {}

    @Override
    public void HighlightDetalhadoAtual(int linha, int coluna, int tamanho)
    {}

    @Override
    public void simbolosAlterados(List<Simbolo> simbolos)
    {
        SourceTreeNode root = (SourceTreeNode) model.getRoot();
        limparModificado(root);
        for (Simbolo simbolo : simbolos){
            if (!(simbolo instanceof Funcao)){
                PortugolTreeNode node = getPortugolTreeNode(root, simbolo);
                if (node != null)
                {    
                    modificar(simbolo,node);
                    SwingUtilities.invokeLater(new FireChangedEvent(node));
                }
            }
        }
        this.repaint();
    }
    
     @Override
    public void simboloRemovido(Simbolo simbolo)
    {
        SourceTreeNode root = (SourceTreeNode) model.getRoot();
        limparModificado(root);   
        if (!(simbolo instanceof Funcao)){
            PortugolTreeNode node = getPortugolTreeNode(root, simbolo);
            if (node != null)
            {
                remover(node,simbolo);
                node.setDeclarado(false);
                SwingUtilities.invokeLater(new FireChangedEvent(node));
            }
        }
        this.repaint();
    }

    @Override
    public void simboloDeclarado(Simbolo simbolo)
    {
        SourceTreeNode root = (SourceTreeNode) model.getRoot();
        limparModificado(root);   
        if (!(simbolo instanceof Funcao)){
            PortugolTreeNode node = getPortugolTreeNode(root, simbolo);
            if (node != null)
            {
                inicializar(node,simbolo);
                node.setDeclarado(true);
                SwingUtilities.invokeLater(new FireChangedEvent(node));
            }
        }
        this.repaint();
    }

    private void inicializar(PortugolTreeNode node, Simbolo simbolo)
    {
        if(simbolo instanceof Variavel)
        {
            inicializarVariavel(simbolo, node);
        } 
        else if (simbolo instanceof Vetor) 
        { 
            if (node.getChildCount() == 0) {
                inicializarVetor(simbolo, node);
            }
        }
        else if (simbolo instanceof Matriz) 
        {
            if (node.getChildCount() == 0) {
                inicializarMatriz(simbolo, node);
            }
        }
        else if (simbolo instanceof Ponteiro){
            inicializar(node, ((Ponteiro)simbolo).getSimboloApontado());
        }
    }

    private void inicializarVariavel(Simbolo simbolo, PortugolTreeNode node)
    {
        final Object valor = ((Variavel)simbolo).getValor(); 
        node.setValor(valor);
        SwingUtilities.invokeLater(new FireChangedEvent(node));
    }

    private void inicializarVetor(Simbolo simbolo, PortugolTreeNode node)
    {
        List<Object> valores = ((Vetor) simbolo).obterValores();
       
        for (int i = 0; i < valores.size(); i++)
        {
            ValorTreeNode vtn = new ValorTreeNode(i, valores.get(i),simbolo.getTipoDado());
            inserirNo(vtn, node);
        }
    }

    private void inicializarMatriz(Simbolo simbolo, PortugolTreeNode node)
    {
        List<List<Object>> obterValores = ((Matriz) simbolo).obterValores();

        for (int i = 0; i < obterValores.size(); i++)
        {
            List<Object> list = obterValores.get(i);
            ValorTreeNode valorTreeNode = new ValorTreeNode(i, null,simbolo.getTipoDado());
            valorTreeNode.setColuna(true);
            for (int j = 0; j < list.size(); j++)
            {
               ValorTreeNode vtn = new ValorTreeNode(j, list.get(j),simbolo.getTipoDado());
               valorTreeNode.add(vtn);
            }
            inserirNo(valorTreeNode, node);
        }
    }

    private void inserirNo(ValorTreeNode vtn, PortugolTreeNode node)
    {
        try
        {
            SwingUtilities.invokeAndWait(new InsertNode(vtn, node));
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(PortugolOutlineTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InvocationTargetException ex)
        {
            Logger.getLogger(PortugolOutlineTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modificar(Simbolo simbolo, PortugolTreeNode noAlteraldo)
    {
        if(simbolo instanceof Variavel)
        {
            final Object valor = ((Variavel)simbolo).getValor(); 
            noAlteraldo.setValor(valor);
            noAlteraldo.setModificado(true);
            SwingUtilities.invokeLater(new FireChangedEvent(noAlteraldo));
        } 
        else if (simbolo instanceof Vetor) 
        { 
            final Vetor vetor = (Vetor) simbolo;
            List<Object> valores = vetor.obterValores();
            for (int i = 0; i < valores.size(); i++)
            {
                if (vetor.getUltimoIndiceModificado() == i){
                    ValorTreeNode valorTreenode =  (ValorTreeNode) noAlteraldo.getChildAt(i);
                    valorTreenode.setModificado(true);
                    noAlteraldo.setModificado(true);
                    valorTreenode.setValor(valores.get(i));
                    SwingUtilities.invokeLater(new FireChangedEvent(valorTreenode));
                    SwingUtilities.invokeLater(new FireChangedEvent(noAlteraldo));
                    return;
                }

            }             
        } 
        else if (simbolo instanceof Matriz) {
            Matriz matriz =((Matriz)simbolo);
            List<List<Object>> obterValores = matriz.obterValores();
            for (int i = 0; i < obterValores.size(); i++)
            {   
                if (matriz.getUltimaLinhaModificada() == i) {
                    ValorTreeNode linha =  (ValorTreeNode) noAlteraldo.getChildAt(i);
                    linha.setModificado(true);
                    noAlteraldo.setModificado(true);
                    List<Object> list = obterValores.get(i);
                    for (int j = 0; j < list.size(); j++)
                    {
                        if (matriz.getUltimaColunaModificada() == j) {
                            ValorTreeNode coluna =  (ValorTreeNode) linha.getChildAt(j);
                            coluna.setModificado(true);
                            coluna.setValor(list.get(j));
                            SwingUtilities.invokeLater(new FireChangedEvent(noAlteraldo));
                            SwingUtilities.invokeLater(new FireChangedEvent(linha));
                            SwingUtilities.invokeLater(new FireChangedEvent(coluna));
                            return;
                        }
                    }
                }
            }
        } else if (simbolo instanceof Ponteiro) {
            modificar(((Ponteiro)simbolo).getSimboloApontado(), noAlteraldo);
        }
                        
    }

    private void limparModificado(SourceTreeNode root)
    {
        Enumeration en = root.depthFirstEnumeration();
        while (en.hasMoreElements()) 
        {
            SourceTreeNode s = (SourceTreeNode)en.nextElement();
            s.setModificado(false);
        }
    }

    private PortugolTreeNode getPortugolTreeNode(SourceTreeNode root, Simbolo simbolo)
    {
        Enumeration en = root.depthFirstEnumeration();
        NoDeclaracao noDeclaracao = simbolo.getOrigemDoSimbolo();
        PortugolTreeNode node = null;
        while (en.hasMoreElements()) 
        {
            SourceTreeNode s = (SourceTreeNode)en.nextElement();
            if (s instanceof PortugolTreeNode){
                node = (PortugolTreeNode) s;                 
                if (node.getASTNode() != null && noDeclaracao != null &&
                    comparador.compare(node.getASTNode(), noDeclaracao) > 0)
                {
                    break;
                }
            }
        }
        return node;
    }

    private void remover(PortugolTreeNode node, Simbolo simbolo)
    {
        if(simbolo instanceof Variavel)
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
        else if (simbolo instanceof Ponteiro){
            remover(node, ((Ponteiro)simbolo).getSimboloApontado());
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
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run()
                {
                    for (int i = model.getChildCount(node) -1; i >= 0; i--){
                        model.removeNodeFromParent((MutableTreeNode)model.getChild(node, i));
                    }
                }
            });
        
        } catch (Exception e){
            e.printStackTrace(System.err);
        }
    }

    private class ComparadorNos implements Comparator<No>{

        @Override
        public int compare(No o1, No o2)
        {
            boolean linha = false;
            boolean coluna = false;
            boolean tamanho = false;
            boolean nome = false;
            
            if ((o1 instanceof NoDeclaracao) &&
                    (o2 instanceof NoDeclaracao)) {
                NoDeclaracao dec1 = ((NoDeclaracao)o1);
                NoDeclaracao dec2 = ((NoDeclaracao)o2);
                if (dec1.getTrechoCodigoFonteNome() == null)
                    return 0;
                linha = dec1.getTrechoCodigoFonteNome().getLinha() == dec2.getTrechoCodigoFonteNome().getLinha();
                coluna = dec1.getTrechoCodigoFonteNome().getColuna()== dec2.getTrechoCodigoFonteNome().getColuna();
                tamanho = dec1.getTrechoCodigoFonteNome().getTamanhoTexto()== dec2.getTrechoCodigoFonteNome().getTamanhoTexto();
                nome = dec1.getNome().equals(dec2.getNome());
                
                
            }
            if (linha && coluna && tamanho && nome)
                return 1;
            else 
                return 0;
        }
    
    }

    /**
     * Listens for events this tree is interested in (events in the associated
     * editor, for example), as well as events in this tree.
     */
    private class Listener implements PropertyChangeListener,
            TreeSelectionListener
    {
        /**
         * Called whenever the text area's syntax style changes, as well as when
         * it is re-parsed.
         */
        @Override
        public void propertyChange(PropertyChangeEvent e)
        {
            String name = e.getPropertyName();

            // If the text area is changing the syntax style it is editing
            if (RSyntaxTextArea.SYNTAX_STYLE_PROPERTY.equals(name))
            {
                checkForPortugolParsing();
            }
            else if (PortugolParser.PROPERTY_RESULTADO_ANALISE.equals(name))
            {
                update((ResultadoAnalise) e.getNewValue());
            }
        }

        /**
         * Selects the corresponding element in the text editor when a user
         * clicks on a node in this tree.
         */
        @Override
        public void valueChanged(TreeSelectionEvent e)
        {
            TreePath newPath = e.getNewLeadSelectionPath();
            if (newPath != null)
            {
                gotoElementAtPath(newPath);
            }
            
        }
    }

    private class InsertNode implements Runnable
    {
        private final ValorTreeNode child;
        private final PortugolTreeNode parent;

        public InsertNode(ValorTreeNode valorTreeNode, PortugolTreeNode node)
        {
            this.child = valorTreeNode;
            this.parent = node;
        }

        @Override
        public void run() {
            model.insertNodeInto(child, parent, parent.getChildCount());
        }
    }

    private class FireChangedEvent implements Runnable
    {
        private final SourceTreeNode node;

        public FireChangedEvent(SourceTreeNode node)
        {
            this.node = node;
        }

        @Override
        public void run()
        {
            model.nodeChanged(node);
        }
    }
}
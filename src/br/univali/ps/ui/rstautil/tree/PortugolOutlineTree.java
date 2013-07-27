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
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosConstante;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosFuncao;
import br.univali.portugol.nucleo.depuracao.MemoriaDados;
import br.univali.portugol.nucleo.simbolos.Funcao;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;
import br.univali.ps.ui.AbaConsole;
import br.univali.ps.ui.PainelSaida;
import br.univali.ps.ui.TelaPrincipal;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.completion.PortugolLanguageSuport;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Element;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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
public class PortugolOutlineTree extends AbstractTree
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
    
    public void updateValoresSimbolos(MemoriaDados dados)
    {
        SourceTreeNode root = (SourceTreeNode) model.getRoot();
            for (Simbolo simbolo : dados)
            {
                if (!(simbolo instanceof Funcao)){
                    Enumeration en = root.depthFirstEnumeration();
                    PortugolTreeNode node = null;
                    while (en.hasMoreElements()) 
                    {
                        SourceTreeNode s = (SourceTreeNode)en.nextElement();
                        if (s instanceof PortugolTreeNode){
                            node = (PortugolTreeNode) s;                 
                            if (node.getASTNode() != null && simbolo.getOrigemDoSimbolo() != null &&
                                comparador.compare(node.getASTNode(), simbolo.getOrigemDoSimbolo()) > 0)
                            {
                                break;
                            }
                        }
                    }
                    if (node != null) 
                    {   
                         if(simbolo instanceof Variavel)
                         {
                             final Object valor = ((Variavel)simbolo).getValor(); 
                             if (node.getValor() != null && node.getValor() != valor) {
                                node.setValor(valor); 
                                model.nodeChanged(node);
                             } else {
                                node.setValor(valor); 
                                model.nodeChanged(node);
                             }
                         } 
                         else if (simbolo instanceof Vetor) 
                         { 
                             List<Object> valores = ((Vetor) simbolo).obterValores();
                             if (node.getChildCount() == 0) {
                                for (int i = 0; i < valores.size(); i++)
                                {
                                    ValorTreeNode vtn = new ValorTreeNode(i, valores.get(i));
                                    model.insertNodeInto(vtn, node, node.getChildCount());
                                }
                             } else {
                                for (int i = 0; i < valores.size(); i++)
                                {
                                    ValorTreeNode valorTreenode =  (ValorTreeNode) node.getChildAt(i);
                                    if (valorTreenode.getValor() != valores.get(i)) {
                                        valorTreenode.setModificado(true);
                                        model.nodeChanged(valorTreenode);
                                        valorTreenode.setValor(valores.get(i));
                                    }
                                    
                                    
                                }
                             }
                             
                         } 
                         else if (simbolo instanceof Matriz) 
                         {
                             List<List<Object>> obterValores = ((Matriz) simbolo).obterValores();
                             if (node.getChildCount() == 0) {
                                for (int i = 0; i < obterValores.size(); i++)
                                {
                                    List<Object> list = obterValores.get(i);
                                    ValorTreeNode valorTreeNode = new ValorTreeNode(i, null);
                                    valorTreeNode.setColuna(true);
                                    for (int j = 0; j < list.size(); j++)
                                    {
                                       ValorTreeNode vtn = new ValorTreeNode(j, list.get(j));
                                       valorTreeNode.add(vtn);
                                    }
                                    model.insertNodeInto(valorTreeNode, node, node.getChildCount());
                                }
                             } else {
                                for (int i = 0; i < obterValores.size(); i++)
                                {   
                                    ValorTreeNode linha =  (ValorTreeNode) node.getChildAt(i);
                                    List<Object> list = obterValores.get(i);
                                    for (int j = 0; j < list.size(); j++)
                                    {
                                        ValorTreeNode coluna =  (ValorTreeNode) linha.getChildAt(j);
                                        if (coluna.getValor() != list.get(j)) {
                                            coluna.setModificado(true);
                                            coluna.setValor(list.get(j));
                                            model.nodeChanged(coluna);
                                        }
                                    }
                                }
                             }
                         }


                        //drefresh();

                        //model.reload();
                        //TreeUtils.expandAll(this, true);

                   }

               }
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
            else if ((o1 instanceof NoDeclaracaoParametro) &&
                    (o2 instanceof NoDeclaracaoParametro))
            {
                NoDeclaracaoParametro dec1 = ((NoDeclaracaoParametro)o1);
                NoDeclaracaoParametro dec2 = ((NoDeclaracaoParametro)o2);
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
}
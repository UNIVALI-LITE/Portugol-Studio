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

import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrata;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.CarregadorBibliotecas;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import br.univali.ps.ui.rstautil.AbstractSourceTree;
import br.univali.ps.ui.rstautil.IconFactory;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.completion.PortugolLanguageSuport;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Element;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;


/**
 * A tree view showing the outline of Java source, similar to the "Outline"
 * view in the Eclipse JDT.  It also uses Eclipse's icons, just like the rest
 * of this code completion library.<p>
 *
 * You can get this tree automatically updating in response to edits in an
 * <code>RSyntaxTextArea</code> with {@link JavaLanguageSupport} installed by
 * calling {@link #listenTo(RSyntaxTextArea)}.  Note that an instance of this
 * class can only listen to a single editor at a time, so if your application
 * contains multiple instances of RSyntaxTextArea, you'll either need a separate
 * <code>JavaOutlineTree</code> for each one, or call <code>uninstall()</code>
 * and <code>listenTo(RSyntaxTextArea)</code> each time a new RSTA receives
 * focus.
 * 
 * @author Robert Futrell
 * @version 1.0
 */
public class PortugolOutlineTree extends AbstractSourceTree 
{
        private DefaultTreeModel model;
	private RSyntaxTextArea textArea;
	private PortugolParser parser;
	private Listener listener;


	/**
	 * Constructor.  The tree created will not have its elements sorted
	 * alphabetically.
	 */
	public PortugolOutlineTree() {
		this(false);
	}
        
        public PortugolOutlineTree(int a) {
		this(false);
	}

	/**
	 * Constructor.
	 *
	 * @param sorted Whether the tree should sort its elements alphabetically.
	 *        Note that outline trees will likely group nodes by type before
	 *        sorting (i.e. methods will be sorted in one group, fields in
	 *        another group, etc.).
	 */
	public PortugolOutlineTree(boolean sorted) {
		setSorted(sorted);
		setBorder(BorderFactory.createEmptyBorder(0,8,0,8));
		setRootVisible(false);
		setCellRenderer(new AstTreeCellRenderer());
		model = new DefaultTreeModel(new DefaultMutableTreeNode("Nothing"));
		setModel(model);
		listener = new Listener();
		addTreeSelectionListener(listener);   
	}


	/**
	 * Refreshes this tree.
	 *
	 * @param ast The parsed compilation unit.  If this is <code>null</code>
	 *        then the tree is cleared.
	 */
	private void update(ArvoreSintaticaAbstrata ast) {
		PortugolTreeNode root = new PortugolTreeNode("Remove me!", IconFactory.SOURCE_FILE_ICON);
		root.setSortable(false);
		if (ast==null) {
			//model.setRoot(root);
			return;
		}

                MemberTreeNode bibliotecas = new MemberTreeNode("Bibliotecas");
                
                for (NoInclusaoBiblioteca inclusao : ((ArvoreSintaticaAbstrataPrograma) ast).getListaInclusoesBibliotecas())
                {
                    bibliotecas.add(createMemberNode(inclusao));
                }
                
                MemberTreeNode programa = new MemberTreeNode("Programa");
                
		for (Iterator<NoDeclaracao> i = ast.getListaDeclaracoesGlobais().iterator(); i.hasNext(); ) {
			NoDeclaracao td =  i.next();
			MemberTreeNode dmtn = createMemberNode(td);
			
                        programa.add(dmtn);
                }
                
                
		root.add(bibliotecas);
                root.add(programa);
                
                model.setRoot(root);
		root.setSorted(isSorted());
		refresh();
                
                model.reload();
	}


	/**
	 * Refreshes listeners on the text area when its syntax style changes.
	 */
        
	private void checkForPortugolParsing() {

		
                PortugolLanguageSuport jls = new PortugolLanguageSuport();

		// Listen for re-parsing of the editor, and update the tree accordingly
		parser = jls.getParser(textArea);
		if (parser!=null) { // Should always be true
			parser.addPropertyChangeListener(
					PortugolParser.PROPERTY_AST, listener);
			// Populate with any already-existing CompilationUnit
			ArvoreSintaticaAbstrata ast = parser.getRoot();
			update(ast);
		}
		else {
			update((ArvoreSintaticaAbstrata)null); // Clear the tree
		}

	}
        
        
        private MemberTreeNode createMemberNode(NoInclusaoBiblioteca inclusao)
        {
            try
            {                
                Biblioteca biblioteca = CarregadorBibliotecas.carregarBiblioteca(inclusao.getNome());
                MemberTreeNode raizBiblioteca = new LibraryTreeNode(inclusao, biblioteca);
                
                List<Method> funcoes = biblioteca.getFuncoes();
                
                if (funcoes != null)
                {
                    for (Method funcao : funcoes)
                    {
                        raizBiblioteca.add(new LibraryFunctionTreeNode(biblioteca, funcao));
                    }
                }
                
                List<Field> variaveis =  biblioteca.getVariaveis();
                
                if (variaveis != null)
                {
                    for (Field variavel : variaveis)
                    {
                        raizBiblioteca.add(new LibraryVarTreeNode(biblioteca, variavel));
                    }
                }                
            
                return raizBiblioteca;
            }
            catch (ErroCarregamentoBiblioteca erro)
            {
                return new LibraryTreeNode(erro);
            }
        }

	private MemberTreeNode createMemberNode(NoDeclaracao member) {

		MemberTreeNode node = null;
		/*if (member instanceof CodeBlock) {
			node = new MemberTreeNode((CodeBlock)member);
		}
		else*/ if (member instanceof NoDeclaracaoVariavel) {
			node = new MemberTreeNode(member);
		}
		else {
			node = new MemberTreeNode((NoDeclaracaoFuncao)member);
		}

		/*CodeBlock body = null;
		if (member instanceof CodeBlock) {
			body = (CodeBlock)member;
		}                
		else*/
                List<NoBloco> body = null;
                if (member instanceof NoDeclaracaoFuncao) {
                    
                        List<NoDeclaracaoParametro> parametros = ((NoDeclaracaoFuncao) member).getParametros();
                        
                        if (parametros != null)
                        {
                            for (NoDeclaracaoParametro parametro : parametros)
                            {
                                node.add(new FuncParamTreeNode(parametro));
                            }
                        }
                    
			body = ((NoDeclaracaoFuncao)member).getBlocos();
		}                

		if (body!=null && !getShowMajorElementsOnly()) {
			for (int i=0; i<body.size(); i++) {
                            NoBloco no = body.get(i);
                            if(no instanceof NoDeclaracaoVariavel){
				LocalVarTreeNode varNode = new LocalVarTreeNode((NoDeclaracao)body.get(i));
				node.add(varNode);
                            }
			}
		}

		return node;

	}


	/**
	 * {@inheritDoc}
	 */
        @Override
	public void expandInitialNodes() {

		// First, collapse all rows.
		int j=0;
                
                while (j<getRowCount()) {
			expandRow(j++);
		}
                
                /*
		while (j<getRowCount()) {
			collapseRow(j++);
		}*/
/*
		// Expand only type declarations
		expandRow(0);
		j = 1;
		while (j<getRowCount()) {
			TreePath path = getPathForRow(j);
			Object comp = path.getLastPathComponent();
                        
                        
			//if (comp instanceof TypeDeclarationTreeNode) {
			//	expandPath(path);
			//}
			j++;
		}
*/
	}


	private void gotoElementAtPath(TreePath path) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.
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
                        
                       
                if (trechoCodigoFonte != null )
                {
                    int linha = trechoCodigoFonte.getLinha() - 1;
                    Element elem = textArea.getDocument().getDefaultRootElement().getElement(linha);
                    int offs = elem.getStartOffset() + trechoCodigoFonte.getColuna();
                    int end =  offs + trechoCodigoFonte.getTamanhoTexto();
                           
                    textArea.select(offs, end);
                    textArea.requestFocus();                            
		}
	}


	/**
	 * {@inheritDoc}
	 */
        @Override
	public boolean gotoSelectedElement() {
		TreePath path = getLeadSelectionPath();//e.getNewLeadSelectionPath();
		if (path != null) {
			gotoElementAtPath(path);
			return true;
		}
		return false;
	}


	/**
	 * {@inheritDoc}
	 */
        @Override
	public void listenTo(RSyntaxTextArea textArea) {

		if (this.textArea!=null) {
			uninstall();
		}

		// Nothing new to listen to
		if (textArea==null) {
			return;
		}

		// Listen for future language changes in the text editor
		this.textArea = textArea;
		textArea.addPropertyChangeListener(
			PortugolParser.PROPERTY_AST, listener);

		// Check whether we're currently editing Java
		checkForPortugolParsing();

	}


	/**
	 *{@inheritDoc}
	 */
        @Override
	public void uninstall() {

		if (parser!=null) {
			parser.removePropertyChangeListener(
					PortugolParser.PROPERTY_AST, listener);
			parser = null;
		}

		if (textArea!=null) {
			textArea.removePropertyChangeListener(
					RSyntaxTextArea.SYNTAX_STYLE_PROPERTY, listener);
			textArea = null;
		}

	}


	/**
	 * Overridden to also update the UI of the child cell renderer.
	 */
        @Override
	public void updateUI() {
		super.updateUI();
		// DefaultTreeCellRenderer caches colors, so we can't just call
		// ((JComponent)getCellRenderer()).updateUI()...
		setCellRenderer(new AstTreeCellRenderer());
	}


	/**
	 * Listens for events this tree is interested in (events in the associated
	 * editor, for example), as well as events in this tree.
	 */
	private class Listener implements PropertyChangeListener,
							TreeSelectionListener {

		/**
		 * Called whenever the text area's syntax style changes, as well as
		 * when it is re-parsed.
		 */
                @Override
		public void propertyChange(PropertyChangeEvent e) {

			String name = e.getPropertyName();

			// If the text area is changing the syntax style it is editing
			if (RSyntaxTextArea.SYNTAX_STYLE_PROPERTY.equals(name)) {
				checkForPortugolParsing();
			}

			else if (PortugolParser.PROPERTY_AST.equals(name)) {
				ArvoreSintaticaAbstrata ast = (ArvoreSintaticaAbstrata)e.getNewValue();
				update(ast);
			}

		}

		/**
		 * Selects the corresponding element in the text editor when a user
		 * clicks on a node in this tree.
		 */
                @Override
		public void valueChanged(TreeSelectionEvent e) {
			if (getGotoSelectedElementOnClick()) {
				//gotoSelectedElement();
				TreePath newPath = e.getNewLeadSelectionPath();
				if (newPath!=null) {
					gotoElementAtPath(newPath);
				}
			}
		}

	}


}
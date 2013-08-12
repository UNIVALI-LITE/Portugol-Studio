package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoBitwiseNao;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoCaracter;
import br.univali.portugol.nucleo.asa.NoCaso;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoEscolha;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoLogico;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoMenosUnario;
import br.univali.portugol.nucleo.asa.NoNao;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseE;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseLeftShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseOu;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseRightShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseXOR;
import br.univali.portugol.nucleo.asa.NoOperacaoDivisao;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaDiferenca;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaE;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaIgualdade;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaior;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaiorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenor;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaOU;
import br.univali.portugol.nucleo.asa.NoOperacaoModulo;
import br.univali.portugol.nucleo.asa.NoOperacaoMultiplicacao;
import br.univali.portugol.nucleo.asa.NoOperacaoSoma;
import br.univali.portugol.nucleo.asa.NoOperacaoSubtracao;
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoPare;
import br.univali.portugol.nucleo.asa.NoReal;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosParametros;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Renderer for the AST tree in the UI.
 *
 */
class AstTreeCellRenderer extends DefaultTreeCellRenderer implements VisitanteASA, OutlineTreeVisitor
{
    private static final long serialVersionUID = 1L;
    private PortugolTreeNode currentPortugolTreeNode;
    private JLabel component;

    public AstTreeCellRenderer()
    {
        //setBorder(new CompoundBorder(getBorder(), new EmptyBorder(4, 0, 4, 0)));
    }
    
    
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        component = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
        if (value != null && value instanceof SourceTreeNode)
        {
            ((SourceTreeNode) value).aceitar(this);
        }
        
        //Dimension tam = component.getSize();
        
        //component.setSize(new Dimension(tam.width, tam.height + 100));
        //component.setPreferredSize(new Dimension(tam.width, tam.height + 100));
        
        return component;
    }
    
    private Icon getIcon(TipoDado tipoDado){
        String iconName = "unknown.png";
        if (tipoDado != null) {
            iconName = tipoDado.getNome()+".png";
        }
        return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, iconName);
    }

    @Override
    public Object visitar(ArvoreSintaticaAbstrataPrograma asap) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(noCaso.getClass().getSimpleName().replace("No", "").toLowerCase());
        component.setText(sb.toString());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "desvio.png"));
        return null;
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(declaracaoFuncao.getNome());
        sb.append('(');
        
        int paramCount = declaracaoFuncao.getParametros().size();
        
        for (int i = 0; i < paramCount; i++)
        {
            NoDeclaracaoParametro param = declaracaoFuncao.getParametros().get(i);
            
            sb.append(param.getTipoDado().getNome());	
            
            Quantificador quantificador = param.getQuantificador();
            
            if (quantificador == Quantificador.VETOR) {
                sb.append("[]");
            } else if (quantificador == Quantificador.MATRIZ){
                sb.append("[][]");
            }
            
            if (i < paramCount - 1)
            {
                sb.append(", ");
            }
        }
        
        sb.append(')');
        
        if (declaracaoFuncao.getTipoDado() != null)
        {
            sb.append(" : ");
            sb.append("<font color='#888888'>");
            sb.append(declaracaoFuncao.getTipoDado().getNome());
            Quantificador quantificador = declaracaoFuncao.getQuantificador();
            
            if (quantificador == Quantificador.VETOR) {
                sb.append("[]");
            } else if (quantificador == Quantificador.MATRIZ){
                sb.append("[][]");
            }
        }
        
        component.setText(sb.toString());
        if (currentPortugolTreeNode.isModificado()) {
            component.setForeground(Color.BLUE);
        }
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "funcaoDoUsuario.png"));
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
    {   
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        if (currentPortugolTreeNode.isDeclarado()) {
            sb.append("<b>");
        }
        sb.append(noDeclaracaoMatriz.getNome());
        if (currentPortugolTreeNode.isDeclarado()) {
            sb.append("</b>");
        }
        sb.append("[][]");
        sb.append(" : ");
        sb.append("<font color='#888888'>");
        sb.append(noDeclaracaoMatriz.getTipoDado().getNome());	
        component.setText(sb.toString());
        if (currentPortugolTreeNode.isModificado()) {
            component.setForeground(Color.BLUE);
        }
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "matriz.gif"));
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        if (currentPortugolTreeNode.isDeclarado()) {
            sb.append("<b>");
        }
        sb.append(noDeclaracaoVariavel.getNome());
        if (currentPortugolTreeNode.isDeclarado()) {
            sb.append("</b>");
        }
        sb.append(" : ");
        sb.append("<font color='#888888'>");
        sb.append(noDeclaracaoVariavel.getTipoDado().getNome());
        sb.append("<font color='#000000'>");
        if (currentPortugolTreeNode != null &&
                currentPortugolTreeNode.getValor() != null &&
                !(currentPortugolTreeNode.getValor() instanceof List)) {
            sb.append(" = ").append(currentPortugolTreeNode.getValor());
            }
        final String valor = sb.toString();
        component.setText(valor);
        if (currentPortugolTreeNode.isModificado()) {
            component.setForeground(Color.BLUE);
        }
        component.setIcon(getIcon(noDeclaracaoVariavel.getTipoDado()));
        return null;
    }
    
    private Dimension getSise(String s)
    {

        FontMetrics fontMetrics = component.getFontMetrics(component.getFont());
        int width = fontMetrics.stringWidth(s);
        int height = fontMetrics.getHeight();
        return new Dimension(width, height);
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        if (currentPortugolTreeNode.isDeclarado()) {
            sb.append("<b>");
        }
        sb.append(noDeclaracaoVetor.getNome());
        if (currentPortugolTreeNode.isDeclarado()) {
            sb.append("</b>");
        }
        sb.append("[]");
        sb.append(" : ");
        sb.append("<font color='#888888'>");
        sb.append(noDeclaracaoVetor.getTipoDado().getNome());	
        component.setText(sb.toString());
        if (currentPortugolTreeNode.isModificado()) {
            component.setForeground(Color.BLUE);
        }
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "vetor.gif"));
        return null;
    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(noEnquanto.getClass().getSimpleName().replace("No", "").toLowerCase());
        component.setText(sb.toString());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "loop.png"));
        return null;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(noEscolha.getClass().getSimpleName().replace("No", "").toLowerCase());
        component.setText(sb.toString());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "desvio.png"));
        return null;
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(noFacaEnquanto.getClass().getSimpleName().replace("No", "").toLowerCase());
        component.setText(sb.toString());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "loop.png"));
        return null;
    }

    @Override
    public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(noPara.getClass().getSimpleName().replace("No", "").toLowerCase());
        component.setText(sb.toString());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "loop.png"));
        return null;
    }

    @Override
    public Object visitar(NoPare noPare) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReal noReal) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(noSe.getClass().getSimpleName().replace("No", "").toLowerCase());
        component.setText(sb.toString());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "desvio.png"));
        return null;
    }

    @Override
    public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        if (currentPortugolTreeNode.isDeclarado()) {
            sb.append("<b>");
        }
        sb.append(noDeclaracaoParametro.getNome());
        if (currentPortugolTreeNode.isDeclarado()) {
            sb.append("</b>");
        }               
        Icon icon = null;
        if (noDeclaracaoParametro.getQuantificador() == Quantificador.VETOR) {
            sb.append("[]");
            icon = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "vetor.gif");
        } else if (noDeclaracaoParametro.getQuantificador() == Quantificador.MATRIZ){
            sb.append("[][]");
            icon = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "matriz.gif");
        } else {
            icon = getIcon(noDeclaracaoParametro.getTipoDado());
        }
        sb.append(" : ");
        sb.append("<font color='#888888'>");
        sb.append(noDeclaracaoParametro.getTipoDado().getNome());
        
        if (currentPortugolTreeNode != null &&
                currentPortugolTreeNode.getValor() != null &&
                !(currentPortugolTreeNode.getValor() instanceof List)) {
            sb.append(" = ").append(currentPortugolTreeNode.getValor());
               }
        
        component.setText(sb.toString());
        if (currentPortugolTreeNode.isModificado()){
            component.setForeground(Color.BLUE);
        }
        component.setIcon(icon);
        return null;
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(noInclusaoBiblioteca.getNome());
        
        if (noInclusaoBiblioteca.getAlias() != null)
        {
            sb.append(" (");
            sb.append(noInclusaoBiblioteca.getAlias());
            sb.append(")");
        }       
        
        component.setText(sb.toString());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "lib2.png"));
        return null;
    }

    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(PortugolTreeNode node)
    {
        try
        {
            this.currentPortugolTreeNode = node;
            return (node.getASTNode() != null) ? (JLabel) node.getASTNode().aceitar(this) : null;
        }
        catch (ExcecaoVisitaASA ex)
        {
            return null;
        }
    }

    @Override
    public Object visitar(LibraryFunctionTreeNode no)
    {
        StringBuilder sb = new StringBuilder("<html>");
        sb.append(no.getMetaDadosFuncao().getNome());
        sb.append('(');
        
        MetaDadosParametros parametros = no.getMetaDadosFuncao().obterMetaDadosParametros();
        
        for (int i = 0; i < parametros.quantidade(); i++)
        {
            sb.append(parametros.obter(i).getTipoDado());
            
            if (i < parametros.quantidade() - 1)
            {
                sb.append(", ");
            }
        }
        
        sb.append(')');
        
        TipoDado tipo = no.getMetaDadosFuncao().getTipoDado();
        
        if (tipo != null)
        {
            sb.append(" : ");
            sb.append("<font color='#888888'>");
            sb.append(tipo);
        }
        component.setText(sb.toString());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "funcaoDeBiblioteca.png"));
        return null;
    }

    @Override
    public Object visitar(LibraryVarTreeNode no)
    {
        
        StringBuilder sb = new StringBuilder("<html>");
        sb.append(no.getMetaDadosConstante().getNome());
        
        sb.append(" : ");
        sb.append("<font color='#888888'>");
        TipoDado tipo = no.getMetaDadosConstante().getTipoDado();
        sb.append(tipo);
        sb.append("</font> = ");        
        sb.append(no.obterValorConstante());
        
        component.setText(sb.toString());
        component.setIcon(getIcon(tipo));
        
        return null;
    }

    @Override
    public Object visitar(LibraryTreeNode no)
    {
        StringBuilder sb = new StringBuilder("<html>");

        if (no.getErro() != null) 
        {
            sb.append(no.getErro().getMessage());
        }
        else 
        {
            sb.append(no.getNoInclusaoBiblioteca().getNome());
            if (no.getNoInclusaoBiblioteca().getAlias() != null)
            {
                sb.append(" (");
                sb.append(no.getNoInclusaoBiblioteca().getAlias());
                sb.append(")");
            }
        }   
        component.setText(sb.toString());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "biblioteca.gif"));
        return null;
    }

    @Override
    public Object visitar(ValorTreeNode no)
    {
        StringBuilder sb = new StringBuilder("<html>[");
        sb.append(no.getPosicao()).append("]");
        Icon icon = getIcon(no.getTipoDado());
        if (no.getValor() != null) {
            sb.append(" = ").append(no.getValor());
        } else if (no.isColuna()) {
            icon = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "vetor.gif");
        }
        component.setText(sb.toString());
        //component.setPreferredSize(new Dimension(200,20));
        if (no.isModificado()) {
            component.setForeground(Color.BLUE);
        }
        component.setIcon(icon);
        return null;
    }

    @Override
    public Object visitar(ProgramaTreeNode no)
    {
        component.setText((String) no.getUserObject());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "programa.png"));
        return null;
    }

    @Override
    public Object visitar(BibliotecasTreeNode no)
    {
        component.setText((String) no.getUserObject());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bibliotecas.gif"));
        return null;
    }

    @Override
    public Object visitar(GenericTreeNode no)
    {
        component.setText((String) no.getUserObject());
        component.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "unknown.png"));
        return null;
    }
}
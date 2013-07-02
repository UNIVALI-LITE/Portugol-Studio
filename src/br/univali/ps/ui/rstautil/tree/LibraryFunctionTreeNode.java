package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.ps.ui.rstautil.DecoratableIcon;
import br.univali.ps.ui.rstautil.IconFactory;
import static br.univali.ps.ui.rstautil.tree.MemberTreeNode.appendType;
import static br.univali.ps.ui.rstautil.tree.PortugolTreeNode.PRIORITY_METHOD;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.Icon;
import org.fife.ui.autocomplete.Util;

/**
 * 
 * @author Luiz Fernando Noschang
 */
final class LibraryFunctionTreeNode extends PortugolTreeNode
{
    private String texto;
    private Method funcao;
    private Biblioteca biblioteca;
    
    public LibraryFunctionTreeNode(Biblioteca biblioteca, Method funcao)
    {
        super(funcao.getName(), null);
        
        String icon = IconFactory.METHOD_DEFAULT_ICON;
        int priority = PRIORITY_METHOD;

        StringBuffer sb = new StringBuffer();
        
        sb.append("<html>");
        sb.append(funcao.getName());
        sb.append('(');
        
        Class[] parametros = funcao.getParameterTypes();
        
        for (int i = 0; i < parametros.length; i++)
        {
             appendType(TipoDado.obterTipoDadoPeloTipoJava(parametros[i]), sb);
            
            if (i < parametros.length - 1)
            {
                sb.append(", ");
            }
        }
        
        sb.append(')');
        
        TipoDado tipo = TipoDado.obterTipoDadoPeloTipoJava(funcao.getReturnType());
        
        if (tipo != null)
        {
            sb.append(" : ");
            sb.append("<font color='#888888'>");
            appendType(tipo, sb);
        }

        texto = sb.toString();

        IconFactory fact = IconFactory.get();
        Icon base = fact.getIcon(icon);
        DecoratableIcon di = new DecoratableIcon(base);
        setIcon(di);

        setSortPriority(priority);
    }

    public Method getFuncao()
    {
        return funcao;
    }

    public Biblioteca getBiblioteca()
    {
        return biblioteca;
    }
    
    @Override
    public String getText(boolean selected)
    {
        return selected ? Util.stripHtml(texto).replaceAll("&lt;", "<").replaceAll("&gt;", ">") : texto;
    }
}
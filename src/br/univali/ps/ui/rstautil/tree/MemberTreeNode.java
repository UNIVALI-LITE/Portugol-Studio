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

import br.univali.portugol.nucleo.asa.No;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.ps.ui.rstautil.DecoratableIcon;
import br.univali.ps.ui.rstautil.IconFactory;
import static br.univali.ps.ui.rstautil.tree.PortugolTreeNode.PRIORITY_FIELD;
import javax.swing.Icon;

import org.fife.ui.autocomplete.Util;

/**
 * Tree node for a field or method.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class MemberTreeNode extends PortugolTreeNode
{
    private String text;

    public MemberTreeNode(String texto)
    {
        super(texto, null);
        
        String icon = IconFactory.PACKAGE_ICON;

        StringBuilder sb = new StringBuilder();
        
        sb.append("<html>");
        sb.append(texto);

        text = sb.toString();
        
        int priority = PRIORITY_TYPE;

        IconFactory fact = IconFactory.get();
        Icon base = fact.getIcon(icon);
        DecoratableIcon di = new DecoratableIcon(base);
        setIcon(di);

        setSortPriority(priority);
    }

    public MemberTreeNode(No cb)
    {
        super(cb);
        text = "<html>" + cb.getClass().getSimpleName();
        IconFactory fact = IconFactory.get();
        Icon base = fact.getIcon(IconFactory.METHOD_PRIVATE_ICON);
        DecoratableIcon di = new DecoratableIcon(base);
        int priority = PRIORITY_METHOD;

        setIcon(di);
        setSortPriority(priority);
    }

    public MemberTreeNode(NoDeclaracao field)
    {
        super(field);

        String icon = IconFactory.FIELD_DEFAULT_ICON;

        StringBuffer sb = new StringBuffer();
        
        sb.append("<html>");
        sb.append(field.getNome());
        
        if (field instanceof NoDeclaracaoVetor){
            sb.append("[]");
        } else if (field instanceof NoDeclaracaoMatriz) {
            sb.append("[][]");
        }
        
        sb.append(" : ");
        sb.append("<font color='#888888'>");

        appendType(field.getTipoDado(), sb);
        text = sb.toString();
        
        int priority = PRIORITY_FIELD;

        IconFactory fact = IconFactory.get();
        Icon base = fact.getIcon(icon);
        DecoratableIcon di = new DecoratableIcon(base);
        setIcon(di);

        setSortPriority(priority);
    }

    public MemberTreeNode(NoDeclaracaoParametro field)
    {
        super(field);

        String icon = IconFactory.FIELD_DEFAULT_ICON;

        StringBuffer sb = new StringBuffer();
        sb.append("<html>");
        sb.append(field.getNome());
        sb.append(" : ");
        sb.append("<font color='#888888'>");

        appendType(field.getTipoDado(), sb);
        text = sb.toString();
        
        int priority = PRIORITY_FIELD;

        IconFactory fact = IconFactory.get();
        Icon base = fact.getIcon(icon);
        DecoratableIcon di = new DecoratableIcon(base);
        setIcon(di);

        setSortPriority(priority);

    }

    public MemberTreeNode(NoDeclaracaoFuncao method)
    {

        super(method);

        String icon = IconFactory.METHOD_DEFAULT_ICON;
        int priority = PRIORITY_METHOD;

        StringBuffer sb = new StringBuffer();
        
        sb.append("<html>");
        sb.append(method.getNome());
        sb.append('(');
        
        int paramCount = method.getParametros().size();
        
        for (int i = 0; i < paramCount; i++)
        {
            NoDeclaracaoParametro param = method.getParametros().get(i);
            
            appendType(param.getTipoDado(), sb);
            
            if (i < paramCount - 1)
            {
                sb.append(", ");
            }
        }
        
        sb.append(')');
        
        if (method.getTipoDado() != null)
        {
            sb.append(" : ");
            sb.append("<font color='#888888'>");
            appendType(method.getTipoDado(), sb);
        }

        text = sb.toString();

        IconFactory fact = IconFactory.get();
        Icon base = fact.getIcon(icon);
        DecoratableIcon di = new DecoratableIcon(base);
        setIcon(di);

        setSortPriority(priority);

    }

    static void appendType(TipoDado type, StringBuffer sb)
    {
        if (type != null)
        {
            String t = type.getNome();
            t = t.replaceAll("<", "&lt;");
            t = t.replaceAll(">", "&gt;");
            sb.append(t);
        }
    }

    @Override
    public String getText(boolean selected)
    {
        return selected ? Util.stripHtml(text).replaceAll("&lt;", "<").replaceAll("&gt;", ">") : text;
    }
}
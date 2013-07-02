package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.ps.ui.rstautil.IconFactory;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.fife.ui.autocomplete.Util;

/**
 * 
 * @author Luiz Fernando Noschang
 */
final class LibraryVarTreeNode extends PortugolTreeNode
{
    private static final int NUMERO_CARACTERES_EXIBIDOS = 12;
    private static final NumberFormat formatadorNumero = new DecimalFormat("#,##0.0000000000000000");
    
    private Field variable;
    private Biblioteca library;
    
    private String texto;
    
    public LibraryVarTreeNode(Biblioteca biblioteca, Field variavel)
    {
        super(variavel.getName(), null);
        
        String valor = obterValorVariavel(biblioteca, variavel.getName());
        
        setIcon(IconFactory.get().getIcon(IconFactory.LOCAL_VARIABLE_ICON));
        setSortPriority(PRIORITY_LOCAL_VAR);

        StringBuffer sb = new StringBuffer();
        
        sb.append("<html>");
        sb.append(variavel.getName());
        
        sb.append(" (");
        sb.append(valor);
        sb.append(")");
        
        sb.append(" : ");
        sb.append("<font color='#888888'>");
        
        MemberTreeNode.appendType(TipoDado.obterTipoDadoPeloTipoJava(variavel.getType()), sb);
        
        this.variable = variavel;
        this.library = biblioteca;
        this.texto = sb.toString();
    }
    
    public Field getVariable()
    {
        return variable;
    }

    public Biblioteca getLibrary()
    {
        return library;
    }
    
    @Override
    public String getText(boolean selected)
    {
        return selected ? Util.stripHtml(texto).replaceAll("&lt;", "<").replaceAll("&gt;", ">") : texto;
    }

    private String obterValorVariavel(Biblioteca biblioteca, String nome)
    {
        String valorFormatado = "Indefinido";
        
        try
        {
            Object valor = biblioteca.getValorVariavel(nome);
            
            if (valor instanceof Double)
            {
                valorFormatado = formatadorNumero.format((Double) valor).replace(",", ".");
            }

            else

            if (valor instanceof Boolean)
            {
                valorFormatado = ((Boolean) valor)? "verdadeiro" : "falso";
            }

            else

            if (valor instanceof Integer)
            {
                valorFormatado = ((Integer) valor).toString();
            }

            else

            if (valor instanceof Character)
            {
                valorFormatado = String.format("'%s'", valor);
            }

            else

            if (valor instanceof String)
            {
                valorFormatado = String.format("\"%s\"", valor);
            }
            
            if (valorFormatado.length() > NUMERO_CARACTERES_EXIBIDOS)
            {
                valorFormatado = valorFormatado.substring(0, NUMERO_CARACTERES_EXIBIDOS).concat("...");
            }
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }
        
        return valorFormatado;
    }
}
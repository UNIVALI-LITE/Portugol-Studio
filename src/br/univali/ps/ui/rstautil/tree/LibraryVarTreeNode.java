package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;

final class LibraryVarTreeNode extends SourceTreeNode
{
    private static final int NUMERO_CARACTERES_EXIBIDOS = 12;
    private static final NumberFormat formatadorNumero = new DecimalFormat("#,##0.0000000000000000");
    
    private Field variable;
    private Biblioteca library;
    
    
    public LibraryVarTreeNode(Biblioteca biblioteca, Field variavel)
    {
        super(variavel);
        this.variable = variavel;
        this.library = biblioteca;
    }
    
    public Field getVariable()
    {
        return variable;
    }

    public Biblioteca getLibrary()
    {
        return library;
    }
  
    public String obterValorVariavel()
    {
        String valorFormatado = "Indefinido";
        
        try
        {
            Object valor = this.library.getValorVariavel(this.variable.getName());
            
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

    @Override
    Object aceitar(OutlineTreeVisitor visitor)
    {
        return visitor.visitar(this);
    }
}
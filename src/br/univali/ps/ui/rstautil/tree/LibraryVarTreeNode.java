package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosConstante;
import java.text.DecimalFormat;
import java.text.NumberFormat;

final class LibraryVarTreeNode extends SourceTreeNode
{
    private static final int NUMERO_CARACTERES_EXIBIDOS = 12;
    private static final NumberFormat formatadorNumero = new DecimalFormat("#,##0.0000000000000000");
    
    private MetaDadosConstante metaDadosConstante;
    private MetaDadosBiblioteca metaDadosBiblioteca;
    
    
    public LibraryVarTreeNode(MetaDadosBiblioteca metaDadosBiblioteca, MetaDadosConstante metaDadosConstante)
    {
        super(metaDadosConstante);
        this.metaDadosConstante = metaDadosConstante;
        this.metaDadosBiblioteca = metaDadosBiblioteca;
    }

    public MetaDadosConstante getMetaDadosConstante()
    {
        return metaDadosConstante;
    }

    public MetaDadosBiblioteca getMetaDadosBiblioteca()
    {
        return metaDadosBiblioteca;
    }
  
    public String obterValorConstante()
    {
        String valorFormatado = "Indefinido";
        
        try
        {
            //Object valor = this.metaDadosBiblioteca.getValorVariavel(this.metaDadosConstante.getNome());
            Object valor = "indefinido";
            
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
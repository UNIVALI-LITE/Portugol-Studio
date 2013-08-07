package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.TipoDado;


public class ValorTreeNode extends SourceTreeNode
{
    private boolean coluna = false;
    private int posicao; 
    private Object valor;
    private TipoDado tipoDado;

    public ValorTreeNode(int posicao, Object valor, TipoDado tipoDado)
    {
        super(posicao);
        this.posicao = posicao;
        this.valor = valor;
        this.tipoDado = tipoDado;
    }

    public TipoDado getTipoDado()
    {
        return tipoDado;
    }

    public boolean isColuna()
    {
        return coluna;
    }

    public void setColuna(boolean coluna)
    {
        this.coluna = coluna;
    }

    public void setValor(Object valor)
    {
        this.valor = valor;
    }
    
    public int getPosicao()
    {
        return posicao;
    }

    public Object getValor()
    {
        return valor;
    }
   
    @Override
    Object aceitar(OutlineTreeVisitor visitor)
    {
        return visitor.visitar(this);
    }

    @Override
    public String toString()
    {
        String s = "["+posicao+"]";
        if (valor != null)
            s += " = " + valor;
        return s;
    }
    
    
}

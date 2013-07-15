package br.univali.ps.ui.rstautil.tree;


public class ValorTreeNode extends SourceTreeNode
{
    private boolean coluna = false;
    private int posicao; 
    private Object valor;

    public ValorTreeNode(int posicao, Object valor)
    {
        super(valor);
        this.posicao = posicao;
        this.valor = valor;
    }

    public boolean isColuna()
    {
        return coluna;
    }

    public void setColuna(boolean coluna)
    {
        this.coluna = coluna;
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
}

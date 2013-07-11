package br.univali.ps.ui.rstautil.tree;


public class ValorTreeNode extends SourceTreeNode
{
    private int posicao; 
    private Object valor;

    public ValorTreeNode(int posicao, Object valor)
    {
        super(null);
        this.posicao = posicao;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

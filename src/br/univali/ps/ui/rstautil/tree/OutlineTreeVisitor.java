package br.univali.ps.ui.rstautil.tree;


interface OutlineTreeVisitor
{
    public Object visitar(BibliotecasTreeNode no);
    public Object visitar(PortugolTreeNode no);
    public Object visitar(ProgramaTreeNode no);
    public Object visitar(LibraryFunctionTreeNode no);
    public Object visitar(LibraryVarTreeNode no);
    public Object visitar(LibraryTreeNode no);
    public Object visitar(ValorTreeNode no);
    public Object visitar(GenericTreeNode no);
}

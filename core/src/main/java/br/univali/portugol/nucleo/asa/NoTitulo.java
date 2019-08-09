package br.univali.portugol.nucleo.asa;

/**
 *
 * @author Nereu
 */
public class NoTitulo extends NoBloco
{
    private String nome;

    public NoTitulo(String nome)
    {
        this.nome = nome;
    }
    
    /**
     * @return the nome
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }
}

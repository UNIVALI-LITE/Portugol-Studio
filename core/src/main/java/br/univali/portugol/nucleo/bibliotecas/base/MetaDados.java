package br.univali.portugol.nucleo.bibliotecas.base;

/**
 *
 * @author Luiz Fernando Noschang
 */
abstract class MetaDados
{
    private String nome;
    
    MetaDados()
    {

    }

    public final String getNome()
    {
        return nome;
    }

    final void setNome(String nome)
    {
        this.nome = nome;
    }
}

package br.univali.portugol.nucleo.simbolos;

/**
 *
 * @author Luiz Fernando
 */
public class ExcecaoSimboloNaoDeclarado extends Exception
{
    private String nome;

    public ExcecaoSimboloNaoDeclarado(String nome)
    {
        this.nome = nome;
    }

    public String getNome()
    {
        return nome;
    }
}

package br.univali.portugol.nucleo.bibliotecas.base;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroCarregamentoBiblioteca extends Exception
{
    private String nome;
    
    public ErroCarregamentoBiblioteca(String nome, String mensagem)
    {
        super(String.format("Erro ao carregar a biblioteca \"%s\": %s", nome, mensagem));
        this.nome = nome;
    }

    public ErroCarregamentoBiblioteca(String nome, Throwable causa)
    {
        super(String.format("Erro ao carregar a biblioteca \"%s\": %s", nome, causa.getMessage()), causa);
        this.nome = nome;
    }    
    
    public String getNome()
    {
        return nome;
    }
}

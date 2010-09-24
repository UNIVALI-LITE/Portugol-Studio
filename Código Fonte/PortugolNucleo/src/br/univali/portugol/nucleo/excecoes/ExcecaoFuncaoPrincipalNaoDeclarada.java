package br.univali.portugol.nucleo.excecoes;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public class ExcecaoFuncaoPrincipalNaoDeclarada extends Exception
{
    private static final long serialVersionUID = 6751504464638080224L;
    private String nomeFuncaoPrincipal;

    public ExcecaoFuncaoPrincipalNaoDeclarada(String nome)
    {
            super(construirMensagem(nome));
            this.nomeFuncaoPrincipal = nome;
    }

    public String getNomeFuncaoPrincipal()
    {
        return nomeFuncaoPrincipal;
    }
	

    private static String construirMensagem(String nomeFuncaoPrincipal)
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("A função principal \"");
        construtorString.append(nomeFuncaoPrincipal);
        construtorString.append("\" não foi declarada.");

        return construtorString.toString();
    }
}

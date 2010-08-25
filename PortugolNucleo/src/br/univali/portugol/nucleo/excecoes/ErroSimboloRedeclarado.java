package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.simbolos.Funcao;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;

public final class ErroSimboloRedeclarado extends Erro
{
    private static final long serialVersionUID = 3697051020139726117L;

    private Simbolo simboloExistente;
    private Simbolo simboloRedeclarado;

    public ErroSimboloRedeclarado(Simbolo simboloRedeclarado, Simbolo simboloExistente)
    {
        this.simboloRedeclarado = simboloRedeclarado;
        this.simboloExistente = simboloExistente;

        setLinha(simboloRedeclarado.getTokenNome().getLinha());
        setColuna(simboloRedeclarado.getTokenNome().getColuna());
    }

    public Simbolo getSimboloRedeclarado()
    {
        return simboloRedeclarado;
    }

    public Simbolo getSimboloExistente()
    {
        return simboloExistente;
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("O símbolo \"");
        construtorString.append(simboloRedeclarado.getNome());
        construtorString.append("\" já foi declarado como ");

        if (simboloRedeclarado instanceof Vetor) construtorString.append("um vetor");
        else
        if (simboloRedeclarado instanceof Matriz) construtorString.append("uma matriz");
        else
        if (simboloRedeclarado instanceof Variavel) construtorString.append("uma variável");
        else
        if (simboloRedeclarado instanceof Funcao) construtorString.append("uma função");

        construtorString.append(" na linha: ");
        construtorString.append(simboloExistente.getTokenNome().getLinha());
        construtorString.append(", coluna: ");
        construtorString.append(simboloExistente.getTokenNome().getColuna());

        return construtorString.toString();
    }
}

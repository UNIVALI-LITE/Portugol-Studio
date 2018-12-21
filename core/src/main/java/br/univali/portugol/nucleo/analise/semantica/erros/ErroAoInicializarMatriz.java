package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.GeradorDeExemplosDeInicializacao;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.simbolos.Matriz;

/**
 *
 * @author Fillipi Domingos Pelz
 * @author Luiz Fernando Noschang
 * @since 03/07/2016
 */
public class ErroAoInicializarMatriz extends ErroSemantico
{
    private final int numeroLinhas;
    private final int numeroColunas;
    private final Matriz matriz;
    private String codigo = "ErroSemantico.ErroAoInicializarMatriz";

    public ErroAoInicializarMatriz(Matriz matriz, TrechoCodigoFonte trechoCodigoFonte, int numeroLinhas, int numeroColunas)
    {
        super(trechoCodigoFonte);
        this.matriz = matriz;
        this.numeroLinhas = numeroLinhas;
        this.numeroColunas = numeroColunas;
        super.setCodigo(codigo);	
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("A matriz '%s' deve ser inicializada com uma matriz literal. Exemplo: ", matriz.getNome()));
        builder.append(GeradorDeExemplosDeInicializacao.gerarExemploDeInicializacaoDeMatriz(matriz, numeroLinhas, numeroColunas));
        

        return builder.toString();
    }
}

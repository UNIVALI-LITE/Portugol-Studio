package br.univali.portugol.nucleo;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class GeradorDeExemplosDeInicializacao
{
    private static final Random rnd = new Random(System.currentTimeMillis());
    private static final DecimalFormat format = buildFormat();
    private static final String[] cadeias = new String[]
    {
        "barco", "estrela", "maçã", "pato", "cachorro", "gato", "leão", "azul", "vermelho", "branco", "preto", "amarelo", "banana", "árvore", "laranja", "criança", "saúde", "luz", "paz", "verde", "marrom", "rosa", "tio", "tia", "pai", "mãe", "professor", "aluno"
    };

    private static final int minimoInteiro = -50;
    private static final int maximoInteiro = 50;

    public static String gerarExemploDeInicializacaoDeMatriz(Matriz matriz, int numeroLinhas, int numeroColunas)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(matriz.getTipoDado().getNome());
        builder.append(" ");
        builder.append(matriz.getNome());

        if (numeroLinhas == 0 || numeroColunas == 0)
        {
            builder.append("[][]");
        }
        else
        {
            builder.append("[");
            builder.append(numeroLinhas);
            builder.append("][");
            builder.append(numeroColunas);
            builder.append("]");
        }

        builder.append(" = {");

        int maximoLinhas = (numeroLinhas > 3 || numeroLinhas == 0) ? 3 : numeroLinhas;
        int maximoColunas = (numeroColunas > 3 || numeroColunas == 0) ? 3 : numeroColunas;

        for (int i = 0; i < maximoLinhas; i++)
        {
            builder.append("{");

            for (int j = 0; j < maximoColunas; j++)
            {
                builder.append(GeradorDeExemplosDeInicializacao.gerarValorAleatorio(matriz.getTipoDado()));

                if (j < maximoColunas - 1)
                {
                    builder.append(", ");
                }
                else
                {
                    if (numeroColunas > 3)
                    {
                        builder.append(", ...");
                    }
                }
            }

            if (i < maximoLinhas - 1)
            {
                builder.append("}, ");
            }
            else
            {
                if (numeroLinhas > 3)
                {
                    builder.append("}, ... }");
                }
                else
                {
                    builder.append("}}");
                }
            }
        }

        return builder.toString();
    }

    public static String gerarExemploDeInicializacaoDeVetor(Vetor vetor, int tamanhoVetor)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(vetor.getTipoDado().getNome());
        builder.append(" ");
        builder.append(vetor.getNome());

        if (tamanhoVetor == 0)
        {
            builder.append("[]");
        }
        else
        {
            builder.append("[");
            builder.append(tamanhoVetor);
            builder.append("]");
        }

        builder.append(" = {");

        int tamanhoMaximo = (tamanhoVetor > 4 || tamanhoVetor == 0) ? 4 : tamanhoVetor;

        for (int i = 0; i < tamanhoMaximo; i++)
        {
            builder.append(GeradorDeExemplosDeInicializacao.gerarValorAleatorio(vetor.getTipoDado()));

            if (i < tamanhoMaximo - 1)
            {
                builder.append(", ");
            }
        }

        if (tamanhoVetor > 4)
        {
            builder.append(", ... }");
        }
        else
        {
            builder.append("}");
        }
        
        return builder.toString();
    }
    
    public static String gerarExemploDeInicializacaoDeVariavel(Variavel variavel)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(variavel.getTipoDado().getNome());
        builder.append(" ");
        builder.append(variavel.getNome());
        builder.append(" = ");
        builder.append(gerarValorAleatorio(variavel.getTipoDado()));
        
        return builder.toString();

    }

    public static String gerarValorAleatorio(TipoDado tipoDado)
    {
        switch (tipoDado)
        {
            case INTEIRO:
                return Integer.toString(minimoInteiro + rnd.nextInt(maximoInteiro + 1 - minimoInteiro));
            case CARACTER:
                return "'" + ((char) (65 + rnd.nextInt(26))) + "'";
            case REAL:
                return format.format(rnd.nextFloat() * rnd.nextInt(101));
            case CADEIA:
                return "\"" + cadeias[rnd.nextInt(cadeias.length)] + "\"";
            case LOGICO:
            {
                int valor = rnd.nextInt(2);

                if (valor == 0)
                {
                    return "falso";
                }
                else
                {
                    return "verdadeiro";
                }
            }
            default:
                break;
        }

        return "?";
    }

    private static DecimalFormat buildFormat()
    {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');

        return new DecimalFormat("0.00", symbols);
    }
}

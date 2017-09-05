package br.univali.ps.ui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.BadLocationException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class EscopoCursor
{
    private static final Pattern padraoDeteccaoNomeEscopo = Pattern.compile("funcao([^\\(]+)");
    private static final Pattern padraoDeteccaoNivelEscopo = Pattern.compile("\\{|\\}");
    private static final char[] caracteresParada = new char[]
    {
        ' ', '\r', '\t', '\n'
    };

    private final String nome;
    private final int profundidade;
    private final int linha;
    private final int coluna;

    public EscopoCursor(String nome, int profundidade, int linha, int coluna)
    {
        this.nome = nome;
        this.profundidade = profundidade;
        this.linha = linha;
        this.coluna = coluna;
    }

    public String getNome()
    {
        return nome;
    }

    public int getProfundidade()
    {
        return profundidade;
    }

    public int getLinha()
    {
        return linha;
    }

    public int getColuna()
    {
        return coluna;
    }

    public static EscopoCursor localizar(RSyntaxTextArea textArea)
    {
        int linha = textArea.getCaretLineNumber() + 1;
        int coluna = textArea.getCaretOffsetFromLineStart();

        try
        {
            String text = textArea.getText(0, textArea.getCaretPosition());

            int nivel = getNivelEscopo(text);
            String nome = getNomeEscopo(text, nivel);

            return new EscopoCursor(nome, nivel, linha, coluna);

        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace(System.out);
        }

        return new EscopoCursor("indefinido", 0, linha, coluna);
    }

    private static String getNomeEscopo(String texto, int nivel)
    {
        String nome = null;
        Matcher avaliadorNome = padraoDeteccaoNomeEscopo.matcher(texto);

        while (avaliadorNome.find())
        {
            int inicio = 0;
            String temp = avaliadorNome.group(1).trim();

            for (int i = temp.length() - 1; i > 0; i--)
            {
                if (caracterParada(temp.charAt(i)))
                {
                    inicio = i + 1;
                    break;
                }
            }

            nome = temp.substring(inicio);
        }

        if (nivel == 0)
        {
            nome = "indefinido";
        }
        else if (nivel == 1)
        {
            nome = "programa";
        }

        return nome;
    }

    private static int getNivelEscopo(String texto)
    {
        int nivel = 0;
        Matcher avaliadorNivel = padraoDeteccaoNivelEscopo.matcher(texto);

        while (avaliadorNivel.find())
        {
            switch (avaliadorNivel.group())
            {
                case "{":
                    nivel++;
                    break;
                case "}":
                    nivel--;
                    break;
            }
        }

        return nivel;
    }

    private static boolean caracterParada(char caracter)
    {
        for (int i = 0; i < caracteresParada.length; i++)
        {
            if (caracter == caracteresParada[i])
            {
                return true;
            }
        }

        return false;
    }
}

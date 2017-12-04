package br.univali.ps.ui.editor.formatador;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Elieser
 */
public class FormatadorCodigoTest
{
    
    @Test
    public void testEscolha() throws Exception
    {
        String codigo = "programa {                             "
                + "    funcao inicio() {                        "
                + "         inteiro x = 0                       "                                
                + "         escolha (x) {                       "
                + "             caso 0:                         "
                + "                 escreva(\"teste\")          "
                + "                 pare                        "                
                + "             caso 1:                         "
                + "                 escreva(\"tiste\")          "                
                + "                 pare                        "                                
                + "             caso contrario:                 "                
                + "                 escreva(\"toste\")          "                
                + "         }                                   "                
                + "    }                                        "
                + "}                                            ";

        String esperado = "programa\n"
                + "{\n"
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "        inteiro x = 0\n"                                
                + "        escolha (x) {\n"
                + "            caso 0:\n"
                + "                escreva(\"teste\")\n"
                + "                pare\n"
                + "            caso 1:\n"
                + "                escreva(\"tiste\")\n"
                + "                pare\n"
                + "            caso contrario:\n"
                + "                escreva(\"toste\")\n"
                + "        }\n"                
                + "    }\n"
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        //System.out.println(formatado);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }
    
    @Test
    public void testSe() throws Exception
    {
        String codigo = "programa {                             "
                + "    funcao inicio() {                        "
                + "         inteiro x = 0                       "                                
                + "         se (x < 10) {                 "
                + "             se (x < 10)             "
                + "                 escreva(\"teste\")          "
                + "             senao"
                + "                 escreva(\"toste\")          "                
                + "             x++                             "
                + "         }                                   "                
                + "    }                                        "
                + "}                                            ";

        String esperado = "programa\n"
                + "{\n"
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "        inteiro x = 0\n"
                + "        se (x < 10) {\n"
                + "            se (x < 10) {\n"
                + "                escreva(\"teste\")\n"
                + "            }\n"                                
                + "            senao {\n"
                + "                escreva(\"toste\")\n"                                
                + "            }\n"                
                + "            x = x + 1\n"
                + "        }\n"
                + "    }\n"
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        //System.out.println(formatado);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }
    
    @Test
    public void testEnquanto() throws Exception
    {
        String codigo = "programa {                             "
                + "    funcao inicio() {                        "
                + "         inteiro x = 0                       "                                
                + "         enquanto (x < 10) {                 "
                + "             enquanto (x < 10)             "
                + "                 escreva(\"teste\")          "
                + "             x++                             "
                + "         }                                   "                
                + "    }                                        "
                + "}                                            ";

        String esperado = "programa\n"
                + "{\n"
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "        inteiro x = 0\n"
                + "        enquanto (x < 10) {\n"
                + "            enquanto (x < 10) {\n"
                + "                escreva(\"teste\")\n"
                + "            }\n"                
                + "            x = x + 1\n"
                + "        }\n"
                + "    }\n"
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        //System.out.println(formatado);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }
    
    @Test
    public void testRetorne() throws Exception
    {
        String codigo = "programa {                                             "
                + "    funcao inicio() {                                        "
                + "    }                                                        "
                
                + "    funcao inteiro teste(){"
                + "         retorne 0"
                + "    }"
                + "    funcao teste2(){"
                + "         retorne"
                + "    }"                
                + "}";

        String esperado = "programa\n"
                + "{\n"
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "\n"
                + "    }\n"
                + "\n"
                + "    funcao inteiro teste()\n"
                + "    {\n"
                + "        retorne 0\n"
                + "    }\n"
                + "\n"                
                + "    funcao teste2()\n"
                + "    {\n"
                + "        retorne\n"
                + "    }\n"                
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        //System.out.println(formatado);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }

    @Test
    public void testChamadaFuncao() throws Exception
    {
        String codigo = "programa {                                             "
                + "    funcao inicio() {                                        "
                + "         caracter v[3]                                       "
                + "         logico m[2][2]                                      "
                + "         inteiro x = 10                                      "                
                
                + "         teste(0, x, v, m)                                      "                
                + "         teste(10 / 3, x, v, m)                                       "                                
                + "         teste(x, x, v, m)                                       "                                                
                + "    }                                                        "
                
                + "    funcao teste(inteiro x, inteiro &x2, caracter vetor[], logico m[][]){} "
                
                + "}";

        String esperado = "programa\n"
                + "{\n"
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "        caracter v[3]\n"
                + "        logico m[2][2]\n"
                + "        inteiro x = 10\n"                
                + "        teste(0, x, v, m)\n"                
                + "        teste(10 / 3, x, v, m)\n"                                
                + "        teste(x, x, v, m)\n"                                                
                + "    }\n"
                + "\n"
                + "    funcao teste(inteiro x, inteiro &x2, caracter vetor[], logico m[][])\n"
                + "    {\n"
                + "\n"
                + "    }\n"
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }

    @Test
    public void testOperacoes() throws Exception
    {
        String codigo = "programa{"
                + "    funcao inicio() {"
                + "         inteiro x = 10 + 3"
                + "         x = 10 / 3"
                + "         inteiro y = (x % 3 * 2) / 4"
                + "         inteiro v[] = {1, 2, 3}"
                + "         inteiro vet[3]"
                + "         inteiro m[][] = {{1, 2}, {2, 3}}"
                + "         inteiro mat[3][2]"
                + "         mat[1][1] = v[10 / 3]"
                + "         mat[0][0] = v[m[0][0]]"
                + "         logico teste = (2 >= 3 e 1 < 17) ou falso"                
                + "    }"
                + "}";

        String esperado = "programa\n"
                + "{\n"
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "        inteiro x = 10 + 3\n"
                + "        x = 10 / 3\n"
                + "        inteiro y = (x % 3 * 2) / 4\n"
                + "        inteiro v[] = {1, 2, 3}\n"
                + "        inteiro vet[3]\n"
                + "        inteiro m[][] = {{1, 2}, {2, 3}}\n"
                + "        inteiro mat[3][2]\n"
                + "        mat[1][1] = v[10 / 3]\n"
                + "        mat[0][0] = v[m[0][0]]\n"
                + "        logico teste = (2 >= 3 e 1 < 17) ou falso\n"                
                + "    }\n"
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        //System.out.println(formatado);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }

    @Test
    public void testDeclaracaoFuncao() throws Exception
    {
        String codigo = "programa {"
                + "    funcao inicio() {}"
                + "    funcao teste(inteiro x, logico flag, caracter vetor[]) {}"
                + "}";

        String esperado = "programa\n"
                + "{\n"
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "\n"
                + "    }\n"
                + "\n"
                + "    funcao teste(inteiro x, logico flag, caracter vetor[])\n"
                + "    {\n"
                + "\n"
                + "    }\n"
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }

    @Test
    public void testImportacaoBiblioteca() throws Exception
    {
        String codigo = "programa {"
                + "    inclua biblioteca Graficos"
                + "    inclua biblioteca Matematica --> m"
                + "    funcao inicio() {}"
                + "}";

        String esperado = "programa\n"
                + "{\n"
                + "    inclua biblioteca Graficos\n"
                + "    inclua biblioteca Matematica --> m\n"
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "\n"
                + "    }\n"
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }

    @Test
    public void testProgramaVazio() throws Exception
    {
        String codigo = "programa{"
                + "    funcao inicio() {"
                + "    }"
                + "}";

        String esperado = "programa\n"
                + "{\n"
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "\n"
                + "    }\n"
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }

}

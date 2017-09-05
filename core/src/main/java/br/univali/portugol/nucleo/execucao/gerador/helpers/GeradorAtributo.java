package br.univali.portugol.nucleo.execucao.gerador.helpers;

import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import java.io.PrintWriter;

/**
 * @author Elieser
 */
public class GeradorAtributo
{
    public void gera(NoInclusaoBiblioteca biblioteca, PrintWriter saida, int nivelEscopo)
    {
        String tipo = biblioteca.getNome();
        String nome = tipo; // o nome da variável é sempre idêntico ao tipo, o alias da biblioteca é ignorado na geração do código Java
        
        saida.append(Utils.geraIdentacao(nivelEscopo))
                .format("private final %s %s = new %s();", tipo, nome, tipo)
                .println();
    }

    public boolean gera(NoDeclaracao no, PrintWriter saida, VisitanteASA visitor, int nivelEscopo) throws ExcecaoVisitaASA
    {
        if (podeDeclararComoAtributo(no))
        {
            saida.append(Utils.geraIdentacao(nivelEscopo))
                    .append("private ")
                    .append(no.constante() ? "final " : "");

            no.aceitar(visitor);

            saida.append(";").println();

            return true;
        }

        return false;
    }

    private static boolean podeDeclararComoAtributo(NoDeclaracao no)
    {
        return no instanceof NoDeclaracaoVariavel
                || no instanceof NoDeclaracaoVetor
                || no instanceof NoDeclaracaoMatriz;
    }

}

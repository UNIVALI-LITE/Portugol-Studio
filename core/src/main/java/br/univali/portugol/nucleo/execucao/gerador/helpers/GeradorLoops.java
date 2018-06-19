package br.univali.portugol.nucleo.execucao.gerador.helpers;

import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoLogico;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import br.univali.portugol.nucleo.execucao.gerador.GeradorCodigoJava;
import java.io.PrintWriter;
import java.util.List;

public class GeradorLoops
{

    public void gera(NoEnquanto no, PrintWriter saida, VisitanteASA visitor, int nivelEscopo, GeradorCodigoJava.Opcoes opcoes, long seed) throws ExcecaoVisitaASA
    {
        boolean loopInfinito = loopInfinito(no);

        /**
         * Quando detecta um loop infinito (uma constante sendo usada como
         * condição) declara uma flag fora do loop para evitar "Unreacheable
         * statement" no java.
         */
        String flag = "flag_" + String.valueOf(seed);
        if (loopInfinito) {
            saida.format("boolean %s =", flag);
            no.getCondicao().aceitar(visitor);
            saida.append(";");
        }

        saida.append("while(");

        if (!loopInfinito) {
            no.getCondicao().aceitar(visitor);
        } else {
            saida.append(flag); // usa flag como condição do loop para 'enganar' o java e impedir que ele detecte o 'Unreacheable Stament' error
        }

        saida.append(")").println();

        String identacao = Utils.geraIdentacao(nivelEscopo);

        saida.append(identacao).append("{").println();

        if (opcoes.gerandoCodigoParaInterrupcaoDeThread) {
            Utils.geraVerificacaoThreadInterrompida(saida, nivelEscopo);
        }

        Utils.visitarBlocos(no.getBlocos(), saida, visitor, nivelEscopo, opcoes, seed);

        saida.println();

        saida.append(identacao)
                .append("}")
                .println();
    }

    public void gera(NoFacaEnquanto no, PrintWriter saida, VisitanteASA visitor, int nivelEscopo, GeradorCodigoJava.Opcoes opcoes, long seed) throws ExcecaoVisitaASA
    {
        String identacao = Utils.geraIdentacao(nivelEscopo);

        boolean loopInfinito = loopInfinito(no);

        /**
         * Quando detecta um loop infinito (uma constante sendo usada como
         * condição) declara uma flag fora do loop para evitar "Unreacheable
         * statement" no java.
         */
        
        String flag = "flag_" + String.valueOf(seed);
        if (loopInfinito) {
            saida.format("boolean %s =", flag);
            no.getCondicao().aceitar(visitor);
            saida.append(";");
        }
        
        saida.append("do").println();
        saida.append(identacao).append("{").println();

        if (opcoes.gerandoCodigoParaInterrupcaoDeThread) {
            Utils.geraVerificacaoThreadInterrompida(saida, nivelEscopo);
        }

        List<NoBloco> blocos = no.getBlocos();
        if (blocos != null) {
            Utils.visitarBlocos(blocos, saida, visitor, nivelEscopo, opcoes, seed);
            saida.println();
        }

        saida.append(identacao).append("}").println();

        saida.append(identacao).append("while(");

        if (!loopInfinito) {
            no.getCondicao().aceitar(visitor);
        }
        else { // usa flag como condição para o loop
            saida.append(flag);
        }

        saida.append(");").println();
    }

    private boolean loopInfinito(NoEnquanto loop)
    {
        NoExpressao condicao = loop.getCondicao();
        return condicao instanceof NoLogico;
    }
    
    private boolean loopInfinito(NoFacaEnquanto loop)
    {
        NoExpressao condicao = loop.getCondicao();
        return condicao instanceof NoLogico;
    }
}

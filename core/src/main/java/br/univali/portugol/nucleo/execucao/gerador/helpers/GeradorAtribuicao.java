package br.univali.portugol.nucleo.execucao.gerador.helpers;

import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoOperacao;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import java.io.PrintWriter;

/**
 * @author Elieser
 */
public class GeradorAtribuicao
{
    public void gera(NoOperacaoAtribuicao no, PrintWriter saida, VisitanteASA visitor, 
            int nivelEscopo) throws ExcecaoVisitaASA
    {
        NoExpressao opEsquerdo = no.getOperandoEsquerdo();
        NoExpressao opDireito = no.getOperandoDireito();

        opEsquerdo.aceitar(visitor);

        saida.append(" = ");

        boolean precisaConverterTipo = precisaConverterTipo(opEsquerdo, opDireito);
        if (precisaConverterTipo)
        {
            saida.append("(int)");
        }

        boolean opDireitoEhOperacao = opDireito instanceof NoOperacao;
        if (precisaConverterTipo && opDireitoEhOperacao) // coloca toda a operação dentro de parênteses para que o cast seja aplicado no resultado da operação
        {
            saida.append("(");
        }

        no.getOperandoDireito().aceitar(visitor);

        if (precisaConverterTipo && opDireitoEhOperacao) // coloca toda a operação dentro de parênteses para que o cast seja aplicado no resultado da operação
        {
            saida.append(")");
        }
    }
    
    private static boolean precisaConverterTipo(NoExpressao opEsquerdo, NoExpressao opDireito)
    {
        TipoDado tipoOpEsquerdo = opEsquerdo.getTipoResultante();
        TipoDado tipoOpDireito = opDireito.getTipoResultante();
        return tipoOpEsquerdo == TipoDado.INTEIRO && tipoOpDireito == TipoDado.REAL;    
    }
}

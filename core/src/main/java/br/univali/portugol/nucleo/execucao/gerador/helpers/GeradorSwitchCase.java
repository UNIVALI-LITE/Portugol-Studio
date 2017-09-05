package br.univali.portugol.nucleo.execucao.gerador.helpers;

import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import br.univali.portugol.nucleo.execucao.gerador.GeradorCodigoJava;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Elieser
 */
public class GeradorSwitchCase
{
    public static final String NOME_VARIAVEL_BREAK = "___sw_break___";

    public void geraSwitchCase(NoEscolha no, PrintWriter saida, VisitanteASA visitor, 
            int nivelEscopo, GeradorCodigoJava.Opcoes opcoes, long seed) throws ExcecaoVisitaASA
    {
        String identacao = Utils.geraIdentacao(nivelEscopo);

        saida.append("switch(");

        no.getExpressao().aceitar(visitor);

        saida.append(")").println();
        saida.append(identacao).append("{").println();

        List<NoCaso> casos = no.getCasos();
        if (casos != null)
        {
            for (NoCaso caso : casos)
            {
                NoExpressao expressaoCaso = caso.getExpressao();
                if (expressaoCaso != null)
                {
                    saida.append(identacao).append("case ");

                    expressaoCaso.aceitar(visitor);

                    saida.append(":").println();
                }
                else
                {
                    saida.append(identacao).append("default:");
                }

                Utils.visitarBlocos(caso.getBlocos(), saida, visitor, nivelEscopo, opcoes, seed);

                saida.println();
            }
        }

        saida.append(identacao).append("}").println();

    }

    public static String geraNomeVariavelBreak(int nivelEscopo) 
    {
        return NOME_VARIAVEL_BREAK + nivelEscopo;
    }
    
    public void geraSeSenao(NoEscolha noEscolha, PrintWriter saida, VisitanteASA visitor, 
            int nivelEscopo, GeradorCodigoJava.Opcoes opcoes) throws ExcecaoVisitaASA
    {

        if (opcoes.gerandoCodigoParaPontosDeParada)
        {
            Utils.geraParadaPassoAPasso(noEscolha.getExpressao(), saida, nivelEscopo);
        }

        saida.append("{").println();
        
        String identacao = Utils.geraIdentacao(nivelEscopo);

        saida.append(identacao);
        
        String nomeVariavelBreak = geraNomeVariavelBreak(nivelEscopo);
        
        saida.append("boolean ")
                .append(nomeVariavelBreak)
                .append(" = false;");
        
        saida.println();

        List<NoCaso> casos = noEscolha.getCasos();

        for (NoCaso noCaso : casos)
        {
            NoExpressao verificacaoBreak = new NoNao(new NoReferenciaVariavel(null, nomeVariavelBreak));
            NoExpressao comparacaoCaso;

            // case default: 
            if (noCaso.getExpressao() == null)
            {
                comparacaoCaso = new NoLogico(true);
            }
            else
            {
                // case expressao_nao_constante:
                comparacaoCaso = new NoOperacaoLogicaIgualdade(noEscolha.getExpressao(), noCaso.getExpressao());
            }

            NoExpressao condicao = new NoOperacaoLogicaE(verificacaoBreak, comparacaoCaso);

            if (noCaso.getExpressao() != null)
            {
                condicao.setTrechoCodigoFonte(noCaso.getExpressao().getTrechoCodigoFonte());
            }
            
            NoSe se = new NoSe(condicao);
            se.setBlocosVerdadeiros(noCaso.getBlocos());
            
            if (opcoes.gerandoCodigoParaPontosDeParada)
            {
                Utils.geraParadaPassoAPasso(se, saida, nivelEscopo);
            }

            saida.append(identacao);
            
            se.aceitar(visitor);
            
            saida.println();
        }

        saida.append("}");
    }

    public static boolean contemCasosNaoConstantes(NoEscolha noEscolha)
    {
        List<NoCaso> casos = noEscolha.getCasos();
        for (NoCaso caso : casos)
        {
            if (caso.getExpressao() != null)
            {
                if (!(caso.getExpressao() instanceof NoExpressaoLiteral))
                {
                    return true;
                }
            }
        }

        return false;
    }

}

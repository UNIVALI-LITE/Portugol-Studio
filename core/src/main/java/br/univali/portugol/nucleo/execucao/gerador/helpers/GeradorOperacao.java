package br.univali.portugol.nucleo.execucao.gerador.helpers;

import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Elieser
 */
public class GeradorOperacao
{
    private static final Map<Class, String> OPERADORES = new HashMap<>();

    public void gera(NoOperacao no, PrintWriter saida, VisitanteASA visitor) throws ExcecaoVisitaASA
    {
        if (no.estaEntreParenteses())
        {
            saida.append("(");
        }

        boolean precisaConcatenar = no instanceof NoOperacaoSoma && 
                (no.getOperandoEsquerdo().getTipoResultante() == TipoDado.CADEIA ^ no.getOperandoDireito().getTipoResultante() == TipoDado.CADEIA);
        
        if (!precisaConcatenar)
        {
            geraOperacaoPadrao(no, saida, visitor);
        }
        else
        {
            geraConcatenacao(no, saida, visitor); // gera código otimizado para concatenações
        }

        if (no.estaEntreParenteses())
        {
            saida.append(")");
        }
    }

    private void geraConcatenacao(NoOperacao no, PrintWriter saida, VisitanteASA visitor) throws ExcecaoVisitaASA
    {
        //chama o método protegido da classe 'Programa' que internamente usa um StringBuilder
        
        saida.append("concatena(");
        no.getOperandoEsquerdo().aceitar(visitor);
        
        saida.append(", "); // vírgula separando os dois operandos da concatenação
        
        no.getOperandoDireito().aceitar(visitor);
        saida.append(")");
    }
    
    private void geraOperacaoPadrao(NoOperacao no, PrintWriter saida, VisitanteASA visitor) throws ExcecaoVisitaASA
    {
        boolean operandosSaoStrings = operandosSaoStrings(no.getOperandoEsquerdo(), no.getOperandoDireito());

        String operador = OPERADORES.get(no.getClass());
        assert (operador != null);
        if (operandosSaoStrings)
        {
            boolean operadorInvalido = operador.equals(">")  || 
                                       operador.equals("<")  ||
                                       operador.equals(">=") ||
                                       operador.equals("<=");
            if (operadorInvalido)
            {
                throw new ExcecaoVisitaASA("Não é possível comparar cadeias com o operador " + operador, null, no);
            }
        }
        
        boolean usaOperadorPadrao = usaOperadorPadrao(no, operandosSaoStrings);
        
        boolean precisaDeNegacao = !usaOperadorPadrao && (no instanceof NoOperacaoLogicaDiferenca);
        if (precisaDeNegacao)
        {
            saida.append("!"); // not equals
        }
        
        boolean eVetorMatrizDeString = ((no.getOperandoEsquerdo() instanceof NoReferenciaMatriz || no.getOperandoEsquerdo() instanceof NoReferenciaVetor)&& !usaOperadorPadrao);
        if(eVetorMatrizDeString)
        {
            //se é um vetor ou matriz de cadeia, caso o usuário não tenha inicializado o vetor/matriz é necessário retornar para ele uma cadeia vazia
            //ex: ((vet[0] != null)? vet[0]:"").equals(" X ")
            saida.append("((");
            no.getOperandoEsquerdo().aceitar(visitor);
            saida.append("!= null)?");
            no.getOperandoEsquerdo().aceitar(visitor);
            saida.append(":\"\")");
            saida.append(".equals(");
        }
        else
        {
            no.getOperandoEsquerdo().aceitar(visitor);
            if (usaOperadorPadrao)
            {
                saida.format(" %s ", operador);
            }
            else
            {
                saida.append(".equals(");
            }
        }
        
        no.getOperandoDireito().aceitar(visitor);

        if (!usaOperadorPadrao)
        {
            saida.append(")"); // fecha o parênteses do .equals()
        }
    }
    
    private static boolean operandosSaoStrings(NoExpressao a, NoExpressao b)
    {
        return a.getTipoResultante() == TipoDado.CADEIA
                && b.getTipoResultante() == TipoDado.CADEIA;
    }

    private static boolean usaOperadorPadrao(NoOperacao no, boolean operandosSaoStrings)
    {
        if (no instanceof NoOperacaoLogicaIgualdade || no instanceof NoOperacaoLogicaDiferenca)
        {
            return !operandosSaoStrings;
        }

        return true;
    }

    static
    {
        OPERADORES.put(NoOperacaoAtribuicao.class, "=");
        OPERADORES.put(NoOperacaoDivisao.class, "/");
        OPERADORES.put(NoOperacaoModulo.class, "%");
        OPERADORES.put(NoOperacaoMultiplicacao.class, "*");
        OPERADORES.put(NoOperacaoSoma.class, "+");
        OPERADORES.put(NoOperacaoSubtracao.class, "-");

        OPERADORES.put(NoMenosUnario.class, "-");

        OPERADORES.put(NoOperacaoLogicaDiferenca.class, "!=");
        OPERADORES.put(NoOperacaoLogicaIgualdade.class, "==");
        OPERADORES.put(NoOperacaoLogicaMaior.class, ">");
        OPERADORES.put(NoOperacaoLogicaMaiorIgual.class, ">=");
        OPERADORES.put(NoOperacaoLogicaMenor.class, "<");
        OPERADORES.put(NoOperacaoLogicaMenorIgual.class, "<=");
        OPERADORES.put(NoOperacaoLogicaOU.class, "||");
        OPERADORES.put(NoOperacaoLogicaE.class, "&&");

        OPERADORES.put(NoOperacaoBitwiseE.class, "&");
        OPERADORES.put(NoOperacaoBitwiseOu.class, "|");
        OPERADORES.put(NoOperacaoBitwiseXOR.class, "^");
        OPERADORES.put(NoOperacaoBitwiseLeftShift.class, "<<");
        OPERADORES.put(NoOperacaoBitwiseRightShift.class, ">>");
    }

}

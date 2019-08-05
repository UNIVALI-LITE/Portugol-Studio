package br.univali.portugol.nucleo.asa;

import java.util.HashMap;
import java.util.Map;

public final class FabricaNoOperacao
{
    private static final Map<String, Class<? extends NoOperacao>> nos = criarMapaNos();
         
    private static Map<String, Class<? extends NoOperacao>> criarMapaNos()
    {
        Map<String, Class<? extends NoOperacao>> mapaNos = new HashMap<String, Class<? extends NoOperacao>>();
        
        mapaNos.put("==", NoOperacaoLogicaIgualdade.class);
        mapaNos.put("!=", NoOperacaoLogicaDiferenca.class);        
        mapaNos.put("=", NoOperacaoAtribuicao.class);
        mapaNos.put("e", NoOperacaoLogicaE.class);        
        mapaNos.put("ou", NoOperacaoLogicaOU.class);        
        mapaNos.put(">", NoOperacaoLogicaMaior.class);
        mapaNos.put(">=", NoOperacaoLogicaMaiorIgual.class);        
        mapaNos.put("<", NoOperacaoLogicaMenor.class);        
        mapaNos.put("<=", NoOperacaoLogicaMenorIgual.class);        
        mapaNos.put("+", NoOperacaoSoma.class);        
        mapaNos.put("-", NoOperacaoSubtracao.class);        
        mapaNos.put("/", NoOperacaoDivisao.class);        
        mapaNos.put("*", NoOperacaoMultiplicacao.class);        
        mapaNos.put("%", NoOperacaoModulo.class);
        mapaNos.put("<<", NoOperacaoBitwiseLeftShift.class);
        mapaNos.put(">>", NoOperacaoBitwiseRightShift.class);
        mapaNos.put("&", NoOperacaoBitwiseE.class);
        mapaNos.put("|", NoOperacaoBitwiseOu.class);
        mapaNos.put("^", NoOperacaoBitwiseXOR.class);
        
        return mapaNos;
    }
    
    public static NoOperacao novoNo(String operador, NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
    {        
        try
        {
            return nos.get(operador).getConstructor(NoExpressao.class, NoExpressao.class).newInstance(operandoEsquerdo, operandoDireito);
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }
        
        return null;
    }
}

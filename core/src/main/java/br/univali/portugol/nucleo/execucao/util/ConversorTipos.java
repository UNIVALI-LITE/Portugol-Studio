package br.univali.portugol.nucleo.execucao.util;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.erros.ErroImpossivelConverterTipos;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ConversorTipos
{
    public static Object converter(Object objeto, Class para) throws ErroImpossivelConverterTipos
    {
        try
        {
            Method metodo = ConversorTipos.class.getMethod("converter", objeto.getClass(), para);
            
            return metodo.invoke(null, objeto, null);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException excecao)
        {
            excecao.printStackTrace(System.out);
            
            throw new ErroImpossivelConverterTipos(TipoDado.obterTipoDadoPeloTipoJava(objeto.getClass()), TipoDado.obterTipoDadoPeloTipoJava(para));
        }
    }
    
    public static Object converter(Double real, Integer inteiro) 
    {
        return real.intValue();
    }
    
    public static Object converter(Integer inteiro, Double real)
    {
        return Double.parseDouble(inteiro.toString());
    }
}

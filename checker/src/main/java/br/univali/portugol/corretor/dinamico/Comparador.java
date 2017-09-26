package br.univali.portugol.corretor.dinamico;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Comparador {
    
    boolean afirmarIgualdade(Object a, Object b)
    {          
        try {
            Class ca = a.getClass();
            Class cb = b.getClass();
            
            Method metodo;
            metodo = this.getClass().getDeclaredMethod("afirmarIgualdade", ca, cb);
            return (Boolean) metodo.invoke(this, a, b);
        } catch (Exception ex) {
            Logger.getLogger(Comparador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    boolean afirmarIgualdade(Integer a, Integer b)
    {
        return a.equals(b);
    }
    
    boolean afirmarIgualdade(Integer a, Double b)
    {
        return a.equals(b.intValue());
    }
    
    boolean afirmarIgualdade(Integer a, Float b)
    {
        return a.equals(b.intValue());
    }
    
    boolean afirmarIgualdade(Float a, Float b)
    {
        return a.equals(b);
    }
    
    boolean afirmarIgualdade(Float a, Integer b)
    {
        return a.equals(b.floatValue());
    }
    
    boolean afirmarIgualdade(Float a, Double b)
    {
        return a.equals(b.floatValue());
    }
    
    boolean afirmarIgualdade(Double a, Double b)
    {
        return a.equals(b);
    }
    
    boolean afirmarIgualdade(Double a, Integer b)
    {
        return a.equals(b.doubleValue());
    }
    
    boolean afirmarIgualdade(Double a, Float b)
    {
        return a.equals(b.doubleValue());
    }
    
    boolean afirmarIgualdade(Character a, Character b)
    {
        return a.equals(b);
    }
    
    boolean afirmarIgualdade(Character a, Integer b)
    {
        return a.equals((char)b.intValue());
    }
    
    boolean afirmarIgualdade(String a, String b)
    {
        return a.equals(b);
    }
    
    boolean afirmarIgualdade(Boolean a, Boolean b)
    {
        return a.equals(b);
    }
    
}

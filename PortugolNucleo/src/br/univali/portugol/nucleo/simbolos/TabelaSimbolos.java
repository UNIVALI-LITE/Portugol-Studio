package br.univali.portugol.nucleo.simbolos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;


public final class TabelaSimbolos implements Iterable<Simbolo>
{
    private Stack<Map<String, Simbolo>> escopos;

    public TabelaSimbolos()
    {
        escopos = new Stack<Map<String, Simbolo>>();
        empilharEscopo();
    }

    public void empilharEscopo()
    {
        escopos.push(new HashMap<String, Simbolo>());
    }

    public void desempilharEscopo()
    {
        escopos.pop();
    }

    public void adicionar(Simbolo simbolo)
    {
        escopos.peek().put(simbolo.getNome(), simbolo);
    }

    public Simbolo obter(String nome)
    {
        Simbolo simbolo = null;
        
        for (int i = escopos.size() - 1; i >= 0; i--)
            if ((simbolo = escopos.elementAt(i).get(nome)) != null)
                return simbolo;

        return null;
    }

    public boolean contem(String nome)
    {
        for (Map<String, Simbolo> escopo: escopos)
            if (escopo.containsKey(nome))
                return true;

        return false;
    }

    public Iterator<Simbolo> iterator()
    {
        return escopos.peek().values().iterator();
    }
}
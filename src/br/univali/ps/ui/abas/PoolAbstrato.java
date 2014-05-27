package br.univali.ps.ui.abas;

import java.util.ArrayList;
import java.util.List;

public abstract class PoolAbstrato<T>
{
    private final List<T> pool;
    private final int tamanhoInicial;

    public PoolAbstrato(int tamanhoInicial)
    {
        if (tamanhoInicial < 1)
        {
            throw new IllegalArgumentException("O tamanho inicial do pool deve ser pelo menos 1!");
        }
        
        this.tamanhoInicial = tamanhoInicial;
        this.pool = criarObjetos(tamanhoInicial);
    }

    public T obter()
    {
        if (pool.isEmpty())
        {
            expandir();
        }
        
        return pool.remove(0);//retorna sempre o primeiro objeto e remove
    }
    
    public void devolver(T objeto)
    {
        if (!pool.contains(objeto))
        {
            pool.add(objeto);
        }
    }

    private void expandir()
    {
        this.pool.addAll(criarObjetos(tamanhoInicial));
    }

    private List<T> criarObjetos(final int quantidade)
    {
        final List<T> objetos = new ArrayList<>(quantidade);
        
        for (int i = 0; i < quantidade; i++)
        {
            objetos.add(criarObjeto());
        }
        
        return objetos;
    }
    
    protected abstract T criarObjeto();
}

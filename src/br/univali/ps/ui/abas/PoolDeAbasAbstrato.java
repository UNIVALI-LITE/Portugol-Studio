package br.univali.ps.ui.abas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class  PoolDeAbasAbstrato <T> {

    private final List<T> pool;
    private final int tamanhoInicialDoPool;

    public PoolDeAbasAbstrato(int tamanhoInicial)
    {
        if(tamanhoInicial < 1){
            throw new IllegalArgumentException("O tamanho inicial do pool deve ser pelo menos 1!");
        }
        this.tamanhoInicialDoPool = tamanhoInicial;
        this.pool = new ArrayList( criaObjetos(tamanhoInicialDoPool));
    }
    
    public T get(){
        if(pool.isEmpty()){
            expande();
        }
        return pool.remove(0);//retorna sempre o primeiro objeto e remove
    }
    
    private void expande(){
        this.pool.addAll(criaObjetos(tamanhoInicialDoPool));
    }
    
    protected abstract Collection<T> criaObjetos(int quantidadeDeObjetos);
    
}

package br.univali.ps.plugins.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class MetaDadosPlugins implements Iterable<MetaDadosPlugin>
{
    private final Map<String, MetaDadosPlugin> mapa = new TreeMap<>();
    private final List<MetaDadosPlugin> lista = new ArrayList<>();
    private final Comparador comparador = new Comparador();

    private boolean ordenar = true;

    public MetaDadosPlugins(boolean ordenar)
    {
        this.ordenar = ordenar;
    }

    public int quantidade()
    {
        return lista.size();
    }

    public boolean vazio()
    {
        return lista.isEmpty();
    }

    boolean contem(Class classe)
    {
        return mapa.containsKey(classe.getName());
    }

    final void incluir(MetaDadosPlugin metaDado)
    {
        mapa.put(metaDado.getClasse().getName(), metaDado);
        lista.add(metaDado);

        if (ordenar)
        {
            Collections.sort(lista, comparador);
        }
    }

    MetaDadosPlugin obter(Class classePlugin)
    {
        return mapa.get(classePlugin.getName());
    }

    public final MetaDadosPlugin obter(int indice)
    {
        return lista.get(indice);
    }

    @Override
    public final Iterator<MetaDadosPlugin> iterator()
    {
        return new IteradorImutavel<>(lista.iterator());
    }

    private class Comparador implements Comparator<MetaDadosPlugin>
    {
        @Override
        public int compare(MetaDadosPlugin o1, MetaDadosPlugin o2)
        {
            return o1.getNome().compareTo(o2.getNome());
        }
    }

    private class IteradorImutavel<T> implements Iterator<T>
    {
        private final Iterator<T> iteradorOriginal;

        public IteradorImutavel(Iterator<T> iteradorOriginal)
        {
            this.iteradorOriginal = iteradorOriginal;
        }

        @Override
        public boolean hasNext()
        {
            return this.iteradorOriginal.hasNext();
        }

        @Override
        public T next()
        {
            return this.iteradorOriginal.next();
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Não é permitido alterar os metadados dos plugins");
        }
    }
}

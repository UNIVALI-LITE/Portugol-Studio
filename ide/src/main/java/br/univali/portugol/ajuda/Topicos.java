package br.univali.portugol.ajuda;

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
public final class Topicos implements Iterable<Topico>
{
    private ComparadorTopicos comparador;
    private Map<String, Topico> mapaTopicos;
    private List<Topico> listaTopicos;
    
    public Topicos()
    {
        mapaTopicos = new TreeMap<>();
        listaTopicos = new ArrayList<>();
        comparador = new ComparadorTopicos();
    }
    
    public boolean vazio()
    {
        return listaTopicos.isEmpty();
    }
    
    public int quantidade()
    {
        return listaTopicos.size();
    }
    
    public boolean contem(String titulo)
    {
        return mapaTopicos.containsKey(titulo);
    }
    
    public boolean contem(Topico topico)
    {
        return mapaTopicos.containsKey(topico.getTitulo());
    }
    
    public void incluir(Topico topico)
    {
        listaTopicos.add(topico);
        mapaTopicos.put(topico.getTitulo(), topico);
        Collections.sort(listaTopicos, comparador);
    }
    
    public Topico remover(int indice)
    {
        Topico topico = listaTopicos.remove(indice);        
        mapaTopicos.remove(topico.getTitulo());
        
        return topico;
    }
    
    public Topico obter(String titulo)
    {
        return mapaTopicos.get(titulo);
    }
    
    public Topico obter(int indice)
    {
        return listaTopicos.get(indice);
    }

    @Override
    public final Iterator<Topico> iterator()
    {
        return new IteradorTopicos(listaTopicos.iterator(), "Não é permitido remover tópicos da ajuda");
    }
    
    private class ComparadorTopicos implements Comparator<Topico>
    {
        @Override
        public int compare(Topico topicoA, Topico topicoB)
        {
            Integer ordem1 = topicoA.getOrdem();
            Integer ordemB = topicoB.getOrdem();
            
            return ordem1.compareTo(ordemB);                    
        }
    }

    private class IteradorTopicos implements Iterator<Topico>
    {
        private Iterator<Topico> iteradorOriginal;
        private String mensagemErroRemocao;

        public IteradorTopicos(Iterator<Topico> iteradorOriginal, String mensagemErroRemocao)
        {
            this.iteradorOriginal = iteradorOriginal;
            this.mensagemErroRemocao = mensagemErroRemocao;
        }

        @Override
        public boolean hasNext()
        {
            return this.iteradorOriginal.hasNext();
        }

        @Override
        public Topico next()
        {
            return this.iteradorOriginal.next();
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException(this.mensagemErroRemocao);
        }
    }
}

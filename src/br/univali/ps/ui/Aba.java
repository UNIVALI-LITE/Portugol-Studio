package br.univali.ps.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public abstract class Aba extends JPanel
{
    private static List<Class<? extends Aba>> classesFilhas = new ArrayList<Class<? extends Aba>>();
    protected CabecalhoAba cabecalho;
    private JTabbedPane painelTabulado;
    private List<AbaListener> listeners;

    private Aba()
    {
        if (!classesFilhas.contains(this.getClass()))
        {
            classesFilhas.add(this.getClass());
        }
    }

    public final static List<Class<? extends Aba>> classesFilhas()
    {
        return new ArrayList<Class<? extends Aba>>(classesFilhas);
    }

    public Aba(JTabbedPane painelTabulado)
    {
        this();
        listeners = new ArrayList<AbaListener>();
        this.painelTabulado = painelTabulado;
        cabecalho = criarCabecalho();
        //int posicao = painelTabulado.getComponentCount();
        //if (posicao > 0)
        //    posicao = 1;
        adicionar(painelTabulado);
    }
    
    public final void adicionar(JTabbedPane painelTabulado)
    {
        painelTabulado.add(this);
        painelTabulado.setTabComponentAt(painelTabulado.indexOfComponent(this), cabecalho);
        painelTabulado.setSelectedComponent(this);
    }

    public JTabbedPane getPainelTabulado()
    {
        return painelTabulado;
    }

    protected CabecalhoAba criarCabecalho()
    {
        CabecalhoAba cabecalho = new CabecalhoAba(this);
        cabecalho.setTitulo("Sem título");
        cabecalho.setBotaoFecharVisivel(true);
        return cabecalho;
    }

    public void setRemovivel(boolean removivel)
    {
        cabecalho.setBotaoFecharVisivel(removivel);
    }

    public boolean isRemovivel()
    {
        return cabecalho.isBotaoFecharVisivel();
    }

    public boolean fechar()
    {

        boolean podeFechar = true;

        for (AbaListener listener : listeners)
        {
            if (!listener.fechandoAba(this))
            {
                podeFechar = false;
            }
        }

        if (podeFechar)
        {
            painelTabulado.remove(this);
        }

        return podeFechar;
    }

    public void selecionar()
    {
        painelTabulado.setSelectedComponent(this);
    }

    public void adicionarAbaListener(AbaListener listener)
    {
        if (!listeners.contains(listener))
        {
            listeners.add(listener);
        }
    }

    public void removerAbaListener(AbaListener listener)
    {
        listeners.remove(listener);
    }
}

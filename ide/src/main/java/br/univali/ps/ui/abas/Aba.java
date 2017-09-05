package br.univali.ps.ui.abas;

import br.univali.ps.ui.paineis.utils.PainelTabulado;
import br.univali.ps.ui.utils.IconFactory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JPanel;

public abstract class Aba extends JPanel
{
    private static List<Class<? extends Aba>> classesFilhas = new ArrayList<>();
    
    private CabecalhoAba cabecalho;
    private PainelTabulado painelTabulado;
    private List<AbaListener> listeners;

    public Aba() {
        if (!classesFilhas.contains(this.getClass()))
        {
            classesFilhas.add(this.getClass());
        }
        
        this.listeners = new ArrayList<>();
        this.cabecalho = criarCabecalhoPadrao("Sem título", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "unknown.png"), false);
    }

    public void setPainelTabulado(PainelTabulado painelTabulado)
    {
        this.painelTabulado = painelTabulado;
    }

    
     public Aba(String titulo, Icon icone, boolean removivel)
     {
         this();
         this.cabecalho = criarCabecalhoPadrao(titulo, icone, removivel);
     }
    
//    public Aba(CabecalhoAba cabecalhoAba)
//    {
//        this();        
//        this.cabecalho = cabecalhoAba;
//    }
    
    public static List<Class<? extends Aba>> classesFilhas()
    {
        return new ArrayList<>(classesFilhas);
    }

    private CabecalhoAba criarCabecalhoPadrao(String titulo, Icon icone, boolean removivel)
    {
        CabecalhoAba cabecalhoPadrao = new CabecalhoAba(this);
        
        cabecalhoPadrao.setTitulo(titulo);
        cabecalhoPadrao.setIcone(icone);
        cabecalhoPadrao.setBotaoFecharVisivel(removivel);
        
        return cabecalhoPadrao;
    }
    
//    public void adicionar(PainelTabulado painelTabulado)
//    {
////        this.painelTabulado = painelTabulado;
////        this.painelTabulado.add(this);
////        this.painelTabulado.setTabComponentAt(painelTabulado.indexOfComponent(this), cabecalho);
////        this.painelTabulado.setSelectedComponent(this);
//    }

    public CabecalhoAba getCabecalho()
    {
        return cabecalho;
    }

    protected void setCabecalho(CabecalhoAba cabecalho)
    {
        this.cabecalho = cabecalho;
    }
    
    public PainelTabulado getPainelTabulado()
    {
        return painelTabulado;
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
            if (painelTabulado != null)
            {
                painelTabulado.remove(this);
                painelTabulado = null;
            }
        }

        return podeFechar;
    }

    public void selecionar()
    {
        if (painelTabulado != null)
        {
            if(painelTabulado.indexOfComponent(this) >= 0){
                painelTabulado.setSelectedComponent(this);
            }
        }
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

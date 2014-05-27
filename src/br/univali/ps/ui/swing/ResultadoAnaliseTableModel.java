package br.univali.ps.ui.swing;

import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.mensagens.*;
import br.univali.ps.ui.util.IconFactory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Luiz Fernando Noschang
 * @Fillipi Pelz
 */
public final class ResultadoAnaliseTableModel extends AbstractTableModel
{
    private List<Mensagem> mensagens = new ArrayList<Mensagem>();
    
    public Mensagem getMensagem(int indice)
    {
        if (mensagens != null && !mensagens.isEmpty())
        {
            return mensagens.get(indice);
        }
        
        return null;
    }

    @Override
    public int getRowCount()
    {
        return mensagens.size();
    }

    @Override
    public int getColumnCount()
    {
        return 3;
    }

    @Override
    public Object getValueAt(int linha, int coluna)
    {
        if (mensagens != null)
        {
            Mensagem mensagem = mensagens.get(linha);
            
            if (coluna == 0)
            {
                return getTipoMensagem(mensagem);
            }
            else if (coluna == 1)
            {
                return getLinha(mensagem);
            }
            else if (coluna == 2)
            {
                return mensagem.getMensagem();
            }             
        }

        return null;
    }

    @Override
    public String getColumnName(int indice)
    {
        if (indice == 0)
        {
            return " ";
        }
        else if (indice == 1)
        {
            return "Linha";
        }
        else if (indice == 2)
        {
            return "Mensagem";            
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int indice)
    {
        if (indice == 0)
        {
            return Icon.class;
        }
        else if (indice == 1)
        {
            return Integer.class;
        }
        else if (indice == 2)
        {
            return String.class;
        }
        
        return null;
    }

    public void setResultadoAnalise(ResultadoAnalise resultadoAnalise)
    {
        mensagens = new ArrayList<Mensagem>();
        
        if (resultadoAnalise != null)
        {
            for (Mensagem m : resultadoAnalise.getErros())
            {
                mensagens.add(m);
            }
            for (Mensagem m : resultadoAnalise.getAvisos())
            {
                mensagens.add(m);
            }
        }
        
        fireTableDataChanged();
    }

    private Icon getTipoMensagem(Mensagem mensagem)
    {
        Icon icone = null;

        if (mensagem instanceof Aviso)
        {
            icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "warning.png");
        }
        else if (mensagem instanceof Erro)
        {
            icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "exclamation.png");
        }

        return icone;
    }

    private Integer getLinha(Mensagem mensagem)
    {
        Integer linha = null;
        
        if (mensagem instanceof AvisoAnalise)
        {
            linha = ((AvisoAnalise) mensagem).getLinha();
        }
        if (mensagem instanceof ErroAnalise)
        {
            linha = ((ErroAnalise) mensagem).getLinha();
        }

        return linha;
    }

    
}

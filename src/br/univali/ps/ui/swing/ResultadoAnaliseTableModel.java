package br.univali.ps.ui.swing;

import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.mensagens.*;
import br.univali.ps.ui.utils.IconFactory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Luiz Fernando Noschang
 * @author Fillipi Pelz
 */
public final class ResultadoAnaliseTableModel extends AbstractTableModel
{
    private List<Mensagem> mensagens = new ArrayList<>();
    
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
            
            switch (coluna)
            {
                case 0:
                    return getTipoMensagem(mensagem);
                case 1:
                    return getLinha(mensagem);
                case 2:
                    return mensagem.getMensagem();
                default:
                    break;
            }
        }

        return null;
    }

    @Override
    public String getColumnName(int indice)
    {
        switch (indice)
        {
            case 0:
                return " ";
            case 1:
                return "Linha";
            case 2:
                return "Mensagem";
            default:
                break;            
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int indice)
    {
        switch (indice)
        {
            case 0:
                return Icon.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            default:
                break;
        }
        
        return null;
    }

    public void setResultadoAnalise(ResultadoAnalise resultadoAnalise)
    {
        mensagens = new ArrayList<>();
        
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

package br.univali.ps.ui.swing;

import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;
import javax.swing.table.AbstractTableModel;

public final class ResultadoAnaliseTableModel extends AbstractTableModel
{
    private ResultadoAnalise resultadoAnalise;
    
    @Override
    public int getRowCount()
    {
        if (resultadoAnalise != null)
            return resultadoAnalise.getNumeroTotalErros();
        
        return 0;
    }

    @Override
    public int getColumnCount()
    {
        return 3;
    }

    @Override
    public Object getValueAt(int linha, int coluna)
    {
        if (resultadoAnalise != null)
        {
            ErroAnalise erroAnalise = resultadoAnalise.getErros().get(linha);
            
            if (coluna == 0) return erroAnalise.getMensagem();
            else
            if (coluna == 1) return erroAnalise.getLinha();
            else
            if (coluna == 2) return erroAnalise.getColuna();
            
        }
        
        return null;
    }

    @Override
    public String getColumnName(int indice)
    {
        if (indice == 0) return "Mensagem";
        else
        if (indice == 1) return "Linha";        
        else
        if (indice == 2) return "Coluna";
        
        
        return null;
    }

    public void setResultadoAnalise(ResultadoAnalise resultadoAnalise)
    {
        this.resultadoAnalise = resultadoAnalise;
        fireTableDataChanged();
    }
}

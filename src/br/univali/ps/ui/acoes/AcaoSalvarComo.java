package br.univali.ps.ui.acoes;

import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

public class AcaoSalvarComo extends Acao
{
    private Container parent;
    private JFileChooser chooser;
    private AcaoSalvarArquivo acaoSalvarArquivo;
    private FiltroArquivo filtroSelecionado;
    private FiltroArquivo[] filtros;

    public AcaoSalvarComo()
    {
        super("arquivo salvo com sucesso");
        
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
    }

    public void configurar(AcaoSalvarArquivo salvarArquivo, Container parent, JFileChooser chooser, FiltroArquivo filtroSelecionado, FiltroArquivo...filtros)
    {
        this.parent = parent;
        this.chooser = chooser;
        this.acaoSalvarArquivo = salvarArquivo;
        this.filtroSelecionado = filtroSelecionado;
        this.filtros = filtros;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        for (FileFilter filtro : chooser.getChoosableFileFilters())
        {
            chooser.removeChoosableFileFilter(filtro);
        }
        
        for (FiltroArquivo filtro : filtros)
        {
            chooser.addChoosableFileFilter(filtro);
        }
        
        chooser.setFileFilter(filtroSelecionado);
        
        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION)
        {
            File arquivo = chooser.getSelectedFile();
            
            if (!arquivo.getName().toLowerCase().endsWith(".por"))
            {
                arquivo = new File(arquivo.getPath().concat(".por"));
            }
            
            acaoSalvarArquivo.getDocumento().setFile(arquivo);
            acaoSalvarArquivo.actionPerformed(e);
        }
        else
        {
            throw new Exception("Seleção de arquivo cancelada pelo usuário");
        }
    }
}

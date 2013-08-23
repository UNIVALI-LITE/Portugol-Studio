package br.univali.ps.ui.acoes;

import br.univali.portugol.corretor.dinamico.Unmarshal;
import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.AbaCodigoFonte;
import br.univali.ps.ui.PainelTabulado;
import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import br.univali.ps.ui.util.FileHandle;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

public final class AcaoAbrirArquivo extends Acao
{
    private JFileChooser chooser;
    private Container parent;
    private PainelTabulado painelTabulado;
    private FiltroArquivo filtroSelecionado;
    private FiltroArquivo[] filtros;

    public AcaoAbrirArquivo()
    {
        super("Arquivo aberto com sucesso!");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
    }

    public void configurar(PainelTabulado painelTabulado, Container parent, JFileChooser chooser, FiltroArquivo filtroSelecionado, FiltroArquivo...filtros)
    {
        this.painelTabulado = painelTabulado;
        this.parent = parent;
        this.chooser = chooser;
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
            
        
        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
        {
            File[] arquivos = chooser.getSelectedFiles();

            PortugolStudio.getInstancia().abrirArquivosCodigoFonte(new ArrayList<>(Arrays.asList(arquivos)));
        }
        else
        {
            throw new Exception("Seleção de arquivo cancelada pelo usuário");
        }
    }
}

package br.univali.ps.ui.acoes;

import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.FileHandle;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

public class AcaoSalvarArquivo extends Acao
{
    private PortugolDocumento documento;
    private AcaoSalvarComo salvarComo;
    private int posicaoCursor;
    
    public AcaoSalvarArquivo()
    {
        super("Arquivo salvo com sucesso");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
    }

    public void configurar(PortugolDocumento documento, AcaoSalvarComo acaoSalvarComo){
        this.documento = documento;
        this.salvarComo = acaoSalvarComo;
    }

    public void setPosicaoCursor(int posicaoCursor)
    {
        this.posicaoCursor = posicaoCursor;
    }

    public PortugolDocumento getDocumento() {
        return documento;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {       
        try {
            String texto = documento.getText(0, documento.getLength());
            
            StringBuilder sb = new StringBuilder(texto);
            
            if (posicaoCursor >= 0)
            {
                sb.insert(posicaoCursor, "/*${cursor}*/");
            }
            
            texto = sb.toString();
            
            if (documento.getFile() != null) {
                FileHandle.save(texto, documento.getFile());
                documento.setChanged(false);
            } else {
                salvarComo.actionPerformed(e);
            }
        } catch (BadLocationException ex) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
        } catch (Exception ex) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
        }
    }
}

package br.univali.ps.ui.acoes;

import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.FileHandle;
import java.awt.event.ActionEvent;
import javax.swing.text.BadLocationException;

public class AcaoSalvarArquivo extends Acao
{
    private PortugolDocumento documento;
    
    public AcaoSalvarArquivo()
    {
        super("Arquivo salvo com sucesso");
    }

    public void configurar(PortugolDocumento documento){
        this.documento = documento;
    }

    public PortugolDocumento getDocumento() {
        return documento;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {       
        try {
            String texto = documento.getText(0, documento.getLength());
            if (documento.getFile() != null) {
                FileHandle.save(texto, documento.getFile());
                documento.setChanged(false);
            } else {
                PortugolStudio.getInstancia().getTelaPrincipal().dialogoSalvar();
            }
        } catch (BadLocationException ex) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
        } catch (Exception ex) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
        }
    }
}

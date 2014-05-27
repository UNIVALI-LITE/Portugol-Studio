package br.univali.ps.dominio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;

public class PortugolDocumento extends RSyntaxDocument {
    
    
    private boolean changed = false;
    private File file = null;
    private List<PortugolDocumentoListener> listeners = new ArrayList<PortugolDocumentoListener>();

    public boolean addPortugolDocumentoListener(PortugolDocumentoListener listener){
        if (!listeners.contains(listener)){
            return listeners.add(listener);
        }
        return false;
    }
    
    public String getCodigoFonte(){
        try {
            return getText(0, this.getLength());
        } catch (BadLocationException ex) {}
        return "";
    }

    public boolean removePortugolDocumentoListener(PortugolDocumentoListener listener){
        return listeners.remove(listener);
    }

    public PortugolDocumento() {
        super(new AbstractTokenMakerFactory() {
           
            @Override
            protected void initTokenMakerMap()
            {
                putMapping("text/por", "br.univali.ps.dominio.PortugolTokenMaker"); //To change body of generated methods, choose Tools | Templates.
            }
        },"text/por" );
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        
        String nome = (file != null)? file.getName() : null;
        
         this.file = file;
         disparaNomeArquivoAlterado(nome);
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
        disparaDocumentoModificado(changed);
    }

    @Override
    protected void fireInsertUpdate(DocumentEvent e) {
        super.fireInsertUpdate(e);
        setChanged(true);
    }

    @Override
    protected void fireRemoveUpdate(DocumentEvent chng) {
        super.fireRemoveUpdate(chng);
        setChanged(true);
    }

    private void disparaDocumentoModificado(boolean status){
        for (PortugolDocumentoListener portugolDocumentoListener : listeners) {
            portugolDocumentoListener.documentoModificado(status);
        }
    }
    
    private void disparaNomeArquivoAlterado(String nome){
        for (PortugolDocumentoListener portugolDocumentoListener : listeners) {
            portugolDocumentoListener.nomeArquivoAlterado(nome);
        }
    }
}

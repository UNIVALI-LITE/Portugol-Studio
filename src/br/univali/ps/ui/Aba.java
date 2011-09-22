package br.univali.ps.ui;

import br.univali.ps.dominio.PortugolDocumento;
import javax.swing.JComponent;

public class Aba{
    
    private CabecalhoAba cabecalho;
    private Conteudo conteudo;

    public Aba() {
        cabecalho = new CabecalhoAba();
        cabecalho.setTitulo("Sem título");
        cabecalho.setRemovivel(true);
        conteudo = new Conteudo();
    }
    
    public Aba(PortugolDocumento documento){
        cabecalho = new CabecalhoAba();
        cabecalho.setTitulo(documento.getFile().getName());
        cabecalho.setRemovivel(true);
        conteudo = new Conteudo();
        conteudo.getEditor().setPortugolDocumento(documento);
    }
    
    public JComponent getCabecalho() {
        return cabecalho;
    }

    public JComponent getConteudo() {
        return conteudo;
    }

}

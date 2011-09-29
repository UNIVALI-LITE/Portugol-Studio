package br.univali.ps.dominio;

public interface PortugolDocumentoListener {

    public void documentoModificado(boolean status);
    public void nomeArquivoAlterado(String nome);

}

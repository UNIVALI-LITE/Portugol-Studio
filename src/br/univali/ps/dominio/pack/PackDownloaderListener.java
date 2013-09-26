package br.univali.ps.dominio.pack;

/**
 *
 * @author Elieser
 */
public interface PackDownloaderListener {

    void downloadStarted();
    void downloadFinished();
    void downloadProgress(int bytesDownloaded, int totalBytes );
    
}

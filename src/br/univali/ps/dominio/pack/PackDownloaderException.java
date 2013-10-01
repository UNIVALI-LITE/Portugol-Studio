package br.univali.ps.dominio.pack;

/**
 *
 * @author Elieser
 */
public class PackDownloaderException extends Exception {

    private String packName;
    
    public PackDownloaderException(String message, String packName)
    {
        super(message);
    }
    
    public PackDownloaderException(String message)
    {
        super(message);
    }

    public PackDownloaderException(Throwable cause, String packName)
    {
        super(cause);
    }

    
    
    
}

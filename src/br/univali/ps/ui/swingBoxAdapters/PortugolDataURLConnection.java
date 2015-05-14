package br.univali.ps.ui.swingBoxAdapters;

/**
 * DataURLConnection.java
 */


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * The URL connection for the data: URI scheme. 
 * @author burgetr
 */
class PortugolDataURLConnection extends URLConnection
{
    private String mime;
    private String charset;
    private byte[] data;

    public PortugolDataURLConnection(URL url, String mime, String charset, byte[] data)
    {
        super(url);
        this.mime = new String(mime);
        this.charset = new String(charset);
        this.data = data;
    }
    
    protected PortugolDataURLConnection(URL url)
    {
        super(url);
    }

    @Override
    public void connect() throws IOException
    {
    }

    @Override
    public String getContentEncoding()
    {
        return charset;
    }

    @Override
    public int getContentLength()
    {
        return data.length;
    }

    @Override
    public String getContentType()
    {
        return mime;
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        return new ByteArrayInputStream(data);
    }
}
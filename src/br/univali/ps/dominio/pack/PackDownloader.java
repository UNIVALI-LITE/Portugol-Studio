package br.univali.ps.dominio.pack;

import br.univali.ps.nucleo.Configuracoes;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.lingala.zip4j.core.ZipFile;

/**
 * @author Elieser, Luis e Fillipi Esta classe é utilizada para baixar (caso
 * necessário) os pacotes de arquivos de exemplos e de ajuda do PortugolStudio.
 * Cada pacote é constituído de um arquivo ZIP e um arquivo contendo a versão do
 * pacote. Este arquivo com a versão do pacote é gerado pelo Ant. Sempre que
 * novos arquivos são adicionados ao pacote um novo build é realizado no projeto
 * dos exemplos ou da ajuda e um novo arquivo de versão é gerado automaticamente
 * pelo Ant.
 *
 * Esta classe carrega o arquivo de versão do pacote utilizando a classe
 * Properties e verifica se a versão do pacote que está no servidor é mais nova
 * do que a versão que estã na máquina do usuário. Caso sim, o pacote mais novo
 * é baixado.
 *
 */
public class PackDownloader
{
    private List<PackDownloaderListener> listeners = new ArrayList<>();
    private ExecutorService service = Executors.newSingleThreadExecutor();

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void downloadPack(URL baseUrl, String packName) throws PackDownloaderException
    {
        service.submit(new DownloadTask(baseUrl, Configuracoes.obterDiretorioPortugol().getAbsolutePath(), packName));
    }

    public void downloadPack(String baseUrl, String packName) throws PackDownloaderException
    {
        try
        {
            downloadPack(new URL(baseUrl), packName);
        }
        catch (Exception e)
        {
            throw new PackDownloaderException(e);
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class DownloadTask implements Runnable
    {
        private URL baseURL;
        private String destPath;
        private String packName;

        public DownloadTask(URL fileURL, String destPath, String packName)
        {
            this.baseURL = fileURL;
            this.destPath = destPath;
            this.packName = packName;
        }

        @Override
        public void run()
        {
            fireDownloadStarted();
            try
            {
                if (newVersionAvailable(baseURL, packName))
                {
                    deleteOldPackFiles(packName);
                    extractZipContent(downloadZipFile(baseURL, packName));
                    overwriteLocalVersionFile(baseURL, packName);
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            fireDownloadFinished();
            service.shutdown();
        }
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void deleteOldPackFiles(String packName)
    {
        File oldFile = new File(Configuracoes.obterDiretorioPortugol() + "/" + packName);
        deleteFile(oldFile);//deleta os arquivos antigos
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void deleteFile(File file)
    {//deleta a estrutura de diretórios recursivamente
        if (file.isDirectory())
        {
            File files[] = file.listFiles();
            for (File f : files)
            {
                deleteFile(f);
            }
        }
        file.delete();
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private File downloadZipFile(URL baseURL, String packName) throws Exception
    {
        URL zipUrl = new URL(baseURL, packName + "/" + packName + ".zip");
        return downloadFile(zipUrl, Configuracoes.obterDiretorioPortugol() + "/" + packName + ".zip", true);
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void extractZipContent(File zipFile) throws PackDownloaderException
    {
        try
        {
            ZipFile zip = new ZipFile(zipFile);
            zip.setRunInThread(false);
            zip.setFileNameCharset("ISO-8859-1");
            zip.extractAll(Configuracoes.obterDiretorioPortugol().getAbsolutePath());
            zipFile.delete();
        }
        catch (Exception e)
        {
            throw new PackDownloaderException(e.getMessage());
        }

    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private File downloadFile(URL url, String destPath, boolean fireProgressEvent) throws Exception
    {
        System.out.println("download file...");
        InputStream is = null;
        OutputStream os = null;
        File file = new File(destPath);
        try
        {
            try
            {
                HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
                connection.setUseCaches(false);//resolveu o problema do applet não baixar a nova versão do zip, estava usando a versão do cache da JVM
                long totalBytes = connection.getContentLengthLong();
                int progressStep = ((int) totalBytes / 100) + 1;
                is = new BufferedInputStream(connection.getInputStream());
                os = new BufferedOutputStream(new FileOutputStream(file));
                int theReadedByte = -1;
                int readedBytes = 0;
                do
                {
                    theReadedByte = is.read();
                    if (theReadedByte != -1)
                    {
                        os.write(theReadedByte);
                        readedBytes++;
                        if (fireProgressEvent && (readedBytes % progressStep == 0))//se é multiplo de progressStep
                        {
                            fireDownloadProgress(readedBytes, (int) totalBytes);
                        }
                    }
                }
                while (theReadedByte != -1);
            }
            finally
            {
                if (os != null)
                {
                    os.close();
                }
                if (is != null)
                {
                    is.close();
                }
            }
        }
        catch (Exception e)
        {
            throw new PackDownloaderException(e);
        }
        return file;
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private boolean newVersionAvailable(URL baseUrl, String packName) throws Exception
    {
        URL url = new URL(baseUrl, packName + "/build.number");
        Properties remoteProperties = new Properties();
        remoteProperties.load(url.openStream());//lê o properties diretamente da URL, não é necessário baixar o arquivo

        Properties localProperties = new Properties();
        final File localfile = new File(Configuracoes.obterDiretorioPortugol() + "/" + packName + ".build.number");
        if (localfile.exists())
        {
            localProperties.load(new FileInputStream(localfile));
            Integer remoteVersion = Integer.parseInt(remoteProperties.getProperty("build.number"));
            Integer localVersion = Integer.parseInt(localProperties.getProperty("build.number"));
            return (remoteVersion > localVersion);
        }
        else
        {
            saveRemotePropertiesInLocalFileSystem(remoteProperties, packName);
            return true;
        }
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void saveRemotePropertiesInLocalFileSystem(Properties remoteProperties, String packName) throws Exception
    {
        File targetFile = new File(Configuracoes.obterDiretorioPortugol() + "/" + packName + ".build.number");
        OutputStream os = new FileOutputStream(targetFile);
        remoteProperties.store(os, "");
        os.close();
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void overwriteLocalVersionFile(URL baseUrl, String packName) throws Exception
    {
        URL url = new URL(baseUrl, packName + "/build.number");
        downloadFile(url, Configuracoes.obterDiretorioPortugol() + "/" + packName + ".build.number", false);
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void addListener(PackDownloaderListener l)
    {
        listeners.add(l);
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void fireDownloadStarted()
    {
        for (PackDownloaderListener packDownloaderListener : listeners)
        {
            packDownloaderListener.downloadStarted();
        }
    }

    private void fireDownloadFinished()
    {
        for (PackDownloaderListener packDownloaderListener : listeners)
        {
            packDownloaderListener.downloadFinished();
        }
    }

    private void fireDownloadProgress(int bytesDownloaded, int totalBytes)
    {
        for (PackDownloaderListener packDownloaderListener : listeners)
        {
            packDownloaderListener.downloadProgress(bytesDownloaded, totalBytes);
        }
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static void main(String args[]) throws Exception
    {
        PackDownloader packDownloader = new PackDownloader();

        packDownloader.addListener(new PackDownloaderListener()
        {
            @Override
            public void downloadStarted()
            {
                System.out.println("INICIADO");
            }

            @Override
            public void downloadFinished()
            {
                System.out.println("FINALIZADO");
            }

            @Override
            public void downloadProgress(int bytesDownloaded, int totalBytes)
            {
                System.out.println("progresso: " + bytesDownloaded + "/" + totalBytes);
            }
        });


        for (int i = 0; i < 10; i++)
        {
            System.out.println("contando: " + i);
            if (i == 5)//inicia o download no meio da contagem apenas para ver as threads alternando a execução
            {
                packDownloader.downloadPack(new URL("http://localhost/portugol-exemplos/"), "exemplos");
            }
            Thread.sleep(100);
        }
    }
}

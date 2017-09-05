package br.univali.portugol;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Caminhos
{
    public static final File diretorioInstalacao = resolverDiretorioInstalacao();
    private static final File diretorioTemporario = new File(diretorioInstalacao, "temp");
    private static final File diretorioCompilacao = new File(diretorioTemporario, "compilacao");
    private static final File diretorioAplicacao = new File(diretorioInstalacao, "aplicacao");

    private static File resolverDiretorioInstalacao()
    {
        if (!rodandoEmDesenvolvimento())
        {
            CodeSource localCodigo = Caminhos.class.getProtectionDomain().getCodeSource();
            URL local = localCodigo.getLocation();

            return new File(URI.create(local.toExternalForm())).getParentFile().getParentFile();
        }
        else
        {
            File diretorio = new File("../Portugol-Instalador/arquivos/compartilhados");

            return diretorio;
        }
    }

//    private static File resolverDiretorioInstalacao()
//    {
//        if (rodandoNoNetbeans())
//        {
//            File diretorio = new File("./teste");
//            diretorio.mkdirs();
//
//            return diretorio;
//        }
//
//        return new File(extrairCaminho(new File(".")));
//    }

    public static File getDiretorioAplicacao() 
    {
        return diretorioAplicacao;
    }

    public static File getDiretorioCompilacao() 
    {
        return diretorioCompilacao;
    }

    public static File getDiretorioInstalacao() 
    {
        return diretorioInstalacao;
    }

    public static File getDiretorioTemporario() 
    {
        return diretorioTemporario;
    }
    
    private static String getCaminhoDoJAR() throws URISyntaxException
    {
        return Caminhos.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
    }
    
    public static String obterCaminhoExecutavelJavac()
    {
        if (Caminhos.rodandoEmDesenvolvimento())
        {
            if (rodandoNoWindows())
            {
                return "javac.exe";
            }
            else
            {
                return "javac";
            }
        }
        else
        {
            File executavel;

            if (rodandoNoWindows())
            {
                try {
                    
                    String caminhoDoJAR = getCaminhoDoJAR();
                    
                    executavel = new File(new File(new File(new File(new File(caminhoDoJAR).getParentFile().getParentFile(), "java"), "java-windows"), "bin"), "javac.exe");
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Caminhos.class.getName()).log(Level.SEVERE, null, ex);
                    executavel = new File(new File(new File(new File(diretorioInstalacao, "java"), "java-windows"), "bin"), "javac.exe");
                }
                
            }
            else if (rodandoNoLinux())
            {
                executavel = new File(new File(new File(new File(diretorioInstalacao, "java"), "java-linux"), "bin"), "javac");
            }
            else if (rodandoNoMac())
            {
                executavel = new File(new File(new File(new File(diretorioInstalacao, "java"), "java-mac"), "bin"), "javac");
            }
            else
            {
                return "javac";
            }
            
            return extrairCaminho(executavel);
        }
    }

    public static boolean rodandoEmDesenvolvimento()
    {
        return !(new File("aplicacao/portugol-studio.jar").exists());
    }

    public static boolean rodandoNoWindows()
    {
        String so = System.getProperty("os.name");

        return (so != null && so.toLowerCase().contains("win"));
    }

    public static boolean rodandoNoLinux()
    {
        String so = System.getProperty("os.name");

        return (so != null && so.toLowerCase().contains("linux"));
    }

    public static boolean rodandoNoMac()
    {
        String so = System.getProperty("os.name");

        return (so != null && so.toLowerCase().contains("os x"));
    }

    public static String extrairCaminho(File arquivo)
    {
        try
        {
            return arquivo.getCanonicalPath();
        }
        catch (IOException excecao)
        {
            return arquivo.getAbsolutePath();
        }
    }
}

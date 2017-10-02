package br.univali.ps.nucleo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Caminhos
{

    private static final Logger LOGGER = Logger.getLogger(Caminhos.class.getName());
    
    public static final File diretorioInstalacao = obterDiretorioInstalacao();
    public static final File diretorioTemporario = new File(diretorioInstalacao, "temp");
    public static final File diretorioBackup = new File(diretorioInstalacao, "backup");
    public static final File diretorioAplicacao = new File(diretorioInstalacao, "aplicacao");

    public static final File jarPortugolStudio = new File(diretorioAplicacao, "portugol-studio.jar");
    
    public static final File scriptAtualizacao = new File(diretorioInstalacao, "atualizacao.script");
    public static final File arquivoInicializadorModificado = new File(diretorioInstalacao, "inicializador-ps-antigo.jar");
    
    public static final File parametrosJVM = new File(diretorioInstalacao, "jvm.properties");
    public static final File logAtualizacao = new File(diretorioInstalacao, "atualizacao.log");
    public static final File arquivoMutex = new File(diretorioInstalacao, "mutex");

    public static File obterDiretorioInstalacao()
    {
        if (!rodandoEmDesenvolvimento())
        {
            try
            {
                CodeSource localCodigo = Caminhos.class.getProtectionDomain().getCodeSource();
                URL local = localCodigo.getLocation();

                return new File(URI.create(local.toExternalForm())).getParentFile().getParentFile();
            }
            catch(Exception ex)
            {
                LOGGER.log(Level.SEVERE, null, ex);
                return new File(".");
            }
        }
        return new File("src/main/assets");
    }

    public static String obterCaminhoExecutavelJavac()
    {
        if (Caminhos.rodandoEmDesenvolvimento())
        {
            if (Caminhos.rodandoNoWindows())
            {
                return "javac.exe";
            }
            else if (Caminhos.rodandoNoMac())
            {
                return "javac";
            }
            else { // Linux
                assert (Caminhos.rodandoNoLinux()); // just in case :)
                String javaHome = System.getProperty("java.home");
                if (javaHome != null) {
                    File jrePath = new File(javaHome);
                    String jdkBinPath = new File(jrePath.getParent(), "bin").getAbsolutePath();
                    return jdkBinPath + "/javac";
                }
                else {
                    LOGGER.log(Level.SEVERE, "A propriedade 'java.home' está nula! O usuário não adicionou o caminho do JAVA no PATH do sistema!");
                }
                return "javac";
            }
        }
        else
        {
            File executavel;

            if (rodandoNoWindows())
            {
                executavel = new File(new File(new File(new File(diretorioInstalacao, "java"), "java-windows"), "bin"), "javac.exe");
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
    
    public static String obterCaminhoExecutavelJava()
    {
        if (Caminhos.rodandoEmDesenvolvimento())
        {
            if (Caminhos.rodandoNoWindows())
            {
                return "java.exe";
            }
            else if (Caminhos.rodandoNoMac())
            {
                return "java";
            }
            else { // Linux
                assert (Caminhos.rodandoNoLinux()); // just in case :)
                String javaHome = System.getProperty("java.home");
                if (javaHome != null) {
                    File jrePath = new File(javaHome);
                    String jdkBinPath = new File(jrePath.getParent(), "bin").getAbsolutePath();
                    return jdkBinPath + "/java";
                }
                else {
                    LOGGER.log(Level.SEVERE, "A propriedade 'java.home' está nula! O usuário não adicionou o caminho do JAVA no PATH do sistema!");
                }
                return "java";
            }
        }
        else
        {
            File executavel;

            if (rodandoNoWindows())
            {
                executavel = new File(new File(new File(new File(diretorioInstalacao, "java"), "java-windows"), "bin"), "java.exe");
            }
            else if (rodandoNoLinux())
            {
                executavel = new File(new File(new File(new File(diretorioInstalacao, "java"), "java-linux"), "bin"), "java");
            }
            else if (rodandoNoMac())
            {
                executavel = new File(new File(new File(new File(diretorioInstalacao, "java"), "java-mac"), "bin"), "java");
            }
            else
            {
                return "java";
            }
            
            return extrairCaminho(executavel);
        }
    }
    
    public static boolean rodandoEmDesenvolvimento()
    {
        String path = new File(".").getAbsolutePath();
                      
        //Mac - quando está instalado o executável do PS fica em /Applications/Portugol Studio.app/Contents/MacOSx
        return (!path.contains("Portugol Studio.app")) && path.endsWith("ide\\.");
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

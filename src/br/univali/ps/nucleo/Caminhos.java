package br.univali.ps.nucleo;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Caminhos
{

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

    private static File obterDiretorioInstalacao()
    {
        if (!rodandoNoNetbeans())
        {
            return new File(".");
        }
        else
        {
            File diretorio = new File("../Portugol-Instalador/arquivos/compartilhados");

            return diretorio;
        }
    }

    public static String obterCaminhoExecutavelJavac()
    {
        if (Caminhos.rodandoNoNetbeans())
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
                
                File jrePath = new File(System.getProperty("java.home"));
                String jdkBinPath = new File(jrePath.getParent(), "bin").getAbsolutePath();
                return jdkBinPath + "/javac";
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
    
    public static boolean rodandoNoNetbeans()
    {
        return System.getProperty("netbeans") != null;
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

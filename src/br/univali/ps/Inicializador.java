package br.univali.ps;

import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.ContextoDeTrabalho;
import br.univali.ps.ui.TelaPrincipalDesktop;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author Luiz Fernando Noschang
 * @author Fillipi Pelz
 */
public final class Inicializador
{
    
    private static SplashScreen mySplash;
    private static java.awt.geom.Rectangle2D.Double splashProgressArea;
    private static Graphics2D splashGraphics;
    private static Font font;
    
    public static void main(String argumentos[])
    {
        try
        {
            splashInit();
            String property = System.getProperty("java.specification.version");
            if (Double.valueOf(property) < 1.7)
            {
                JOptionPane.showMessageDialog(null, "Para executar o Portugol Studio é preciso utilizar o Java 1.7 ou superior.","Erro na inicialização",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            PortugolStudio portugolStudio = PortugolStudio.getInstancia();
            portugolStudio.setDepurando(isDepurando(argumentos));
            splashProgress(50);
            
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            
            ContextoDeTrabalho tela = (runningApplet()) ? (new TelaPrincipalApplet()) : (new TelaPrincipalDesktop());
            portugolStudio.iniciar( listarArquivos(argumentos), tela );
            
            splashProgress(100);
        }
        catch (NumberFormatException | HeadlessException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException excecao)
        {            
            String mensagem = "O Portugol Studio encontrou um erro desconhecido e precisa ser fechado:\n" + excecao.getMessage();
            ExcecaoAplicacao excecaoAplicacao = new ExcecaoAplicacao(mensagem, excecao, ExcecaoAplicacao.Tipo.ERRO);

            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
            System.exit(0);
        }
        
        try
        {
            if (mySplash != null)   // check if we really had a spash screen
                mySplash.close();   // if so we're now done with it
        }
        catch (Exception ex)
        {
            ex.printStackTrace(System.out);
        }        
    }
    
    private static boolean runningApplet(){
        return System.getSecurityManager() != null;
    }
    
    private static void splashInit()
    {
        mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null)
        {   // if there are any problems displaying the splash this will be null
            Dimension ssDim = mySplash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;
            // stake out some area for our status information
            splashProgressArea = new Rectangle2D.Double(width * .55, height*.92, width*.4, 12 );

            // create the Graphics environment for drawing status info
            splashGraphics = mySplash.createGraphics();
            font = new Font("Dialog", Font.PLAIN, 14);
            splashGraphics.setFont(font);
            
            splashProgress(0);
        }
    }

    public static void splashProgress(int pct)
    {
        if (mySplash != null && mySplash.isVisible())
        {

            // Note: 3 colors are used here to demonstrate steps
            // erase the old one
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashProgressArea);

            // draw an outline
            splashGraphics.setPaint(Color.BLUE);
            splashGraphics.draw(splashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();

            int doneWidth = Math.round(pct*wid/100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid-1));  // limit 0-width

            // fill the done part one pixel smaller than the outline
            splashGraphics.setPaint(Color.GREEN);
            splashGraphics.fillRect(x, y+1, doneWidth, hgt-1);

            // make sure it's displayed
            mySplash.update();
        }
    }
    
    private static boolean isDepurando(String[] argumentos)
    {
        for (String argumento : argumentos)
        {
            if (argumento.equals("-debug"))
            {
                return true;
            }
        }

        return false;
    }

    private static List<File> listarArquivos(String[] argumentos)
    {
        List<File> arquivos = new ArrayList();
        
        if (argumentos != null && argumentos.length > 0)
        {
            for (String argumento : argumentos)
            {
                File arquivo = new File(argumento);
                
                if (arquivo.exists() && arquivo.isFile() && arquivo.canRead() && arquivo.getName().toLowerCase().endsWith(".por"))
                {
                    arquivos.add(arquivo);
                }
            }
        }
        
        return arquivos;
    }
}

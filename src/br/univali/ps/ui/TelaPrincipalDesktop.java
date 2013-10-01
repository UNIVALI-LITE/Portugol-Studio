package br.univali.ps.ui;

import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.IconFactory;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public final class TelaPrincipalDesktop extends JFrame implements TelaPrincipal
{
    private List<File> arquivosIniciais;
    private boolean primeiraVez = true;
    
    public TelaPrincipalDesktop()
    {
        initComponents();        
        configurarJanela();    
        instalarObservadores();
    }
    
    private void configurarJanela()
    {
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        try
        {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS + "/light-bulb-code.png")));
        }
        catch (IOException ioe)
        {
        }        
    }
    
    private void instalarObservadores()
    {
        instalarObservadorExcecoesNaoTratadas();
        instalarObservadorJanela();
    }
    
    private void instalarObservadorExcecoesNaoTratadas()
    {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread thread, Throwable excecao)
            {
                if ((excecao instanceof ClassNotFoundException) || (excecao instanceof NoClassDefFoundError))
                {
                    String mensagem = "Uma das bibliotecas ou classes necessárias para o funcionamento do Portugol Studio não foi encontrada.\nO Portugol Studio será enecerrado.";
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(mensagem, excecao, ExcecaoAplicacao.Tipo.ERRO));
                    System.exit(1);
                }
                else if (excecao instanceof IllegalArgumentException)
                {
                    excecao.printStackTrace(System.err);
                }
                else
                {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                
                    excecao.printStackTrace(pw);
                    excecao.printStackTrace(System.err);

                    if (sw.toString().contains("rsyntax"))
                    {
                        // Erro do RSyntaxTextArea, printa no console e ignora
                        System.out.println("Erro do RSyntaxTextArea");
                    }
                    else
                    {
                        PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(excecao, ExcecaoAplicacao.Tipo.ERRO));
                    }
                }
            }
        });
    }
    
    private void instalarObservadorJanela()
    {
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                fecharAplicativo();
            }            
        });
        
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                if (primeiraVez)
                {
                    primeiraVez = false;
                    
                    PortugolStudio.getInstancia().abrirArquivosCodigoFonte(arquivosIniciais);
                }
            }
        });
    }
    
    private void fecharAplicativo()
    {
        painelTabulado.fecharTodasAbas(AbaCodigoFonte.class);

        if (!painelTabulado.temAbaAberta(AbaCodigoFonte.class))
        {
            try
            {
                PortugolStudio.getInstancia().getConfiguracoes().salvar();
            }
            catch (ExcecaoAplicacao excecaoAplicacao)
            {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
            }
            
            System.exit(0);
        }
    }    

    @Override
    public PainelTabuladoPrincipal getPainelTabulado()
    {
        return painelTabulado;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painelTabulado = new br.univali.ps.ui.PainelTabuladoPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Portugol Studio");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(700, 520));

        painelTabulado.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        getContentPane().add(painelTabulado, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.univali.ps.ui.PainelTabuladoPrincipal painelTabulado;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setArquivosIniciais(List<File> arquivos)
    {
        this.arquivosIniciais = arquivos;
    }
}

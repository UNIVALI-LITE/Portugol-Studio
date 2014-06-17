package br.univali.ps.ui;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.atualizador.GerenciadorAtualizacoes;
import br.univali.ps.atualizador.ObservadorAtualizacao;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.ui.abas.AbaInicial;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.abas.BotoesControleAba;
import br.univali.ps.ui.telas.TelaErrosPluginsBibliotecas;
import br.univali.ps.ui.telas.TelaLogAtualizacoes;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.apache.commons.io.FileUtils;

public final class TelaPrincipal extends JFrame
{
    private static final Logger LOGGER = Logger.getLogger(TelaPrincipal.class.getName());

    private boolean exibindoPrimeiraVez = true;
    private List<File> arquivosIniciais;

    public static void main(final String argumentos[])
    {
        PortugolStudio.getInstancia().iniciar(argumentos);
    }

    public TelaPrincipal()
    {
        initComponents();
        configurarJanela();
        criaAbas();
        instalarObservadores();
    }

    public void setArquivosIniciais(List<File> arquivos)
    {
        this.arquivosIniciais = arquivos;
    }

    private void criaAbas()
    {
        painelTabuladoPrincipal.setAbaInicial(new AbaInicial(this));
    }

    private void configurarJanela()
    {
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        try
        {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_GRANDES + "/portugol-studio.png")));
        }
        catch (IOException ioe)
        {
        }
    }

    private void instalarObservadores()
    {
        instalarObservadorJanela();
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
                if (exibindoPrimeiraVez)
                {
                    exibindoPrimeiraVez = false;

                    abrirArquivosCodigoFonte(arquivosIniciais);

                    exibirErrosPluginsBibliotecas();
                    exibirLogAtualizacoes();

                    baixarNovasAtualizacoes();
                }
            }
        });
    }

    private void exibirErrosPluginsBibliotecas()
    {
        boolean errosPlugins = GerenciadorPlugins.getInstance().getResultadoCarregamento().contemErros();
        boolean errosBibliotecas = false;

        if (errosPlugins || errosBibliotecas)
        {
            TelaErrosPluginsBibliotecas telaErrosPluginsBibliotecas = PortugolStudio.getInstancia().getTelaErrosPluginsBibliotecas();
            telaErrosPluginsBibliotecas.setVisible(true);
        }
    }

    private void exibirLogAtualizacoes()
    {
        File logAtualizacoes = Configuracoes.getInstancia().getCaminhoLogAtualizacoes();

        if (logAtualizacoes.exists())
        {
            try
            {
                String atualizacoes = FileHandle.open(logAtualizacoes);
                TelaLogAtualizacoes telaLogAtualizacoes = new TelaLogAtualizacoes();

                telaLogAtualizacoes.setAtualizacoes(atualizacoes);
                telaLogAtualizacoes.setVisible(true);

                FileUtils.deleteQuietly(logAtualizacoes);
            }
            catch (Exception excecao)
            {
                LOGGER.log(Level.SEVERE, "Erro ao carregar o log de atualizações", excecao);
            }
        }
    }

    private void baixarNovasAtualizacoes()
    {
        GerenciadorAtualizacoes gerenciadorAtualizacoes = PortugolStudio.getInstancia().getGerenciadorAtualizacoes();
        gerenciadorAtualizacoes.setObservadorAtualizacao(new ObservadorAtualizacao()
        {
            @Override
            public void atualizacaoConcluida()
            {
                BotoesControleAba cabecalho = (BotoesControleAba) getPainelTabulado().getAbaInicial().getCabecalho();
                cabecalho.exibirDica("Foram encontradas novas atualizações. Elas serão instaladas na próxima vez em que o Portugol Studio for iniciado");
            }
        });

        gerenciadorAtualizacoes.baixarAtualizacoes();
    }

    public void criarNovoCodigoFonte()
    {
        AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();

        painelTabuladoPrincipal.add(abaCodigoFonte);
    }

    public void abrirArquivosCodigoFonte(final List<File> arquivos)
    {
        if (arquivos != null && !arquivos.isEmpty())
        {
            focarJanela();
            
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    TelaPrincipal.this.setEnabled(false);
                    
                    for (File arquivo : arquivos)
                    {
                        if (arquivoJaEstaAberto(arquivo))
                        {
                            AbaCodigoFonte aba = obterAbaArquivo(arquivo);
                            aba.selecionar();
                        }
                        else
                        {
                            try
                            {
                                final String conteudo = FileHandle.open(arquivo);
                                final AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();

                                abaCodigoFonte.setCodigoFonte(conteudo, arquivo, true);

                                getPainelTabulado().add(abaCodigoFonte);
                            }
                            catch (Exception excecao)
                            {
                                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecao);
                            }
                        }
                    }

                    TelaPrincipal.this.setEnabled(true);
                }
            });
        }
    }

    public void focarJanela()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                if (janelaMinimizada())
                {
                    restaurarJanela();
                }

                TelaPrincipal.this.toFront();
                TelaPrincipal.this.requestFocusInWindow();                
            }
        });
    }

    private boolean janelaMinimizada()
    {
        return (getExtendedState() & JFrame.ICONIFIED) == JFrame.ICONIFIED;
    }

    private void restaurarJanela()
    {
        setExtendedState(getExtendedState() & (~JFrame.ICONIFIED));
    }

    private boolean arquivoJaEstaAberto(File arquivo)
    {
        AbaCodigoFonte aba = obterAbaArquivo(arquivo);

        return aba != null;
    }

    public AbaCodigoFonte obterAbaArquivo(File arquivo)
    {
        for (Aba aba : getPainelTabulado().getAbas(AbaCodigoFonte.class))
        {
            AbaCodigoFonte abaCodigoFonte = (AbaCodigoFonte) aba;
            PortugolDocumento documento = abaCodigoFonte.getPortugolDocumento();

            if (documento.getFile() != null)
            {
                try
                {
                    Path caminhoArquivoAba = documento.getFile().toPath();
                    Path caminhoArquivoAbrir = arquivo.toPath();

                    if (Files.isSameFile(caminhoArquivoAba, caminhoArquivoAbrir))
                    {
                        return abaCodigoFonte;
                    }
                }
                catch (IOException excecao)
                {
                    LOGGER.log(Level.SEVERE, String.format("Erro ao verificar se o '%s' arquivo já estava aberto em alguma aba", arquivo.getAbsolutePath()), excecao);
                }
            }
        }

        return null;
    }

    private void fecharAplicativo()
    {
        painelTabuladoPrincipal.fecharTodasAbas(AbaCodigoFonte.class);

        if (!painelTabuladoPrincipal.temAbaAberta(AbaCodigoFonte.class))
        {
            PortugolStudio.getInstancia().finalizar(0);
        }
    }

    public PainelTabuladoPrincipal getPainelTabulado()
    {
        return painelTabuladoPrincipal;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painelTabuladoPrincipal = new br.univali.ps.ui.PainelTabuladoPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Portugol Studio");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(700, 520));

        painelTabuladoPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        getContentPane().add(painelTabuladoPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.univali.ps.ui.PainelTabuladoPrincipal painelTabuladoPrincipal;
    // End of variables declaration//GEN-END:variables
}

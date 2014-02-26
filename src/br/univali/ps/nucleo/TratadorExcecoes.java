
package br.univali.ps.nucleo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 23/08/2011
 * 
 */

public final class TratadorExcecoes
{
    private static Logger LOGGER = Logger.getLogger(TratadorExcecoes.class.getName());
    
    private JPanel painel;
    private JLabel rotulo;
    private JTextArea caixaTexto;
    private PrintWriter escritorExcecao;
    private OutputStream fluxoSaida;    
    private JScrollPane painelRolagem;
    private JPanel painelsul;
    private JButton botaoCopiarTexto;

    TratadorExcecoes()
    {
        
    }
  
    public void exibirExcecao(Exception excecao)
    {
        LOGGER.log(Level.WARNING, excecao.getMessage(), excecao);
        
        ExcecaoAplicacao excecaoAplicacao = transformarExcecao(excecao);
        PortugolStudio portugolStudio = PortugolStudio.getInstancia();
        
        int tipoDialogo = getTipoDialogo(excecaoAplicacao);
                
        if ((portugolStudio.isDepurando()) && (excecaoAplicacao.getTipo() == ExcecaoAplicacao.Tipo.ERRO))
            exibirExcecaoDetalhada(excecaoAplicacao);
                
        else exibirexcecaoSimples(excecaoAplicacao, tipoDialogo);
    }
    
    private void exibirexcecaoSimples(ExcecaoAplicacao excecaoAplicacao, int tipoDialogo)
    {        
        JOptionPane.showMessageDialog(null, excecaoAplicacao.getMessage(), "Portugol Studio", tipoDialogo);
    }
    
    private void exibirExcecaoDetalhada(ExcecaoAplicacao excecaoAplicacao)
    {
        if (caixaTexto == null) inicializarComponentes();
        
        caixaTexto.setText(null);
        rotulo.setText(String.format("<html><p>%s<br><br>Detalhes:</p></html>", excecaoAplicacao.getMessage()));
        
        excecaoAplicacao.printStackTrace(escritorExcecao);
        
        JOptionPane.showMessageDialog(null, painel, "Biblioteca GEGK", JOptionPane.ERROR_MESSAGE);
    }

    private void inicializarComponentes()
    {
        rotulo = new JLabel();
        rotulo.setBorder(new EmptyBorder(8, 0, 8, 8));

        caixaTexto = new JTextArea();
        caixaTexto.setEditable(false);
        
        painelRolagem = new JScrollPane();
        painelRolagem.setViewportView(caixaTexto);
        painelRolagem.setPreferredSize(new Dimension(400, 250));

        botaoCopiarTexto = new JButton();               
        
        botaoCopiarTexto.setAction(new AbstractAction("Copiar Texto")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Clipboard areaTransferencia = Toolkit.getDefaultToolkit().getSystemClipboard();
                areaTransferencia.setContents(new StringSelection(caixaTexto.getText()), null);
            }
        });
        
        painelsul = new JPanel();
        painelsul.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelsul.setPreferredSize(new Dimension(20, 50));
        painelsul.add(botaoCopiarTexto);
        
        painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.add(BorderLayout.NORTH, rotulo);
        painel.add(BorderLayout.CENTER, painelRolagem);
        painel.add(BorderLayout.SOUTH, painelsul);
        
        fluxoSaida = new FluxoSaidaExcecao();
        escritorExcecao = new PrintWriter(fluxoSaida, true);
    }
    
    private final class FluxoSaidaExcecao extends OutputStream
    {
        private StringBuilder construtorTexto;

        public FluxoSaidaExcecao() 
        {
            construtorTexto = new StringBuilder(128);
        }
        
        @Override
        public void write(int b) throws IOException 
        {
            construtorTexto.append((char) b);
        }

        @Override
        public void flush() throws IOException 
        {
            caixaTexto.append(construtorTexto.toString());
            construtorTexto.setLength(0);            
        }
    }
    
    private int getTipoDialogo(ExcecaoAplicacao excecaoAplicacao)
    {
        switch (excecaoAplicacao.getTipo())
        {
            case ERRO : return JOptionPane.ERROR_MESSAGE;
            case MENSAGEM : return JOptionPane.INFORMATION_MESSAGE;
            case AVISO : return JOptionPane.WARNING_MESSAGE;
            default : return 0;
        }
    }
    
    private ExcecaoAplicacao transformarExcecao(Exception excecao)
    {
         if (!(excecao instanceof ExcecaoAplicacao))
             excecao = new ExcecaoAplicacao(excecao, ExcecaoAplicacao.Tipo.ERRO);
        
        return (ExcecaoAplicacao) excecao;
    }
}

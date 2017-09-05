package br.univali.portugol.nucleo.bibliotecas.base;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author Luiz Fernando
 */
final class Testador
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run()
            {                
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    
                    JFrame janela = new JFrame("Testar bibliotecas");
                    janela.setSize(550, 250);
                    janela.setLocationRelativeTo(null);
                    janela.setResizable(false);
                    janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    final JEditorPane editorLog = new JEditorPane();
                    editorLog.setEditable(false);
                    editorLog.setContentType("text/html");
                    
                    JScrollPane painelRolagem = new JScrollPane();
                    painelRolagem.setViewportView(editorLog);
                    painelRolagem.setBorder(new LineBorder(new Color(210, 210, 210), 1));

                    final JLabel rotuloStatus = new JLabel("Não foram encontrados erros");
                    rotuloStatus.setPreferredSize(new Dimension(10, 30));
                    rotuloStatus.setFont(rotuloStatus.getFont().deriveFont(20.0f));
                    rotuloStatus.setVerticalAlignment(JLabel.CENTER);
                    
                    JPanel painelConteudo = new JPanel(new BorderLayout());
                    painelConteudo.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), painelConteudo.getBorder()));
                    painelConteudo.add(painelRolagem, BorderLayout.CENTER);
                    painelConteudo.add(rotuloStatus, BorderLayout.SOUTH);

                    janela.getContentPane().setLayout(new GridLayout(1, 1));
                    janela.getContentPane().add(painelConteudo);
                    
                    janela.addComponentListener(new ComponentAdapter() 
                    {
                        private boolean primeiraVez = true;
                        
                        @Override
                        public void componentShown(ComponentEvent e)
                        {
                            if (primeiraVez)
                            {
                                primeiraVez = false;
                                int erros = 0;
                                
                                StringBuilder log = new StringBuilder();
                                GerenciadorBibliotecas gerenciadorBibliotecas = GerenciadorBibliotecas.getInstance();
                                
                                log.append("<html><body><div>");
                                    
                                for (String biblioteca : gerenciadorBibliotecas.listarBibliotecasDisponiveis())
                                {
                                    try
                                    {
                                        gerenciadorBibliotecas.obterMetaDadosBiblioteca(biblioteca);
                                        log.append(String.format("<p>A biblioteca '%s' não possui erros!</p><br>", biblioteca));
                                    }
                                    catch(Exception excecao)
                                    {
                                        excecao.printStackTrace(System.err);
                                        erros++;
                                        log.append(String.format("<p style='text-align: justify; color : red;'>%s</p><br><br>", excecao.getMessage()));
                                    }                                    
                                }
                                
                                log.append("</div></body></html>");
                                
                                editorLog.setText(log.toString());
                                
                                if (erros > 0)
                                {
                                    rotuloStatus.setText(String.format("%d erro(s)", erros));
                                    rotuloStatus.setForeground(Color.RED);
                                }
                            }
                        }
                    });
                    
                    janela.setVisible(true);
                }
                catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | HeadlessException excecao)
                {
                    JOptionPane.showMessageDialog(null, excecao.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    excecao.printStackTrace(System.out);
                }
            }
        });
    }
}

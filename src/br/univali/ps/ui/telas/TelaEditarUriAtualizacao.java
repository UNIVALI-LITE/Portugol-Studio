package br.univali.ps.ui.telas;

import br.univali.ps.atualizador.GerenciadorAtualizacoes;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.nucleo.TratadorExcecoes;
import br.univali.ps.ui.utils.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class TelaEditarUriAtualizacao extends JDialog
{
    private static final Logger LOGGER = Logger.getLogger(TelaEditarUriAtualizacao.class.getName());
    private static final File arquivoUris = new File(new File(Configuracoes.getInstancia().getDiretorioUsuario(), ".portugol"), "uri.properties");
    private static final DefaultComboBoxModel<String> listaUris = new DefaultComboBoxModel<>();

    private Color corTextoPadrao;

    public TelaEditarUriAtualizacao()
    {
        initComponents();

        caixaSelecaoUri.setModel(listaUris);
        caixaSelecaoUri.setRenderer(new Renderer());
        caixaSelecaoUri.setEditor(new Editor());
        getRootPane().setDefaultButton(botaoOK);
        this.setIconImage(IconFactory.getDefaultWindowIcon());
        setModal(true);
        carregarUris();
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                caixaSelecaoUri.getEditor().setItem(Configuracoes.getInstancia().getUriAtualizacao());
            }
        });
    }

    private void carregarUris()
    {
        try (FileReader reader = new FileReader(arquivoUris))
        {
            Properties uris = new Properties();

            uris.load(reader);

            int tamanho = Integer.parseInt(uris.getProperty("uris", "0"));

            for (int i = 0; i < tamanho; i++)
            {
                listaUris.addElement(uris.getProperty(String.format("uri[%d]", i)));
            }
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.WARNING, "Erro ao carregar o arquivo de URIs", excecao);
        }
    }

    private void salvarUris()
    {
        try (FileWriter writer = new FileWriter(arquivoUris))
        {
            Properties uris = new Properties();

            uris.setProperty("uris", Integer.toString(listaUris.getSize()));

            for (int i = 0; i < listaUris.getSize(); i++)
            {
                uris.setProperty(String.format("uri[%d]", i), listaUris.getElementAt(i));
            }

            uris.store(writer, null);
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.WARNING, "Erro ao salvar o arquivo de URIs", excecao);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rotuloTitulo = new javax.swing.JLabel();
        botaoOK = new javax.swing.JButton();
        caixaSelecaoUri = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Portugol Studio");
        setResizable(false);

        rotuloTitulo.setText("Informe a nova URI de onde as atualizações serão obtidas:");

        botaoOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/yes.png"))); // NOI18N
        botaoOK.setText("OK");
        botaoOK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoOK.setFocusPainted(false);
        botaoOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoOKActionPerformed(evt);
            }
        });

        caixaSelecaoUri.setEditable(true);
        caixaSelecaoUri.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(caixaSelecaoUri, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoOK, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rotuloTitulo)
                        .addGap(0, 178, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rotuloTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caixaSelecaoUri, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoOK, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoOKActionPerformed
    {//GEN-HEADEREND:event_botaoOKActionPerformed
        try
        {
            String uri = (String) caixaSelecaoUri.getSelectedItem();
            URL url = new URL(uri);

            Configuracoes.getInstancia().setUriAtualizacao(url.toString());
            
            GerenciadorAtualizacoes gerenciadorAtualizacoes = PortugolStudio.getInstancia().getGerenciadorAtualizacoes();
            gerenciadorAtualizacoes.setUriAtualizacao(url.toString());

            if (listaUris.getIndexOf(uri) < 0)
            {
                listaUris.addElement(uri);
            }

            salvarUris();
            dispose();
        }
        catch (MalformedURLException ex)
        {
            TratadorExcecoes tratadorExcecoes = PortugolStudio.getInstancia().getTratadorExcecoes();
            tratadorExcecoes.exibirExcecao(new ExcecaoAplicacao("A URI informada é inválida", ExcecaoAplicacao.Tipo.ERRO));
        }
    }//GEN-LAST:event_botaoOKActionPerformed

    private final class Renderer extends DefaultListCellRenderer
    {
        private final Border borda = new EmptyBorder(4, 4, 4, 4);

        public Renderer()
        {
            setPreferredSize(new Dimension(10, 32));
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            JLabel componente = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            componente.setBorder(borda);
            componente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            return componente;
        }
    }

    private final class Editor extends JTextField implements ComboBoxEditor
    {
        private final String mensagemAjuda = "Digite uma URI ou selecione uma na lista abaixo";
        private final Font fonteTextoAjuda;

        public Editor()
        {
            setBorder(new EmptyBorder(2, 4, 2, 0));
            fonteTextoAjuda = getFont().deriveFont(getFont().getStyle());

            addFocusListener(new FocusAdapter()
            {
                @Override
                public void focusGained(FocusEvent e)
                {
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e)
                {
                    repaint();
                }
            });
        }

        @Override
        public Component getEditorComponent()
        {
            return this;
        }

        @Override
        public void setItem(Object anObject)
        {
            setText((anObject != null) ? anObject.toString() : "");
            setSelectionStart(getText().length());
            setSelectionEnd(getText().length());
        }

        @Override
        public Object getItem()
        {
            return getText();
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            if ((this.getText() == null || this.getText().trim().length() == 0))
            {
                int altura = this.getHeight();
                Font fonteAnterior = g.getFont();
                Color corAnterior = g.getColor();

                g.setFont(fonteTextoAjuda);
                g.setColor(getDisabledTextColor());

                int h = g.getFontMetrics().getHeight();
                int baseTexto = (altura - h) / 2 + h - 4;
                int x = this.getInsets().left;

                Graphics2D g2d = (Graphics2D) g;
                RenderingHints hints = g2d.getRenderingHints();

                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.drawString(mensagemAjuda, x, baseTexto);
                g2d.setRenderingHints(hints);

                g.setFont(fonteAnterior);
                g.setColor(corAnterior);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoOK;
    private javax.swing.JComboBox caixaSelecaoUri;
    private javax.swing.JLabel rotuloTitulo;
    // End of variables declaration//GEN-END:variables
}

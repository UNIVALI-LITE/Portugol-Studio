package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.telas.utils.DicaInterface;
import br.univali.ps.ui.utils.IconFactory;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

/**
 *
 * @author Adson Estevesa
 */
public class TelaDicas extends javax.swing.JPanel implements Themeable {

    /**
     * Creates new form TelaDicas
     */
    private final List<DicaInterface> dicas;
    private Integer item = 0;

    /**
     * Creates new form TelaDicas
     */
    public TelaDicas() {
        initComponents();
        configurarCores();
        String dir = IconFactory.CAMINHO_IMAGENS + "/dicas";
        dicas = loadHints(dir);
        atualiza(item);
        exibirSempre.setSelected(true);

        webButton1.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "previous.png"));
        webButton2.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "next.png"));
        webButton3.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "theme.png"));
        configurarNavegacaoPeloTeclado();
    }

    @Override
    public void configurarCores() {
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configurarBotao(webButton1, ColorController.FUNDO_CLARO, Color.white, ColorController.COR_DESTAQUE, Color.orange, 15);
            WeblafUtils.configurarBotao(webButton2, ColorController.FUNDO_CLARO, Color.white, ColorController.COR_DESTAQUE, Color.orange, 15);
            WeblafUtils.configurarBotao(webButton3, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 15);
        }
        titleLabel.setBackground(ColorController.FUNDO_ESCURO);
        this.setBackground(ColorController.FUNDO_CLARO);
        descriptionLabel.setForeground(ColorController.COR_LETRA);
        exibirSempre.setForeground(ColorController.COR_LETRA);
    }

    private void configurarNavegacaoPeloTeclado() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Proxima");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Anterior");

        actionMap.put("Proxima", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webButton2.doClick();
            }
        });

        actionMap.put("Anterior", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webButton1.doClick();
            }
        });
    }

    private void atualiza(Integer indice) {

        imagePane.removeAll();
        DicaInterface dicaInterface = dicas.get(indice);
        JLabel imagelabel = new JLabel(dicaInterface.getImagem());
        imagePane.add(imagelabel);
        titleLabel.setText("<html><head></head><body>" + (indice + 1) + "/" + dicas.size() + " - " + dicaInterface.getTitulo() + "</body></html>");
        descriptionLabel.setText("<html><head></head><body>" + dicaInterface.getDescricao() + "</body></html>");
    }

    private List<DicaInterface> loadHints(String dir) {
        List<DicaInterface> lista = new ArrayList<>();
        Properties prop = new Properties();
        try {
            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(dir + "/index.properties");
            prop.load(new InputStreamReader(resourceAsStream, "UTF-8"));
            for (int i = 0; i < Integer.parseInt(prop.getProperty("dicas")); i++) {
                String nome = "dica" + i + ".";
                String titulo = prop.getProperty(nome + "title");
                String descricao = prop.getProperty(nome + "description");
//                InputStream imageStream = ClassLoader.getSystemClassLoader().getResourceAsStream(dir+"/"+prop.getProperty(nome+"image"));
//                Image imagem = ImageIO.read(imageStream);
                ImageIcon imagem = new ImageIcon(getClass().getResource("/" + dir + "/" + prop.getProperty(nome + "image")));
                DicaInterface dica = new DicaInterface(titulo, descricao, imagem);
                lista.add(dica);
            }
            return lista;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        carrouselPane = new javax.swing.JPanel();
        hintPane = new javax.swing.JPanel();
        imagePane = new javax.swing.JPanel();
        descriptionPane = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        descriptionLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        webButton1 = new com.alee.laf.button.WebButton();
        jPanel3 = new javax.swing.JPanel();
        webButton2 = new com.alee.laf.button.WebButton();
        scrollPane = new javax.swing.JPanel();
        optionPane = new javax.swing.JPanel();
        exibirSempre = new javax.swing.JCheckBox();
        webButton3 = new com.alee.laf.button.WebButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setMinimumSize(new java.awt.Dimension(640, 480));
        setPreferredSize(new java.awt.Dimension(640, 550));
        setLayout(new java.awt.BorderLayout());

        carrouselPane.setOpaque(false);
        carrouselPane.setLayout(new java.awt.BorderLayout());

        hintPane.setOpaque(false);
        hintPane.setLayout(new java.awt.BorderLayout());

        imagePane.setBackground(new java.awt.Color(51, 51, 51));
        imagePane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        imagePane.setLayout(new java.awt.BorderLayout());
        hintPane.add(imagePane, java.awt.BorderLayout.CENTER);

        descriptionPane.setOpaque(false);
        descriptionPane.setLayout(new java.awt.BorderLayout());

        titleLabel.setBackground(new java.awt.Color(49, 104, 146));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Título");
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        titleLabel.setOpaque(true);
        descriptionPane.add(titleLabel, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(148, 140));
        jPanel1.setLayout(new java.awt.BorderLayout());

        descriptionLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        descriptionLabel.setMaximumSize(new java.awt.Dimension(34, 150));
        descriptionLabel.setMinimumSize(new java.awt.Dimension(34, 125));
        descriptionLabel.setPreferredSize(new java.awt.Dimension(34, 125));
        jPanel1.add(descriptionLabel, java.awt.BorderLayout.CENTER);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        webButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/grande/previous.png"))); // NOI18N
        webButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(webButton1, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        webButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/grande/next.png"))); // NOI18N
        webButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(webButton2, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel3, java.awt.BorderLayout.LINE_END);

        descriptionPane.add(jPanel1, java.awt.BorderLayout.CENTER);

        hintPane.add(descriptionPane, java.awt.BorderLayout.SOUTH);

        carrouselPane.add(hintPane, java.awt.BorderLayout.CENTER);

        scrollPane.setOpaque(false);
        scrollPane.setLayout(new java.awt.BorderLayout());
        carrouselPane.add(scrollPane, java.awt.BorderLayout.SOUTH);

        add(carrouselPane, java.awt.BorderLayout.CENTER);

        optionPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 7, 0, 7));
        optionPane.setOpaque(false);
        optionPane.setPreferredSize(new java.awt.Dimension(0, 40));
        optionPane.setLayout(new java.awt.BorderLayout());

        exibirSempre.setBackground(new java.awt.Color(250, 250, 250));
        exibirSempre.setText("Mostrar Dicas ao Iniciar");
        exibirSempre.setOpaque(false);
        exibirSempre.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                exibirSempreStateChanged(evt);
            }
        });
        optionPane.add(exibirSempre, java.awt.BorderLayout.WEST);

        webButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/grande/theme.png"))); // NOI18N
        webButton3.setText("Trocar Tema");
        webButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webButton3ActionPerformed(evt);
            }
        });
        optionPane.add(webButton3, java.awt.BorderLayout.EAST);

        add(optionPane, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void webButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webButton1ActionPerformed
        item--;
        if (item < 0) {
            item = dicas.size() - 1;
        }
        atualiza(item);
    }//GEN-LAST:event_webButton1ActionPerformed

    private void webButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webButton2ActionPerformed
        item = (item + 1) % dicas.size();
        atualiza(item);
    }//GEN-LAST:event_webButton2ActionPerformed

    private void exibirSempreStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_exibirSempreStateChanged
        Configuracoes configuracoes = Configuracoes.getInstancia();
        configuracoes.setExibirDicasInterface(exibirSempre.isSelected());
    }//GEN-LAST:event_exibirSempreStateChanged

    private void webButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webButton3ActionPerformed
        PortugolStudio.getInstancia().getTelaEditarTemas().setVisible(true);
    }//GEN-LAST:event_webButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel carrouselPane;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JPanel descriptionPane;
    private javax.swing.JCheckBox exibirSempre;
    private javax.swing.JPanel hintPane;
    private javax.swing.JPanel imagePane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel optionPane;
    private javax.swing.JPanel scrollPane;
    private javax.swing.JLabel titleLabel;
    private com.alee.laf.button.WebButton webButton1;
    private com.alee.laf.button.WebButton webButton2;
    private com.alee.laf.button.WebButton webButton3;
    // End of variables declaration//GEN-END:variables
}

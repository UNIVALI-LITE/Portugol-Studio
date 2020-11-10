package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.paineis.PainelTextoAlerta;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.PSMainTabbedPaneUI;
import br.univali.ps.ui.utils.FileHandle;
import br.univali.ps.ui.utils.WebConnectionUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adson Estevesa
 */
public class TelaAlertas extends javax.swing.JPanel implements Themeable {

    List<PainelTextoAlerta> alertas;
    List<String> mensagensLidas;
    
    public TelaAlertas() {
        initComponents();
        configurarCores();
        carregarMensagensLidas();
        alertas = new ArrayList<>();
        loadAlertas(Configuracoes.getInstancia().getUriAlertas());
    }

    @Override
    public void configurarCores() {
        this.labelSemAlertas.setForeground(ColorController.COR_LETRA);
        this.labelTitulo.setForeground(ColorController.COR_LETRA);
        this.painelTabuladoAlertas.setForeground(ColorController.COR_LETRA);
        this.caixaCheckLido.setForeground(ColorController.COR_LETRA);
        setBackground(ColorController.COR_DESTAQUE);
        this.painelDeBaixo.setOpaque(false);
        this.painelTitulo.setBackground(ColorController.VERMELHO);
        this.painelTabuladoAlertas.setOpaque(false);
        this.painelTabuladoAlertas.setUI(new PSMainTabbedPaneUI());
    }
    
    public void carregarMensagensLidas()
    {
        mensagensLidas = new ArrayList<>();
        File diretorioConfiguracoes = Configuracoes.getInstancia().getDiretorioConfiguracoes();
        File arquivoMensagensLidas = new File(diretorioConfiguracoes, "mensagensLidas.txt");
        if(arquivoMensagensLidas.exists())
        {
            try {
                String arquivo = FileHandle.open(arquivoMensagensLidas);
                String [] caminhos = arquivo.split("\n");
                for (String caminho : caminhos) {
                        mensagensLidas.add(caminho);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void mostrarTela()
    {
        if(this.alertas.isEmpty())
        {
            return;
        }
        PortugolStudio.getInstancia().getTelaAlertas().setVisible(true);
    }

    private void loadAlertas(String dir) {
        
        try {
            String jsontext = WebConnectionUtils.getString(dir);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = (ObjectNode) mapper.readTree(jsontext);
            
            JsonNode alertasNode = node.get("alertas&avisos");
            
            if(alertasNode.size()>0)
            {
                this.painelTabuladoAlertas.removeAll();
            }
            
            for (JsonNode jsonNode : alertasNode) {
                ArrayNode versaoArray = (ArrayNode)jsonNode.get("versao");
                ArrayNode OSArray = (ArrayNode)jsonNode.get("OS");
                String idmensagem = jsonNode.get("id").asText("");
                String OSName = System.getProperty("os.name").toLowerCase();
                boolean acceptedOS = false;
                boolean acceptedVersion = false;
                                
                for (JsonNode OS : OSArray) {
                    if(OSName.contains(OS.asText("?")))
                    {
                        acceptedOS = true;
                        break;
                    }
                }
                
                for (JsonNode versao : versaoArray) {
                    if(versao.asText("").equals(PortugolStudio.getInstancia().getVersao()))
                    {
                        acceptedVersion = true;
                        break;
                    }
                }
                
                if(!acceptedOS || !acceptedVersion || mensagensLidas.contains(idmensagem))
                {
                    continue;
                }                
                
                String titulo = jsonNode.get("titulo").asText("");
                String subtitulo = jsonNode.get("subtitulo").asText("");
                String mensagem = jsonNode.get("mensagem").asText("");
                String link = jsonNode.get("link").asText("");
                String tipo = jsonNode.get("tipo").asText("");
                
                PainelTextoAlerta alerta = new PainelTextoAlerta(titulo, subtitulo, mensagem, link, idmensagem);
                this.alertas.add(alerta);
                this.painelTabuladoAlertas.add(tipo,alerta);
            }
            
            revalidate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelTitulo = new javax.swing.JPanel();
        labelTitulo = new javax.swing.JLabel();
        painelTabuladoAlertas = new javax.swing.JTabbedPane();
        labelSemAlertas = new javax.swing.JLabel();
        painelDeBaixo = new javax.swing.JPanel();
        caixaCheckLido = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setMinimumSize(new java.awt.Dimension(640, 480));
        setPreferredSize(new java.awt.Dimension(640, 550));
        setLayout(new java.awt.BorderLayout());

        painelTitulo.setMaximumSize(new java.awt.Dimension(640, 100));
        painelTitulo.setMinimumSize(new java.awt.Dimension(640, 100));
        painelTitulo.setPreferredSize(new java.awt.Dimension(640, 100));
        painelTitulo.setLayout(new java.awt.BorderLayout());

        labelTitulo.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitulo.setText("Alerta Importante!");
        painelTitulo.add(labelTitulo, java.awt.BorderLayout.CENTER);

        add(painelTitulo, java.awt.BorderLayout.NORTH);

        painelTabuladoAlertas.setMaximumSize(new java.awt.Dimension(640, 340));
        painelTabuladoAlertas.setMinimumSize(new java.awt.Dimension(640, 340));
        painelTabuladoAlertas.setPreferredSize(new java.awt.Dimension(640, 340));

        labelSemAlertas.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        labelSemAlertas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSemAlertas.setText("Não há Alertas ou Avisos!");
        painelTabuladoAlertas.addTab("", labelSemAlertas);

        add(painelTabuladoAlertas, java.awt.BorderLayout.CENTER);

        painelDeBaixo.setMaximumSize(new java.awt.Dimension(640, 40));
        painelDeBaixo.setMinimumSize(new java.awt.Dimension(640, 40));
        painelDeBaixo.setPreferredSize(new java.awt.Dimension(640, 40));
        painelDeBaixo.setLayout(new java.awt.BorderLayout());

        caixaCheckLido.setText("Li os avisos e não quero que apareçam novamente");
        caixaCheckLido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caixaCheckLidoActionPerformed(evt);
            }
        });
        painelDeBaixo.add(caixaCheckLido, java.awt.BorderLayout.CENTER);

        add(painelDeBaixo, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void caixaCheckLidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caixaCheckLidoActionPerformed
        File diretorioConfiguracoes = Configuracoes.getInstancia().getDiretorioConfiguracoes();
        File arquivoMensagensLidas = new File(diretorioConfiguracoes, "mensagensLidas.txt");
        try {
            for (PainelTextoAlerta alerta : alertas) {
                    mensagensLidas.add(alerta.getIdMensagem());
            }

            StringBuilder mensagens = new StringBuilder();

            for (String mensagemLida : mensagensLidas) {
                mensagens.append(mensagemLida+"\n");
            }

            FileHandle.save(mensagens.toString(), arquivoMensagensLidas, "UTF-8");                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_caixaCheckLidoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox caixaCheckLido;
    private javax.swing.JLabel labelSemAlertas;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JPanel painelDeBaixo;
    private javax.swing.JTabbedPane painelTabuladoAlertas;
    private javax.swing.JPanel painelTitulo;
    // End of variables declaration//GEN-END:variables
}

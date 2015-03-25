package br.univali.ps.ui.rstautil.lista;

import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.No;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.TransferHandler;

/**
 *
 * @author elieser
 */
public class ListaDeNosInspecionados extends JList<No> {

    private static final String INSTRUCAO = "Arraste um símbolo para inspecioná-lo";
    private DefaultListModel<No> model = new DefaultListModel<>();

    public ListaDeNosInspecionados() {
        model.clear();
        setModel(model);
        setDropMode(DropMode.INSERT);
        setTransferHandler(new TransferHandler() {

            public boolean canImport(TransferHandler.TransferSupport support) {
                support.setShowDropLocation(true);
                return support.isDrop();
            }

            @Override
            public boolean importData(TransferHandler.TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }

                No noTransferido = null;
                try {
                    noTransferido = (No) support.getTransferable().getTransferData(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);
                } catch (Exception e) {
                    return false;
                }

                //JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
                //DefaultListModel listModel = (DefaultListModel) listaDeNosInspecionados.getModel();
                model.addElement(noTransferido);
                return true;
            }
        });

        setCellRenderer(new ListCellRenderer<No>() {

            private JLabel component = new JLabel("teste");
            
            //@Override
            public Component getListCellRendererComponent(JList<? extends No> jlist, No no, int i, boolean bln, boolean bln1) {
                NoDeclaracaoVariavel noDeclaracaoVariavel = (NoDeclaracaoVariavel)no;
                StringBuilder sb = new StringBuilder();
                sb.append("<html>");
//                if (currentPortugolTreeNode.isDeclarado()) {
//                    sb.append("<b>");
//                }
                sb.append(noDeclaracaoVariavel.getNome());
//                if (currentPortugolTreeNode.isDeclarado()) {
//                    sb.append("</b>");
//                }
                
                  //tipo
//                sb.append(" : ");
//                sb.append("<font color='#888888'>");
//                sb.append(noDeclaracaoVariavel.getTipoDado().getNome());
//                sb.append("<font color='#000000'>");
                  //-----------
                
//                if (currentPortugolTreeNode != null && currentPortugolTreeNode.getValor() != null  && !(currentPortugolTreeNode.getValor() instanceof List)) {
//                    Object valor = currentPortugolTreeNode.getValor();
//                    if (valor instanceof Boolean) {
//                        valor = ((Boolean) valor) ? "verdadeiro" : "falso";
//                    }
//                    sb.append(" = ").append(valor);
//                }
                final String valor = sb.toString();
                component.setText(valor);
//                if (currentPortugolTreeNode.isModificado()) {
//                    component.setForeground(Color.BLUE);
//                }
                Icon icone = getIcon(noDeclaracaoVariavel.getTipoDado());
                component.setIcon(icone);
                component.setDisabledIcon(icone);
                return component;
            }

            private Icon getIcon(TipoDado tipoDado) {
                String iconName = "unknown.png";
                if (tipoDado != null) {
                    iconName = tipoDado.getNome() + ".png";
                }
                return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, iconName);
            }

            //@Override
//            public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
//                StringBuilder sb = new StringBuilder();
//                sb.append("<html>");
//                if (currentPortugolTreeNode.isDeclarado()) {
//                    sb.append("<b>");
//                }
//                sb.append(noDeclaracaoVariavel.getNome());
//                if (currentPortugolTreeNode.isDeclarado()) {
//                    sb.append("</b>");
//                }
//                sb.append(" : ");
//                sb.append("<font color='#888888'>");
//                sb.append(noDeclaracaoVariavel.getTipoDado().getNome());
//                sb.append("<font color='#000000'>");
//                if (currentPortugolTreeNode != null
//                        && currentPortugolTreeNode.getValor() != null
//                        && !(currentPortugolTreeNode.getValor() instanceof List)) {
//                    Object valor = currentPortugolTreeNode.getValor();
//                    if (valor instanceof Boolean) {
//                        valor = ((Boolean) valor) ? "verdadeiro" : "falso";
//                    }
//                    sb.append(" = ").append(valor);
//                }
//                final String valor = sb.toString();
//                component.setText(valor);
//                if (currentPortugolTreeNode.isModificado()) {
//                    component.setForeground(Color.BLUE);
//                }
//                Icon icone = getIcon(noDeclaracaoVariavel.getTipoDado());
//                component.setIcon(icone);
//                component.setDisabledIcon(icone);
//                return null;
//            }
//
//            //@Override
//            public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA {
//                StringBuilder sb = new StringBuilder();
//                sb.append("<html>");
//                if (currentPortugolTreeNode.isDeclarado()) {
//                    sb.append("<b>");
//                }
//                sb.append(noDeclaracaoParametro.getNome());
//                if (currentPortugolTreeNode.isDeclarado()) {
//                    sb.append("</b>");
//                }
//                Icon icon = null;
//                if (noDeclaracaoParametro.getQuantificador() == Quantificador.VETOR) {
//                    sb.append("[]");
//                    icon = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "vetor.gif");
//                } else if (noDeclaracaoParametro.getQuantificador() == Quantificador.MATRIZ) {
//                    sb.append("[][]");
//                    icon = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "matriz.gif");
//                } else {
//                    icon = getIcon(noDeclaracaoParametro.getTipoDado());
//                }
//                sb.append(" : ");
//                sb.append("<font color='#888888'>");
//                sb.append(noDeclaracaoParametro.getTipoDado().getNome());
//
//                if (currentPortugolTreeNode != null
//                        && currentPortugolTreeNode.getValor() != null
//                        && !(currentPortugolTreeNode.getValor() instanceof List)) {
//                    sb.append(" = ").append(currentPortugolTreeNode.getValor());
//                }
//
//                component.setText(sb.toString());
//                if (currentPortugolTreeNode.isModificado()) {
//                    component.setForeground(Color.BLUE);
//                }
//                component.setDisabledIcon(icon);
//                component.setIcon(icon);
//                return null;
//            }
//
//
//            
//            public Object visitar(PortugolTreeNode node) {
//                try {
//                    this.currentPortugolTreeNode = node;
//                    return (node.getASTNode() != null) ? (JLabel) node.getASTNode().aceitar(this) : null;
//                } catch (ExcecaoVisitaASA ex) {
//                    return null;
//                }
//            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getModel().getSize() <= 0) {
            FontMetrics metrics = g.getFontMetrics();
            int larguraInstrucao = metrics.stringWidth(INSTRUCAO);
            int x = getWidth() / 2 - larguraInstrucao / 2;
            if (x < 0) {
                x = 0;
            }
            g.setColor(Color.GRAY);
            g.drawString(INSTRUCAO, x, getHeight() / 2);
        }
    }

}

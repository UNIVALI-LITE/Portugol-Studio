package br.univali.ps.ui.rstautil.lista;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.rstautil.ComparadorNos;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
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
public class ListaDeNosInspecionados extends JList<ListaDeNosInspecionados.ItemDaLista> implements ObservadorExecucao {

    private static final String INSTRUCAO = "Arraste um símbolo para inspecioná-lo";
    private DefaultListModel<ItemDaLista> model = new DefaultListModel<>();
    private static final ComparadorNos COMPARADOR_NOS = new ComparadorNos();

    public ListaDeNosInspecionados() {
        model.clear();
        setModel(model);
        setDropMode(DropMode.INSERT);
        setTransferHandler(new TratadorDeArrastamento());
        setCellRenderer(new RenderizadorDosItensDaLista());
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

    public static class ItemDaLista {

        private NoDeclaracaoVariavel no;
        private Object valor;

        public ItemDaLista(NoDeclaracaoVariavel no) {
            this.no = no;
        }

        void setValor(Object valor) {
            this.valor = valor;
        }

        public Object getValor() {
            return valor;
        }

        public String getNome() {
            return no.getNome();
        }

        public TipoDado getTipo() {
            return no.getTipoDado();
        }

        public NoDeclaracaoVariavel getNo() {
            return no;
        }
    }

    private class RenderizadorDosItensDaLista implements ListCellRenderer<ItemDaLista> {

        @Override
        public Component getListCellRendererComponent(JList<? extends ItemDaLista> list, ItemDaLista item, int index, boolean selected, boolean hasFocus) {
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
//                if (currentPortugolTreeNode.isDeclarado()) {
//                    sb.append("<b>");
//                }

            sb.append(item.getNome());
//                if (currentPortugolTreeNode.isDeclarado()) {
//                    sb.append("</b>");
//                }

            //tipo
//                sb.append(" : ");
//                sb.append("<font color='#888888'>");
//                sb.append(noDeclaracaoVariavel.getTipoDado().getNome());
//                sb.append("<font color='#000000'>");
            //-----------
            if (item.getValor() != null) {
                Object valor = item.getValor();
                if (valor instanceof Boolean) {
                    valor = ((Boolean) valor) ? "verdadeiro" : "falso";
                }
                sb.append(" = ").append(valor);
            }
            final String valor = sb.toString();
            component.setText(valor);
//                if (currentPortugolTreeNode.isModificado()) {
//                    component.setForeground(Color.BLUE);
//                }
            Icon icone = getIcon(item.getTipo());
            component.setIcon(icone);
            component.setDisabledIcon(icone);
            return component;
        }

        private JLabel component = new JLabel("teste");

        private Icon getIcon(TipoDado tipoDado) {
            String iconName = "unknown.png";
            if (tipoDado != null) {
                iconName = tipoDado.getNome() + ".png";
            }
            return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, iconName);
        }

    }

    private boolean mesmoNo(NoDeclaracaoVariavel no1, NoDeclaracaoVariavel no2) {
        return COMPARADOR_NOS.compare(no1, no2) > 0;
    }

    @Override
    public void simbolosAlterados(List<Simbolo> simbolos) {
        boolean itemsAlterados = false;
        for (Simbolo simbolo : simbolos) {
            if (simbolo != null && simbolo.getOrigemDoSimbolo() instanceof NoDeclaracaoVariavel) {
                NoDeclaracaoVariavel noDeclaracao = (NoDeclaracaoVariavel) simbolo.getOrigemDoSimbolo();
                for (int i = 0; i < model.getSize(); i++) {
                    ItemDaLista itemDaLista = model.getElementAt(i);
                    if (mesmoNo(itemDaLista.getNo(), noDeclaracao) && simbolo instanceof Variavel) {
                        itemDaLista.setValor(((Variavel) simbolo).getValor());
                        itemsAlterados = true;
                        break;
                    }
                }
            }
        }
        if (itemsAlterados) {
            repaint();
        }
    }

    @Override
    public void execucaoIniciada(Programa programa) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void highlightLinha(int linha) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void highlightDetalhadoAtual(int linha, int coluna, int tamanho) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void simboloDeclarado(Simbolo simbolo) {
        List<Simbolo> lista = new ArrayList<Simbolo>();
        lista.add(simbolo);
        simbolosAlterados(lista);
    }

    @Override
    public void simboloRemovido(Simbolo simbolo) {

    }

    private class TratadorDeArrastamento extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            support.setShowDropLocation(true);
            return support.isDrop();
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            NoDeclaracaoVariavel noTransferido = null;
            try {
                noTransferido = (NoDeclaracaoVariavel) support.getTransferable().getTransferData(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);
            } catch (Exception e) {
                return false;
            }

            //JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            //DefaultListModel listModel = (DefaultListModel) listaDeNosInspecionados.getModel();
            model.addElement(new ItemDaLista(noTransferido));
            return true;
        }
    }

}

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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
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

    private static final String INSTRUCAO = "Arraste uma variável para inspecioná-la";
    private DefaultListModel<ItemDaLista> model = new DefaultListModel<>();
    private static final ComparadorNos COMPARADOR_NOS = new ComparadorNos();

    public ListaDeNosInspecionados() {
        model.clear();
        setModel(model);
        setDropMode(DropMode.ON);
        setTransferHandler(new TratadorDeArrastamento());
        setCellRenderer(new RenderizadorDosItensDaLista());
        addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent fe) {
                clearSelection();
            }

        });
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_DELETE && getSelectedIndex() >= 0) {
                    model.remove(getSelectedIndex());
                }
            }

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

    public static class ItemDaLista {

        private static ItemDaLista ultimoItemModificado = null;

        private NoDeclaracaoVariavel no;
        private Object valor;

        public ItemDaLista(NoDeclaracaoVariavel no) {
            this.no = no;
        }

        void setValor(Object valor) {
            this.valor = valor;
            ultimoItemModificado = this;
        }

        public boolean ehUltimoItemAtualizado() {
            return this == ultimoItemModificado;
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

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class RenderizadorDosItensDaLista extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<? extends Object> list, Object object, int index, boolean selected, boolean hasFocus) {
            ItemDaLista item = (ItemDaLista) object;
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            if (item.ehUltimoItemAtualizado()) {
                sb.append("<b>");
            }
            sb.append(item.getNome());
            if (item.ehUltimoItemAtualizado()) {
                sb.append("</b>");
            }

            //tipo
            sb.append(" : ");
            sb.append("<font color='#888888'>");
            sb.append(item.getTipo().getNome());
            sb.append("<font color='#000000'>");
            //-----------
            if (item.getValor() != null) {
                Object valor = item.getValor();
                if (valor instanceof Boolean) {
                    valor = ((Boolean) valor) ? "verdadeiro" : "falso";
                }
                sb.append(" = ").append(valor);
            }
            final String valor = sb.toString();
            JLabel component = (JLabel) super.getListCellRendererComponent(list, object, index, selected, hasFocus);
            component.setText(valor);
            Icon icone = getIcon(item.getTipo());
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

    }

    private boolean mesmoNo(NoDeclaracaoVariavel no1, NoDeclaracaoVariavel no2) {
        return COMPARADOR_NOS.compare(no1, no2) > 0;
    }

    private boolean simboloEhPermitido(Simbolo simbolo) {
        return simbolo != null && simbolo instanceof Variavel && simbolo.getOrigemDoSimbolo() instanceof NoDeclaracaoVariavel;
    }

    @Override
    public void simbolosAlterados(List<Simbolo> simbolos) {
        boolean itemsAlterados = false;
        for (Simbolo simbolo : simbolos) {
            if (simboloEhPermitido(simbolo)) {
                NoDeclaracaoVariavel noDeclaracao = (NoDeclaracaoVariavel) simbolo.getOrigemDoSimbolo();
                ItemDaLista itemDaLista = getItemDoNo(noDeclaracao);
                if (itemDaLista != null) {
                    itemDaLista.setValor(((Variavel) simbolo).getValor());
                    itemsAlterados = true;
                }
            }
        }
        if (itemsAlterados) {
            repaint();
        }
    }

    private ItemDaLista getItemDoNo(NoDeclaracaoVariavel no) {
        for (int i = 0; i < model.getSize(); i++) {
            ItemDaLista item = model.getElementAt(i);
            if (mesmoNo(item.getNo(), no)) {
                return item;
            }
        }
        return null;
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
        if (simbolo instanceof Variavel) {
            List<Simbolo> lista = new ArrayList<Simbolo>();
            lista.add(simbolo);
            simbolosAlterados(lista);
        }
    }

    @Override
    public void simboloRemovido(Simbolo simbolo) {

    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class TratadorDeArrastamento extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            NoDeclaracaoVariavel noTransferido = null;
            try {
                noTransferido = (NoDeclaracaoVariavel) support.getTransferable().getTransferData(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);
            } catch (Exception e) {
                return false;
            }
            support.setShowDropLocation(true);
            return support.isDrop() && !contemNo(noTransferido);
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
            if (!contemNo(noTransferido)) {
                model.addElement(new ItemDaLista(noTransferido));
                return true;
            }
            return false;
        }

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        private boolean contemNo(NoDeclaracaoVariavel no) {
            if (no == null) {
                return false;
            }
            return getItemDoNo(no) != null;
        }
    }

}

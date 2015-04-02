package br.univali.ps.ui.rstautil.lista;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.rstautil.ComparadorNos;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author elieser
 */
public class ListaDeNosInspecionados extends JList<ListaDeNosInspecionados.ItemDaLista> implements ObservadorExecucao {

    private static final String INSTRUCAO = "Arraste uma das variáveis acima \n para este painél se quiser inspecioná-la";
    private DefaultListModel<ItemDaLista> model = new DefaultListModel<>();
    private static final ComparadorNos COMPARADOR_NOS = new ComparadorNos();

    boolean programaExecutando = false;

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
                if (ke.getKeyCode() == KeyEvent.VK_DELETE) {
                    int indices[] = getSelectedIndices();
                    int modelSize = model.getSize();
                    for (int i = indices.length - 1; i >= 0; i--) {
                        int indice = indices[i];
                        if (indice >= 0 && indice < modelSize) {
                            model.remove(indice);
                        }
                    }
                }
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getModel().getSize() <= 0) {
            g.setColor(Color.GRAY);
            FontMetrics metrics = g.getFontMetrics();
            String texto = INSTRUCAO.replace("\n", "");
            int larguraInstrucao = metrics.stringWidth(texto);
            if (larguraInstrucao <= getWidth()) {
                int x = getWidth() / 2 - larguraInstrucao / 2;
                g.drawString(texto, x, getHeight() / 2);
            } else {//separa o texto em duas linhas
                String[] linhas = INSTRUCAO.split("\n");
                int y = getHeight() / 2;
                for (int i = 0; i < linhas.length; i++) {
                    String string = linhas[i].trim();
                    int x = getWidth() / 2 - metrics.stringWidth(string) / 2;
                    g.drawString(string, x, y);
                    y += metrics.getHeight();
                }
            }
        }
    }

    public static class ItemDaLista {

        private static ItemDaLista ultimoItemModificado = null;

        private final NoDeclaracao noDeclaracao;
        private Object valor;

        public ItemDaLista(NoDeclaracao no) {
            this.noDeclaracao = no;
        }

        boolean ehVetor() {
            return noDeclaracao instanceof NoDeclaracaoVetor;
        }

        boolean ehMatriz() {
            return noDeclaracao instanceof NoDeclaracaoMatriz;
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
            return noDeclaracao.getNome();
        }

        public TipoDado getTipo() {
            return noDeclaracao.getTipoDado();
        }

        public NoDeclaracao getNoDeclaracao() {
            return noDeclaracao;
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
            if (item.ehVetor()) {
                sb.append("[]");
            } else if (item.ehMatriz()) {
                sb.append("[][]");
            }
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
            component.setFont(getFont());
            Icon icone = getIcon(item);
            component.setIcon(icone);
            component.setDisabledIcon(icone);
            return component;
        }

        private Icon getIcon(ItemDaLista item) {
            if (item.ehVetor()) {
                return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "vetor.gif");
            } else if (item.ehMatriz()) {
                return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "matriz.gif");
            }
            String iconName = "unknown.png";
            TipoDado tipoDado = item.getTipo();
            if (tipoDado != null) {
                iconName = tipoDado.getNome() + ".png";
            }
            return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, iconName);
        }

    }

    private boolean mesmoNo(NoDeclaracao no1, NoDeclaracao no2) {
        return COMPARADOR_NOS.compare(no1, no2) > 0;
    }

    private boolean simboloEhPermitido(Simbolo simbolo) {
        return simbolo != null && simbolo instanceof Variavel && simbolo.getOrigemDoSimbolo() instanceof NoDeclaracaoVariavel;
    }

    @Override
    public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao) {
        programaExecutando = false;
        //limpa os valores no fim da execução
        for (int i = 0; i < model.getSize(); i++) {
            model.getElementAt(i).setValor(null);
        }
        ItemDaLista.ultimoItemModificado = null;
        repaint();
    }

    @Override
    public void simboloRemovido(final Simbolo simbolo) {
        if (!programaExecutando) {//não remove os símbolos durante a execução do programa
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (simboloEhPermitido(simbolo)) {
                        ItemDaLista item = getItemDoNo((NoDeclaracaoVariavel) simbolo.getOrigemDoSimbolo());
                        if (item != null) {
                            model.removeElement(item);
                        }
                    }
                }
            });
        }
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

    private boolean contemNo(NoDeclaracao no) {
        if (no == null) {
            return false;
        }
        return getItemDoNo(no) != null;
    }

    private ItemDaLista getItemDoNo(NoDeclaracao no) {
        for (int i = 0; i < model.getSize(); i++) {
            ItemDaLista item = model.getElementAt(i);
            if (mesmoNo(item.getNoDeclaracao(), no)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void execucaoIniciada(Programa programa) {
        programaExecutando = true;
    }

    @Override
    public void highlightLinha(int linha) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void highlightDetalhadoAtual(int linha, int coluna, int tamanho) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class TratadorDeArrastamento extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            List<NoDeclaracao> nosTransferidos = null;
            try {
                nosTransferidos = (List<NoDeclaracao>) support.getTransferable().getTransferData(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);
            } catch (Exception e) {
                return false;
            }
            support.setShowDropLocation(true);
            if (!support.isDrop()) {
                return false;
            }

            for (NoDeclaracao no : nosTransferidos) {
                if (!contemNo(no)) {
                    return true;//basta que um dos nós transferidos ainda não esteja no inspetor e deve ser possível adicionar este nó na lista
                }
            }
            return false;
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            List<NoDeclaracao> nosTransferidos = null;
            try {
                nosTransferidos = (List<NoDeclaracao>) support.getTransferable().getTransferData(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);
            } catch (Exception e) {
                return false;
            }
            boolean importou = false;
            for (NoDeclaracao noTransferido : nosTransferidos) {
                if (!contemNo(noTransferido)) {
                    model.addElement(new ItemDaLista(noTransferido));
                    importou = true;
                }
            }
            return importou;
        }

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }

    //sempre que o código fonte é alterado este listener é disparado. Toda a árvore sintática
    //é recriada e é necessário verificar se os símbolos que estão no inspetor ainda existem
    //no código. Também é possível que os símbolos tenham sido renomeados, ou que o tipo deles
    //tenha mudado.
    public void observar(RSyntaxTextArea textArea) {
        PortugolParser.getParser(textArea).addPropertyChangeListener(PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (model.isEmpty()) {
                    return;//não existem símbolos sendo inspecionados
                }
                String name = pce.getPropertyName();
                if (RSyntaxTextArea.SYNTAX_STYLE_PROPERTY.equals(name) || PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO.equals(name)) {
                    Programa programa = (Programa) pce.getNewValue();
                    final ArvoreSintaticaAbstrataPrograma ast = programa.getArvoreSintaticaAbstrata();
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            //model.clear();

                            try {
                                final List<NoDeclaracao> nosInspecionados = new ArrayList<>();
                                ast.aceitar(new VisitanteNulo() {

                                    @Override
                                    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
                                        if (contemNo(noDeclaracaoVariavel)) {
                                            nosInspecionados.add(noDeclaracaoVariavel);
                                        }

                                        return null;
                                    }

                                    @Override
                                    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
                                        if (contemNo(noDeclaracaoMatriz)) {
                                            nosInspecionados.add(noDeclaracaoMatriz);
                                        }
                                        return null;
                                    }

                                    @Override
                                    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
                                        if (contemNo(noDeclaracaoVetor)) {
                                            nosInspecionados.add(noDeclaracaoVetor);
                                        }
                                        return null;
                                    }

                                });
                                model.clear();
                                for (NoDeclaracao no : nosInspecionados) {
                                    model.addElement(new ItemDaLista(no));
                                }
                            } catch (Exception e) {
                                //Logger.getLogger(ListaDeNosInspecionados.class.getName()).log(Level.WARNING, null, e);
                            }

                        }
                    });

                }
            }
        });
    }

}

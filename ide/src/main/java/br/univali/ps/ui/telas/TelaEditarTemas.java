/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.swing.ColorController;
import static br.univali.ps.ui.swing.ColorController.ARQUIVO_TEMA;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.swing.weblaf.jOptionPane.QuestionDialog;
import br.univali.ps.ui.utils.IconFactory;
import com.alee.laf.button.WebButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import org.json.JSONObject;

/**
 *
 * @author Adson Esteves
 */
public class TelaEditarTemas extends javax.swing.JPanel implements Themeable{

    /**
     * Creates new form TelaEditarTemas
     */
    
    private TelaCustomBorder dialog;
    private MeuModel model;
    private List<PainelCor> coresIDE;
    private List<PainelCor> coresEditor;
    private JRadioButton op1;
    private JRadioButton op2;
    
    private int editavelcount = 1;
    
    
    public TelaEditarTemas(TelaCustomBorder dialog) {
        initComponents();
        this.dialog = dialog;
        coresEditor = new ArrayList<>();
        coresIDE = new ArrayList<>();
        configurarBotoes();
        configurarCores();
        carregarTemas();
    }
    
    private void configurarBotoes()
    {
        botaoAplicarTema.addActionListener(new AbstractAction("Aplicar Tema") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject tema = ColorController.getTemas().getJSONObject(listaTemas.getSelectedValue());
                if(tema==null){return;}
                for (PainelCor painelCor : coresIDE) {
                    tema.put(painelCor.getNome(), ColorToHex(painelCor.getSelectedColor()));
                }
                
                JSONObject temaEditor = tema.getJSONObject("Editor");                
                for (PainelCor painelCor : coresEditor) {
                    temaEditor.put(painelCor.getNome(), ColorToHex(painelCor.getSelectedColor()));
                }
                Configuracoes.getInstancia().TrocarTema(listaTemas.getSelectedValue());
            }
        });
        
        botaoNovoTema.addActionListener(new AbstractAction("Novo Tema") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeTema = QuestionDialog.getInstance().showInputMessage("Escreva o nome do novo tema:");
                if(nomeTema == null){return;}
                criarNovoTema(nomeTema);
                resetarTemasExpansiveis();
            }
        });
        
        botaoRemoverTema.addActionListener(new AbstractAction("Remover Tema") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temaSelecionado = listaTemas.getSelectedValue();
                int newIndex;
                if(listaTemas.getSelectedIndex()-1<0)
                {
                    newIndex = 1;
                    listaTemas.setSelectedIndex(1);                    
                }
                else{
                    newIndex = listaTemas.getSelectedIndex()-1;
                    listaTemas.setSelectedIndex(listaTemas.getSelectedIndex()-1);
                }
                String tema = model.getElementAt(newIndex);
                if(tema.equals(ColorController.ARQUIVO_TEMA.getString("tema_selecionado")) || tema.equals("Dark") || tema.equals("Portugol"))
                {
                    botaoRemoverTema.setVisible(false);
                }
                model.removeElement(temaSelecionado);
                carregarCoresTema(listaTemas.getSelectedValue());
                JSONObject temas = ColorController.getTemas();
                temas.remove(temaSelecionado);
                Configuracoes.getInstancia().salvarTemas(); 
                resetarTemasExpansiveis();
            }
        });
        
        botaoRenomear.addActionListener(new AbstractAction("Renomear") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeTema = QuestionDialog.getInstance().showInputMessage("Escreva o novo nome do tema:");
                if(nomeTema == null){return;}
                int index = listaTemas.getSelectedIndex();
                String oldName = listaTemas.getSelectedValue();
                model.setElementAt(nomeTema, index);
                renameTheme(oldName, nomeTema);
            }
        });
        
        botaoCancelar.addActionListener(new AbstractAction("Cancelar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
    }
    
    public void resetarTemasExpansiveis()
    {
        Aba abaselecionada = PortugolStudio.getInstancia().getTelaPrincipal().getPainelTabulado().getAbaSelecionada();
        if(abaselecionada instanceof AbaCodigoFonte)
        {            
            ((AbaCodigoFonte)abaselecionada).getBarraBotoesEditor().resetaTemas();
            ((AbaCodigoFonte)abaselecionada).criaMenuTemas();
        }
    }
    
    public void renameTheme(String oldName, String newName)
    {
        String tema_selecionado = ARQUIVO_TEMA.getString("tema_selecionado");
        
        if(oldName.equals(tema_selecionado))
        {
            ARQUIVO_TEMA.put("tema_selecionado", newName);
        }
        JSONObject obj = ColorController.getTemas();        
        obj.put(newName, obj.get(oldName));
        obj.remove(oldName);
        Configuracoes.getInstancia().salvarTemas();
    }
    
    public void criarNovoTema(String nomeTema)
    {
        JSONObject temas = ColorController.getTemas();
        if(nomeTema.contains("PortugolEditado"))
        {
            temas.put(nomeTema, ColorController.getNovoTemaBasicoPortugol());
        }else{
            temas.put(nomeTema, ColorController.getNovoTemaBasicoDark());
        }
        model.addElement(nomeTema);
        listaTemas.setSelectedValue(nomeTema, true);
        this.botaoRemoverTema.setVisible(true);
        carregarCoresTema(nomeTema);
        Configuracoes.getInstancia().salvarTemas();
    }
    
    public void alterarCorPainel(String name, Color cor)
    {
        for (PainelCor painelCor : coresIDE) {
            if(painelCor.getNome().equals(name))
            {
                painelCor.setSelectedColor(cor);
                WeblafUtils.configurarBotao(painelCor.getBotaoCor(), cor, ColorController.COR_LETRA_TITULO, cor.brighter(), ColorController.COR_LETRA_TITULO, 1, true);
                return;
            }
        }
        for (PainelCor painelCor : coresEditor) {
            if(painelCor.getNome().equals(name))
            {
                painelCor.setSelectedColor(cor);
                WeblafUtils.configurarBotao(painelCor.getBotaoCor(), cor, ColorController.COR_LETRA_TITULO, cor.brighter(), ColorController.COR_LETRA_TITULO, 1, true);
                return;
            }
        }
    }
    
    public static String ColorToHex(Color color)
    {
        String rgb = Integer.toHexString(color.getRGB());
        return rgb.substring(2, rgb.length());
    }
    
    private void carregarTemas()
    {
        String[] temas = ColorController.listarTemas();
        model = new MeuModel();
        
        for (String tema : temas) 
        {
            model.addElement(tema);
        }
        listaTemas.setCellRenderer(new Render());
        listaTemas.setModel(model);
        listaTemas.setSelectedValue(Configuracoes.getInstancia().getTemaPortugol(), true);
        this.botaoRemoverTema.setVisible(false);
        carregarCoresTema(Configuracoes.getInstancia().getTemaPortugol());
    }
    
    private PainelCor criarPainelCor(String name, String tema, JSONObject json)
    {
        JLabel nomeVariavel = new JLabel("<html><body width='100px'><div>"+name.replace("_", " ")+"</div></body></html>");                
        nomeVariavel.setForeground(ColorController.COR_LETRA);
        WebButton botaoColorPicker = new WebButton(" ");
        Color cor = new Color(Integer.parseInt(json.getString(name), 16));
        WeblafUtils.configurarBotao(botaoColorPicker, cor, ColorController.COR_LETRA_TITULO, cor.brighter(), ColorController.COR_LETRA_TITULO, 1, true);

        PainelCor estilo = new PainelCor(cor, name);
        estilo.setSelectedColor(cor);
        estilo.setNome(name);
        estilo.setOpaque(false);
        estilo.add(botaoColorPicker);
        estilo.add(nomeVariavel);
        estilo.setBotaoCor(botaoColorPicker);
        
        nomeVariavel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                botaoColorPicker.doClick();
            }
        });
        acaoBotaoPickColor(botaoColorPicker, tema, estilo);
        
        return estilo;
    }
    
    private JPanel criarRadioPanel(String name, JSONObject json, String tema)
    {
        JPanel painel = new JPanel();
        JLabel label = new JLabel(name+": ");
        JPanel painelRadio = new JPanel();
        JRadioButton option = new JRadioButton("Claros");
        JRadioButton option2 = new JRadioButton("Escuros");
        
        option.setSelectedIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "selected_rounded.png"));
        option.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "unselected_rounded.png"));
        option2.setSelectedIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "selected_rounded.png"));
        option2.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "unselected_rounded.png"));
        
        label.setForeground(ColorController.COR_LETRA);
        option.setForeground(ColorController.COR_LETRA);
        option2.setForeground(ColorController.COR_LETRA);
        
        painel.setLayout(new BorderLayout());
        painelRadio.setLayout(new BoxLayout(painelRadio, BoxLayout.Y_AXIS));
        
        this.op1 = option;
        this.op2 = option2;
        
        if(json.getString(name).equals("Dark")){
            option.doClick();
        }
        else{
            option2.doClick();
        }
        
        option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tema.equals("Dark") && !tema.equals("Portugol"))
                {                    
                    json.put(name, "Dark");
                    option2.setSelected(!option.isSelected());
                    Configuracoes.getInstancia().salvarTemas();  
                }
                else{
                    String newName =tema+"Editado"+editavelcount;
                    editavelcount++;
                    while(model.contains(newName))
                    {
                        newName = tema+"Editado"+editavelcount;
                        editavelcount++;
                    }
                    criarNovoTema(newName);
                    op1.doClick();
                }
            }
        });        
        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tema.equals("Dark") && !tema.equals("Portugol"))
                { 
                    json.put(name, "Portugol");
                    option.setSelected(!option2.isSelected());
                    Configuracoes.getInstancia().salvarTemas();  
                }
                else{
                    String newName =tema+"Editado"+editavelcount;
                    editavelcount++;
                    while(model.contains(newName))
                    {
                        newName = tema+"Editado"+editavelcount;
                        editavelcount++;
                    }
                    criarNovoTema(newName);
                    op2.doClick();
                }
            }
        });       
        
        painel.setOpaque(false);
        painelRadio.setOpaque(false);
        
        painelRadio.add(option);
        painelRadio.add(option2);
        painelRadio.setBorder(new EmptyBorder(0, 10, 0, 0));
        painel.add(label, BorderLayout.WEST);
        painel.add(painelRadio, BorderLayout.CENTER);
        
        return painel;
    }
    
    private void carregarCoresTema(String tema)
    {
        this.painelVariaveisPSInterior.removeAll();
        this.painelVariaveisEditorInterior.removeAll();
        this.coresIDE.clear();
        this.coresEditor.clear();
        this.botaoRenomear.setVisible(true);
        if(tema.equals("Portugol") || tema.equals("Dark"))
        {
            this.botaoRenomear.setVisible(false);
        }
        JSONObject temas = ColorController.getTemas();
        JSONObject coresTema = temas.getJSONObject(tema);
        for (String name : JSONObject.getNames(coresTema)) 
        {
            if(!name.equals("Editor") && !name.equals("icones"))
            {
                PainelCor pc = criarPainelCor(name, tema, coresTema);
                this.coresIDE.add(pc);
                this.painelVariaveisPSInterior.add(pc);
            }
        }
        this.painelVariaveisPSInterior.add(criarRadioPanel("icones", coresTema, tema));
        JSONObject coresEditor = coresTema.getJSONObject("Editor");
        
        for (String name : JSONObject.getNames(coresEditor)) 
        {
            PainelCor pc = criarPainelCor(name, tema, coresEditor);
            this.coresEditor.add(pc);
            this.painelVariaveisEditorInterior.add(pc);
        }
        
        this.painelVariaveisPSInterior.revalidate();
        this.painelVariaveisPSInterior.repaint();
        this.painelVariaveisEditorInterior.revalidate();
        this.painelVariaveisEditorInterior.repaint();
    }
    
    public void alterarCorJson(String tema, PainelCor pc)
    {
        JSONObject jsonTema;
        if(coresIDE.contains(pc))
        {
            jsonTema = ColorController.getTemas().getJSONObject(tema);
        }
        else if(coresEditor.contains(pc))
        {
            jsonTema = ColorController.getTemas().getJSONObject(tema).getJSONObject("Editor");
        }
        else{
            return;
        }                
        jsonTema.put(pc.getNome(), ColorToHex(pc.getSelectedColor()));
        Configuracoes.getInstancia().salvarTemas();        
    }
    
    public void acaoBotaoPickColor(WebButton botao, String tema, PainelCor pc)
    {
        botao.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color cor = JColorChooser.showDialog(null, "Selecione a cor", pc.getSelectedColor());
                if(cor!=null)
                {
                    if(!tema.equals("Dark") && !tema.equals("Portugol"))
                    {
                        WeblafUtils.configurarBotao(botao, cor, ColorController.COR_LETRA_TITULO, cor.brighter(), ColorController.COR_LETRA_TITULO, 1, true);
                        pc.setSelectedColor(cor);
                        botao.revalidate();
                        botao.repaint();
                    }
                    else
                    {
                        String newName =tema+"Editado"+editavelcount;
                        editavelcount++;
                        while(model.contains(newName))
                        {
                            newName = tema+"Editado"+editavelcount;
                            editavelcount++;
                        }
                        criarNovoTema(newName);
                        alterarCorPainel(pc.getNome(),cor);
                        botao.revalidate();
                        botao.repaint();
                    }
                    alterarCorJson(tema, pc);
                }
            }
        });
    }

    @Override
    public void configurarCores() {
        setBackground(ColorController.FUNDO_MEDIO);
        setForeground(ColorController.COR_LETRA);
        labelEditor.setForeground(ColorController.COR_LETRA);
        labelPS.setForeground(ColorController.COR_LETRA);
        listaTemas.setBackground(ColorController.FUNDO_ESCURO);
        listaTemas.setSelectionBackground(ColorController.FUNDO_CLARO);
        
        if(WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configuraWebLaf(variavelScrollPane);
            WeblafUtils.configurarBotao(botaoNovoTema, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);
            WeblafUtils.configurarBotao(botaoAplicarTema, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);
            WeblafUtils.configurarBotao(botaoCancelar, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);
            WeblafUtils.configurarBotao(botaoRemoverTema, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);
            WeblafUtils.configurarBotao(botaoRenomear, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);
        }
    }
    
    public class Render extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            final JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            renderer.setForeground(ColorController.COR_LETRA_TITULO);
            renderer.setBackground(ColorController.FUNDO_ESCURO);
            if(isSelected)
            {
              renderer.setBackground(ColorController.AMARELO);
              renderer.setForeground(ColorController.FUNDO_ESCURO);
            }
            
            renderer.setBorder(new EmptyBorder(5, 10, 5, 0));
            return renderer;
        }
        
    }
    
    private class MeuModel extends DefaultListModel<String> {
        
        List<String> temas = new ArrayList<>();

        @Override
        public int getSize() {
            return temas.size();
        }

        @Override
        public String getElementAt(int index) {
            return temas.get(index);
        }

        @Override
        public void add(int index, String element) {
            temas.add(index, element);
            fireContentsChanged(this, 0, temas.size() - 1);
        }

        @Override
        public void addElement(String element) {
            temas.add(element);
            fireContentsChanged(this, 0, temas.size() - 1);
        }
        
        @Override
        public void clear() {
            temas.clear();
            fireContentsChanged(this, 0, temas.size() - 1);
        }

        @Override
        public boolean removeElement(Object dado) {
            temas.remove((String) dado);
            fireContentsChanged(this, 0, temas.size() - 1);
            
            return true;
        }

        @Override
        public void setElementAt(String element, int index) {
            temas.remove(index);
            temas.add(index, element);
            fireContentsChanged(this, 0, temas.size() - 1);
        }

        public boolean contains(String elem) {
            return temas.contains(elem);
        }
    }
    
    public class PainelCor extends JPanel
    {
        private Color selectedColor;
        private String nome;
        private WebButton botaoCor;

        public PainelCor(Color selectedColor, String name) {
            this.selectedColor = selectedColor;
            this.nome = name;
        }

        public Color getSelectedColor() {
            return selectedColor;
        }

        public void setSelectedColor(Color selectedColor) {
            this.selectedColor = selectedColor;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String name) {
            this.nome = name;
        }

        public WebButton getBotaoCor() {
            return botaoCor;
        }

        public void setBotaoCor(WebButton botaoCor) {
            this.botaoCor = botaoCor;
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

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaTemas = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        variavelScrollPane = new javax.swing.JScrollPane();
        painelVariaveis = new javax.swing.JPanel();
        painelVariaveisIDE = new javax.swing.JPanel();
        labelPS = new javax.swing.JLabel();
        painelVariaveisPSInterior = new javax.swing.JPanel();
        painelVariaveisEditor = new javax.swing.JPanel();
        labelEditor = new javax.swing.JLabel();
        painelVariaveisEditorInterior = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        botaoNovoTema = new com.alee.laf.button.WebButton();
        botaoRenomear = new com.alee.laf.button.WebButton();
        botaoRemoverTema = new com.alee.laf.button.WebButton();
        botaoAplicarTema = new com.alee.laf.button.WebButton();
        botaoCancelar = new com.alee.laf.button.WebButton();

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("jLabel1");
        jPanel3.add(jLabel1, java.awt.BorderLayout.WEST);

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.Y_AXIS));

        jRadioButton1.setText("jRadioButton1");
        jPanel6.add(jRadioButton1);

        jRadioButton2.setText("jRadioButton2");
        jPanel6.add(jRadioButton2);

        jPanel3.add(jPanel6, java.awt.BorderLayout.EAST);

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new java.awt.BorderLayout());

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        listaTemas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaTemas.setPreferredSize(new java.awt.Dimension(100, 80));
        listaTemas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaTemasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaTemas);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        variavelScrollPane.setBorder(null);
        variavelScrollPane.setMaximumSize(new java.awt.Dimension(760, 580));
        variavelScrollPane.setOpaque(false);

        painelVariaveis.setMaximumSize(new java.awt.Dimension(760, 580));
        painelVariaveis.setOpaque(false);
        painelVariaveis.setLayout(new java.awt.BorderLayout());

        painelVariaveisIDE.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 20, 5));
        painelVariaveisIDE.setOpaque(false);
        painelVariaveisIDE.setLayout(new java.awt.BorderLayout());

        labelPS.setText("Portugol Studio");
        labelPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelVariaveisIDE.add(labelPS, java.awt.BorderLayout.PAGE_START);

        painelVariaveisPSInterior.setOpaque(false);
        painelVariaveisPSInterior.setLayout(new java.awt.GridLayout(5, 3));
        painelVariaveisIDE.add(painelVariaveisPSInterior, java.awt.BorderLayout.CENTER);

        painelVariaveis.add(painelVariaveisIDE, java.awt.BorderLayout.NORTH);

        painelVariaveisEditor.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelVariaveisEditor.setOpaque(false);
        painelVariaveisEditor.setLayout(new java.awt.BorderLayout());

        labelEditor.setText("Editor");
        labelEditor.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelVariaveisEditor.add(labelEditor, java.awt.BorderLayout.PAGE_START);

        painelVariaveisEditorInterior.setOpaque(false);
        painelVariaveisEditorInterior.setLayout(new java.awt.GridLayout(10, 3));
        painelVariaveisEditor.add(painelVariaveisEditorInterior, java.awt.BorderLayout.CENTER);

        painelVariaveis.add(painelVariaveisEditor, java.awt.BorderLayout.CENTER);

        variavelScrollPane.setViewportView(painelVariaveis);

        jPanel1.add(variavelScrollPane, java.awt.BorderLayout.CENTER);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        botaoNovoTema.setText("adicionar tema");
        botaoNovoTema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNovoTemaActionPerformed(evt);
            }
        });
        jPanel4.add(botaoNovoTema);

        botaoRenomear.setText("renomear");
        jPanel4.add(botaoRenomear);

        botaoRemoverTema.setText("remover tema");
        jPanel4.add(botaoRemoverTema);

        botaoAplicarTema.setText("aplicar tema");
        jPanel4.add(botaoAplicarTema);

        botaoCancelar.setText("cancelar");
        jPanel4.add(botaoCancelar);

        jPanel1.add(jPanel4, java.awt.BorderLayout.SOUTH);

        jPanel5.add(jPanel1, java.awt.BorderLayout.CENTER);

        add(jPanel5, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoNovoTemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoTemaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoNovoTemaActionPerformed

    private void listaTemasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaTemasMouseClicked
        final int index = ((JList) evt.getSource()).getSelectedIndex();
        String tema = model.getElementAt(index);
        carregarCoresTema(tema);
        this.botaoRemoverTema.setVisible(true);
        if(tema.equals(ColorController.ARQUIVO_TEMA.getString("tema_selecionado")) || tema.equals("Dark") || tema.equals("Portugol"))
        {
            this.botaoRemoverTema.setVisible(false);
        }
    }//GEN-LAST:event_listaTemasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoAplicarTema;
    private com.alee.laf.button.WebButton botaoCancelar;
    private com.alee.laf.button.WebButton botaoNovoTema;
    private com.alee.laf.button.WebButton botaoRemoverTema;
    private com.alee.laf.button.WebButton botaoRenomear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelEditor;
    private javax.swing.JLabel labelPS;
    private javax.swing.JList<String> listaTemas;
    private javax.swing.JPanel painelVariaveis;
    private javax.swing.JPanel painelVariaveisEditor;
    private javax.swing.JPanel painelVariaveisEditorInterior;
    private javax.swing.JPanel painelVariaveisIDE;
    private javax.swing.JPanel painelVariaveisPSInterior;
    private javax.swing.JScrollPane variavelScrollPane;
    // End of variables declaration//GEN-END:variables
}

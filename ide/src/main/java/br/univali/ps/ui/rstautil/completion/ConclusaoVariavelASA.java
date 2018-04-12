/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.ps.ui.utils.IconFactory;
import javax.swing.Icon;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.VariableCompletion;

/**
 *
 * @author 5663296
 */
public final class ConclusaoVariavelASA extends VariableCompletion implements ConclusaoASA {
    
    final int nivelASA;
    final TrechoCodigoFonte trechoCodigoFonte;
    final String local;

    public ConclusaoVariavelASA(CompletionProvider provider, String nome, String tipo, boolean constante, int nivelASA, TrechoCodigoFonte trechoCodigoFonte, String local) {
        super(provider, nome, tipo);
        this.nivelASA = nivelASA;
        this.trechoCodigoFonte = trechoCodigoFonte;
        this.local = local;
        try {
            this.setIcon(getIcon(TipoDado.obterTipoDadoPeloNome(tipo), constante));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    
    @Override
    public String getDefinitionString() {
        StringBuilder sb = new StringBuilder();

        if (getType()!=null) {
            sb.append("<span class='type-").append(getType()).append("'>");
            sb.append(getType()).append("</span> ");
        }
        sb.append("<span class='name'>").append(getName()).append("</span>");

        return sb.toString();

    }
    
    @Override
    protected void possiblyAddDefinedIn(StringBuilder sb) {
        if (getDefinedIn()!=null) {
            sb.append("<hr>Escopo:"); // TODO: Localize me
            sb.append(" <em>").append(getDefinedIn()).append("</em>");
        }
    }

    @Override
    public int getNivelASA() {
        return nivelASA;
    }

    @Override
    public TrechoCodigoFonte getTrechoCodigoFonte() {
        return trechoCodigoFonte;
    }

    @Override
    public String getLocal() {
        return local;
    }
    
    
    
    private static Icon getIcon(TipoDado tipoDado, Boolean constante) {
        String iconName = "unknown.png";
        if (tipoDado != null) {
            if(constante){
                iconName = tipoDado.getNome() + "_c.png";
            }
            else{
                iconName = tipoDado.getNome() + ".png";
            }
        }
        return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, iconName);
    }
    private static Icon getMatrixIcon(TipoDado tipoDado) {
        String iconName = "unknown.png";
        if (tipoDado != null) {
            iconName ="matriz_" +tipoDado.getNome() + ".png";
        }
        return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, iconName);
    }
    private static Icon getVectorIcon(TipoDado tipoDado) {
        String iconName = "unknown.png";
        if (tipoDado != null) {
            iconName ="vetor_" +tipoDado.getNome() + ".png";
        }
        return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, iconName);
    }
}

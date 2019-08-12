/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.ps.ui.utils.IconFactory;
import javax.swing.Icon;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.VariableCompletion;

/**
 *
 * @author 5663296
 */
public final class ConclusaoConstanteBiblioteca extends VariableCompletion {
    
    final String local;

    public ConclusaoConstanteBiblioteca(CompletionProvider provider, String nome, String tipo, String local) {
        super(provider, nome, tipo);
        this.local = local;
        try {
            this.setIcon(getIcon(TipoDado.obterTipoDadoPeloNome(tipo), true));
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
    // private static Icon getMatrixIcon(TipoDado tipoDado) {
    //     String iconName = "unknown.png";
    //     if (tipoDado != null) {
    //         iconName ="matriz_" +tipoDado.getNome() + ".png";
    //     }
    //     return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, iconName);
    // }
    // private static Icon getVectorIcon(TipoDado tipoDado) {
    //     String iconName = "unknown.png";
    //     if (tipoDado != null) {
    //         iconName ="vetor_" +tipoDado.getNome() + ".png";
    //     }
    //     return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, iconName);
    // }
}

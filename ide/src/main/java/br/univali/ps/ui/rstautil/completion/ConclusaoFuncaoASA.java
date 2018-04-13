/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.ps.ui.utils.IconFactory;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;

/**
 *
 * @author 5663296
 */
public final class ConclusaoFuncaoASA extends FunctionCompletion implements ConclusaoASA {
    
    private final int nivelASA;
    private final TrechoCodigoFonte trechoCodigoFonte;
    private final String local;
    private final String returnType;

    public ConclusaoFuncaoASA(CompletionProvider provider, String name, String returnType, int nivelASA, TrechoCodigoFonte trechoCodigoFonte, String local) {
        super(provider, name, returnType);
        this.nivelASA = nivelASA;
        this.trechoCodigoFonte = trechoCodigoFonte;
        this.local = local;
        this.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "funcaoDoUsuario.png"));
        this.returnType = returnType;
    }
    
    @Override
    public String getDefinitionString() {
        StringBuilder sb = new StringBuilder();

        if (getType()!=null) {
            sb.append("<span class='type-").append(getType()).append("'>");
            sb.append(getType()).append("</span> ");
        }
        sb.append("<span class='function'>").append(getName()).append("</span>");

        return sb.toString();

    }
    @Override
    protected void addParameters(StringBuilder sb) {
        int paramCount = getParamCount();
        if (paramCount>0) {
            sb.append("<h4>Parametros:</h4><br>");
            sb.append("<center><table width='90%'>");
            for (int i=0; i<paramCount; i++) {
                sb.append("<tr><td>");
                Parameter param = getParam(i);
//                System.out.println("PATH: " + ClassLoader.getSystemClassLoader().getResource(IconFactory.CAMINHO_ICONES_PEQUENOS + "/" + getType() + ".png" ).toExternalForm());
//                sb.append("<img src='").append(ClassLoader.getSystemClassLoader().getResource(IconFactory.CAMINHO_ICONES_PEQUENOS + "/" + getType() + ".png" ).toExternalForm()).append("'>").append("</img></td><td style='width:200px'>");
//                
                String desc = param.getDescription();
                sb.append("<b class='type-"+param.getType()+"'>");
                sb.append(param.getName()!=null ? param.getName() : param.getType());
                sb.append("</b>: <span class='type-name'>"+param.getType()+"</span><br>");
                if (desc!=null) {
                    sb.append(desc);
                }
                sb.append("</td></tr>");                
            }
            
            sb.append("</table></center><br><br>");
        }

        if (getReturnValueDescription()!=null) {
            sb.append("<h4>Retorna:</h4><br><center><table width='90%'><tr><td>");
            sb.append(getReturnValueDescription());
            sb.append("</td></tr></table></center><br><br>");
        }

    }
    
    @Override
    protected void possiblyAddDefinedIn(StringBuilder sb) {
        if (getDefinedIn()!=null) {
            sb.append("<hr>Escopo:"); // TODO: Localize me
            sb.append(" <em>").append(getDefinedIn()).append("</em>");
        }
    }
    
    public String getReturnType() {
        return returnType;
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
    
}

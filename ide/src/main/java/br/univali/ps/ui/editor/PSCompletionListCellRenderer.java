/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.editor;

import br.univali.ps.ui.rstautil.completion.ConclusaoConstanteBiblioteca;
import br.univali.ps.ui.rstautil.completion.ConclusaoFuncaoASA;
import br.univali.ps.ui.rstautil.completion.ConclusaoFuncaoBiblioteca;
import br.univali.ps.ui.rstautil.completion.ConclusaoVariavelASA;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.utils.IconFactory;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;
import org.fife.ui.autocomplete.AbstractCompletion;
import org.fife.ui.autocomplete.ParameterizedCompletion;

/**
 *
 * @author 5663296
 */
public class PSCompletionListCellRenderer extends DefaultListCellRenderer{
    
    @Override
    public Component getListCellRendererComponent(JList<?> jlist, Object o, int ii, boolean bln, boolean bln1) {
        JLabel renderer = (JLabel) super.getListCellRendererComponent(jlist, o, ii, bln, bln1);
        renderer.setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
        if(bln){
            renderer.setBackground(ColorController.FUNDO_CLARO);
        }else if(bln1){
            renderer.setBackground(ColorController.AMARELO);
        }
        
        renderer.setBorder(new EmptyBorder(0, 10, 0, 0));
        renderer.setForeground(ColorController.COR_LETRA);
        
        if (o instanceof AbstractCompletion)
        {
            AbstractCompletion completion = (AbstractCompletion) o;
            
            setIcon(completion.getIcon());
        }        
        
        if (o instanceof ConclusaoVariavelASA)
        {
            ConclusaoVariavelASA conclusaoVariavel = (ConclusaoVariavelASA) o;
            
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append(conclusaoVariavel.getName());
            sb.append(" : ");
            sb.append("<font color='#888888'>");
            sb.append(conclusaoVariavel.getType());

            final String valor = sb.toString();

            setText(valor);
            
            if (conclusaoVariavel.getType().equals("Biblioteca")){
                setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "biblioteca.png"));
            }
        }
        else if (o instanceof ConclusaoConstanteBiblioteca)
        {
            ConclusaoConstanteBiblioteca conclusaoConstante = (ConclusaoConstanteBiblioteca) o;
            
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append(conclusaoConstante.getName());
            sb.append(" : ");
            sb.append("<font color='#888888'>");
            sb.append(conclusaoConstante.getType());

            final String valor = sb.toString();

            setText(valor);
        }
        else if (o instanceof ConclusaoFuncaoASA)
        {
             ConclusaoFuncaoASA conclusaoFuncao = (ConclusaoFuncaoASA) o;
            
            StringBuilder sb = new StringBuilder();
            
            sb.append("<html>");
            sb.append(conclusaoFuncao.getName());
            sb.append('(');

            int paramCount = conclusaoFuncao.getParamCount();

            for (int i = 0; i < paramCount; i++) {
                ParameterizedCompletion.Parameter param = conclusaoFuncao.getParam(i);

                sb.append(param.getType());

//                Quantificador quantificador = param.getQuantificador();
//
//                if (quantificador == Quantificador.VETOR) {
//                    sb.append("[]");
//                } else if (quantificador == Quantificador.MATRIZ) {
//                    sb.append("[][]");
//                }

                if (i < paramCount - 1) {
                    sb.append(", ");
                }
            }

            sb.append(')');

            if (conclusaoFuncao.getReturnType() != null) {
                sb.append(" : ");
                sb.append("<font color='#888888'>");
                sb.append(conclusaoFuncao.getReturnType());
//                Quantificador quantificador = declaracaoFuncao.getQuantificador();
//
//                if (quantificador == Quantificador.VETOR) {
//                    sb.append("[]");
//                } else if (quantificador == Quantificador.MATRIZ) {
//                    sb.append("[][]");
//                }
            }

            setText(sb.toString());
        }
        else if (o instanceof ConclusaoFuncaoBiblioteca)
        {
             ConclusaoFuncaoBiblioteca conclusaoFuncao = (ConclusaoFuncaoBiblioteca) o;
            
            StringBuilder sb = new StringBuilder();
            
            sb.append("<html>");
            sb.append(conclusaoFuncao.getName());
            sb.append('(');

            int paramCount = conclusaoFuncao.getParamCount();

            for (int i = 0; i < paramCount; i++) {
                ParameterizedCompletion.Parameter param = conclusaoFuncao.getParam(i);

                sb.append(param.getType());

//                Quantificador quantificador = param.getQuantificador();
//
//                if (quantificador == Quantificador.VETOR) {
//                    sb.append("[]");
//                } else if (quantificador == Quantificador.MATRIZ) {
//                    sb.append("[][]");
//                }

                if (i < paramCount - 1) {
                    sb.append(", ");
                }
            }

            sb.append(')');

            if (conclusaoFuncao.getReturnType() != null) {
                sb.append(" : ");
                sb.append("<font color='#888888'>");
                sb.append(conclusaoFuncao.getReturnType());
//                Quantificador quantificador = declaracaoFuncao.getQuantificador();
//
//                if (quantificador == Quantificador.VETOR) {
//                    sb.append("[]");
//                } else if (quantificador == Quantificador.MATRIZ) {
//                    sb.append("[][]");
//                }
            }

            setText(sb.toString());
        }
        
        return renderer;
    }
}

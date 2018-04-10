package br.univali.ps.ui.coletor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.util.Date;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleText;
import javax.swing.JButton;
import javax.swing.JLabel;

class Interacao
{

    private final String nomeComponente;
    private final String nomeClasseComponente;
    private final String hierarquia;
    private final String extra; // getText, getAcessibleText
    private final Point posicao;
    private final Date timeStamp;

    public Interacao(Component componente, Point posicao)
    {

        assert (componente != null);
        assert (posicao != null);

        this.nomeComponente = componente.getName();
        this.hierarquia = getHierarquia(componente);
        this.extra = getExtra(componente);
        this.nomeClasseComponente = componente.getClass().getName();
        this.posicao = posicao;
        this.timeStamp = new Date();
    }

    private static String getExtra(Component componente) 
    {
        assert(componente != null);
        
        boolean possuiInformacaoExtra = componente instanceof JButton || componente instanceof JLabel;
        String nomeComponente = componente.getName();
        
        if ((nomeComponente == null || nomeComponente.isEmpty()) && possuiInformacaoExtra) {
            if (componente instanceof JButton) {
                return getInformacaoExtra((JButton) componente);
            } else if (componente instanceof JLabel) {
                return getInformacaoExtra((JLabel) componente);
            }
        }
        
        return null;
    }
    
    private static String getInformacaoExtra(JLabel label)
    {
        if (!label.getText().isEmpty()) {
            return "texto: " + label.getText();
        }

        return getInformacaoExtra(label.getAccessibleContext());
    }

    private static String getInformacaoExtra(JButton botao)
    {
        if (!botao.getText().isEmpty()) {
            return "texto: " + botao.getText();
        }

        return getInformacaoExtra(botao.getAccessibleContext());
    }

    private static String getInformacaoExtra(AccessibleContext contexto)
    {
        assert (contexto != null) ;

        if (!contexto.getAccessibleName().isEmpty()) {
            return "acessibleName: " + contexto.getAccessibleName();
        }

        AccessibleText accessibleText = contexto.getAccessibleText();
        if (accessibleText != null && !accessibleText.toString().isEmpty()) {
            return "acessibleText: " + accessibleText.toString();
        }

        return null;

    }

    private static String getHierarquia(Component c)
    {
        Container pai = c.getParent();

        if (pai == null) {
            return "";
        }

        String nomePai = pai.getName();
        boolean paiPossuiNome = nomePai == null || nomePai.isEmpty();
        
        String hierarquia = String.format("%s (%s)", pai.getClass().getName(), paiPossuiNome ? nomePai : "Sem nome");

        if (!paiPossuiNome) {
            hierarquia += " => ";
            hierarquia += getHierarquia(pai);
        }

        return hierarquia;
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }

    public String getNomeClasseComponente()
    {
        return nomeClasseComponente;
    }

    public String getNomePaiComponente()
    {
        return hierarquia;
    }

    public String getNomeComponente()
    {
        return nomeComponente;
    }

    public Point getPosicao()
    {
        return posicao;
    }

    @Override
    public String toString()
    {
        String string = "{ "
                + "\"componente\": \"" + nomeComponente + "\", ";

        if (extra != null) {
            string += "\"extra\": \"" + extra + "\", ";
        }

        string += "\"hierarquia\": \"" + hierarquia + "\", "
                + "\"classeComponente\": \"" + nomeClasseComponente + "\", "
                + "\"posicao\": { \"x\": \"" + posicao.getX() + "\", \"y\": \"" + posicao.getY() + "\"}, "
                + "\"timeStamp\": \"" + timeStamp + "\""
                + '}';

        return string;
    }

}

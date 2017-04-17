/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.swing;

import br.univali.ps.nucleo.Configuracoes;
import java.awt.Color;

/**
 *
 * @author LITE
 */
public final class ColorController {
    
    private static Color[] getDefaultTheme(){
        return new Color[]{
                new Color(51,51,51),    //cinza escuro
                new Color(210,231,252), //branco azulado
                new Color(255,255,255), //branco
                new Color(49,104,146),  //azul
                new Color(228,241,254), //branco azulado
                new Color(243,243,243), //branco
                new Color(255,194,0),   //amarelo
                new Color(255,194,0),   //amarelo
                new Color(69,189,255),  //azul claro
                new Color(240,67,59),   //vermelho
                new Color(0,239,192)    //ciano
            };
    }
    private static Color[] getASHTheme(){
        return new Color[]{
                new Color(205,205,205), //cinza claro
                new Color(58,70,76),    //cinza azulado
                new Color(38,50,56),    //cinza azulado mais escuro
                new Color(18,30,36),    //cinza azulado muito mais escuro
                new Color(38,50,56),    //cinza azulado mais escuro
                new Color(68,80,86),    //cinza azulado mais claro
                new Color(241,67,60),   //vermelho
                new Color(255,194,0),   //amarelo
                new Color(69,189,255),  //azul claro
                new Color(240,67,59),   //vermelho
                new Color(0,239,192)    //ciano
            };
    }
    
    private static Color[] getTheme()
    {
        String temaSelecionado = Configuracoes.getInstancia().getTemaPortugol();
        if(temaSelecionado.equals("Portugol"))
        {
            return getDefaultTheme();
        }
        else
        {
            return getASHTheme();
        }
    }
    
    private static final Color[] THEME= getTheme();
    public static final Color COR_LETRA = THEME[0];
    public static final Color COR_DESTAQUE = THEME[1];
    public static final Color COR_PRINCIPAL = THEME[2];
    public static final Color FUNDO_ESCURO = THEME[3];
    public static final Color FUNDO_MEDIO = THEME[4];
    public static final Color FUNDO_CLARO = THEME[5];
    public static final Color PROGRESS_BAR = THEME[6];
    public static final Color AMARELO = THEME[7];
    public static final Color AZUL = THEME[8];
    public static final Color VERMELHO = THEME[9];
    public static final Color VERDE = THEME[10];
}

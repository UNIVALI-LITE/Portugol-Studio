/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui;

import java.awt.Color;

/**
 *
 * @author LITE
 */
public final class ColorController {
    
    private static Color[] getDefaultTheme(){
        return new Color[]{
                new Color(51,51,51),
                new Color(243,243,243),
                new Color(255,255,255),
                new Color(49,104,146),
                new Color(210,231,252),
                new Color(228,241,254),
                new Color(255,194,0)
            };
    }
    private static Color[] getDarkTheme(){
        return new Color[]{
                new Color(251,251,251),
                new Color(100,100,100),
                new Color(51,51,51),
                new Color(30,30,30),
                new Color(80,80,80),
                new Color(90,90,90),
                new Color(255,194,0)
            };
    }
    
    private static final Color[] THEME= getDefaultTheme();
    public static final Color COR_LETRA = THEME[0];
    public static final Color COR_DESTAQUE = THEME[1];
    public static final Color COR_PRINCIPAL = THEME[2];
    public static final Color FUNDO_ESCURO = THEME[3];
    public static final Color FUNDO_MEDIO = THEME[4];
    public static final Color FUNDO_CLARO = THEME[5];
    public static final Color PROGRESS_BAR = THEME[6];
}

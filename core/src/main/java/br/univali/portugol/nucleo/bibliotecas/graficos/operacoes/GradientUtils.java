/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.Graficos;
import br.univali.portugol.nucleo.bibliotecas.graficos.SuperficieDesenho;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

/**
 *
 * @author Alisson
 */
public class GradientUtils {
    public static void doGradient(SuperficieDesenho.InformacaoGradiente gradientInfo, OperacaoDesenho desenho){
    }
    public static void doGradient(Graphics2D graficos,SuperficieDesenho.InformacaoGradiente gradientInfo, OperacaoDesenho desenho, int altura, int largura){
        if(gradientInfo!=null){
            GradientPaint paint = new GradientPaint(desenho.x, desenho.y+altura/2, gradientInfo.cor1,desenho.x+largura, desenho.y+altura/2, gradientInfo.cor2);;
            switch (gradientInfo.tipo) {
                case Graficos.GRADIENTE_INFERIOR_DIREITO:
                    paint = new GradientPaint(desenho.x, desenho.y, gradientInfo.cor1,desenho.x+largura, desenho.y+altura, gradientInfo.cor2);
                    break;
                case Graficos.GRADIENTE_INFERIOR_ESQUERDO:
                    paint = new GradientPaint(desenho.x+largura, desenho.y, gradientInfo.cor1,desenho.x, desenho.y+altura, gradientInfo.cor2);
                    break;
                case Graficos.GRADIENTE_SUPERIOR_DIREITO:
                    paint = new GradientPaint(desenho.x, desenho.y+altura, gradientInfo.cor1,desenho.x+largura, desenho.y, gradientInfo.cor2);
                    break;
                case Graficos.GRADIENTE_SUPERIOR_ESQUERDO:
                    paint = new GradientPaint(desenho.x+largura, desenho.y+altura, gradientInfo.cor1,desenho.x, desenho.y, gradientInfo.cor2);
                    break;
                case Graficos.GRADIENTE_ABAIXO:
                    paint = new GradientPaint(desenho.x, desenho.y, gradientInfo.cor1,desenho.x, desenho.y+altura, gradientInfo.cor2);
                    break;
                case Graficos.GRADIENTE_ACIMA:
                    paint = new GradientPaint(desenho.x, desenho.y+altura, gradientInfo.cor1,desenho.x, desenho.y, gradientInfo.cor2);
                    break;
                case Graficos.GRADIENTE_ESQUERDA:
                    paint = new GradientPaint(desenho.x+largura, desenho.y, gradientInfo.cor1,desenho.x, desenho.y, gradientInfo.cor2);
                    break;
                case Graficos.GRADIENTE_DIREITA:
                    paint = new GradientPaint(desenho.x, desenho.y, gradientInfo.cor1,desenho.x+largura, desenho.y, gradientInfo.cor2);
                    break;
                default:
                    break;
            }
            graficos.setPaint(paint);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas.utils;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author LITE
 */
public class DicaInterface {
    private final String titulo;
    private final String descricao;
    private final ImageIcon imagem;

    public DicaInterface(String titulo, String descricao, ImageIcon imagem) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public ImageIcon getImagem() {
        return imagem;
    }
    
}

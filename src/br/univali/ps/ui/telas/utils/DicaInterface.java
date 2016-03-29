/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas.utils;

import java.awt.Image;

/**
 *
 * @author LITE
 */
public class DicaInterface {
    private final String titulo;
    private final String descricao;
    private final Image imagem;

    public DicaInterface(String titulo, String descricao, Image imagem) {
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

    public Image getImagem() {
        return imagem;
    }
    
}

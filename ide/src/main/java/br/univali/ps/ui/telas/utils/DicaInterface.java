package br.univali.ps.ui.telas.utils;

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

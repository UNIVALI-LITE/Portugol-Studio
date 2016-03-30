package br.univali.ps.ui.editor;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 03/23/2016
 */
public final class MetadadosDoSimboloSobOCursorDoTeclado
{
    private String nomeDoSimbolo = null;
    private String escopoDoSimbolo = null;
    private int linha;
    private int coluna;
    private boolean existeSimboloSobOCursor = false;

    public String getNomeDoSimbolo()
    {
        return nomeDoSimbolo;
    }

    public void setNomeDoSimbolo(String nomeDoSimbolo)
    {
        this.nomeDoSimbolo = nomeDoSimbolo;
    }

    public String getEscopoDoSimbolo()
    {
        return escopoDoSimbolo;
    }

    public void setEscopoDoSimbolo(String escopoDoSimbolo)
    {
        this.escopoDoSimbolo = escopoDoSimbolo;
    }

    public int getLinha()
    {
        return linha;
    }

    public void setLinha(int linha)
    {
        this.linha = linha;
    }

    public int getColuna()
    {
        return coluna;
    }

    public void setColuna(int coluna)
    {
        this.coluna = coluna;
    }

    public boolean isExisteSimboloSobOCursor()
    {
        return existeSimboloSobOCursor;
    }

    public void setExisteSimboloSobOCursor(boolean existeSimboloSobOCursor)
    {
        this.existeSimboloSobOCursor = existeSimboloSobOCursor;
    }
    
    public boolean isSimboloDeBiblioteca()
    {
        return nomeDoSimbolo != null;
    }
}

package br.univali.ps.ui.carrossel;

/**
 *
 * @author Elieser
 */
class Curso {
    
    private final String _id;
    private final String titulo;
    private final String descricao;
    private final int tempoExibicao;
    private final String caminhoImagem;
    private final String link;

    public Curso() {
        this._id = null;
        this.titulo = null;
        this.descricao = null;
        this.tempoExibicao = 0;
        this.caminhoImagem = null;
        this.link = null;
    }

    
    
    public Curso(String _id, String titulo, String descricao, String link, String caminhoImagem, int tempoExibicao) {
        this._id = _id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.tempoExibicao = tempoExibicao;
        this.caminhoImagem = caminhoImagem;
        this.link = link;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLink() {
        return link;
    }

    public int getTempoExibicao() {
        return tempoExibicao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String get_id() {
        return _id;
    }
}

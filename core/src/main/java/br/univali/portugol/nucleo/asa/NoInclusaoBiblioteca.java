package br.univali.portugol.nucleo.asa;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class NoInclusaoBiblioteca extends No implements NoDeclaracao
{
    private String nome;
    private String alias;
    
    private TrechoCodigoFonte trechoCodigoFonte;
    private TrechoCodigoFonte trechoCodigoFonteNome;
    private TrechoCodigoFonte trechoCodigoFonteAlias;

    public NoInclusaoBiblioteca(String nome) {
        this.nome = nome;
    }

    public NoInclusaoBiblioteca(String nome, String alias) {
        this(nome);
        this.alias = alias;
    }
    
    public String getNome()
    {
        return nome;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }
    
    public TrechoCodigoFonte getTrechoCodigoFonteNome()
    {
        return trechoCodigoFonteNome;
    }

    public TrechoCodigoFonte getTrechoCodigoFonteAlias()
    {
        return trechoCodigoFonteAlias;
    }

    public TrechoCodigoFonte getTrechoCodigoFonte()
    {
        return trechoCodigoFonte;
    }    

    public void setTrechoCodigoFonte(TrechoCodigoFonte trechoCodigoFonte)
    {
        this.trechoCodigoFonte = trechoCodigoFonte;
    }

    public void setTrechoCodigoFonteNome(TrechoCodigoFonte trechoCodigoFonteNome)
    {
        this.trechoCodigoFonteNome = trechoCodigoFonteNome;
    }

    public void setTrechoCodigoFonteAlias(TrechoCodigoFonte trechoCodigoFonteAlias)
    {
        this.trechoCodigoFonteAlias = trechoCodigoFonteAlias;
    }
    
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }    
}

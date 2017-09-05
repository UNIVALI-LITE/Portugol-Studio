package br.univali.portugol.nucleo.bibliotecas.base;

import br.univali.portugol.nucleo.asa.ModoAcesso;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class MetaDadosParametro extends MetaDados
{
    private TipoDado tipoDado;
    private int indice;
    private ModoAcesso modoAcesso;
    private Quantificador quantificador;
    private DocumentacaoParametro documentacaoParametro;

    MetaDadosParametro()
    {
        
    }

    public int getIndice()
    {
        return indice;
    }

    void setIndice(int indice)
    {
        this.indice = indice;
    }

    public TipoDado getTipoDado()
    {
        return tipoDado;
    }

    void setTipoDado(TipoDado tipoDado)
    {
        this.tipoDado = tipoDado;
    }

    public ModoAcesso getModoAcesso()
    {
        return modoAcesso;
    }

    void setModoAcesso(ModoAcesso modoAcesso)
    {
        this.modoAcesso = modoAcesso;
    }

    public Quantificador getQuantificador()
    {
        return quantificador;
    }

    void setQuantificador(Quantificador quantificador)
    {
        this.quantificador = quantificador;
    }
    
    public DocumentacaoParametro getDocumentacaoParametro()
    {
        return documentacaoParametro;
    }

    void setDocumentacaoParametro(DocumentacaoParametro documentacaoParametro)
    {
        this.documentacaoParametro = documentacaoParametro;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof MetaDadosParametro)
        {
            return ((MetaDadosParametro) obj).getNome().equals(this.getNome());
        }
        
        return false;
    }

    @Override
    public int hashCode()
    {
        return 497 + (this.getNome() != null ? this.getNome().hashCode() : 0);
    }
}
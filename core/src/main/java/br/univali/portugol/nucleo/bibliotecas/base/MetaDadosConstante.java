package br.univali.portugol.nucleo.bibliotecas.base;

import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoConstante;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class MetaDadosConstante extends MetaDados
{
    private Object valor;
    private TipoDado tipoDado;
    private Quantificador quantificador;
    private DocumentacaoConstante documentacao;

    MetaDadosConstante()
    {
        
    }

    public Object getValor()
    {
        return valor;
    }

    void setValor(Object valor)
    {
        this.valor = valor;
    }

    public TipoDado getTipoDado()
    {
        return tipoDado;
    }

    void setTipoDado(TipoDado tipoDado)
    {
        this.tipoDado = tipoDado;
    }

    public Quantificador getQuantificador()
    {
        return quantificador;
    }

    void setQuantificador(Quantificador quantificador)
    {
        this.quantificador = quantificador;
    }

    public DocumentacaoConstante getDocumentacao()
    {
        return documentacao;
    }

    void setDocumentacao(DocumentacaoConstante documentacao)
    {
        this.documentacao = documentacao;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof MetaDadosConstante)
        {
            return ((MetaDadosConstante) obj).getNome().equals(this.getNome());
        }
        
        return false;
    }

    @Override
    public int hashCode()
    {
        return 581 + (this.getNome() != null ? this.getNome().hashCode() : 0);
    }    
}

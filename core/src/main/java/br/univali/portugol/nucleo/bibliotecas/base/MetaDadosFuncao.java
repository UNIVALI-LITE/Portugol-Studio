package br.univali.portugol.nucleo.bibliotecas.base;

import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class MetaDadosFuncao extends MetaDados
{
    private TipoDado tipoDado;
    private Quantificador quantificador;
    private DocumentacaoFuncao documentacao;
    private MetaDadosParametros metaDadosParametros;

    MetaDadosFuncao()
    {
        
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
    
    public DocumentacaoFuncao getDocumentacao()
    {
        return documentacao;
    }

    void setDocumentacao(DocumentacaoFuncao documentacao)
    {
        this.documentacao = documentacao;
    }    

    public MetaDadosParametros obterMetaDadosParametros()
    {
        return metaDadosParametros;
    }

    void setMetaDadosParametros(MetaDadosParametros metaDadosParametros)
    {
        this.metaDadosParametros = metaDadosParametros;
    }    

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof MetaDadosFuncao)
        {
            return ((MetaDadosFuncao) obj).getNome().equals(this.getNome());
        }
        
        return false;
    }

    @Override
    public int hashCode()
    {
        return 91 + (this.getNome() != null ? this.getNome().hashCode() : 0);
    }
}

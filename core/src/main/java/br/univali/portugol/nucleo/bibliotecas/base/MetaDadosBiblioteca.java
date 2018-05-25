package br.univali.portugol.nucleo.bibliotecas.base;

import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;

/**
 * 
 * @author Luiz Fernando Noschang
 */
public final class MetaDadosBiblioteca extends MetaDados
{
    private TipoBiblioteca tipo;
    private DocumentacaoBiblioteca documentacao;
    private MetaDadosFuncoes metaDadosFuncoes;
    private MetaDadosConstantes metaDadosConstantes;
    private String pacoteBiblioteca;
    
    MetaDadosBiblioteca()
    {
        
    }

    public String getPacoteBiblioteca() {
        return pacoteBiblioteca;
    }

    public void setPacoteBiblioteca(String pacoteBiblioteca) {
        this.pacoteBiblioteca = pacoteBiblioteca + ".";
    }

    public TipoBiblioteca getTipo()
    {
        return tipo;
    }

    void setTipo(TipoBiblioteca tipo)
    {
        this.tipo = tipo;
    }
    
    public DocumentacaoBiblioteca getDocumentacao()
    {
        return documentacao;
    }

    void setDocumentacao(DocumentacaoBiblioteca documentacao)
    {
        this.documentacao = documentacao;
    }
  
    public MetaDadosFuncoes obterMetaDadosFuncoes()
    {
        return metaDadosFuncoes;
    }

    void setMetaDadosFuncoes(MetaDadosFuncoes metaDadosFuncoes)
    {
        this.metaDadosFuncoes = metaDadosFuncoes;
    }

    public MetaDadosConstantes getMetaDadosConstantes()
    {
        return metaDadosConstantes;
    }

    void setMetaDadosConstantes(MetaDadosConstantes metaDadosConstantes)
    {
        this.metaDadosConstantes = metaDadosConstantes;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof MetaDadosBiblioteca)
        {
            return ((MetaDadosBiblioteca) obj).getNome().equals(this.getNome());
        }
        
        return false;
    }

    @Override
    public int hashCode()
    {
        return 623 + (this.getNome() != null ? this.getNome().hashCode() : 0);
    }
}

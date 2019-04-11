package br.univali.portugol.nucleo.asa;

/**
 * Representa uma referência de variável no código fonte.
 * <p>
 * Uma referência de variável é utilizada para acessar o valor de uma variável.
 * Uma referência de variável é composta apenas pelo nome da variável que está
 * sendo referenciada.
 * <p>
 * Exemplo:  <code><pre>
 *
 *      funcao exemploReferenciaVariavel()
 *      {
 *           inteiro var = 25
 *
 *           escreva("Isto é uma referência de variável: ", var)
 *      }
 *
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoReferenciaVariavel extends NoReferencia<NoDeclaracaoBase>
{
    private boolean variavelDeBiblioteca = false;
    private TipoDado tipoBiblioteca = TipoDado.VAZIO;
    
    private int indiceReferencia = -1;
    
    /**
     *
     * @param nome o nome da variável que está sendo referenciada.
     *
     * @param escopo o escopo da variável sendo referenciada. Se o escopo for
     * nulo, então é uma variável do programa, senão é uma variável de
     * biblioteca.
     * @since 1.0
     */
    public NoReferenciaVariavel(String escopo, String nome)
    {
        super(escopo, nome);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected TrechoCodigoFonte montarTrechoCodigoFonte()
    {

        TrechoCodigoFonte trechoCodigoFonteNome = getTrechoCodigoFonteNome();
        
        if (trechoCodigoFonteNome == null) {
            return NoBloco.TRECHO_NULO;
        }
        
        int tamanhoTexto = 0;

        int linha = trechoCodigoFonteNome.getLinha();
        int coluna = trechoCodigoFonteNome.getColuna();

        tamanhoTexto = tamanhoTexto + trechoCodigoFonteNome.getTamanhoTexto();

        return new TrechoCodigoFonte(linha, coluna, tamanhoTexto);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }

    @Override
    public String toString()
    {
        return getNome();
    }

    @Override
    public TipoDado getTipoResultante()
    {
        if (isVariavelDeBiblioteca())
        {
            return getTipoBiblioteca();
        }
        
        return super.getTipoResultante();
    }

    public TipoDado getTipoBiblioteca()
    {
        return tipoBiblioteca;
    }

    public void setTipoBiblioteca(TipoDado tipoBiblioteca)
    {
        this.tipoBiblioteca = tipoBiblioteca;
    }
    
    public boolean isVariavelDeBiblioteca()
    {
        return variavelDeBiblioteca;
    }

    public void setVariavelDeBiblioteca(boolean variavelDeBiblioteca)
    {
        this.variavelDeBiblioteca = variavelDeBiblioteca;
    }

    public boolean ehPassadoPorReferencia()
    {
        return indiceReferencia >= 0;
    }

    public int getIndiceReferencia()
    {
        return indiceReferencia;
    }

    public void setIndiceReferencia(int indiceReferencia)
    {
        this.indiceReferencia = indiceReferencia;
    }
}

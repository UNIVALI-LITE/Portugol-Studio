package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.programa.Estado;

import java.util.Collections;
import java.util.List;

/**
 * Representa uma chamada de função no código fonte.
 * <p>
 * Exemplo:  <code><pre>
 *
 *      funcao exemploDeChamadaDeFuncao()
 *      {
 *           inteiro fat = fatorial(5) // Usto é uma chamada à função 'fatorial' passando 1 parâmetro!
 *
 *           escreva("Isto é uma chamada à função 'escreva' passando ", 3, " parâmetros!")
 *      }
 *
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 *
 */
public final class NoChamadaFuncao extends NoReferencia<NoDeclaracaoFuncao>
{
    private boolean funcaoDeBiblioteca = false;
    private List<NoExpressao> parametros;
    private TipoDado tipoRetornoBiblioteca = TipoDado.VAZIO;

    /**
     * @param escopo o escopo da função sendo referenciada. Se o escopo for
     * nulo, então é uma função do programa, senão é uma função de biblioteca.
     *
     * @param nome o nome da função que está sendo chamadada.
     *
     * @since 1.0
     */
    public NoChamadaFuncao(String escopo, String nome)
    {
        super(escopo, nome);
    }

    @Override  // sobrescrevendo para retornar tipo mais específico (tipo de retorno covariante)
    public NoDeclaracaoFuncao getOrigemDaReferencia()
    {
        return origemDaReferencia;
    }

    /**
     * Obtém a lista dos parâmetros que estão sendo passados para a função. Os
     * prâmetros podem ser qualquer tipo de expressão, inclusive outras chamadas
     * de função. A lista poderá estar vazia caso a função não necessite de
     * parâmetros.
     *
     * @return a lista de parâmetros passados para a função
     *
     * @since 1.0
     */
    public List<NoExpressao> getParametros()
    {
        if (parametros != null)
            return parametros;
        
        return Collections.EMPTY_LIST;
    }

    /**
     * Define a lista de parâmetros que estão sendo passados para a função.
     *
     * @param parametros lista de parâmetros que estão sendo passados para a
     * função.
     *
     * @since 1.0
     */
    public void setParametros(List<NoExpressao> parametros)
    {
        this.parametros = parametros;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected TrechoCodigoFonte montarTrechoCodigoFonte()
    {
        int tamanhoTexto = 0;

        int linha = getTrechoCodigoFonteNome().getLinha();
        int coluna = getTrechoCodigoFonteNome().getColuna();

        tamanhoTexto = tamanhoTexto + getTrechoCodigoFonteNome().getTamanhoTexto() + 2;

        if (parametros != null)
        {
            for (NoExpressao parametro : parametros)
            {
                tamanhoTexto = tamanhoTexto + parametro.getTrechoCodigoFonte().getTamanhoTexto();
            }
        }

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
    public boolean ehParavel(Estado estado)
    {
        return (estado == Estado.BREAK_POINT && pontoDeParadaEstaAtivo()) || estado == Estado.STEP_OVER;
    }

    @Override
    public TipoDado getTipoResultante()
    {
        if (isFuncaoDeBiblioteca())
        {
            return getTipoRetornoBiblioteca();
        }
        
        return super.getTipoResultante();
    }

    public boolean isFuncaoDeBiblioteca()
    {
        return funcaoDeBiblioteca;
    }

    public void setFuncaoDeBiblioteca(boolean funcaoDeBiblioteca)
    {
        this.funcaoDeBiblioteca = funcaoDeBiblioteca;
    }

    public void setTipoRetornoBiblioteca(TipoDado tipoRetornoBiblioteca)
    {
        this.tipoRetornoBiblioteca = tipoRetornoBiblioteca;
    }

    public TipoDado getTipoRetornoBiblioteca()
    {
        return tipoRetornoBiblioteca;
    }
}

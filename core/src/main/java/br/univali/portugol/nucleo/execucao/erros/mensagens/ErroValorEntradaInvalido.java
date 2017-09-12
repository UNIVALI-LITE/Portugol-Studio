package br.univali.portugol.nucleo.execucao.erros.mensagens;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author Luiz Fernando Noschangg
 * @since 2.1
 */
public final class ErroValorEntradaInvalido extends ErroExecucao
{
	private static final long serialVersionUID = 1L;
	
	private final TipoDado tipoDado;
    
    public ErroValorEntradaInvalido(TipoDado tipoDado, int linha, int coluna)
    {
        this.tipoDado = tipoDado;
        this.setLinha(linha);
        this.setColuna(coluna);
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("A entrada de dados do programa esperava um valor do tipo '%s', mas nenhum valor foi informado ou o valor informado é de outro tipo", tipoDado.getNome());
    }
}

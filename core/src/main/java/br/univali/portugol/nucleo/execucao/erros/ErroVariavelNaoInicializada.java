package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroVariavelNaoInicializada extends ErroExecucao
{
    private final NoReferenciaVariavel referencia;
    private String codigo = "ErroExecucao.ErroVariavelNaoInicializada";

    public ErroVariavelNaoInicializada(NoReferenciaVariavel referencia)
    {
        this.referencia = referencia;
        this.setLinha(referencia.getTrechoCodigoFonteNome().getLinha());
        this.setColuna(referencia.getTrechoCodigoFonteNome().getColuna());
        super.setCodigo(codigo);
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("A variável '%s' não foi inicializada", referencia.getNome());
    }
}

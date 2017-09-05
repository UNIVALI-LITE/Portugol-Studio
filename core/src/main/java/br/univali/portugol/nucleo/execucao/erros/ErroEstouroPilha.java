package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author Fillipi Pelz
 * @author Luiz Fernando Noschang
 *
 */
public final class ErroEstouroPilha extends ErroExecucao
{
    private final NoChamadaFuncao chamadaFuncaoCausadora;
    
    public ErroEstouroPilha(NoChamadaFuncao chamadaFuncaoCausadora)
    {
        this.chamadaFuncaoCausadora = chamadaFuncaoCausadora;
        this.setLinha(chamadaFuncaoCausadora.getTrechoCodigoFonteNome().getLinha());
        this.setColuna(chamadaFuncaoCausadora.getTrechoCodigoFonteNome().getColuna());
    }
    
    @Override
    protected String construirMensagem()
    {
        return String.format("Ocorreu um ESTOURO DE PILHA. A função '%s' foi chamada recursivamente sem que uma condição de parada pudesse ser satisfeita", chamadaFuncaoCausadora.getNome());
    }
}

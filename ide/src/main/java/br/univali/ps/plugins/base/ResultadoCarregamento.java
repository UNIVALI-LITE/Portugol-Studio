package br.univali.ps.plugins.base;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ResultadoCarregamento
{
    private final List<ErroCarregamentoPlugin> erros = new ArrayList<>();

    public ResultadoCarregamento()
    {

    }

    void adicionarErro(ErroCarregamentoPlugin erro)
    {
        erros.add(erro);
    }

    public boolean contemErros()
    {
        return !erros.isEmpty();
    }

    public List<ErroCarregamentoPlugin> getErros()
    {
        return erros;
    }
}

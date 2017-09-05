package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroParaEsperaCondicao extends ErroSintatico
{
    public ErroParaEsperaCondicao(int linha, int coluna)
    {
        super(linha, coluna,"ErroSintatico.ErroParaEsperaCondicao");
    }

    @Override
    protected String construirMensagem()
    {
        return "O comando \"para\" necessita ao menos de uma condição de parada. Utilize a seguinte construção para corrigir o problema: \"para( ; <condicao> ; ){ <comandos> }\"";
    }
}

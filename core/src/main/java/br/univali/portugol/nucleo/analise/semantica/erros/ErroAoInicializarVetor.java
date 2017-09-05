package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.GeradorDeExemplosDeInicializacao;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.simbolos.Vetor;

/**
 *
 * @author Fillipi Domingos Pelz
 * @author Luiz Fernando Noschang
 */
public class ErroAoInicializarVetor extends ErroSemantico
{
    private final int tamanhoVetor;
    private final Vetor vetor;

    public ErroAoInicializarVetor(Vetor vetor, TrechoCodigoFonte trechoCodigoFonte, int tamanhoVetor)
    {
        super(trechoCodigoFonte, "ErroSemantico.ErroAoInicializarVetor");
        this.vetor = vetor;
        this.tamanhoVetor = tamanhoVetor;
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("O vetor '%s' deve ser inicializado com um vetor literal. Exemplo: ", vetor.getNome()));
        builder.append(GeradorDeExemplosDeInicializacao.gerarExemploDeInicializacaoDeVetor(vetor, tamanhoVetor));
        

        return builder.toString();
    }
}
